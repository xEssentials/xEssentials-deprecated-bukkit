package tv.mineinthebox.essentials.events.shops;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.hook.Hooks;
import tv.mineinthebox.essentials.instances.ShopSign;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

public class SignAdminShopOpenEvent implements Listener {

	private HashMap<String, Sign> signData = new HashMap<String, Sign>();
	private final ShopSign shop = new ShopSign();

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			//buy items
			if(e.getClickedBlock().getState() instanceof Sign) {
				Sign sign = (Sign) e.getClickedBlock().getState();
				if(sign.getLine(0).equalsIgnoreCase(Configuration.getShopConfig().getAdminPrefix()) || sign.getLine(0).equalsIgnoreCase(ChatColor.GOLD + "[backpack]")) {
					if(e.getPlayer().hasPermission(PermissionKey.SIGN_SHOP_USE.getPermission())) {
						if(sign.getLine(2).contains("b") || sign.getLine(2).contains("B")) {
							Double cost = shop.getBuyPrice(sign.getLine(2));
							if(Configuration.getEconomyConfig().isEconomyEnabled()) {
								xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
								if((xp.getTotalEssentialsMoney()-cost) < 0.0) {
									e.getPlayer().sendMessage(ChatColor.RED + "you cannot afford this!");
									e.setCancelled(true);
									return;
								}
							} else if(Hooks.isVaultEnabled()) {
								if(!xEssentials.getManagers().getVaultManager().hasEnough(e.getPlayer().getName(), cost)) {
									e.getPlayer().sendMessage(ChatColor.RED + "you cannot afford this!");
									e.setCancelled(true);
									return;
								}
							}
							Inventory inv = Bukkit.createInventory(null, 36, ChatColor.stripColor(sign.getLine(0)) + " | buy");
							String[] items = sign.getLine(3).split(":");
							ItemStack item = new ItemStack(Material.getMaterial(items[0].toUpperCase()), Integer.parseInt(sign.getLine(1)));
							if(items.length > 1) {
								item.setDurability((short)Short.parseShort(items[1]));	
							}
							for(int i = 0; i < inv.getSize(); i++) {
								inv.setItem(i, item);
							}
							signData.put(e.getPlayer().getName(), sign);
							e.getPlayer().openInventory(inv);
							e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.CHEST_OPEN, 1F, 1F);
							e.setCancelled(true);
						} else {
							e.getPlayer().sendMessage(ChatColor.RED + "this is not a buy sign!");
						}
					} else {
						e.getPlayer().sendMessage(ChatColor.RED + "you are not allowed to do that!");
					}
				}
			}
		} else if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if(e.getClickedBlock().getState() instanceof Sign) {
				Sign sign = (Sign) e.getClickedBlock().getState();
				if(sign.getLine(0).equalsIgnoreCase(Configuration.getShopConfig().getAdminPrefix()) || sign.getLine(0).equalsIgnoreCase(ChatColor.GOLD + "[backpack]")) {
					if(e.getPlayer().hasPermission(PermissionKey.SIGN_SHOP_USE.getPermission())) {
						if(!e.getPlayer().isSneaking()) {
							if(sign.getLine(2).contains("s") || sign.getLine(2).contains("S")) {

								String[] items = sign.getLine(3).split(":");
								Material data = Material.getMaterial(items[0].toUpperCase());
								Short subdata;
								if(items.length > 1) {
									subdata = Short.parseShort(items[1]);
								} else {
									subdata = (short)0;
								}
								Double money = shop.getSellPrice(sign.getLine(2));
								int amount = Integer.parseInt(sign.getLine(1));

								if(e.getItem() != null) {
									if(e.getItem().getType() == data && e.getItem().getDurability() == subdata) {
										if(e.getItem().getAmount() == amount || e.getItem().getAmount() > amount) {
											int newAmount = (e.getItem().getAmount()-amount);
											if(Configuration.getEconomyConfig().isEconomyEnabled()) {
												xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
												xp.addEssentialsMoney(money);
												e.getPlayer().sendMessage(ChatColor.GREEN + "you have successfully sold your item to the shop!");
												if(e.getItem().getAmount() == amount) {
													e.getPlayer().getInventory().setItemInHand(null);
												} else {
													e.getItem().setAmount(newAmount);	
												}
											} else {
												if(Hooks.isVaultEnabled()) {
													xEssentials.getManagers().getVaultManager().desposit(e.getPlayer(), money);
													e.getPlayer().sendMessage(ChatColor.GREEN + "you have successfully sold your item to the shop!");
													if(e.getItem().getAmount() == amount) {
														e.getPlayer().getInventory().setItemInHand(null);
													} else {
														e.getItem().setAmount(newAmount);	
													}
												} else {
													xEssentials.getPlugin().log(e.getPlayer().getName() + " tried to sell something, but failed because there whas no Economy plugin found!, is Vault installed?", LogType.SEVERE);
													e.getPlayer().sendMessage(ChatColor.RED + "there is no such economy system enabled on the server!");
												}
											}
										} else {
											e.getPlayer().sendMessage(ChatColor.RED + "you don't have enough items to sell!");
										}
									} else {
										e.getPlayer().sendMessage(ChatColor.RED + "you tried to sell a invalid item!");
									}
								} else {
									e.getPlayer().sendMessage(ChatColor.RED + "you cannot sell air");
								}
							} else {
								e.getPlayer().sendMessage(ChatColor.RED + "this is not a sell sign!");
							}
							e.setCancelled(true);
						}
					} else {
						e.getPlayer().sendMessage(ChatColor.RED + "you are not allowed to do that!");
					}
				}
			}
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(e.getCurrentItem() != null) {
			if(e.getInventory().getTitle().equalsIgnoreCase(Configuration.getShopConfig().getAdminPrefix() + " | buy")) {
				Player p = (Player) e.getWhoClicked();
				Sign sign = signData.get(p.getName());

				Double cost = shop.getBuyPrice(sign.getLine(2));
				int amount = Integer.parseInt(sign.getLine(1));
				String[] itemdata = sign.getLine(3).split(":");
				Material mat = Material.getMaterial(itemdata[0].toUpperCase());
				Short subValue;
				if(itemdata.length > 1) {
					subValue = Short.parseShort(itemdata[1]);
				} else {
					subValue = (short) 0;
				}

				if(Configuration.getEconomyConfig().isEconomyEnabled()) {
					xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(p.getName());
					if(e.getCurrentItem().getType() == mat && e.getCurrentItem().getDurability() == subValue && e.getCurrentItem().getAmount() == amount) {
						if((xp.getTotalEssentialsMoney()-cost) > -1) {
							xp.payEssentialsMoney(cost);
							ItemStack item = e.getCurrentItem().clone();
							p.getInventory().addItem(item);
							p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1F, 1F);
							p.sendMessage(ChatColor.GREEN + "you have successfully bought " + ChatColor.GRAY + item.getType().name() + "(" + sign.getLine(1) + "x)" + ChatColor.GREEN + " from the " + Configuration.getShopConfig().getAdminPrefix() + "!");
						} else {
							p.closeInventory();
							p.sendMessage(ChatColor.RED + "you don't have enough money to afford this!");
						}
					}
				} else {
					if(Hooks.isVaultEnabled()) {
						if(e.getCurrentItem().getType() == mat && e.getCurrentItem().getDurability() == subValue && e.getCurrentItem().getAmount() == amount) {
							if(xEssentials.getManagers().getVaultManager().hasEnough(p.getName(), cost)) {
								xEssentials.getManagers().getVaultManager().withdraw(p.getName(), cost);
								ItemStack item = e.getCurrentItem().clone();
								p.getInventory().addItem(item);
								p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1F, 1F);
								p.sendMessage(ChatColor.GREEN + "you have successfully bought " + ChatColor.GRAY + item.getType().name() + "(" + sign.getLine(1) + "x)" + ChatColor.GREEN + " from the " + Configuration.getShopConfig().getAdminPrefix() + "!");
							} else {
								p.closeInventory();
								p.sendMessage(ChatColor.RED + "you don't have enough money to afford this!");
							}
						}
					} else {
						xEssentials.getPlugin().log(p.getName() + " tried to buy something, but failed because there whas no Economy plugin found!, is Vault installed?", LogType.SEVERE);
						p.closeInventory();
						p.sendMessage(ChatColor.RED + "there is no such economy system enabled on the server!");
					}
				}
				e.setCancelled(true);
			} else if(e.getInventory().getTitle().equalsIgnoreCase("[backpack]" + " | buy")) {
				Player p = (Player) e.getWhoClicked();
				Sign sign = signData.get(p.getName());

				Double cost = shop.getBuyPrice(sign.getLine(2));
				int amount = Integer.parseInt(sign.getLine(1));
				String[] itemdata = sign.getLine(3).split(":");
				Material mat = Material.getMaterial(itemdata[0].toUpperCase());
				Short subValue;
				if(itemdata.length > 1) {
					subValue = Short.parseShort(itemdata[1]);
				} else {
					subValue = (short) 0;
				}

				if(Configuration.getEconomyConfig().isEconomyEnabled()) {
					xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(p.getName());
					if(e.getCurrentItem().getType() == mat && e.getCurrentItem().getDurability() == subValue && e.getCurrentItem().getAmount() == amount) {
						if((xp.getTotalEssentialsMoney()-cost) > -1) {
							xp.payEssentialsMoney(cost);
							ItemStack item = e.getCurrentItem().clone();
							//BackPack backpack = new BackPack(item.getType(), item.getDurability(), item.getAmount());
							//p.getInventory().addItem(backpack);
							p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1F, 1F);
							p.sendMessage(ChatColor.GREEN + "you have successfully bought " + ChatColor.GRAY + item.getType().name() + "(" + sign.getLine(1) + "x)" + ChatColor.GREEN + " from the " + Configuration.getShopConfig().getAdminPrefix() + "!");
						} else {
							p.closeInventory();
							p.sendMessage(ChatColor.RED + "you don't have enough money to afford this!");
						}
					}
				} else {
					if(Hooks.isVaultEnabled()) {
						if(e.getCurrentItem().getType() == mat && e.getCurrentItem().getDurability() == subValue && e.getCurrentItem().getAmount() == amount) {
							if(xEssentials.getManagers().getVaultManager().hasEnough(p.getName(), cost)) {
								xEssentials.getManagers().getVaultManager().withdraw(p.getName(), cost);
								ItemStack item = e.getCurrentItem().clone();
								//BackPack backpack = new BackPack(item.getType(), item.getDurability(), item.getAmount());
								//p.getInventory().addItem(backpack);
								p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1F, 1F);
								p.sendMessage(ChatColor.GREEN + "you have successfully bought " + ChatColor.GRAY + item.getType().name() + "(" + sign.getLine(1) + "x)" + ChatColor.GREEN + " from the " + Configuration.getShopConfig().getAdminPrefix() + "!");
							} else {
								p.closeInventory();
								p.sendMessage(ChatColor.RED + "you don't have enough money to afford this!");
							}
						}
					} else {
						xEssentials.getPlugin().log(p.getName() + " tried to buy something, but failed because there whas no Economy plugin found!, is Vault installed?", LogType.SEVERE);
						p.closeInventory();
						p.sendMessage(ChatColor.RED + "there is no such economy system enabled on the server!");
					}
				}
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if(e.getInventory().getTitle().equalsIgnoreCase(Configuration.getShopConfig().getAdminPrefix() + " | buy")) {
			Player p = (Player) e.getPlayer();
			p.playSound(p.getPlayer().getLocation(), Sound.VILLAGER_NO, 1F, 1F);
			signData.remove(e.getPlayer().getName());
		} else if(e.getInventory().getTitle().equalsIgnoreCase("[backpack]" + " | buy")) {
			Player p = (Player) e.getPlayer();
			p.playSound(p.getPlayer().getLocation(), Sound.VILLAGER_NO, 1F, 1F);
			signData.remove(e.getPlayer().getName());
		}
	}

}
