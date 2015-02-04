package tv.mineinthebox.essentials.events.players;

import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import tv.mineinthebox.essentials.xEssentials;

public class WorldGuardMonsterEvent implements Listener {
	
	private final xEssentials pl;
	
	public WorldGuardMonsterEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onSpawn(CreatureSpawnEvent e) {
		if(e.getEntity() instanceof Monster) {
			if(pl.getManagers().getWorldGuardManager().isInRegion(e.getLocation())) {
				if(!pl.getManagers().getWorldGuardManager().isFlagAllowed(pl.getManagers().getWorldGuardManager().MONSTER_FLAG, e.getLocation())) {
					e.setCancelled(true);
				}
			}
		}
	}

}
