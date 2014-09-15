package tv.mineinthebox.bukkit.essentials.greylist;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.GreyListCause;
import tv.mineinthebox.bukkit.essentials.enums.LogType;
import tv.mineinthebox.bukkit.essentials.events.customevents.PlayerGreyListedEvent;
import tv.mineinthebox.bukkit.essentials.hook.Hooks;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsOfflinePlayer;

public class GreyListServer implements Runnable {
	
	/*
	 this is a example how the greylist works through php, we can use the json which gets returned by jetty in this way.
	 currently we have 3 possible return types:
	 
	 success = when the player change from group,
	 greylisted = when the player already is greylisted
	 notexist = if the player has never played before

	 php callback example:

	 <?php
		$string = file_get_contents("http://127.0.0.1:8001/adduser/Xeph0re");
		$json = json_decode($string, true);
		$args = $json["xEssentials"]["response"];
		if($args == "success") {
			echo "player has been promoted";
		} else if($args == "greylisted") {
			echo "player already is greylisted";
		} else if($args == "notexist") {
			echo "player has never played before";
		}
	?>
	 */
	
	private final int port;
	private volatile ServerSocket server;
	private volatile Thread thread;
	
	public GreyListServer(int port) {
		this.port = port;
	}
	
	public boolean isRunning() {
		if(!(thread instanceof Thread)) {
			return false;
		} else {
			return true;
		}
	}
	
	@SuppressWarnings({ "deprecation" })
	public void stop() {
		if(isRunning()) {
			xEssentials.getPlugin().log("stopping greylist callback server service at port " + port, LogType.INFO);
			thread.stop();
			this.thread = null;
			try {
				server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void start() {
		if(!(thread instanceof Thread)) {
			xEssentials.getPlugin().log("starting greylist callback server service at port " + port, LogType.INFO);
			try {
				this.server = new ServerSocket(port);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.thread = new Thread(this);
			this.thread.start();
		}
	}

	@Override
	public void run() {
		while(true) {
			try {
				Socket client = server.accept();
				
				String uri;
				String line;
				String user;
				
				InputStream stream = client.getInputStream();
				InputStreamReader iread = new InputStreamReader(stream);
				BufferedReader reader = new BufferedReader(iread);
				DataOutputStream response = new DataOutputStream(client.getOutputStream());
				
				while((line = reader.readLine()) != null) {
					if(line.startsWith("GET".toUpperCase())) {
						//we've found the request, now we need to validate that request, and sent a json back in return.
						//if the method is not returned at all then we return a 404 and disconnect the client.
						
						uri = line.replace("GET ", "").replace(" HTTP/1.1", "");
						
						if(uri.startsWith("/adduser/") && uri.length() > "/adduser/".length()) {
							//valid url lets go futher to check if this is a player.
							
							response.writeBytes("HTTP/1.1 200 OK\r\n");
							response.writeBytes("Content-Type: application/json\r\n");
							
							user = uri.substring("/adduser/".length());
							
							if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(user)) {
								//user is valid.
								
								xEssentialsOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(user);
								if(!off.isGreyListed()) {
									//user is not greylisted before.
									response.writeBytes("\n");
									response.flush();
									response.writeBytes("{\"xEssentials\": {\"response\": \"success\"}}\n");
									response.flush();
									off.setGreyListed(true);
									if(off.getPlayer() instanceof Player) {
										off.getPlayer().sendMessage(ChatColor.GREEN + "you are successfully promoted to " + Configuration.getGrayListConfig().getGroup());
										if(Hooks.isVaultEnabled()) {
											String oldGroup = xEssentials.getManagers().getVaultManager().getGroup(Bukkit.getWorlds().get(0), user);
											String newgroup = Configuration.getGrayListConfig().getGroup();
											xEssentials.getManagers().getVaultManager().setGroup(Bukkit.getWorlds().get(0), off.getUser(), newgroup);
											Bukkit.getPluginManager().callEvent(new PlayerGreyListedEvent(off.getPlayer(), newgroup, oldGroup, GreyListCause.SITE));
										}
									} else {
										if(Hooks.isVaultEnabled()) {
											String oldGroup = xEssentials.getManagers().getVaultManager().getGroup(Bukkit.getWorlds().get(0), user);
											String newgroup = Configuration.getGrayListConfig().getGroup();
											xEssentials.getManagers().getVaultManager().setGroup(Bukkit.getWorlds().get(0), off.getUser(), newgroup);
											Bukkit.getPluginManager().callEvent(new PlayerGreyListedEvent(off.getPlayer(), newgroup, oldGroup, GreyListCause.SITE));
										}
									}
								} else {
									//user already was greylisted
									response.writeBytes("\n");
									response.flush();
									response.writeBytes("{\"xEssentials\": {\"response\": \"greylisted\"}}\n");
									response.flush();
								}
							} else {
								//user is invalid and has never played before.
								response.writeBytes("\n");
								response.flush();
								response.writeBytes("{\"xEssentials\": {\"response\": \"notexist\"}}\n");
								response.flush();
							}
						} else {
							//invalid url.
							response.writeBytes("HTTP/1.1 404 Not Found\r\n");
						}
					}
				}
				response.close();
				reader.close();
				iread.close();
				stream.close();
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
