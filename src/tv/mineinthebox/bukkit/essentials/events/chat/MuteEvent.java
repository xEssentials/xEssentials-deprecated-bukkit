package tv.mineinthebox.bukkit.essentials.events.chat;

import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsPlayer;

public class MuteEvent implements Listener {
	
	//loads as last so no other event can override this ;-)
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMute(AsyncPlayerChatEvent e) {
		if(xEssentials.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isMuted()) {
				if(System.currentTimeMillis() > xp.getMutedTime()) {
					e.getPlayer().sendMessage(ChatColor.GREEN + "you are now allowed to talk again!");
					xp.unmute();
				} else {
					e.getPlayer().sendMessage(ChatColor.GREEN + "you are muted! till: " + new Date(xp.getMutedTime()).toString());
					e.setCancelled(true);
				}
			}
		}
	}

}
