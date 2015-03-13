package tv.mineinthebox.essentials.greylist;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.GreyListCause;
import tv.mineinthebox.essentials.enums.LogType;
import tv.mineinthebox.essentials.events.customevents.PlayerGreyListedEvent;
import tv.mineinthebox.essentials.hook.Hooks;
import tv.mineinthebox.essentials.interfaces.EventTemplate;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;
import tv.mineinthebox.simpleserver.MimeType;
import tv.mineinthebox.simpleserver.events.SimpleServerEvent;
import tv.mineinthebox.simpleserver.events.manager.ServerEvent;
import tv.mineinthebox.simpleserver.events.manager.ServerListener;

public class GreyListServer extends EventTemplate implements ServerListener {
	
	public GreyListServer(xEssentials pl) {
		super(pl, "GreyList");
	}
	
	@ServerEvent
	public void onRequest(SimpleServerEvent e) {
		if(e.isGetRequest()) {
			if(e.getUri().startsWith("/adduser/") && e.getUri().length() > "/adduser/".length()) {
				String user = e.getUri().substring("/adduser/".length());
				if(pl.getManagers().getPlayerManager().isEssentialsPlayer(user)) {
					XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(user);
					if(!off.isGreyListed()) {
						e.setMimeType(MimeType.MIME_JSON);
						e.setContent("{\"xEssentials\": {\"response\": \"success\"}}\n\r".getBytes());
						if(pl.getConfiguration().getDebugConfig().isEnabled()) {
							xEssentials.log("player: " + off.getUser() + " has been greylisted, result: " + off.isGreyListed() + " if this is true its wrong", LogType.DEBUG);	
						}
						if(off instanceof XPlayer) {
							XPlayer xp = (XPlayer)off;
							off.setGreyListed(true);
							sendMessage(xp.getPlayer(), ChatColor.GREEN + "you are successfully promoted to " + pl.getConfiguration().getGreyListConfig().getGroup());
							if(Hooks.isVaultPermissionsEnabled()) {
								String oldGroup = pl.getManagers().getVaultManager().getGroup(off.getPlayer().getWorld(), off.getUser());
								String newgroup = pl.getConfiguration().getGreyListConfig().getGroup();
								pl.getManagers().getVaultManager().setGroup(off.getPlayer().getWorld(), off.getUser(), newgroup);
								Bukkit.getPluginManager().callEvent(new PlayerGreyListedEvent(off.getPlayer(), newgroup, oldGroup, GreyListCause.SITE, pl));
							}
						} else {
							off.setGreyListed(true);
							if(Hooks.isVaultPermissionsEnabled()) {
								String oldGroup = pl.getManagers().getVaultManager().getGroup(off.getLastLocation().getWorld(), user);
								String newgroup = pl.getConfiguration().getGreyListConfig().getGroup();
								pl.getManagers().getVaultManager().setGroup(off.getLastLocation().getWorld(), off.getUser(), newgroup);
								Bukkit.getPluginManager().callEvent(new PlayerGreyListedEvent(off.getPlayer(), newgroup, oldGroup, GreyListCause.SITE, pl));
							}
						}
					} else {
						if(pl.getConfiguration().getDebugConfig().isEnabled()) {
							xEssentials.log("player: " + off.getUser() + " was already greylisted", LogType.DEBUG);	
						}
						e.setMimeType(MimeType.MIME_JSON);
						e.setContent("{\"xEssentials\": {\"response\": \"greylisted\"}}\n\r".getBytes());
					}
				} else {
					if(pl.getConfiguration().getDebugConfig().isEnabled()) {
						xEssentials.log("player: " + user + " does not exist for the greylist request /adduser/"+user, LogType.DEBUG);	
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
