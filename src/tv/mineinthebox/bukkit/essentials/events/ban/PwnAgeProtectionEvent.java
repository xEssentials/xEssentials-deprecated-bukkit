package tv.mineinthebox.bukkit.essentials.events.ban;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsPlayer;

public class PwnAgeProtectionEvent implements Listener {

	private HashMap<String, Long> time = new HashMap<String, Long>();
	private HashMap<String, Long> time2 = new HashMap<String, Long>();

	@EventHandler(priority = EventPriority.LOW)
	public void playerJoinCheckSpamBot(PlayerJoinEvent e) {
		xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());	
		if(!xp.isPermBanned() || !xp.isTempBanned()) {
			if(time2.containsKey(e.getPlayer().getName())) {
				if(System.currentTimeMillis() - time2.get(e.getPlayer().getName()) <= 1000) {
				} else {
					//relogged legit, setting default hashmap again.
					time2.remove(e.getPlayer().getName());
					time.put(e.getPlayer().getName(), System.currentTimeMillis());
				}
			}
			if(time.containsKey(e.getPlayer().getName())) {
				if(System.currentTimeMillis() - time.get(e.getPlayer().getName()) <= 1000) {
					time.remove(e.getPlayer().getName());
					time2.put(e.getPlayer().getName(), System.currentTimeMillis());
					xp.setPermBanned(ChatColor.translateAlternateColorCodes('&', Configuration.getBanConfig().getPwnAgeSpamBanMessage()), "CONSOLE");
					for(Player p : Bukkit.getOnlinePlayers()) {
						xEssentialsPlayer xpp = xEssentials.getManagers().getPlayerManager().getPlayer(p.getName());
						if(xpp.isStaff()) {
							p.sendMessage(ChatColor.RED + "player " + e.getPlayer().getName() + " is banned for botting!");
						}
						time2.remove(e.getPlayer().getName());
					}
				}
			} else {
				//adding first hashmap, like any player, when hes putting in time2 he has bot behaviour, when its 3 hes gets banned.
				//doing a check if this player is not in time2!
				if(!time2.containsKey(e.getPlayer().getName())) {
					time.put(e.getPlayer().getName(), System.currentTimeMillis());
				}
			}
		} else {
			if(time.containsKey(e.getPlayer().getName())) {
				time.remove(e.getPlayer().getName());
			}
			if(time2.containsKey(e.getPlayer().getName())) {
				time2.remove(e.getPlayer().getName());
			}
		}
	}
}
