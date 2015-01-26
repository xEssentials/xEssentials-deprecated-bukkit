package tv.mineinthebox.essentials.events.backpack;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.ItemDespawnEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.Backpack;

public class BackpackDespawningEvent implements Listener {

	private final xEssentials pl;
	
	public BackpackDespawningEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onItemDespawn(ItemDespawnEvent e) {
		if(pl.getManagers().getBackPackManager().isBackpack(e.getEntity().getItemStack())) {
			Backpack pack = pl.getManagers().getBackPackManager().getBackpackByItem(e.getEntity().getItemStack());
			pack.remove();
		}
	}
	
	@EventHandler
	public void onCombustEvent(EntityCombustEvent e) {
		if(e.getEntity() instanceof Item) {
			Item item = (Item) e.getEntity();
			if(pl.getManagers().getBackPackManager().isBackpack(item.getItemStack())) {
				Backpack pack = pl.getManagers().getBackPackManager().getBackpackByItem(item.getItemStack());
				pack.remove();
			}
		}
	}
}
