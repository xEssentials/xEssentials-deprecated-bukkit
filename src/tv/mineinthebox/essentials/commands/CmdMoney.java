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

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.events.customevents.PlayerTransactionEvent;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdMoney extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdMoney(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	private List<String> getPlayerByName(String p) {
		List<String> s = new ArrayList<String>();
		for(XOfflinePlayer name : pl.getManagers().getPlayerManager().getOfflinePlayers()) {
			if(name.getName().toUpperCase().startsWith(p.toUpperCase())) {
				s.add(name.getName());
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
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
						sender.sendMessage(ChatColor.GOLD + ".oO___[your money amount]___Oo.");
						sender.sendMessage(ChatColor.GRAY + "your current amount is: " + ChatColor.GREEN + xp.getMoney() + pl.getConfiguration().getEconomyConfig().getCurency());
					} else {
						getWarning(WarningType.PLAYER_ONLY);
					}
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						showHelp();
					} else if(args[0].equalsIgnoreCase("top")) {
						SortedMap<Double, String> map = new TreeMap<Double, String>().descendingMap();
						for(XOfflinePlayer off : pl.getManagers().getPlayerManager().getOfflinePlayers()) {
							if(off.hasMoney()) {
								map.put(off.getMoney(), off.getName());
							}
						}
						Iterator<Entry<Double, String>> it = map.entrySet().iterator();
						int i = 1;
						sender.sendMessage(ChatColor.GOLD + ".oO___[Money top]___Oo.");
						while(i < 11) {
							if(it.hasNext()) {
								Entry<Double, String> entry = (Entry<Double, String>) it.next();
								sender.sendMessage(ChatColor.GREEN +""+ i + ": " + ChatColor.GRAY + entry.getValue() + ": " + ChatColor.GREEN + entry.getKey() + pl.getConfiguration().getEconomyConfig().getCurency());	
								i++;
							} else {
								break;
							}
						}
					} else if(args[0].equalsIgnoreCase("clear")) {
						if(sender instanceof Player) {
							if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
								XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
								xp.clearMoney();
								sendMessage(ChatColor.GREEN + "you successfully cleared your banks money!");
							} else {
								getWarning(WarningType.NO_PERMISSION);
							}
						} else {
							getWarning(WarningType.PLAYER_ONLY);
						}
					}
				} else if(args.length == 2) {
					if(args[0].equalsIgnoreCase("clear")) {
						if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							if(pl.getManagers().getPlayerManager().isEssentialsPlayer(args[1])) {
								if(pl.getManagers().getPlayerManager().isOnline(args[1])) {
									XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[1]);
									xp.clearMoney();
									sendMessage(ChatColor.GREEN + "you successfully cleared "+ xp.getName() +" banks money!");
								} else {
									XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[1]);
									off.clearMoney();
									sendMessage(ChatColor.GREEN + "you successfully cleared "+ off.getName() +" banks money!");
								}
							} else {
								getWarning(WarningType.NEVER_PLAYED_BEFORE);
							}
						} else {
							getWarning(WarningType.NO_PERMISSION);
						}
					}
				} else if(args.length == 3) {
					if(args[0].equalsIgnoreCase("pay")) {
						if(sender instanceof Player) {
							XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
							if(pl.getManagers().getPlayerManager().isEssentialsPlayer(args[1])) {
								if(pl.getManagers().getPlayerManager().isOnline(args[1])) {
									XPlayer xpp = pl.getManagers().getPlayerManager().getPlayer(args[1]);
									try {
										Double money = Double.parseDouble(args[2]);
										if(xp.hasEnoughMoney(money)) {
											xp.payMoney(money, xpp);
											sendMessage(ChatColor.GREEN + "you have successfully paid " + xpp.getName() + " " + money + pl.getConfiguration().getEconomyConfig().getCurency());
											sendMessageTo(xpp.getPlayer(), ChatColor.GREEN + sender.getName() + " has paid " + money + pl.getConfiguration().getEconomyConfig().getCurency() + " to you!");
											Bukkit.getPluginManager().callEvent(new PlayerTransactionEvent(xp.getPlayer(), money, xpp.getName()));
										} else {
											sendMessage(ChatColor.RED + "you don't have enough money to pay this player!");
										}
									} catch(NumberFormatException e) {
										sendMessage(ChatColor.RED + "invalid money usage on the third argument!");
									}
								} else {
									XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[1]);
									try {
										Double money = Double.parseDouble(args[2]);
										if(xp.hasEnoughMoney(money)) {
											xp.payMoney(money, off);
											sendMessage(ChatColor.GREEN + "you have successfully paid " + off.getName() + " " + money + pl.getConfiguration().getEconomyConfig().getCurency());
											Bukkit.getPluginManager().callEvent(new PlayerTransactionEvent(xp.getPlayer(), money, off.getName()));
										} else {
											sendMessage(ChatColor.RED + "you don't have enough money to pay this player!");
										}
									} catch(NumberFormatException e) {
										sendMessage(ChatColor.RED + "invalid money usage on the third argument!");
									}
								}
							} else {
								getWarning(WarningType.NEVER_PLAYED_BEFORE);
							}
						} else {
							getWarning(WarningType.PLAYER_ONLY);
						}
					} else if(args[0].equalsIgnoreCase("give")) {
						if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							if(pl.getManagers().getPlayerManager().isEssentialsPlayer(args[1])) {
								if(pl.getManagers().getPlayerManager().isOnline(args[1])) {
									XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[1]);
									try {
										Double money = Double.parseDouble(args[2]);
										xp.depositMoney(money);
										sendMessage(ChatColor.GREEN + "you successfully gave " + xp.getName() + " " + money + pl.getConfiguration().getEconomyConfig().getCurency());
										sendMessageTo(xp.getPlayer(), ChatColor.GREEN + sender.getName() + " has given you " + money + pl.getConfiguration().getEconomyConfig().getCurency());
									} catch(NumberFormatException e) {
										sendMessage(ChatColor.RED + "invalid money usage on the third argument!");
									}
								} else {
									XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[1]);
									try {
										Double money = Double.parseDouble(args[2]);
										off.depositMoney(money);
										sendMessage(ChatColor.GREEN + "you successfully gave " + off.getName() + " " + money + pl.getConfiguration().getEconomyConfig().getCurency());
									} catch(NumberFormatException e) {
										sendMessage(ChatColor.RED + "invalid money usage on the third argument!");
									}
								}
							} else {
								getWarning(WarningType.NEVER_PLAYED_BEFORE);
							}
						} else {
							getWarning(WarningType.NO_PERMISSION);
						}
					} else if(args[0].equalsIgnoreCase("set")) {
						if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							if(pl.getManagers().getPlayerManager().isEssentialsPlayer(args[1])) {
								if(pl.getManagers().getPlayerManager().isOnline(args[1])) {
									XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[1]);
									try {
										Double money = Double.parseDouble(args[2]);
										xp.clearMoney();
										xp.depositMoney(money);
										sendMessage(ChatColor.GREEN + "you successfully reset the balance of player " + xp.getName() + " to " + money + pl.getConfiguration().getEconomyConfig().getCurency());
										sendMessageTo(xp.getPlayer(), ChatColor.GREEN + sender.getName() + " has reset your bank balance to " + money + pl.getConfiguration().getEconomyConfig().getCurency());
									} catch(NumberFormatException e) {
										sendMessage(ChatColor.RED + "invalid money usage on the third argument!");
									}
								} else {
									XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[1]);
									try {
										Double money = Double.parseDouble(args[2]);
										off.clearMoney();
										off.depositMoney(money);
										sendMessage(ChatColor.GREEN + "you successfully reset the balance of player " + off.getName() + " to " + money + pl.getConfiguration().getEconomyConfig().getCurency());
									} catch(NumberFormatException e) {
										sendMessage(ChatColor.RED + "invalid money usage on the third argument!");
									}
								}
							} else {
								getWarning(WarningType.NEVER_PLAYED_BEFORE);
							}
						} else {
							getWarning(WarningType.NO_PERMISSION);
						}
					} else if(args[0].equalsIgnoreCase("remove")) {
						if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							if(pl.getManagers().getPlayerManager().isEssentialsPlayer(args[1])) {
								if(pl.getManagers().getPlayerManager().isOnline(args[1])) {
									XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[1]);
									try {
										Double money = Double.parseDouble(args[2]);
										if(xp.hasEnoughMoney(money)) {
											xp.payMoney(money);
											sendMessage(ChatColor.GREEN + "you successfully withdrawed " + money + " from " + xp.getName() + " his bank!");
										} else {
											sendMessage(ChatColor.RED + "you cannot withdraw more money which the player doesn't have!");
										}
									} catch(NumberFormatException e) {
										sendMessage(ChatColor.RED + "invalid money usage on the third argument!");
									}
								} else {
									XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[1]);
									try {
										Double money = Double.parseDouble(args[2]);
										if(off.hasEnoughMoney(money)) {
											off.payMoney(money);
											sendMessage(ChatColor.GREEN + "you successfully withdrawed " + money + " from " + off.getName() + " his bank!");
										} else {
											sendMessage(ChatColor.RED + "you cannot withdraw more money which the player doesn't have!");
										}
									} catch(NumberFormatException e) {
										sendMessage(ChatColor.RED + "invalid money usage on the third argument!");
									}
								}
							} else {
								getWarning(WarningType.NEVER_PLAYED_BEFORE);
							}
						} else {
							getWarning(WarningType.NO_PERMISSION);
						}
					}
				}
			} else {
				getWarning(WarningType.NO_PERMISSION);
			}
		}
		return false;
	}

	@Override
	public void showHelp() {
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
	}

}
