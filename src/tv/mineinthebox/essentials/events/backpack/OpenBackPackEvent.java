package tv.mineinthebox.essentials.events.backpack;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import tv.mineinthebox.essentials.instances.Backpack;
import static tv.mineinthebox.essentials.xEssentials.getManagers;

public class OpenBackPackEvent implements Listener {
	
	@EventHandler
	public void interact(PlayerInteractEvent e) {
		if(e.isCancelled()) {
			return;
		}
		
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			if(e.getItem() != null) {
				if(getManagers().getBackPackManager().isBackpack(e.getItem())) {
					Backpack pack = getManagers().getBackPackManager().getBackpackByItem(e.getItem());
					e.getPlayer().sendMessage(ChatColor.GREEN + "opening inventory");
					e.getPlayer().openInventory(pack.getInventory());
				}
			}
		}
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if(getManagers().getBackPackManager().getBackpackById(e.getInventory().getTitle()) != null) {
			//TODO
		}
	}

}
