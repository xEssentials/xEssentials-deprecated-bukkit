package tv.mineinthebox.essentials.events.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathBackEvent implements Listener {
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		TeleportBackEvent.locations.put(e.getEntity().getName(), e.getEntity().getLocation());
	}

}
