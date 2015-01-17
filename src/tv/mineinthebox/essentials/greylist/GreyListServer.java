package tv.mineinthebox.essentials.greylist;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.GreyListCause;
import tv.mineinthebox.essentials.enums.LogType;
import tv.mineinthebox.essentials.events.customevents.PlayerGreyListedEvent;
import tv.mineinthebox.essentials.hook.Hooks;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;
import tv.mineinthebox.simpleserver.MimeType;
import tv.mineinthebox.simpleserver.events.SimpleServerEvent;
import tv.mineinthebox.simpleserver.events.manager.ServerEvent;
import tv.mineinthebox.simpleserver.events.manager.ServerListener;

public class GreyListServer implements ServerListener {
	
	@ServerEvent
	public void onRequest(SimpleServerEvent e) {
		if(e.isGetRequest()) {
			if(e.getUri().startsWith("/adduser/") && e.getUri().length() > "/adduser/".length()) {
				String user = e.getUri().substring("/adduser/".length());
				if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(user)) {
					XOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(user);
					if(!off.isGreyListed()) {
						e.setMimeType(MimeType.MIME_JSON);
						e.setContent("{\"xEssentials\": {\"response\": \"success\"}}\n\r".getBytes());
						off.setGreyListed(true);
						if(Configuration.getDebugConfig().isEnabled()) {
							xEssentials.getPlugin().log("player: " + off.getUser() + " has been greylisted, result: " + off.isGreyListed() + " if this is true its wrong", LogType.DEBUG);	
						}
						if(off instanceof XPlayer) {
							off.getPlayer().sendMessage(ChatColor.GREEN + "you are successfully promoted to " + Configuration.getGrayListConfig().getGroup());
							if(Hooks.isVaultPermissionsEnabled()) {
								String oldGroup = xEssentials.getManagers().getVaultManager().getGroup(off.getPlayer().getWorld(), off.getUser());
								String newgroup = Configuration.getGrayListConfig().getGroup();
								xEssentials.getManagers().getVaultManager().setGroup(off.getPlayer().getWorld(), off.getUser(), newgroup);
								Bukkit.getPluginManager().callEvent(new PlayerGreyListedEvent(off.getPlayer(), newgroup, oldGroup, GreyListCause.SITE));
							}
						} else {
							if(Hooks.isVaultPermissionsEnabled()) {
								String oldGroup = xEssentials.getManagers().getVaultManager().getGroup(off.getLastLocation().getWorld(), user);
								String newgroup = Configuration.getGrayListConfig().getGroup();
								xEssentials.getManagers().getVaultManager().setGroup(off.getLastLocation().getWorld(), off.getUser(), newgroup);
								Bukkit.getPluginManager().callEvent(new PlayerGreyListedEvent(off.getPlayer(), newgroup, oldGroup, GreyListCause.SITE));
							}
						}
					} else {
						if(Configuration.getDebugConfig().isEnabled()) {
							xEssentials.getPlugin().log("player: " + off.getUser() + " was already greylisted", LogType.DEBUG);	
						}
						e.setMimeType(MimeType.MIME_JSON);
						e.setContent("{\"xEssentials\": {\"response\": \"greylisted\"}}\n\r".getBytes());
					}
				} else {
					if(Configuration.getDebugConfig().isEnabled()) {
						xEssentials.getPlugin().log("player: " + user + " does not exist for the greylist request /adduser/"+user, LogType.DEBUG);	
					}
					e.setMimeType(MimeType.MIME_JSON);
					e.setContent("{\"xEssentials\": {\"response\": \"notexist\"}}\n\r".getBytes());
				}
			} else {
				e.setCancelled(true);
			}
		}
	}

}
