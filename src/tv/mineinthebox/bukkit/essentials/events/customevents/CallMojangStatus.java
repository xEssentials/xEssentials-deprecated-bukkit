package tv.mineinthebox.bukkit.essentials.events.customevents;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.LogType;
import tv.mineinthebox.bukkit.essentials.enums.MojangStatusResponse;
import tv.mineinthebox.bukkit.essentials.instances.MojangStatus;

public class CallMojangStatus {
	
	private volatile boolean stop = false;

	private void startMojangCheck() {
		new BukkitRunnable() {

			private URL url;
			private HttpURLConnection httpcon;
			private InputStreamReader input;
			private BufferedReader reader;
			private JSONArray json;
			private MojangStatus status;

			@Override
			public void run() {
				if(stop) {
					this.cancel();
				}
				JSONParser parser = new JSONParser();
				try {
					this.url = new URL("http://status.mojang.com/check");
					this.httpcon = (HttpURLConnection) url.openConnection();
					this.httpcon.addRequestProperty("User-Agent", xEssentials.getPlugin().getName() + " " + xEssentials.getPlugin().getDescription().getVersion() + " status checker (By xize)");
					this.httpcon.setUseCaches(false);
					this.httpcon.setRequestMethod("GET");
					this.httpcon.connect();
					this.input = new InputStreamReader(httpcon.getInputStream());
					this.reader = new BufferedReader(input);
					this.json = (JSONArray) parser.parse(reader);
					HashMap<String, Boolean> bols = deSerializeJson(json);
					MojangStatus stat = new MojangStatus(bols.get("login"), bols.get("session"), bols.get("skins"));
					if(this.status != null) {
						if(this.status.isLoginServerActive() != stat.isLoginServerActive()) {
							if(stat.isLoginServerActive()) {
								Bukkit.getPluginManager().callEvent(new MojangStatusEvent(stat, MojangStatusResponse.LOGIN_SERVER_ACTIVE));
							} else {
								Bukkit.getPluginManager().callEvent(new MojangStatusEvent(stat, MojangStatusResponse.LOGIN_SERVER_DOWN));
							}
						}
						if(this.status.isSessionServerActive() != stat.isSessionServerActive()) {
							if(stat.isSessionServerActive()) {
								Bukkit.getPluginManager().callEvent(new MojangStatusEvent(stat, MojangStatusResponse.SESSION_SERVER_ACTIVE));
							} else {
								Bukkit.getPluginManager().callEvent(new MojangStatusEvent(stat, MojangStatusResponse.SESSION_SERVER_DOWN));
							}
						}
						if(this.status.isSkinServerActive() != stat.isSkinServerActive()) {
							if(stat.isSkinServerActive()) {
								Bukkit.getPluginManager().callEvent(new MojangStatusEvent(stat, MojangStatusResponse.SKIN_SERVER_UP));
							} else {
								Bukkit.getPluginManager().callEvent(new MojangStatusEvent(stat, MojangStatusResponse.SKIN_SERVER_DOWN));
							}
						}
						this.status = stat;
					} else {
						Bukkit.getPluginManager().callEvent(new MojangStatusEvent(stat, MojangStatusResponse.UNKNOWN));
						this.status = stat;
					}
				} catch(UnknownHostException e) {
					xEssentials.getPlugin().log("couldn't connect to status.mojang.com, is the server down or your connection?", LogType.SEVERE);
				} catch (FileNotFoundException e) {
					xEssentials.getPlugin().log("couldn't find the current page, please talk to xize the developer of xEssentials for support!", LogType.SEVERE);
				} catch (IOException e) {
					xEssentials.getPlugin().log("couldn't connect to status.mojang.com, the website seems down", LogType.SEVERE);
				} catch (ParseException e) {
					xEssentials.getPlugin().log("detected malformed json stats from status.mojang.com", LogType.SEVERE);
					e.printStackTrace();
				} finally {
					try {
						reader.close();
						input.close();
						httpcon.disconnect();
					} catch(Exception e) {}
				}
			}

		}.runTaskTimerAsynchronously(xEssentials.getPlugin(), 100L, 4000L); //default 100L, 4000L
	}

	/**
	 * @author xize
	 * @param deserialize a JsonArray and split it to a normal boolean array.
	 * @param for as example you can declare it as the following output:
	 * @param bol[0] - means login server
	 * @param bol[1] - means session server
	 * @param bol[2] - means skin server 
	 * @return Boolean[]
	 */
	private HashMap<String, Boolean> deSerializeJson(JSONArray json) {
		HashMap<String, Boolean> list = new HashMap<String, Boolean>();
		String login = ((JSONObject) json.get(1)).toJSONString();
		String session = ((JSONObject) json.get(2)).toJSONString();
		String skins = ((JSONObject) json.get(5)).toJSONString();
		if(login.contains("green")) {
			list.put("login", true);
		} else {
			list.put("login", false);
		}
		if(session.contains("green")) {
			list.put("session", true);
		} else {
			list.put("session", false);
		}
		if(skins.contains("green")) {
			list.put("skins", true);
		} else {
			list.put("skins", false);
		}
		return list;
	}
	
	public boolean isRunning() {
		return !stop;
	}
	
	public void stop() {
		this.stop = true;
	}
	
	public void start() {
		this.startMojangCheck();
	}
}
