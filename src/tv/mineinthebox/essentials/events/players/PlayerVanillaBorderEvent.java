package tv.mineinthebox.essentials.events.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.WorldUnloadEvent;

import tv.mineinthebox.essentials.xEssentials;

public class PlayerVanillaBorderEvent implements Listener {
	
	private final xEssentials pl;
	
	public PlayerVanillaBorderEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onBorder(PlayerJoinEvent e) {
		if(pl.getConfiguration().getPlayerConfig().hasSpawnLocation()) {
			e.getPlayer().getWorld().getWorldBorder().setCenter(pl.getConfiguration().getPlayerConfig().getSpawnLocation());
			e.getPlayer().getWorld().getWorldBorder().setSize(pl.getConfiguration().getPlayerConfig().getWorldBorderSize());
		}
	}
	
	@EventHandler
	public void onBorder(WorldUnloadEvent e) {
		if(pl.getConfiguration().getPlayerConfig().hasSpawnLocation()) {
			e.getWorld().getWorldBorder().reset();
		}
	}

}
