package tv.mineinthebox.bukkit.essentials.events.players;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsPlayer;

public class PlayerWallEvent implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(e.isCancelled()) {
			return;
		}

		xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
		if(xp.isWallMode()) {
			xp.setWallMode(false, xp.getWallModeRange());
			Location loc = e.getBlockPlaced().getLocation();
			
			int range = xp.getWallModeRange();

			BlockFace face = getFace(e.getPlayer().getLocation().getYaw(), true);

			if(face == BlockFace.NORTH) {
				//x coordinate
				for(int x = (loc.getBlockX()-(7/2)); x < (loc.getBlockX()+(7/2));x++) {
					for(int y = loc.getBlockY(); y < (loc.getY()+range);y++) {
						Block block = new Location(loc.getWorld(), x, y, loc.getBlockZ()).getBlock();
						if(block.getType() == Material.AIR) {
							block.setTypeIdAndData(e.getItemInHand().getType().getId(), e.getItemInHand().getData().getData(), true);
							Bukkit.getPluginManager().callEvent(new BlockPlaceEvent(block, null, block, e.getItemInHand(), e.getPlayer(), true));
						}
					}
				}
			} else if(face == BlockFace.EAST) {
				//z coordinate
				for(int z = (loc.getBlockZ()-(7/2)); z < (loc.getBlockZ()+(7/2));z++) {
					for(int y = loc.getBlockY(); y < (loc.getY()+range);y++) {
						Block block = new Location(loc.getWorld(), loc.getBlockX(), y, z).getBlock();
						if(block.getType() == Material.AIR) {
							block.setTypeIdAndData(e.getItemInHand().getType().getId(), e.getItemInHand().getData().getData(), true);
							Bukkit.getPluginManager().callEvent(new BlockPlaceEvent(block, null, block, e.getItemInHand(), e.getPlayer(), true));
						}
					}
				}
			} else if(face == BlockFace.SOUTH) {
				//x coordinate
				for(int x = (loc.getBlockX()-(7/2)); x < (loc.getBlockX()+(7/2));x++) {
					for(int y = loc.getBlockY(); y < (loc.getY()+range);y++) {
						Block block = new Location(loc.getWorld(), x, y, loc.getBlockZ()).getBlock();
						if(block.getType() == Material.AIR) {
							block.setTypeIdAndData(e.getItemInHand().getType().getId(), e.getItemInHand().getData().getData(), true);
							Bukkit.getPluginManager().callEvent(new BlockPlaceEvent(block, null, block, e.getItemInHand(), e.getPlayer(), true));
						}
					}
				}
			} else if(face == BlockFace.WEST) {
				//z coordinate
				for(int z = (loc.getBlockZ()-(7/2)); z < (loc.getBlockZ()+(7/2));z++) {
					for(int y = loc.getBlockY(); y < (loc.getY()+range);y++) {
						Block block = new Location(loc.getWorld(), loc.getBlockX(), y, z).getBlock();
						if(block.getType() == Material.AIR) {
							block.setTypeIdAndData(e.getItemInHand().getType().getId(), e.getItemInHand().getData().getData(), true);
							Bukkit.getPluginManager().callEvent(new BlockPlaceEvent(block, null, block, e.getItemInHand(), e.getPlayer(), true));
						}
					}
				}
			}
			xp.setWallMode(true, xp.getWallModeRange());
		}
	}

	//credits by BergerKiller for this underlying code for calculating the yaw's BlockFace

	public static final BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
	public static final BlockFace[] radial = { BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST };


	public static BlockFace getFace(float yaw, boolean useSubCardinalDirections) {
		if (useSubCardinalDirections) {
			return radial[Math.round(yaw / 45f) & 0x7];
		} else {
			return axis[Math.round(yaw / 90f) & 0x3];
		}
	} 

}
