package tv.mineinthebox.bukkit.essentials.events.players;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class TestEvent implements Listener {
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.isCancelled()) {
			return;
		}
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.IRON_FENCE) {
				if(e.getItem() != null && e.getItem().getType() == Material.STICK) {
					e.setCancelled(true);
					
					Random rand = new Random();
					
					if(rand.nextInt(3) == 0) {
						//e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ZOMBIE_REMEDY, 5F, 1F);	
					} else {
						e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ZOMBIE_REMEDY, 3F, 1F);
						//e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ZOMBIE_METAL, 10F, 1F);	
					}
				}
			}
		}
	}
}
