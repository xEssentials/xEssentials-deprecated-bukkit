package tv.mineinthebox.bukkit.essentials.instances;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import org.bukkit.ChatColor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.BanType;
import tv.mineinthebox.bukkit.essentials.enums.ServiceType;
import tv.mineinthebox.bukkit.essentials.interfaces.XOfflinePlayer;


public class AlternateAccount {

	private String[] args;
	private XOfflinePlayer[] off;
	private String name;
	private JSONObject json;

	public AlternateAccount(xEssentialsPlayer xp) {
		this.name = xp.getUser();
		List<String> list = new ArrayList<String>();
		List<XOfflinePlayer> list2 = new ArrayList<XOfflinePlayer>();
		for(XOfflinePlayer offs : xEssentials.getManagers().getPlayerManager().getOfflinePlayers()) {
			if(!xp.getUser().equalsIgnoreCase(offs.getUser())) {
				if(xp.getIp().equalsIgnoreCase(offs.getIp())) {
					list.add(offs.getUser());
					list2.add(offs);
				}
			}
		}
		String[] alts = list.toArray(new String[list.size()]);
		xEssentialsOfflinePlayer[] alts2 = list2.toArray(new xEssentialsOfflinePlayer[list2.size()]);
		this.off = alts2;
		this.args = alts;
	}

	public AlternateAccount(xEssentialsOfflinePlayer xp) {
		this.name = xp.getUser();
		List<String> list = new ArrayList<String>();
		List<XOfflinePlayer> list2 = new ArrayList<XOfflinePlayer>();
		for(XOfflinePlayer offs : xEssentials.getManagers().getPlayerManager().getOfflinePlayers()) {
			if(!xp.getUser().equalsIgnoreCase(offs.getUser())) {
				if(xp.getIp().equalsIgnoreCase(offs.getIp())) {
					list.add(offs.getUser());
					list2.add(offs);
				}
			}
		}
		String[] alts = list.toArray(new String[list.size()]);
		XOfflinePlayer[] alts2 = list2.toArray(new XOfflinePlayer[list2.size()]);
		this.off = alts2;
		this.args = alts;
	}

	/**
	 * @author xize
	 * @param gets the alternate names as Array
	 * @return String[]
	 */
	public String[] getAltNames() {
		return args;
	}

	/**
	 * @author xize
	 * @param gets the alternate names as a single unmodified string
	 * @return String
	 */
	public String getAltsToString() {
		String array = Arrays.toString(getAltNames()).replace("[", "").replace("]", "");
		return array;
	}

	/**
	 * @author xize
	 * @param gets the alt list in a stylish way!
	 * @return String
	 */
	public String getAltsDetailed() {
		List<String> list = new ArrayList<String>();
		for(XOfflinePlayer offliner : off) {
			if(offliner.isPermBanned()) {
				list.add(BanType.BANNED.getPrefix()+ChatColor.GRAY+offliner.getUser());
			} else if(offliner.isTempBanned()) {
				list.add(BanType.TEMPBANNED.getPrefix()+ChatColor.GRAY+offliner.getUser());
			} else if(offliner.isBannedBefore()) {
				list.add(BanType.BANNED_BEFORE.getPrefix()+ChatColor.GRAY+offliner.getUser());
			} else {
				list.add(BanType.NEVER_BANNED.getPrefix()+ChatColor.GRAY+offliner.getUser());
			}
		}
		String[] args = list.toArray(new String[list.size()]);
		return ChatColor.GRAY + Arrays.toString(args).replace("[", "").replace("]", "");
	}

	/**
	 * @author xize
	 * @param returns true if the player is listed on any service listed on fishbans!
	 * @return Boolean
	 */
	public boolean isListedOnService() {
		try {
			if(getLookupStatus() != null) {
				return true;
			}
		} catch (Exception e) {}
		return false;
	}

	/**
	 * @author xize
	 * @param returns the total amount of bans of all the fishbans services!
	 * @return Long
	 * @throws Exception
	 */
	public Long getTotalLookupBans() throws Exception {
		return (Long) getLookupStatus().get("totalbans");
	}

