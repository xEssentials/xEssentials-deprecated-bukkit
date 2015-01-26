package tv.mineinthebox.essentials.events.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class SaveLastLocationEvent implements Listener {
	
	private final xEssentials pl;
	
	public SaveLastLocationEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void doSaveLocation(PlayerQuitEvent e) {
		if(pl.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			xp.saveLastLocation();
		}
	}
	
	@EventHandler
	public void doSaveLocation(PlayerKickEvent e) {
		if(pl.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
		XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			xp.saveLastLocation();
		}
	}

}
