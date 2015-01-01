package tv.mineinthebox.essentials.minigames.chickentennis.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.minigames.MinigameType;
import tv.mineinthebox.essentials.minigames.chickentennis.ChickenTennisArena;

public class ChickenBallMoveEvent  implements Listener {
	
	@EventHandler
	public void doBall(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Chicken) {
			Chicken chicken = (Chicken) e.getEntity();
			if(chicken.getCustomName().equalsIgnoreCase(ChatColor.GOLD + "tennis ball")) {
				if(e.getDamager() instanceof Player) {
					Player p = (Player) e.getDamager();
					
					if(p.hasMetadata("gameType") && p.hasMetadata("game")) {
						String arenaname = p.getMetadata("game").get(0).asString();
						MinigameType type = (MinigameType) p.getMetadata("gameType").get(0).value();
						if(type == MinigameType.CHICKEN_TENNIS) {
							ChickenTennisArena arena = (ChickenTennisArena) xEssentials.getManagers().getMinigameManager().getArenaByName(type, arenaname);
							if(arena.isStarted()) {
								p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 1F, 1F);
								p.sendMessage(ChatColor.GRAY + "poof!");
								chicken.setVelocity(p.getEyeLocation().getDirection().multiply(5).add(new Vector(0, 3, 0)).multiply(3).normalize().add(new Vector(0, -1, 0).multiply(2).normalize()));
							}
							e.setCancelled(true);
						}
					}
				}	
			}
		}
	}

}
