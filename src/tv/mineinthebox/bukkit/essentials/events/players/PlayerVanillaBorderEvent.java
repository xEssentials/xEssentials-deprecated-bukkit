package tv.mineinthebox.bukkit.essentials.events.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.WorldUnloadEvent;

import tv.mineinthebox.bukkit.essentials.Configuration;

public class PlayerVanillaBorderEvent implements Listener {
	
	@EventHandler
	public void onBorder(PlayerJoinEvent e) {
		if(Configuration.getPlayerConfig().hasSpawnLocation()) {
			e.getPlayer().getWorld().getWorldBorder().setCenter(Configuration.getPlayerConfig().getSpawnLocation());
			e.getPlayer().getWorld().getWorldBorder().setSize(Configuration.getPlayerConfig().getWorldBorderSize());
		}
	}
	
	@EventHandler
	public void onBorder(WorldUnloadEvent e) {
		if(Configuration.getPlayerConfig().hasSpawnLocation()) {
			e.getWorld().getWorldBorder().reset();
		}
	}

}
