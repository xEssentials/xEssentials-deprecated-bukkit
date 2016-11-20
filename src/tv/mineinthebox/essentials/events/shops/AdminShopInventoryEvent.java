package tv.mineinthebox.essentials.events.shops;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.hook.Hooks;
import tv.mineinthebox.essentials.instances.Shop;
import tv.mineinthebox.essentials.interfaces.EventTemplate;

public class AdminShopInventoryEvent extends EventTemplate implements Listener {
	
	public AdminShopInventoryEvent(xEssentials pl) {
		super(pl, "Shop");
	}

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

				if(pl.getConfiguration().getEconomyConfig().isEconomyEnabled()) {
					if(p.getInventory().firstEmpty() != -1) {
						if(pl.getManagers().getEcoManager().hasEnough(e.getWhoClicked().getName(), shop.getBuyPrice())) {
							pl.getManagers().getEcoManager().withdrawMoney(p.getName(), shop.getBuyPrice());
							p.getInventory().addItem(shop.getItem());
							p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 1F, 1F);
							sendMessage(p, " you successfully bought a item from the admin shop!");
						} else {
							sendMessage(p, ChatColor.RED + "you cannot afford this!");
							p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
							p.closeInventory();
						}
					} else {
						sendMessage(p, ChatColor.RED + "please make sure you have atleast one empty slot!");
						p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
						p.closeInventory();
					}
				} else if(Hooks.isVaultEcoEnabled()) {
					if(p.getInventory().firstEmpty() != -1) {
						if(pl.getManagers().getVaultManager().hasEnough(e.getWhoClicked().getName(), shop.getBuyPrice())) {
							pl.getManagers().getVaultManager().withdraw(p.getName(), shop.getBuyPrice());
							p.getInventory().addItem(shop.getItem());
							p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 1F, 1F);
							sendMessage(p," you successfully bought a item from the admin shop!");
						} else {
							sendMessage(p, ChatColor.RED + "you cannot afford this!");
							p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
							p.closeInventory();
						}
					} else {
						sendMessage(p, ChatColor.RED + "please make sure you have atleast one empty slot!");
						p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
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
			p.removeMetadata("shop", pl);
		}
	}

}
