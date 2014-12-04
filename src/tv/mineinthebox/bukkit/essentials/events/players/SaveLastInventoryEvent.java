package tv.mineinthebox.bukkit.essentials.events.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsPlayer;

public class SaveLastInventoryEvent implements Listener {
	
	@EventHandler
	public void onQuitSaveInventory(PlayerQuitEvent e) {
		if(xEssentials.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			xp.saveOfflineInventory();
		}
	}
	
	@EventHandler
	public void onQuitSaveInventory(PlayerKickEvent e) {
		if(xEssentials.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			xp.saveOfflineInventory();
		}
	}	

}
