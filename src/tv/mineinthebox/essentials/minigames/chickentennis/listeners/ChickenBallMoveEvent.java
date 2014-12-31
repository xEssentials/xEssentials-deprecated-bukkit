package tv.mineinthebox.essentials.minigames.chickentennis.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class ChickenBallMoveEvent  implements Listener {
	
	@EventHandler
	public void doBall(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Chicken) {
			Chicken chicken = (Chicken) e.getEntity();
			if(chicken.getCustomName().equalsIgnoreCase(ChatColor.GOLD + "tennis ball")) {
				if(e.getDamager() instanceof Player) {
					Player p = (Player) e.getDamager();
					
					if(p.hasMetadata("gameType") && p.hasMetadata("game")) {
						p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 1F, 1F);
						p.sendMessage(ChatColor.GRAY + "poof!");
						
						//Vector direction = e.getTo().toVector().subtract(item.getLocation().toVector()).normalize();
						
						//Vector vec = p.getEyeLocation().toVector().subtract(chicken.getLocation().toVector()).normalize();
						
						chicken.setVelocity(p.getEyeLocation().getDirection().multiply(2).add(new Vector(0, 2, 0)).multiply(3).normalize());
						
						e.setCancelled(true);
					}
				}	
			}
		}
	}

}
