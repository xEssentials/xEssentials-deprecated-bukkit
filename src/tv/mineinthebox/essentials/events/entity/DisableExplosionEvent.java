package tv.mineinthebox.essentials.events.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class DisableExplosionEvent implements Listener {
	
	@EventHandler
	public void onExplosionDisable(EntityExplodeEvent e) {
		e.setCancelled(true);
	}

}
