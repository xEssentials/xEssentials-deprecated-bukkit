package tv.mineinthebox.essentials.events.entity;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockSpreadEvent;

public class DisableFireSpreadEvent implements Listener {
	
	@EventHandler
	public void onFireSpreadDisable(BlockSpreadEvent e) {
		if(e.getNewState().getType() == Material.FIRE) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onFireSpreadDisable(BlockBurnEvent e) {
		if(e.getBlock().getState() != null) {
			e.setCancelled(true);
		}
	}

}
