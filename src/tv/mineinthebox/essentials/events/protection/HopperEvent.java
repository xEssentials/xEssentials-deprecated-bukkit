package tv.mineinthebox.essentials.events.protection;

import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

import tv.mineinthebox.essentials.xEssentials;

public class HopperEvent implements Listener {
	
	@EventHandler
	public void cancelhopper(InventoryMoveItemEvent e) {
		if(e.getSource().getHolder() instanceof BlockState) {
				BlockState state = (BlockState)e.getSource().getHolder();
				if(xEssentials.getManagers().getProtectionDBManager().isRegistered(state.getBlock())) {
					e.setCancelled(true);
				}
		} else if(e.getDestination().getHolder() instanceof BlockState) {
			BlockState state = (BlockState) e.getDestination().getHolder();
			if(xEssentials.getManagers().getProtectionDBManager().isRegistered(state.getBlock())) {
				e.setCancelled(true);
			}
		}
	}

}
