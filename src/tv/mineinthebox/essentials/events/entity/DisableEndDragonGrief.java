package tv.mineinthebox.essentials.events.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class DisableEndDragonGrief implements Listener {
	
	@EventHandler
	public void onEnderDragonDestroy(EntityChangeBlockEvent e) {
		if(e.getEntity().getType() == EntityType.ENDER_DRAGON) {
			e.setCancelled(true);
		}
	}

}
