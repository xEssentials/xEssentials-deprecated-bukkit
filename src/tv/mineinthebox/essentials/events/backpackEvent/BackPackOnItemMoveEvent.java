package tv.mineinthebox.essentials.events.backpackEvent;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import tv.mineinthebox.essentials.xEssentials;

public class BackPackOnItemMoveEvent implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(e.getCurrentItem() instanceof ItemStack) {
			if(e.getInventory().getTitle().equalsIgnoreCase(ChatColor.DARK_GRAY + "Backpack:")) {
				if(xEssentials.getManagers().getBackPackManager().users.containsKey(e.getWhoClicked().getName())) {
					if(e.getCurrentItem().equals(xEssentials.getManagers().getBackPackManager().users.get(e.getWhoClicked().getName()))) {
						Player p = (Player) e.getWhoClicked();
						p.sendMessage(ChatColor.RED + "you cannot move the backpack you have opened!");
						p.playSound(p.getLocation(), Sound.HORSE_ARMOR, 1F, 1F);
						e.setCancelled(true);
					}
				}
			}
		}
	}

}
