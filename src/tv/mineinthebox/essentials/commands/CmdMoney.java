package tv.mineinthebox.essentials.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.events.customevents.PlayerTransactionEvent;
import tv.mineinthebox.essentials.instances.xEssentialsOfflinePlayer;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

public class CmdMoney {

	private List<String> getPlayerByName(String p) {
		List<String> s = new ArrayList<String>();
		for(xEssentialsOfflinePlayer name : xEssentials.getManagers().getPlayerManager().getOfflinePlayers()) {
			if(name.getUser().toUpperCase().startsWith(p.toUpperCase())) {
				s.add(name.getUser());
			}
		}
		return s;
	}

	public List<String> onTabComplete(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("money")) {
			if(sender.hasPermission(PermissionKey.CMD_MONEY.getPermission())) {
				if(args.length == 2) {
					if(args[0].equalsIgnoreCase("clear") && sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
						return getPlayerByName(args[1]);
					} else if(args[0].equalsIgnoreCase("pay")) {
						return getPlayerByName(args[1]);
					} else if(args[0].equalsIgnoreCase("give")) {
						if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							return getPlayerByName(args[1]);
						}
					} else if(args[0].equalsIgnoreCase("set")) {
						if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							return getPlayerByName(args[1]);
						}
					} else if(args[0].equalsIgnoreCase("remove")) {
						if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							return getPlayerByName(args[1]);
						}
					}
				}
			}
		}
		return null;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("money")) {
			if(sender.hasPermission(PermissionKey.CMD_MONEY.getPermission())) {
				if(args.length == 0) {
					if(sender instanceof Player) {
						xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
						sender.sendMessage(ChatColor.GOLD + ".oO___[your money amount]___Oo.");
						sender.sendMessage(ChatColor.GRAY + "your current amount is: " + ChatColor.GREEN + xp.getTotalEssentialsMoney() + Configuration.getEconomyConfig().getCurency());
					} else {
						Warnings.getWarnings(sender).consoleMessage();
					}
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[xEssentials economy help]___Oo.");
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/money " + ChatColor.WHITE + ": shows your money amount!");
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/money help " + ChatColor.WHITE + ": shows help");
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/money top " + ChatColor.WHITE + ": shows a list from 1 to 10 who has the most money");
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/money pay <player> <amount> " + ChatColor.WHITE + ": pay money to a player");
						if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/money clear " + ChatColor.WHITE + ": clears your bank acount!");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/money clear <player> " + ChatColor.WHITE + ": clears the bank acount of a specific player");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/money give <player> <amount> " + ChatColor.WHITE + ": give a player a amount of money");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/money set <player> <amount> " + ChatColor.WHITE + ": set the current amount of money, this act as a reset");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/money remove <player> <amount> " + ChatColor.WHITE + ": withdraw the players money");
						}
					} else if(args[0].equalsIgnoreCase("top")) {
						SortedMap<Double, String> map = new TreeMap<Double, String>().descendingMap();
						for(xEssentialsOfflinePlayer off : xEssentials.getManagers().getPlayerManager().getOfflinePlayers()) {
							if(off.hasEssentialsMoney()) {
								map.put(off.getTotalEssentialsMoney(), off.getUser());
							}
						}
						Iterator<Entry<Double, String>> it = map.entrySet().iterator();
						int i = 1;
						sender.sendMessage(ChatColor.GOLD + ".oO___[Money top]___Oo.");
						while(i < 11) {
							if(it.hasNext()) {
								Entry<Double, String> entry = (Entry<Double, String>) it.next();
								sender.sendMessage(ChatColor.GREEN +""+ i + ": " + ChatColor.GRAY + entry.getValue() + ": " + ChatColor.GREEN + entry.getKey() + Configuration.getEconomyConfig().getCurency());	
								i++;
							} else {
								break;
							}
						}
					} else if(args[0].equalsIgnoreCase("clear")) {
						if(sender instanceof Player) {
							if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
								xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
								xp.clearMoney();
								sender.sendMessage(ChatColor.GREEN + "you successfully cleared your banks money!");
							} else {
								Warnings.getWarnings(sender).noPermission();
							}
						} else {
							Warnings.getWarnings(sender).consoleMessage();
						}
					}
				} else if(args.length == 2) {
					if(args[0].equalsIgnoreCase("clear")) {
						if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(args[1])) {
								if(xEssentials.getManagers().getPlayerManager().isOnline(args[1])) {
									xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(args[1]);
									xp.clearMoney();
									sender.sendMessage(ChatColor.GREEN + "you successfully cleared "+ xp.getUser() +" banks money!");
								} else {
									xEssentialsOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[1]);
									off.clearMoney();
									sender.sendMessage(ChatColor.GREEN + "you successfully cleared "+ off.getUser() +" banks money!");
								}
							} else {
								Warnings.getWarnings(sender).playerHasNeverPlayedBefore();
							}
						} else {
							Warnings.getWarnings(sender).noPermission();
						}
					}
				} else if(args.length == 3) {
					if(args[0].equalsIgnoreCase("pay")) {
						if(sender instanceof Player) {
							xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
							if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(args[1])) {
								if(xEssentials.getManagers().getPlayerManager().isOnline(args[1])) {
									xEssentialsPlayer xpp = xEssentials.getManagers().getPlayerManager().getPlayer(args[1]);
									try {
										Double money = Double.parseDouble(args[2]);
										if(xp.hasPlayerEnoughMoneyFromPrice(money)) {
											xp.payEssentialsMoney(money, xpp);
											sender.sendMessage(ChatColor.GREEN + "you have successfully paid " + xpp.getUser() + " " + money + Configuration.getEconomyConfig().getCurency());
											xpp.getPlayer().sendMessage(ChatColor.GREEN + sender.getName() + " has paid " + money + Configuration.getEconomyConfig().getCurency() + " to you!");
											Bukkit.getPluginManager().callEvent(new PlayerTransactionEvent(xp.getPlayer(), money, xpp.getUser()));
										} else {
											sender.sendMessage(ChatColor.RED + "you don't have enough money to pay this player!");
										}
									} catch(NumberFormatException e) {
										sender.sendMessage(ChatColor.RED + "invalid money usage on the third argument!");
									}
								} else {
									xEssentialsOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[1]);
									try {
										Double money = Double.parseDouble(args[2]);
										if(xp.hasPlayerEnoughMoneyFromPrice(money)) {
											xp.payEssentialsMoney(money, off);
											sender.sendMessage(ChatColor.GREEN + "you have successfully paid " + off.getUser() + " " + money + Configuration.getEconomyConfig().getCurency());
											Bukkit.getPluginManager().callEvent(new PlayerTransactionEvent(xp.getPlayer(), money, off.getUser()));
										} else {
											sender.sendMessage(ChatColor.RED + "you don't have enough money to pay this player!");
										}
									} catch(NumberFormatException e) {
										sender.sendMessage(ChatColor.RED + "invalid money usage on the third argument!");
									}
								}
							} else {
								Warnings.getWarnings(sender).playerHasNeverPlayedBefore();
							}
						} else {
							Warnings.getWarnings(sender).consoleMessage();
						}
					} else if(args[0].equalsIgnoreCase("give")) {
						if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(args[1])) {
								if(xEssentials.getManagers().getPlayerManager().isOnline(args[1])) {
									xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(args[1]);
									try {
										Double money = Double.parseDouble(args[2]);
										xp.addEssentialsMoney(money);
										sender.sendMessage(ChatColor.GREEN + "you successfully gave " + xp.getUser() + " " + money + Configuration.getEconomyConfig().getCurency());
										xp.getPlayer().sendMessage(ChatColor.GREEN + sender.getName() + " has given you " + money + Configuration.getEconomyConfig().getCurency());
									} catch(NumberFormatException e) {
										sender.sendMessage(ChatColor.RED + "invalid money usage on the third argument!");
									}
								} else {
									xEssentialsOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[1]);
									try {
										Double money = Double.parseDouble(args[2]);
										off.addEssentialsMoney(money);
										sender.sendMessage(ChatColor.GREEN + "you successfully gave " + off.getUser() + " " + money + Configuration.getEconomyConfig().getCurency());
									} catch(NumberFormatException e) {
										sender.sendMessage(ChatColor.RED + "invalid money usage on the third argument!");
									}
								}
							} else {
								Warnings.getWarnings(sender).playerHasNeverPlayedBefore();
							}
						} else {
							Warnings.getWarnings(sender).noPermission();
						}
					} else if(args[0].equalsIgnoreCase("set")) {
						if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(args[1])) {
								if(xEssentials.getManagers().getPlayerManager().isOnline(args[1])) {
									xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(args[1]);
									try {
										Double money = Double.parseDouble(args[2]);
										xp.clearMoney();
										xp.addEssentialsMoney(money);
										sender.sendMessage(ChatColor.GREEN + "you successfully reset the balance of player " + xp.getUser() + " to " + money + Configuration.getEconomyConfig().getCurency());
										xp.getPlayer().sendMessage(ChatColor.GREEN + sender.getName() + " has reset your bank balance to " + money + Configuration.getEconomyConfig().getCurency());
									} catch(NumberFormatException e) {
										sender.sendMessage(ChatColor.RED + "invalid money usage on the third argument!");
									}
								} else {
									xEssentialsOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[1]);
									try {
										Double money = Double.parseDouble(args[2]);
										off.clearMoney();
										off.addEssentialsMoney(money);
										sender.sendMessage(ChatColor.GREEN + "you successfully reset the balance of player " + off.getUser() + " to " + money + Configuration.getEconomyConfig().getCurency());
									} catch(NumberFormatException e) {
										sender.sendMessage(ChatColor.RED + "invalid money usage on the third argument!");
									}
								}
							} else {
								Warnings.getWarnings(sender).playerHasNeverPlayedBefore();
							}
						} else {
							Warnings.getWarnings(sender).noPermission();
						}
					} else if(args[0].equalsIgnoreCase("remove")) {
						if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(args[1])) {
								if(xEssentials.getManagers().getPlayerManager().isOnline(args[1])) {
									xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(args[1]);
									try {
										Double money = Double.parseDouble(args[2]);
										if(xp.hasPlayerEnoughMoneyFromPrice(money)) {
											xp.payEssentialsMoney(money);
											sender.sendMessage(ChatColor.GREEN + "you successfully withdrawed " + money + " from " + xp.getUser() + " his bank!");
										} else {
											sender.sendMessage(ChatColor.RED + "you cannot withdraw more money which the player doesn't have!");
										}
									} catch(NumberFormatException e) {
										sender.sendMessage(ChatColor.RED + "invalid money usage on the third argument!");
									}
								} else {
									xEssentialsOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[1]);
									try {
										Double money = Double.parseDouble(args[2]);
										if(off.hasPlayerEnoughMoneyFromPrice(money)) {
											off.payEssentialsMoney(money);
											sender.sendMessage(ChatColor.GREEN + "you successfully withdrawed " + money + " from " + off.getUser() + " his bank!");
										} else {
											sender.sendMessage(ChatColor.RED + "you cannot withdraw more money which the player doesn't have!");
										}
									} catch(NumberFormatException e) {
										sender.sendMessage(ChatColor.RED + "invalid money usage on the third argument!");
									}
								}
							} else {
								Warnings.getWarnings(sender).playerHasNeverPlayedBefore();
							}
						} else {
							Warnings.getWarnings(sender).noPermission();
						}
					}
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
