package tv.mineinthebox.essentials.events.players;

import org.bukkit.ChatColor;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.EventTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class TrollModeEvent extends EventTemplate implements Listener {
	
	public TrollModeEvent(xEssentials pl) {
		super(pl, "TrollMode");
	}

	@EventHandler
	public void onTroll(PlayerInteractEntityEvent e) {
		XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
		if(xp.isTrollMode()) {
			if(e.getRightClicked() instanceof Player) {
				Player p = (Player) e.getRightClicked();
				e.getPlayer().setPassenger(p);
				sendMessage(e.getPlayer(), ChatColor.GREEN + "now left click on a block to place the player!");
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isTrollMode()) {
				if(xp.getBukkitPlayer().getPassenger() instanceof Player) {
					Player p = (Player) xp.getBukkitPlayer().getPassenger();
					p.getVehicle().eject();
					p.teleport(e.getClickedBlock().getRelative(BlockFace.UP).getLocation());
					sendMessage(e.getPlayer(), ChatColor.GREEN + "placed player now he should be confused!");
					e.setCancelled(true);
				}
			}
		}
	}

}
