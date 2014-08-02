package tv.mineinthebox.essentials.events.backpack;

import static tv.mineinthebox.essentials.xEssentials.getManagers;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.ItemDespawnEvent;

import tv.mineinthebox.essentials.instances.Backpack;

public class BackpackDespawningEvent implements Listener {
	
	@EventHandler
	public void onItemDespawn(ItemDespawnEvent e) {
		if(getManagers().getBackPackManager().isBackpack(e.getEntity().getItemStack())) {
			Backpack pack = getManagers().getBackPackManager().getBackpackByItem(e.getEntity().getItemStack());
			pack.remove();
		}
	}
	
	@EventHandler
	public void onCombustEvent(EntityCombustEvent e) {
		if(e.getEntity() instanceof Item) {
			Item item = (Item) e.getEntity();
			if(getManagers().getBackPackManager().isBackpack(item.getItemStack())) {
				Backpack pack = getManagers().getBackPackManager().getBackpackByItem(item.getItemStack());
				pack.remove();
			}
		}
	}
	
}
