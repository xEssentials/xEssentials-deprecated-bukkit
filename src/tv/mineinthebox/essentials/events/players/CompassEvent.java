package tv.mineinthebox.essentials.events.players;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CompassEvent implements Listener {
	
	private final xEssentials pl;
	
	public CompassEvent(xEssentials pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onCompassMove(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getPlayer().getItemInHand().getType() == Material.WATCH) {
				XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
				if(xp.hasCompass()) {
					Player p = xp.getCompass().getBukkitPlayer();
					if(p instanceof Player) {
						Vector direction = p.getLocation().toVector().subtract(e.getPlayer().getLocation().toVector()).normalize().multiply(2);
						e.getPlayer().setVelocity(direction);
					} else {
						Location loc = xp.getCompass().getLastLocation();
						Vector direction = loc.toVector().subtract(e.getPlayer().getLocation().toVector()).normalize().multiply(2);
						e.getPlayer().setVelocity(direction);
					}
				}
			}
		}
	}
}
