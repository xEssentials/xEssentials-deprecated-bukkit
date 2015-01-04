package tv.mineinthebox.essentials.minigames.football.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class FootballNoDamageEvent implements Listener {

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Slime) {
			Slime slime = (Slime) e.getEntity();
			if(slime.getCustomName().equalsIgnoreCase(ChatColor.GOLD + "football")) {
				if(e.getDamager() instanceof Player) {
					Player p = (Player) e.getDamager();
					if(p.hasMetadata("gameType") && p.hasMetadata("game")) {
						e.setCancelled(true);
					}
				}
			}
		}
	}
	
}
