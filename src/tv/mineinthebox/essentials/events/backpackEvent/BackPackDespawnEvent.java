package tv.mineinthebox.essentials.events.backpackEvent;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.inventory.ItemStack;

import tv.mineinthebox.essentials.instances.BackPack;

public class BackPackDespawnEvent implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDespawn(EntityCombustEvent e) {
		if(e.getEntity() instanceof Item) {
			ItemStack item = ((Item)e.getEntity()).getItemStack();
			if(item.hasItemMeta()) {
				if(item.getItemMeta().hasDisplayName() && item.getItemMeta().hasLore()) {
					if(item.getItemMeta().getLore().size() >= 5) {
						if(item.getItemMeta().getLore().get(5) instanceof String) {
							try {
								BackPack pack = new BackPack(item);
								pack.remove();
							} catch(Exception r) {}
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDespawn(ItemDespawnEvent e) {
		ItemStack item = e.getEntity().getItemStack();
		if(item.hasItemMeta()) {
			if(item.getItemMeta().hasDisplayName() && item.getItemMeta().hasLore()) {
				if(item.getItemMeta().getLore().size() >= 5) {
					if(item.getItemMeta().getLore().get(5) instanceof String) {
						try {
							BackPack pack = new BackPack(item);
							pack.remove();
						} catch(Exception r) {}
					}
				}
			}
		}
	}

}
