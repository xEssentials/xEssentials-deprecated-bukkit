package tv.mineinthebox.essentials.events.chat;

import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.EventTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

@SuppressWarnings("deprecation")
public class MuteEvent extends EventTemplate implements Listener {
	
	public MuteEvent(xEssentials pl) {
		super(pl, "Mute");
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMute(PlayerChatEvent e) {
		if(pl.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isMuted()) {
				if(System.currentTimeMillis() > xp.getMutedTime()) {
					sendMessage(e.getPlayer(), ChatColor.GREEN + "you are now allowed to talk again!");
					xp.unmute();
				} else {
					sendMessage(e.getPlayer(), ChatColor.GREEN + "you are muted! till: " + new Date(xp.getMutedTime()).toString());
					e.setCancelled(true);
				}
			}
		}
	}

}
