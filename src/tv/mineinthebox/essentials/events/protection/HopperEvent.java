package tv.mineinthebox.essentials.events.protection;

import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

import tv.mineinthebox.essentials.xEssentials;

public class HopperEvent implements Listener {
	
	private final xEssentials pl;
	
	public HopperEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void cancelhopper(InventoryMoveItemEvent e) {
		if(e.getSource().getHolder() instanceof BlockState) {
				BlockState state = (BlockState)e.getSource().getHolder();
				if(pl.getManagers().getProtectionDBManager().isRegistered(state.getBlock())) {
					e.setCancelled(true);
				}
		} else if(e.getDestination().getHolder() instanceof BlockState) {
			BlockState state = (BlockState) e.getDestination().getHolder();
			if(pl.getManagers().getProtectionDBManager().isRegistered(state.getBlock())) {
				e.setCancelled(true);
			}
		}
	}

}
