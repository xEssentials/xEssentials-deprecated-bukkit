package tv.mineinthebox.essentials.events.portals;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockRedstoneEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.Portal;

public class PortalActivateEvent implements Listener {
	
	private final xEssentials pl;
	
	public PortalActivateEvent(xEssentials pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onPowered(BlockRedstoneEvent e) {
		if(e.getNewCurrent() > 0) {

			if(e.getBlock().getType() == Material.REDSTONE_WIRE || e.getBlock().getType() == Material.DIODE_BLOCK_OFF || e.getBlock().getType() == Material.DIODE_BLOCK_ON) {
				return;
			}

			for(Portal portal : pl.getConfiguration().getPortalConfig().getPortals().values()) {
				if(doesMatch(e.getBlock(), portal)) {
					if(portal.isClosed()) {
						portal.setClosed(false);
						if(portal.isLinked()) {
							Portal linked = portal.getLinkedPortal();
							linked.setClosed(false);
						}
					} else {
						portal.setClosed(true);
						if(portal.isLinked()) {
							Portal linked = portal.getLinkedPortal();
							linked.setClosed(true);
						}
					}
					break;
				}
			}
		}	
	}

	@EventHandler
	public void psycic(BlockPhysicsEvent e) {
		if(e.isCancelled()) {
			return;
		}

		if(e.getBlock().getType() == Material.PORTAL) {
			for(Portal portal : pl.getConfiguration().getPortalConfig().getPortals().values()) {
				if(portal.getInnerBlocks().contains(e.getBlock())) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(e.isCancelled()) {
			return;
		}

		if(e.getBlock().getType() == Material.PORTAL) {
			for(Portal portal : pl.getConfiguration().getPortalConfig().getPortals().values()) {
				if(portal.getInnerBlocks().contains(e.getBlock())) {
					if(e.getPlayer().getGameMode() == GameMode.CREATIVE) {
						e.getPlayer().sendMessage(ChatColor.RED + "you can only break this portal, if you remove this portal.");
					}
					e.setCancelled(true);
					break;
				}
			}
		}
	}

	private boolean doesMatch(Block block, Portal portal) {
		BlockFace[] faces = {BlockFace.SELF, BlockFace.UP, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
		for(BlockFace face : faces) {
			Block newblock = block.getRelative(face);
			List<Block> blocks = Arrays.asList(portal.getBlocks());
			if(blocks.contains(newblock)) {
				return true;
			}
		}
		return false;
	}

}
