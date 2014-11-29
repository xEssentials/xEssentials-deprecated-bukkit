package tv.mineinthebox.bukkit.essentials.events.pvp;

import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import tv.mineinthebox.bukkit.essentials.hook.Hooks;
import tv.mineinthebox.bukkit.essentials.hook.WorldGuardHook;

public class FakePvpEvent implements Listener {
	
	@EventHandler(priority = EventPriority.LOW)
	public void onDamage(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player p = (Player) e.getEntity();
			Player dam = (Player) e.getDamager();
			if(p.getGameMode() != GameMode.SURVIVAL) {
				e.setCancelled(true);
			} else {
				if(dam.getItemInHand().containsEnchantment(Enchantment.FIRE_ASPECT)) {
					e.setCancelled(true);
				} else {
					if(Hooks.isWorldGuardEnabled()) {
						if(WorldGuardHook.isInRegion(p.getLocation())) {
							e.setCancelled(true);
							return;
						}
					}
					e.setDamage(0.001);	
				}	
			}
		}
	}

}
