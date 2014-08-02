package tv.mineinthebox.essentials.events.players;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

public class DoubleJumpEvent implements Listener {

	@EventHandler
	public void onJump(PlayerToggleFlightEvent e) {
		if(e.getPlayer().getGameMode() == GameMode.CREATIVE) {
			return;
		}
		xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
		if(xp.hasDoubleJump()) {
			e.setCancelled(true);
			e.getPlayer().setAllowFlight(false);
			e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().add(new Vector(0, 3, 0)).normalize());
		}
	}
	
	@EventHandler
	public void onJump(PlayerMoveEvent e) {
			xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.hasDoubleJump()) {
				if(e.getPlayer().getGameMode() == GameMode.SURVIVAL && e.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) {
					e.getPlayer().setAllowFlight(true);	
				}
			}
	}

}
