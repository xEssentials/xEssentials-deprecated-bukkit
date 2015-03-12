package tv.mineinthebox.essentials.events.protection;

import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.ProtectedBlock;

public class HopperEvent implements Listener {
	
	private final xEssentials pl;
	
	public HopperEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void cancelhopper(InventoryMoveItemEvent e) {
		if(e.getSource().getHolder() instanceof BlockState) {
				BlockState state = (BlockState)e.getSource().getHolder();
				ProtectedBlock pblock = new ProtectedBlock(pl, state.getBlock());
				if(pblock.isProtected()) {
					e.setCancelled(true);
				}
		} else if(e.getDestination().getHolder() instanceof BlockState) {
			BlockState state = (BlockState) e.getDestination().getHolder();
			ProtectedBlock pblock = new ProtectedBlock(pl, state.getBlock());
			if(pblock.isProtected()) {
				e.setCancelled(true);
			}
		}
	}

}
