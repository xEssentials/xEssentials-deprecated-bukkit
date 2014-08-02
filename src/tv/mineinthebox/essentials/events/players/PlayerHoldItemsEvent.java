package tv.mineinthebox.essentials.events.players;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

public class PlayerHoldItemsEvent implements Listener {
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getEntity().getName());
		if(e.getEntity().getGameMode() == GameMode.SURVIVAL) {
			xp.saveSurvivalInventory();
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
		e.getPlayer().getInventory().setContents(xp.getSurvivalInventory());
		e.getPlayer().getInventory().setArmorContents(xp.getSurvivalArmorInventory());
	}

}
