package tv.mineinthebox.essentials.events.players;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class PlayerFloorEvent implements Listener {
	
	private final xEssentials pl;
	
	public PlayerFloorEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(e.isCancelled() || !e.canBuild()) {
			return;
		}
		
		XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
		if(xp.isFloorMode()) {
			xp.setFloorMode(false, xp.getFloorModeRange());
			Location loc = e.getBlockPlaced().getLocation();
			int range = xp.getFloorModeRange();
			
			int xr = loc.getBlockX() - (range/2);
			int zr = loc.getBlockZ() - (range/2);
			
			int y = loc.getBlockY();
			
			for(int x = xr; x < (loc.getBlockX()+(range/2)); x++) {
				for(int z = zr; z < (loc.getBlockZ()+(range/2)); z++) {
					Block block = new Location(loc.getWorld(), x, y, z).getBlock();
					if(block.getType() == Material.AIR) {
						block.setTypeIdAndData(e.getItemInHand().getType().getId(), e.getItemInHand().getData().getData(), true);
						Bukkit.getPluginManager().callEvent(new BlockPlaceEvent(block, null, block, e.getItemInHand(), e.getPlayer(), true));
					}
				}
			}
			xp.setFloorMode(true, xp.getFloorModeRange());
		}
	}

}
