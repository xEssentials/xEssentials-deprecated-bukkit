package tv.mineinthebox.essentials.events.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

public class PortalSizeEvent implements Listener {
	
	@EventHandler
	public void PortalSizeCheck(PortalCreateEvent e) {
		if(e.getBlocks().size() > 6) {
			e.setCancelled(true);
		}
	}

}
