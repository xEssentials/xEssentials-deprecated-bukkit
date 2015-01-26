package tv.mineinthebox.essentials.commands;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.enums.ServiceType;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;

public class CmdLookup {
	
	private final xEssentials pl;
	
	public CmdLookup(xEssentials pl) {
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("lookup")) {
			if(sender.hasPermission(PermissionKey.CMD_LOOKUP.getPermission())) {
				if(args.length == 0) {
					sender.sendMessage(ChatColor.GOLD + ".oO___[lookup]___Oo.");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/lookup help " + ChatColor.WHITE + ": shows help");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/lookup services " + ChatColor.WHITE + ": get a list of all services");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/lookup <player> " + ChatColor.WHITE + ": lookup a player on every service");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/lookup <service> <player> " + ChatColor.WHITE + ": lookup a player on a service");
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[lookup]___Oo.");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/lookup help " + ChatColor.WHITE + ": shows help");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/lookup services " + ChatColor.WHITE + ": get a list of all services");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/lookup <player> " + ChatColor.WHITE + ": lookup a player on every service");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/lookup <service> <player> " + ChatColor.WHITE + ": lookup a player on a service");
					} else if(args[0].equalsIgnoreCase("services")) {
						StringBuilder build = new StringBuilder();
						for(int i = 0; i < ServiceType.values().length;i++) {
							ServiceType type = ServiceType.values()[i];
							if(i == (ServiceType.values().length-1)) {
								build.append(type.getService());
							} else {
								build.append(type.getService() + ", ");
							}
						}
						sender.sendMessage(ChatColor.GRAY + "services: " + build.toString());
					} else {
						if(pl.getManagers().getPlayerManager().isEssentialsPlayer(args[0])) {
							XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
							try {
								EnumMap<ServiceType, List<String>> messages = getLookupReasons(off.getUser());
								for(ServiceType type : ServiceType.values()) {
									if(messages.containsKey(type)) {
										sender.sendMessage(ChatColor.GOLD + ".oO___["+type.getService()+"]___Oo.");
										for(String s : messages.get(type)) {
											String[] argss = s.split(":");
											sender.sendMessage(argss[0] + ": " + ChatColor.GRAY + argss[1]);
										}
									} else {
										sender.sendMessage(ChatColor.GOLD + ".oO___["+type.getService()+"]___Oo.");
										sender.sendMessage(ChatColor.GRAY + "no bans found for this service.");
									}
								}
							} catch (Exception e) {
								xEssentials.log("could not lookup ban status of player " + off.getUser() + " on api.fishbans.com", LogType.SEVERE);
							}
						} else {
							try {
								EnumMap<ServiceType, List<String>> messages = getLookupReasons(args[0]);
								for(ServiceType type : ServiceType.values()) {
									if(messages.containsKey(type)) {
										sender.sendMessage(ChatColor.GOLD + ".oO___["+type.getService()+"]___Oo.");
										for(String s : messages.get(type)) {
											String[] argss = s.split(":");
											sender.sendMessage(argss[0] + ": " + ChatColor.GRAY + argss[1]);
										}
									} else {
										sender.sendMessage(ChatColor.GOLD + ".oO___["+type.getService()+"]___Oo.");
										sender.sendMessage(ChatColor.GRAY + "no bans found for this service.");
									}
								}
							} catch (Exception e) {
								xEssentials.log("could not lookup ban status of player " + args[0] + " on api.fishbans.com", LogType.SEVERE);
							}
						}
					}
				} else if(args.length == 2) {
					try {
						if(ServiceType.valueOf(args[0].toUpperCase()) instanceof ServiceType) {
							ServiceType type = ServiceType.valueOf(args[0].toUpperCase());
							try {
								EnumMap<ServiceType, List<String>> map = getLookupReasons(args[1], type);
								List<String> list = new ArrayList<String>(map.get(type));
								if(!list.isEmpty()) {
									sender.sendMessage(ChatColor.GOLD + ".oO___["+type.getService()+"]___Oo.");
									for(String s : list) {
										String[] argss = s.split(":");
										sender.sendMessage(argss[0] + ": " + ChatColor.GRAY + argss[1]);
									}
								} else {
									sender.sendMessage(ChatColor.RED + "this player is not banned on this service!");
								}
							} catch (Exception e) {
								e.printStackTrace();
								xEssentials.log("could not lookup ban status of player " + args[1] + " on api.fishbans.com", LogType.SEVERE);
							}
						}
					} catch(IllegalArgumentException r) {
						sender.sendMessage(ChatColor.RED + "invalid service name!");
					}
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}


	/**
	 * @author xize
	 * @param returns all the ban reasons of this player!
	 * @return EnumMap<ServiceType, List<String>()
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public EnumMap<ServiceType, List<String>> getLookupReasons(String name) throws Exception {
		EnumMap<ServiceType, List<String>> map = new EnumMap<ServiceType, List<String>>(ServiceType.class);
		Callable<JSONObject> args = new BanServices(name, pl);
		JSONObject obj = args.call();
		List<String> bans = new ArrayList<String>();
		if(obj.containsKey("bans")) {
			if(((JSONObject)obj.get("bans")).containsKey("service")) {
				JSONObject service = (JSONObject) ((JSONObject) obj.get("bans")).get("service");
				for(ServiceType type : ServiceType.values()) {
					if(service.containsKey(type.getService())) {

						JSONObject ser = (JSONObject)service.get(type.getService());

						Object object = ser.get("ban_info");

						if(object instanceof JSONObject) {
							JSONObject ob = (JSONObject) object;
							Iterator<Entry<String, String>> it = (Iterator<Entry<String, String>>) ob.entrySet().iterator();
							while(it.hasNext()) {
								Entry<String, String> entry = it.next();
								bans.add(entry.getKey()+":"+entry.getValue());
							}	
						}
					}
					map.put(type, bans);
				}
			}
		}
		return map;
	}

	/**
	 * @author xize
	 * @param returns all the ban reasons of this player!
	 * @return EnumMap<ServiceType, List<String>()
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public EnumMap<ServiceType, List<String>> getLookupReasons(String name, ServiceType type) throws Exception {
		EnumMap<ServiceType, List<String>> map = new EnumMap<ServiceType, List<String>>(ServiceType.class);
		Callable<JSONObject> args = new BanServices(name, pl);
		JSONObject obj = args.call();
		List<String> bans = new ArrayList<String>();
		if(obj.containsKey("bans")) {
			if(((JSONObject)obj.get("bans")).containsKey("service")) {
				JSONObject service = (JSONObject) ((JSONObject) obj.get("bans")).get("service");
				if(service.containsKey(type.getService())) {

					JSONObject ser = (JSONObject)service.get(type.getService());

					Object object = ser.get("ban_info");

					if(object instanceof JSONObject) {
						JSONObject ob = (JSONObject) object;
						Iterator<Entry<String, String>> it = (Iterator<Entry<String, String>>) ob.entrySet().iterator();
						while(it.hasNext()) {
							Entry<String, String> entry = it.next();
							bans.add(entry.getKey()+":"+entry.getValue());
						}
					}
				}

			}
		}
		map.put(type, bans);
		return map;
	}

}

class BanServices implements Callable<JSONObject> {

	private final String api = "http://api.fishbans.com/bans/";
	private final String player;
	private final xEssentials pl;

	public BanServices(String player, xEssentials pl) {
		this.player = player;
		this.pl = pl;
	}

	@Override
	public JSONObject call() throws Exception {
		JSONParser parser = new JSONParser();
		URL url = new URL(api + player);
		HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
		httpcon.addRequestProperty("User-Agent", pl.getName() + " " + pl.getDescription().getVersion() + " ban checker (By xize)");
		InputStreamReader rd = new InputStreamReader(httpcon.getInputStream());
		BufferedReader reader = new BufferedReader(rd);
		JSONObject json = (JSONObject) parser.parse(reader);
		reader.close();
		rd.close();
		httpcon.disconnect();
		return json;
	}
}
