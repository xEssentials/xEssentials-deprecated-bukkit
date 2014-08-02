package tv.mineinthebox.essentials.events.backpackEvent;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.BackPack;

public class BackPackCloseEvent implements Listener {
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if(e.getInventory().getTitle().equalsIgnoreCase(ChatColor.DARK_GRAY + "Backpack:")) {
			if(xEssentials.getManagers().getBackPackManager().users.containsKey(e.getPlayer().getName())) {
				try {
					Player p = (Player)e.getPlayer();
					BackPack backpack = new BackPack(xEssentials.getManagers().getBackPackManager().users.get(e.getPlayer().getName()), p);
					
					//e.getPlayer().getInventory().remove(BackPackData.users.get(e.getPlayer().getName()));
					//e.getPlayer().getInventory().addItem(backpack);
					
					backpack.saveBackPack(e.getView().getTopInventory());
					
					ItemStack item2 = new BackPack((ItemStack)backpack, p);
					
					p.setItemInHand(item2);
					
					xEssentials.getManagers().getBackPackManager().users.remove(e.getPlayer().getName());
					p.sendMessage(ChatColor.GREEN + "closing backpack!...");
					p.playSound(e.getPlayer().getLocation(), Sound.CHEST_CLOSE, 1F, 1F);
				
					
				
				} catch (Exception e1) {}
			}
		}
	}
}
