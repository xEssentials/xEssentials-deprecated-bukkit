package tv.mineinthebox.essentials.events.players;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.managers.TeleportManager;

public class TeleportBackEvent implements Listener {

	private final xEssentials pl;
	private final TeleportManager manager;
	
	public TeleportBackEvent(xEssentials pl) {
		this.pl = pl;
		this.manager = pl.getManagers().getTeleportManager();
	}

	@EventHandler
	public void onBackEvent(PlayerTeleportEvent e) {
		if(e.getCause() == TeleportCause.COMMAND || e.getCause() == TeleportCause.PLUGIN) {
			if(manager.hasLastLocation(e.getPlayer().getName())) {
				manager.removeLastLocationData(e.getPlayer().getName());
				manager.addLastLocation(e.getPlayer().getName(), e.getFrom());
				RemoveBack(e.getPlayer().getName());	
			} else {
				manager.addLastLocation(e.getPlayer().getName(), e.getFrom());
				RemoveBack(e.getPlayer().getName());
			}
		}
	}

	private void RemoveBack(final String key) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {

			@Override
			public void run() {
				if(manager.hasLastLocation(key)) {
					manager.removeLastLocationData(key);
				}
			}

		}, 5000);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(manager.hasLastLocation(e.getPlayer().getName())) {
			manager.removeLastLocationData(e.getPlayer().getName());
		}
	}

	@EventHandler
	public void onQuit(PlayerKickEvent e) {
		if(manager.hasLastLocation(e.getPlayer().getName())) {
			manager.removeLastLocationData(e.getPlayer().getName());
		}
	}

}
