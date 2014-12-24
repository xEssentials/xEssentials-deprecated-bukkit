package tv.mineinthebox.essentials.events.shops;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.hook.Hooks;
import tv.mineinthebox.essentials.instances.Shop;

public class AdminShopInventoryEvent implements Listener {

	@EventHandler
	public void onOpen(InventoryClickEvent e) {
		if(e.isCancelled()) {
			return;
		}

		if(e.getInventory().getTitle().equalsIgnoreCase("shop:")) {
			if(e.isShiftClick()) {
				e.setCancelled(true);
				return;
			}
			if(e.getCurrentItem() != null) {
				Player p = (Player) e.getWhoClicked();
				Sign sign = (Sign)p.getMetadata("shop").get(0).value();
				Shop shop = new Shop(sign);

				if(Configuration.getEconomyConfig().isEconomyEnabled()) {
					if(p.getInventory().firstEmpty() != -1) {
						if(xEssentials.getManagers().getEcoManager().hasEnough(e.getWhoClicked().getName(), shop.getBuyPrice())) {
							xEssentials.getManagers().getEcoManager().withdrawMoney(p.getName(), shop.getBuyPrice());
							p.getInventory().addItem(shop.getItem());
							p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1F, 1F);
							p.sendMessage(ChatColor.GREEN + "[Shop] " + ChatColor.GRAY + " you successfully bought a item from the admin shop!");
						} else {
							p.sendMessage(ChatColor.RED + "you cannot afford this!");
							p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1F, 1F);
							p.closeInventory();
						}
					} else {
						p.sendMessage(ChatColor.RED + "please make sure you have atleast one empty slot!");
						p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1F, 1F);
						p.closeInventory();
					}
				} else if(Hooks.isVaultEnabled()) {
					if(p.getInventory().firstEmpty() != -1) {
						if(xEssentials.getManagers().getVaultManager().hasEnough(e.getWhoClicked().getName(), shop.getBuyPrice())) {
							xEssentials.getManagers().getVaultManager().withdraw(p.getName(), shop.getBuyPrice());
							p.getInventory().addItem(shop.getItem());
							p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1F, 1F);
							p.sendMessage(ChatColor.GREEN + "[Shop] " + ChatColor.GRAY + " you successfully bought a item from the admin shop!");
						} else {
							p.sendMessage(ChatColor.RED + "you cannot afford this!");
							p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1F, 1F);
							p.closeInventory();
						}
					} else {
						p.sendMessage(ChatColor.RED + "please make sure you have atleast one empty slot!");
						p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1F, 1F);
						p.closeInventory();
					}
				}

			}
			e.setCancelled(true);
		}

	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		if(p.hasMetadata("shop")) {
			p.removeMetadata("shop", xEssentials.getPlugin());
		}
	}

}
