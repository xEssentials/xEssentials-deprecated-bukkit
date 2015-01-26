package tv.mineinthebox.essentials.events.entity;

import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import tv.mineinthebox.essentials.xEssentials;

public class CustomZombieAggroRangeEvent implements Listener {
	
	private final xEssentials pl;
	
	public CustomZombieAggroRangeEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void getZombieTarget(EntityTargetLivingEntityEvent e) {
		if(e.getEntity() instanceof Zombie) {
			if(e.getTarget() instanceof Player) {
				if(e.getEntity().getLocation().distance(e.getTarget().getLocation()) > pl.getConfiguration().getEntityConfig().getCustomZombieAggroRange()) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void CancelReinformants(CreatureSpawnEvent e) {
		if(e.getSpawnReason() == SpawnReason.REINFORCEMENTS) {
			e.setCancelled(true);
		}
	}

}
