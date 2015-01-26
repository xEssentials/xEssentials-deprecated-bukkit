package tv.mineinthebox.essentials.events.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class SaveLastInventoryEvent implements Listener {
	
	private final xEssentials pl;
	
	public SaveLastInventoryEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onQuitSaveInventory(PlayerQuitEvent e) {
		if(pl.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			xp.saveOfflineInventory();
		}
	}
	
	@EventHandler
	public void onQuitSaveInventory(PlayerKickEvent e) {
		if(pl.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			xp.saveOfflineInventory();
		}
	}	

}
