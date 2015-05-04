package tv.mineinthebox.essentials.events.chairs;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Stairs;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class ChairEvent implements Listener {

	private final xEssentials pl;

	public ChairEvent(xEssentials pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(isChair(e.getClickedBlock())) {
				XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
				Arrow arrow = (Arrow) e.getPlayer().getWorld().spawnEntity(e.getClickedBlock().getLocation().add(0.50, 0, 0.50), EntityType.ARROW);
				arrow.setPassenger(e.getPlayer());
				xp.setInChair(true);	
				e.setCancelled(true);
			}
		}
	}

	private boolean isChair(Block block) {
		if(block.getState().getData() instanceof Stairs) {
			Stairs stair = (Stairs) block.getState().getData();
			BlockFace pos1;
			BlockFace pos2;
			if(!stair.isInverted()) {
				switch(stair.getFacing()) {
				case EAST:
					pos1 = BlockFace.NORTH;
					pos2 = BlockFace.SOUTH;
					if(block.getRelative(pos1).getType() == Material.WALL_SIGN && block.getRelative(pos2).getType() == Material.WALL_SIGN) {
						return true;
					}
				case NORTH:
					pos1 = BlockFace.EAST;
					pos2 = BlockFace.WEST;
					if(block.getRelative(pos1).getType() == Material.WALL_SIGN && block.getRelative(pos2).getType() == Material.WALL_SIGN) {
						return true;
					}
				case SOUTH:
					pos1 = BlockFace.EAST;
					pos2 = BlockFace.WEST;
					if(block.getRelative(pos1).getType() == Material.WALL_SIGN && block.getRelative(pos2).getType() == Material.WALL_SIGN) {
						return true;
					}
				case WEST:
					pos1 = BlockFace.NORTH;
					pos2 = BlockFace.SOUTH;
					if(block.getRelative(pos1).getType() == Material.WALL_SIGN && block.getRelative(pos2).getType() == Material.WALL_SIGN) {
						return true;
					}
				default:
					break;
				}
			}
		}
		return false;
	}

}
