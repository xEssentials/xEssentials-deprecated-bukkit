package tv.mineinthebox.essentials.events.players;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import tv.mineinthebox.essentials.xEssentials;

public class TeleportBackEvent implements Listener {

	public static HashMap<String, Location> locations = new HashMap<String, Location>();

	@EventHandler
	public void onBackEvent(PlayerTeleportEvent e) {
		if(e.getCause() == TeleportCause.COMMAND || e.getCause() == TeleportCause.PLUGIN) {
			if(locations.containsKey(e.getPlayer().getName())) {
				locations.remove(e.getPlayer().getName());
				locations.put(e.getPlayer().getName(), e.getFrom());
				RemoveBack(e.getPlayer().getName());	
			} else {
				locations.put(e.getPlayer().getName(), e.getFrom());
				RemoveBack(e.getPlayer().getName());	
			}
		}
	}

	private void RemoveBack(final String key) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(xEssentials.getPlugin(), new Runnable() {

			@Override
			public void run() {
				if(locations.containsKey(key)) {
					locations.remove(key);
				}
			}

		}, 5000);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(locations.containsKey(e.getPlayer().getName())) {
			locations.remove(e.getPlayer().getName());
		}
	}

	@EventHandler
	public void onQuit(PlayerKickEvent e) {
		if(locations.containsKey(e.getPlayer().getName())) {
			locations.remove(e.getPlayer().getName());
		}
	}

}
