package tv.mineinthebox.essentials.events.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class RealisticWaterEvent implements Listener {
	
	@EventHandler
	public void onItem(PlayerPickupItemEvent e) {
		if(e.isCancelled()) {
			return;
		}
		
		if(e.getItem().hasMetadata("fakefish")) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onHopper(InventoryMoveItemEvent e) {
		if(e.getSource() == null) {
			if(e.getItem().hasItemMeta()) {
				if(e.getItem().getItemMeta().hasDisplayName()) {
					if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("fakefish")) {
						e.setCancelled(true);
					}
				}
			}
		}
	}
	
}