	/**
	 * @author xize
	 * @param returns a detailed list on which services the player has been banned
	 * @return String
	 * @throws Exception
	 */
	public String getServiceLookupResultMessage() throws Exception {
		JSONObject services = (JSONObject)getLookupStatus().get("service");
		StringBuilder build = new StringBuilder();
		for(int i = 0; i < ServiceType.values().length; i++) {
			ServiceType type = ServiceType.values()[i];
			if(i == (ServiceType.values().length-1)) {
				if(services.containsKey(type.getService())) {
					Long serviceTotal = (Long)services.get(type.getService());
					build.append(ChatColor.GRAY + type.getService() + ChatColor.RED + "(" + serviceTotal + ")");
				} else {
					build.append(ChatColor.GRAY + type.getService() + ChatColor.RED + "(0)");
				}
			} else {
				if(services.containsKey(type.getService())) {
					Long serviceTotal = (Long)services.get(type.getService());
					build.append(ChatColor.GRAY + type.getService() + ChatColor.RED + "(" + serviceTotal + ")" + ChatColor.GRAY + ", ");
				} else {
					build.append(ChatColor.GRAY + type.getService() + ChatColor.RED + "(0)" + ChatColor.GRAY + ", ");
				}
			}
		}
		return build.toString();
	}
	
	/**
	 * @author xize
	 * @param returns all the ban reasons of this player!
	 * @return EnumMap<ServiceType, List<String>()
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public EnumMap<ServiceType, List<String>> getLookupReasons() throws Exception {
		EnumMap<ServiceType, List<String>> map = new EnumMap<ServiceType, List<String>>(ServiceType.class);
		Callable<JSONObject> args = new BanServices(name);
		JSONObject obj = args.call();
		if(obj.containsKey("bans")) {
			if(((JSONObject)obj.get("bans")).containsKey("service")) {
				JSONObject service = (JSONObject) ((JSONObject) obj.get("bans")).get("service");
				for(ServiceType type : ServiceType.values()) {
					if(service.containsKey(type.getService())) {
						List<String> bans = new ArrayList<String>();
						
						JSONObject ser = (JSONObject)service.get(type.getService());
						
						Object object = ser.get("ban_info");
						
						if(object instanceof JSONObject) {
							JSONObject ob = (JSONObject) object;
							Iterator<Entry<String, String>> it = (Iterator<Entry<String, String>>) ob.entrySet().iterator();
							while(it.hasNext()) {
							Entry<String, String> entry = it.next();
							bans.add(entry.getKey()+":"+entry.getValue());
							}
							map.put(type, bans);	
						}
					}
				}
			}
		}
		return map;
	}

	private JSONObject getLookupStatus() throws Exception {
		if(json instanceof JSONObject) {
			return json;
		} else {
			Callable<JSONObject> args = new BanStats(name);
			return (JSONObject) args.call().get("stats");	
		}
	}
}

class BanStats implements Callable<JSONObject> {

	private final String api = "http://api.fishbans.com/stats/";
	private final String player;

	public BanStats(String player) {
		this.player = player;
	}

	@Override
	public JSONObject call() throws Exception {
		JSONParser parser = new JSONParser();
		URL url = new URL(api + player);
		HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
		httpcon.addRequestProperty("User-Agent", xEssentials.getPlugin().getName() + " " + xEssentials.getPlugin().getDescription().getVersion() + " ban checker (By xize)");
		InputStreamReader rd = new InputStreamReader(httpcon.getInputStream());
		BufferedReader reader = new BufferedReader(rd);
		JSONObject json = (JSONObject) parser.parse(reader);
		reader.close();
		rd.close();
		httpcon.disconnect();
		return json;
	}
}

class BanServices implements Callable<JSONObject> {

	private final String api = "http://api.fishbans.com/bans/";
	private final String player;

	public BanServices(String player) {
		this.player = player;
	}

	@Override
	public JSONObject call() throws Exception {
		JSONParser parser = new JSONParser();
		URL url = new URL(api + player);
		HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
		httpcon.addRequestProperty("User-Agent", xEssentials.getPlugin().getName() + " " + xEssentials.getPlugin().getDescription().getVersion() + " ban checker (By xize)");
		InputStreamReader rd = new InputStreamReader(httpcon.getInputStream());
		BufferedReader reader = new BufferedReader(rd);
		JSONObject json = (JSONObject) parser.parse(reader);
		reader.close();
		rd.close();
		httpcon.disconnect();
		return json;
	}
}
