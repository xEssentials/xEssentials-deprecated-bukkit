package tv.mineinthebox.essentials.events.ban;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;

public class HumanSpamEvent implements Listener {
	
	private HashMap<String, String> message1 = new HashMap<String, String>();
	private HashMap<String, String> message2 = new HashMap<String, String>();
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void slowSpamCheck(AsyncPlayerChatEvent e) {
		final Player p = (Player) e.getPlayer();
		if(message2.containsKey(e.getPlayer().getName())) {
			if(message2.get(e.getPlayer().getName()).equalsIgnoreCase(e.getMessage())) {
				e.getPlayer().kickPlayer(Configuration.getBanConfig().getHumanSpamBanMessage());
				message2.remove(e.getPlayer().getName());
			} else {
				message2.remove(e.getPlayer().getName());
			}
		} else if(message1.containsKey(e.getPlayer().getName())) {
			if(message1.get(e.getPlayer().getName()).equalsIgnoreCase(e.getMessage())) {
				message1.remove(e.getPlayer().getName());
				message2.put(e.getPlayer().getName(), e.getMessage());
				e.getPlayer().sendMessage(ChatColor.RED + "Warning if you post one more time the same message you will be kicked!");
			} else {
				message1.put(e.getPlayer().getName(), e.getMessage());
			}
		} else {
			message1.put(e.getPlayer().getName(), e.getMessage());
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(xEssentials.getPlugin(), new Runnable() {

			@Override
			public void run() {
				if(message1.containsKey(p.getPlayer().getName())) {
					message1.remove(p.getPlayer().getName());
				} else if(message2.containsKey(p.getPlayer().getName())) {
					message2.remove(p.getPlayer().getName());
				}
			}
		}, 300);
	}
}
