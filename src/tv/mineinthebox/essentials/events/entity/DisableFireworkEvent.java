package tv.mineinthebox.essentials.events.entity;

import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class DisableFireworkEvent implements Listener {
	
	@EventHandler
	public void onDisableFirework(ProjectileLaunchEvent e) {
		if(e.getEntity() instanceof Firework) {
			e.setCancelled(true);
		}
	}

}
