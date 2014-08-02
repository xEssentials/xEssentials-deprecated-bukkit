package tv.mineinthebox.essentials.events.entity;

import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import tv.mineinthebox.essentials.Configuration;

public class CustomZombieAggroRange implements Listener {
	
	@EventHandler
	public void getZombieTarget(EntityTargetLivingEntityEvent e) {
		if(e.getEntity() instanceof Zombie) {
			if(e.getTarget() instanceof Player) {
				if(e.getEntity().getLocation().distance(e.getTarget().getLocation()) > Configuration.getEntityConfig().getCustomZombieAggroRange()) {
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
