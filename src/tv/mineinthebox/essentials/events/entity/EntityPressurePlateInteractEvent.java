package tv.mineinthebox.essentials.events.entity;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;

public class EntityPressurePlateInteractEvent implements Listener {
	
	@EventHandler
	public void onInteract(EntityInteractEvent e) {
		
		if(e.isCancelled()) {
			return;
		}
		
		if(e.getEntity() instanceof LivingEntity && e.getEntity().getType() != EntityType.PLAYER) {
			if(e.getBlock().getType() == Material.STONE_PLATE) {
				e.setCancelled(true);	
			}
		}
	}

}
