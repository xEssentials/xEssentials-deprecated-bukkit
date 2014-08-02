package tv.mineinthebox.essentials.events.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class DisableEndermanGrief implements Listener {
	
	@EventHandler
	public void disableEndermanGrief(EntityChangeBlockEvent e) {
		if(e.getEntity().getType() == EntityType.ENDERMAN) {
			e.setCancelled(true);
		}
	}

}
