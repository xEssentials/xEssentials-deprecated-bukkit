package tv.mineinthebox.essentials.events.chat;

import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class SilenceChatEvent implements Listener {
	
	@EventHandler(priority = EventPriority.LOW)
	public void onChat(AsyncPlayerChatEvent e) {
		if(Configuration.isSilenceToggled) {
			e.getPlayer().sendMessage(ChatColor.GREEN + "all chat activity has been halted!, please wait a few minuts.");
			e.setCancelled(true);
		} else {
			XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isSilenced()) {
				e.getPlayer().sendMessage(ChatColor.GREEN + "you have silenced your chat, so you cannot chat unless you unmute your self with /silence");
				e.setCancelled(true);
				return;
			}
			//this probably will fix the ConcurentModificationException because e.getRecipients() isn't thread safe, so instead we cloned the recipients as a new object
			//else we go use the PlayerChatEvent, credits to feildmaster his silence source
			for(Player p : new HashSet<Player>(e.getRecipients())) {
				XPlayer xp2 = xEssentials.getManagers().getPlayerManager().getPlayer(p.getName());
				if(xp2.isSilenced()) {
					e.getRecipients().remove(p);
				}
			}
		}
	}

}
