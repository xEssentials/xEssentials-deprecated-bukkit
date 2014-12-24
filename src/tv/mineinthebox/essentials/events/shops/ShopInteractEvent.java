package tv.mineinthebox.essentials.events.shops;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.hook.Hooks;
import tv.mineinthebox.essentials.instances.Shop;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;

public class ShopInteractEvent implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.isCancelled()) {
			return;
		}
		
		if(e.getPlayer().isSneaking()) {
			return;
		}

		if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.WALL_SIGN || e.getClickedBlock().getType() == Material.SIGN_POST) {
				Sign sign = (Sign)e.getClickedBlock().getState();
				if(sign.getLine(0).equalsIgnoreCase(Configuration.getShopConfig().getAdminShopPrefix())) {
					Shop shop = new Shop(sign);
					if(shop.hasSellPrice()) {
						if(e.getPlayer().getInventory().containsAtLeast(shop.getItem(), shop.getAmount())) {
							if(Configuration.getEconomyConfig().isEconomyEnabled()) {
								xEssentials.getManagers().getEcoManager().depositMoney(e.getPlayer().getName(), shop.getSellPrice());
								e.getPlayer().getInventory().removeItem(shop.getItem());
								e.getPlayer().sendMessage(ChatColor.GREEN + "[Shop]" + ChatColor.GRAY + " you selled your items for " + shop.getSellPrice());
							} else if(Hooks.isVaultEnabled()) {
								xEssentials.getManagers().getVaultManager().desposit(e.getPlayer(), shop.getSellPrice());
								e.getPlayer().getInventory().removeItem(shop.getItem());
								e.getPlayer().sendMessage(ChatColor.GREEN + "[Shop]" + ChatColor.GRAY + " you selled your items for " + shop.getSellPrice());
							} else {
								e.getPlayer().sendMessage(ChatColor.RED + "no vault installed!");
							}
						} else {
							e.getPlayer().sendMessage(ChatColor.RED + "you dont have enough of these items to sell!");
						}
					} else {
						e.getPlayer().sendMessage(ChatColor.RED + "you cannot sell items in this shop!");
					}
					e.setCancelled(true);
				} else if(isShopSign(sign.getLines())) {
					Shop shop = getShop(sign.getLocation());
					if(shop != null) {
						if(shop.hasSellPrice()) {
							if(e.getPlayer().getInventory().containsAtLeast(shop.getItem(), shop.getAmount())) {
								if(Configuration.getEconomyConfig().isEconomyEnabled()) {
									xEssentials.getManagers().getEcoManager().depositMoney(e.getPlayer().getName(), shop.getSellPrice());
									e.getPlayer().getInventory().removeItem(shop.getItem());
									shop.getShopChest().getInventory().addItem(shop.getItem());
									e.getPlayer().sendMessage(ChatColor.GREEN + "[Shop]" + ChatColor.GRAY + " you selled your items for " + shop.getSellPrice());
								} else if(Hooks.isVaultEnabled()) {
									xEssentials.getManagers().getVaultManager().desposit(e.getPlayer(), shop.getSellPrice());
									e.getPlayer().getInventory().removeItem(shop.getItem());
									shop.getShopChest().getInventory().addItem(shop.getItem());
									e.getPlayer().sendMessage(ChatColor.GREEN + "[Shop]" + ChatColor.GRAY + " you selled your items for " + shop.getSellPrice());
								} else {
									e.getPlayer().sendMessage(ChatColor.RED + "no vault installed!");
								}
							} else {
								e.getPlayer().sendMessage(ChatColor.RED + "you dont have enough of these items to sell!");
							}
						} else {
							e.getPlayer().sendMessage(ChatColor.RED + "you cannot sell items in this shop!");
						}
					} else {
						e.getPlayer().sendMessage(ChatColor.RED + "this shop does not exist!");
					}
					e.setCancelled(true);
				}
			}
		} else if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.WALL_SIGN || e.getClickedBlock().getType() == Material.SIGN_POST) {
				Sign sign = (Sign)e.getClickedBlock().getState();
				if(sign.getLine(0).equalsIgnoreCase(Configuration.getShopConfig().getAdminShopPrefix())) {
					//TO-DO inventory.
					Shop shop = new Shop(sign);
					if(Configuration.getEconomyConfig().isEconomyEnabled()) {
						if(xEssentials.getManagers().getEcoManager().hasEnough(e.getPlayer().getName(), shop.getBuyPrice())) {
							if(e.getPlayer().getInventory().firstEmpty() != -1) {
								Inventory inv = Bukkit.createInventory(null, 36, "shop:");
								for(int i = 0; i < inv.getSize(); i++) {
									inv.setItem(i, shop.getItem());
								}
								e.getPlayer().setMetadata("shop", new FixedMetadataValue(xEssentials.getPlugin(), sign));
								e.getPlayer().openInventory(inv);
							} else {
								e.getPlayer().sendMessage(ChatColor.RED + "you need atleast have one inventory slot open!");
							}
						} else {
							e.getPlayer().sendMessage(ChatColor.RED + "you don't have enough money to afford this!");
						}
					} else if(Hooks.isVaultEnabled()) {
						if(xEssentials.getManagers().getVaultManager().hasEnough(e.getPlayer().getName(), shop.getBuyPrice())) {
							if(e.getPlayer().getInventory().firstEmpty() != -1) {
								Inventory inv = Bukkit.createInventory(null, 36, "shop:");
								for(int i = 0; i < inv.getSize(); i++) {
									inv.setItem(i, shop.getItem());
								}
								e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.CHEST_OPEN, 1F, 1F);
								e.getPlayer().openInventory(inv);
							} else {
								e.getPlayer().sendMessage(ChatColor.RED + "you need atleast have one inventory slot open!");
							}
						} else {
							e.getPlayer().sendMessage(ChatColor.RED + "you don't have enough money to afford this!");
						}
					} else {
						e.getPlayer().sendMessage(ChatColor.RED + "no vault installed!");
					}
					e.setCancelled(true);
				} else if(isShopSign(sign.getLines())) {
					Shop shop = getShop(sign.getLocation());
					if(shop.hasBuyPrice()) {
						if(e.getPlayer().getInventory().firstEmpty() != -1) {
							if(Configuration.getEconomyConfig().isEconomyEnabled()) {
								if(xEssentials.getManagers().getEcoManager().hasEnough(e.getPlayer().getName(), shop.getBuyPrice())) {
									if(!shop.isSoldOut()) {
										xEssentials.getManagers().getEcoManager().withdrawMoney(e.getPlayer().getName(), shop.getBuyPrice());
										xEssentials.getManagers().getEcoManager().depositMoney(sign.getLine(0), shop.getBuyPrice());
										shop.getShopChest().getInventory().removeItem(shop.getItem());
										e.getPlayer().getInventory().addItem(shop.getItem());
										e.getPlayer().sendMessage(ChatColor.GREEN + "[Shop] " + ChatColor.GRAY + "you successfully bought " + shop.getItem().getType().name() + " " + shop.getAmount() + "x for " + shop.getBuyPrice());
									} else {
										e.getPlayer().sendMessage(ChatColor.RED + "the shop is sold out!");
									}
								} else {
									e.getPlayer().sendMessage(ChatColor.RED + "you cannot afford this!");
								}
							} else if(Hooks.isVaultEnabled()) {
								if(xEssentials.getManagers().getVaultManager().hasEnough(e.getPlayer().getName(), shop.getBuyPrice())) {
									if(!shop.isSoldOut()) {
										xEssentials.getManagers().getVaultManager().withdraw(e.getPlayer().getName(), shop.getBuyPrice());
										xEssentials.getManagers().getEcoManager().depositMoney(sign.getLine(0), shop.getBuyPrice());
										shop.getShopChest().getInventory().removeItem(shop.getItem());
										e.getPlayer().getInventory().addItem(shop.getItem());
										e.getPlayer().sendMessage(ChatColor.GREEN + "[Shop] " + ChatColor.GRAY + "you successfully bought " + shop.getItem().getType().name() + " " + shop.getAmount() + "x for " + shop.getBuyPrice());
									} else {
										e.getPlayer().sendMessage(ChatColor.RED + "the shop is sold out!");
									}
								} else {
									e.getPlayer().sendMessage(ChatColor.RED + "you cannot afford this!");
								}
							} else {
								e.getPlayer().sendMessage(ChatColor.RED + "no vault installed!");
							}
						} else {
							e.getPlayer().sendMessage(ChatColor.RED + "you need atleast one empty slot!");
						}
					} else {
						e.getPlayer().sendMessage(ChatColor.RED + "this is not a buy sign!");
					}
					e.setCancelled(true);
				}
			}
		}
	}

	private boolean isShopSign(String[] lines) {
		if(lines[1].matches("[0-9]+")) {
			int amount = Integer.parseInt(lines[1]);
			if(amount <= 64) {
				if(lines[2].matches("(?i)^b \\d+ s \\d+$") || lines[2].matches("(?i)^b \\d+$") || lines[2].matches("(?i)^s \\d+$")) {
					if(lines[3].matches("([0-9]+):([0-9]+)")) {
						return true;
					} else {
						try {
							Material.valueOf(lines[3].toUpperCase().replaceAll(" ", "_"));
							return true;
						} catch(IllegalArgumentException e) {
							return false;
						}
					}
				}
			}
		}
		return false;
	}

	private Shop getShop(Location loc) {
		for(XOfflinePlayer off : xEssentials.getManagers().getPlayerManager().getOfflinePlayers()) {
			if(off.isShop(loc)) {
				return off.getShop(loc);
			}
		}
		return null;
	}

}
