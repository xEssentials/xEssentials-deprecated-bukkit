package tv.mineinthebox.essentials.events.entity;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.player.PlayerInteractEvent;

public class DisableSpawnEggEvent {
	
	@EventHandler
	public void onDisableSpawnEggs(PlayerInteractEvent e) {
		if(e.getPlayer().getItemInHand().getType() == Material.MONSTER_EGG) {
			e.setCancelled(true);
		} else if(e.getPlayer().getItemInHand().getType() == Material.MONSTER_EGGS) {
			e.setCancelled(true);
		}
	}
	
	//in case PlayerInteract gets bypassed which sometimes could happen cancel the spawn.
	
	@EventHandler
	public void onDisableSpawnEggs(CreatureSpawnEvent e) {
		if(e.getSpawnReason() == SpawnReason.SPAWNER_EGG) {
			e.setCancelled(true);
		}
	}

}
