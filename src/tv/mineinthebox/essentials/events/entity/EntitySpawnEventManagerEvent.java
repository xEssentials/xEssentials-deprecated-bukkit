package tv.mineinthebox.essentials.events.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import tv.mineinthebox.essentials.xEssentials;

public class EntitySpawnEventManagerEvent implements Listener {

	private final xEssentials pl;
	
	public EntitySpawnEventManagerEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntitySpawn(CreatureSpawnEvent e) {
		if(!pl.getConfiguration().getEntityConfig().canEntitySpawn(e.getEntityType(), e.getLocation().getBlock().getBiome())) {
			e.setCancelled(true);
		}
	}
}
