package tv.mineinthebox.essentials.events.shops;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.hook.Hooks;
import tv.mineinthebox.essentials.instances.ShopSign;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

public class SignNormalShopOpenEvent implements Listener {

	private final ShopSign shop = new ShopSign();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			//buy items
			if(e.getClickedBlock().getState() instanceof Sign) {
				Sign sign = (Sign) e.getClickedBlock().getState();
				if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(sign.getLine(0)) || shop.isStoredShopSign(sign.getLocation())) {
					if(e.getPlayer().hasPermission(PermissionKey.SIGN_SHOP_USE.getPermission())) {
						if(shop.isAttachedOnChest(e.getClickedBlock())) {
							if(shop.isUserChanged(sign.getLocation(), sign.getLine(0))) {
								sign.setLine(0, shop.getCompatUserName(sign.getLocation()));
								sign.update();
								e.getPlayer().sendMessage(ChatColor.GREEN + "detected user name change on the shop holder, its now updated!");
							}
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
								Chest chest = (Chest) shop.getChestFromSign(sign.getBlock());
								int amount = Integer.parseInt(sign.getLine(1));
								String[] split = sign.getLine(3).split(":");
								Material mat = Material.getMaterial(split[0].toUpperCase());
								Short subdata;
								if(split.length > 1) {
									subdata = Short.parseShort(split[1]);	
								} else {
									subdata = (short)0;
								}
								if(chest.getInventory().contains(mat)) {
									for(ItemStack stack : chest.getInventory()) {
										if(stack != null) {
											if(stack.getType() == mat && stack.getDurability() == subdata && stack.getAmount() == amount || stack.getAmount() > amount) {
												if(Configuration.getEconomyConfig().isEconomyEnabled()) {
													xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
													if((xp.getTotalEssentialsMoney()-cost) > -1) {
														ItemStack item = stack.clone();
														item.setAmount(amount);
														if(stack.getAmount() == amount) {
															chest.getInventory().remove(stack);
														} else {
															stack.setAmount((stack.getAmount()-amount));	
														}
														xp.payEssentialsMoney(cost);
														e.getPlayer().getInventory().addItem(item);
														e.getPlayer().updateInventory();
														e.getPlayer().sendMessage(ChatColor.GREEN + "you have successfully bought " + ChatColor.GRAY + item.getType().name() + "(" + sign.getLine(1) + "x)" + ChatColor.GREEN + " from the " + sign.getLine(0) + "!");
													} else {
														e.getPlayer().sendMessage(ChatColor.RED + "you don't have enough money to afford this!");
													}
												} else if(Hooks.isVaultEnabled()) {
													if(xEssentials.getManagers().getVaultManager().hasEnough(e.getPlayer().getName(), cost)) {
														ItemStack item = stack.clone();
														item.setAmount(amount);
														if(stack.getAmount() == amount) {
															chest.getInventory().remove(stack);
														} else {
															stack.setAmount((stack.getAmount()-amount));	
														}
														xEssentials.getManagers().getVaultManager().withdraw(e.getPlayer().getName(), cost);
														e.getPlayer().getInventory().addItem(item);
														e.getPlayer().updateInventory();
														e.getPlayer().sendMessage(ChatColor.GREEN + "you have successfully bought " + ChatColor.GRAY + item.getType().name() + "(" + sign.getLine(1) + "x)" + ChatColor.GREEN + " from the " + sign.getLine(0) + "!");
													} else {
														e.getPlayer().sendMessage(ChatColor.RED + "you don't have enough money to afford this!");
													}
												} else {
													xEssentials.getPlugin().log(e.getPlayer().getName() + " tried to buy something, but failed because there whas no Economy plugin found!, is Vault installed?", LogType.SEVERE);
													e.getPlayer().sendMessage(ChatColor.RED + "there is no such economy system enabled on the server!");
												}
												e.setCancelled(true);
												break;
											}
										}
									}
								} else {
									e.getPlayer().sendMessage(ChatColor.RED + "the shop is empty!");
								}
							} else {
								e.getPlayer().sendMessage(ChatColor.RED + "this is not a buy sign!");
							}
						} else {
							e.getPlayer().sendMessage(ChatColor.RED + "this sign has not a chest attached!");
						}
						e.setCancelled(true);
					} else {
						e.getPlayer().sendMessage(ChatColor.RED + "you are not allowed to do that!");
					}
				}
			}
		} else if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if(e.getClickedBlock().getState() instanceof Sign) {
				Sign sign = (Sign) e.getClickedBlock().getState();
				if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(sign.getLine(0)) || shop.isStoredShopSign(sign.getLocation())) {
					if(e.getPlayer().hasPermission(PermissionKey.SIGN_SHOP_USE.getPermission())) {
						if(shop.isAttachedOnChest(sign.getBlock())) {
							if(!e.getPlayer().isSneaking()) {
								if(shop.isUserChanged(sign.getLocation(), sign.getLine(0))) {
									sign.setLine(0, shop.getCompatUserName(sign.getLocation()));
									sign.update();
									e.getPlayer().sendMessage(ChatColor.GREEN + "detected user name change on the shop holder, its now updated!");
								}
								if(sign.getLine(2).contains("s") || sign.getLine(2).contains("S")) {
									Chest chest = shop.getChestFromSign(sign.getBlock());
									String[] items = sign.getLine(3).split(":");
									Material data = Material.getMaterial(items[0].toUpperCase());
									Short subdata;
									if(items.length > 1) {
										subdata = Short.parseShort(items[1]);
									} else {
										subdata = (short) 0;
									}
									Double money = shop.getSellPrice(sign.getLine(2));
									int amount = Integer.parseInt(sign.getLine(1));

									if(e.getItem() != null) {
										if(e.getItem().getType() == data && e.getItem().getDurability() == subdata) {
											ItemStack stack = e.getItem().clone();
											if(e.getItem().getAmount() == amount || e.getItem().getAmount() > amount) {
												int newAmount = (e.getItem().getAmount()-amount);
												if(Configuration.getEconomyConfig().isEconomyEnabled()) {
													xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
													xp.addEssentialsMoney(money);
													e.getPlayer().sendMessage(ChatColor.GREEN + "you have successfully sold your item to the shop!");
													stack.setAmount(amount);
													chest.getInventory().addItem(stack);
													if(e.getItem().getAmount() == amount) {
														e.getPlayer().getInventory().setItemInHand(null);
													} else {
														e.getItem().setAmount(newAmount);	
													}
												} else {
													if(Hooks.isVaultEnabled()) {
														xEssentials.getManagers().getVaultManager().desposit(e.getPlayer(), money);
														e.getPlayer().sendMessage(ChatColor.GREEN + "you have successfully sold your item to the shop!");
														stack.setAmount(amount);
														chest.getInventory().addItem(stack);
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
							e.getPlayer().sendMessage(ChatColor.RED + "this sign has not a chest attached!");
						}
					} else {
						xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
						if(!xp.containsShopSign(e.getClickedBlock().getLocation()) || !e.getPlayer().hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							e.getPlayer().sendMessage(ChatColor.RED + "you are not allowed todo that!");
						}
					}
				}
			}
		}
	}
}
