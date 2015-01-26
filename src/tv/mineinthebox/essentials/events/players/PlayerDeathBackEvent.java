package tv.mineinthebox.essentials.events.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.managers.TeleportManager;

public class PlayerDeathBackEvent implements Listener {
	
	private final TeleportManager manager;
	
	public PlayerDeathBackEvent(xEssentials pl) {
		this.manager = pl.getManagers().getTeleportManager();
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		manager.addLastLocation(e.getEntity().getName(), e.getEntity().getLocation());
	}

}
