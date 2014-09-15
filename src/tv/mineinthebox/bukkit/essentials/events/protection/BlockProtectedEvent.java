package tv.mineinthebox.bukkit.essentials.events.protection;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;

public class BlockProtectedEvent implements Listener {

	private final BlockFace[] faces = {BlockFace.UP, BlockFace.DOWN, BlockFace.SELF, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};

	/**
	 * @author xize
	 * @param returns true whenever the player can place the block, false if the face sides does not match the specified criteria.
	 * @param p - the Player instance
	 * @param block - the Block instance
	 * @return Boolean
	 */
	private boolean canBlockPlaced(Player p, Block block) {
		for(BlockFace face : faces) {
			Block block1 = block.getRelative(face);
			if(xEssentials.getManagers().getProtectionDBManager().isRegistered(block1)) {
				if(p.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
					return true;
				} else {
					if(!xEssentials.getManagers().getProtectionDBManager().isOwner(p.getName(), block1)) {
						return false;
					}	
				}
			}
		}
		return true;
	}
	
	private boolean canBlockDestroyed(Block block) {
		for(BlockFace face : faces) {
			Block block1 = block.getRelative(face);
			if(xEssentials.getManagers().getProtectionDBManager().isRegistered(block1)) {
				return true;
			}
		}
		return false;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBreak(BlockBreakEvent e) {
		if(e.isCancelled()) {
			return;
		}
		if(!canBlockPlaced(e.getPlayer(), e.getBlock())) {
			e.getPlayer().sendMessage(Configuration.getProtectionConfig().getDisallowMessage().replace("%BLOCK%", e.getBlock().getType().name()));
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBreak(BlockPlaceEvent e) {
		if(!canBlockPlaced(e.getPlayer(), e.getBlock())) {
			e.getPlayer().sendMessage(Configuration.getProtectionConfig().getDisallowMessage().replace("%BLOCK%", e.getBlock().getType().name()));
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBreak(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(!canBlockPlaced(e.getPlayer(), e.getClickedBlock())) {
				e.getPlayer().sendMessage(Configuration.getProtectionConfig().getDisallowMessage().replace("%BLOCK%", e.getClickedBlock().getType().name()));
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPiston(BlockPistonExtendEvent e) {
		for(Block block : e.getBlocks()) {
			if(canBlockDestroyed(block)) {
				e.setCancelled(true);
			}
		}
	}
	
	
}
