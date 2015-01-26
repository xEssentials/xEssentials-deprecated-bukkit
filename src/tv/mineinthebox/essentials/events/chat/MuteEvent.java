package tv.mineinthebox.essentials.events.chat;

import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;

@SuppressWarnings("deprecation")
public class MuteEvent implements Listener {
	
	private final xEssentials pl;
	
	public MuteEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMute(PlayerChatEvent e) {
		if(pl.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
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
