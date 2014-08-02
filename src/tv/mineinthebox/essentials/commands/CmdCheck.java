package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.Modreq;
import tv.mineinthebox.essentials.instances.xEssentialsOfflinePlayer;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

public class CmdCheck {

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(sender.hasPermission(PermissionKey.CMD_CHECK.getPermission())) {
			if(args.length == 0) {
				if(sender instanceof Player) {
					if(xEssentials.getManagers().getPlayerManager().isOnline(sender.getName())) {
						xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
						sender.sendMessage(ChatColor.GOLD + ".oO___[currently open Modreqs]___Oo.");
						for(Modreq mod : xp.getModreqs()) {
							sender.sendMessage(ChatColor.GRAY + "id: " + ChatColor.GREEN + mod.getId() + ChatColor.GRAY + " message: " + ChatColor.GREEN + mod.getMessage() + ChatColor.GRAY + " published: " + ChatColor.GREEN + mod.getDate());
						}
					} else {
						sender.sendMessage(ChatColor.RED + "something went wrong please reload xEssentials!");
					}
				} else {
					Warnings.getWarnings(sender).consoleMessage();
				}
			} else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("list")) {
					sender.sendMessage(ChatColor.GOLD + ".oO___[Modreq list]___Oo.");
					if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
						for(xEssentialsOfflinePlayer off : xEssentials.getManagers().getPlayerManager().getOfflinePlayers()) {
							if(off.hasModreqsOpen()) {
								for(Modreq mod : off.getModreqs()) {
									sender.sendMessage(ChatColor.GRAY + "id: " + ChatColor.GREEN + mod.getId() + ChatColor.GRAY + " message: " + ChatColor.GREEN + mod.getMessage() + ChatColor.GRAY + " published: " + ChatColor.GREEN + mod.getDate() + ChatColor.GRAY + " by: " + ChatColor.GREEN + off.getUser());	
								}
							}
						}
					} else {
						Warnings.getWarnings(sender).noPermission();
					}

				} else {
					if(sender instanceof Player) {
						if(xEssentials.getManagers().getPlayerManager().isOnline(sender.getName())) {
							xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
							try {
								int id = Integer.parseInt(args[0]);
								if(xp.isValidModreqId(id)) {
									Modreq mod = xp.getModreq(id);
									sender.sendMessage(ChatColor.GOLD + ".oO___[Modreq id: " + mod.getId() +"]___Oo.");
									sender.sendMessage(ChatColor.GRAY + "id: " + ChatColor.GREEN + mod.getId() + ChatColor.GRAY + " message: " + ChatColor.GREEN + mod.getMessage() + ChatColor.GRAY + " published: " + ChatColor.GREEN + mod.getDate());
								} else {
									sender.sendMessage(ChatColor.RED + "invalid modreq id!");
								}
							} catch(NumberFormatException e) {
								sender.sendMessage(ChatColor.RED + "this argument needs to be a number!");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "something went wrong please reload xEssentials!");
						}
					} else {
						Warnings.getWarnings(sender).consoleMessage();
					}
				}
			} else if(args.length == 2) {
				if(args[0].equalsIgnoreCase("list")) {
					if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
						Player victem = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[1]).getPlayer();
						if(victem instanceof Player) {
							if(xEssentials.getManagers().getPlayerManager().isOnline(args[1])) {
								xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(args[1]);
								if(xp.hasModreqsOpen()) {
									sender.sendMessage(ChatColor.GOLD + ".oO___[Modreq list of player " + args[1] + "]___Oo.");
									for(Modreq mod : xp.getModreqs()) {
										sender.sendMessage(ChatColor.GRAY + "id: " + ChatColor.GREEN + mod.getId() + ChatColor.GRAY + " message: " + ChatColor.GREEN + mod.getMessage() + ChatColor.GRAY + " published: " + ChatColor.GREEN + mod.getDate());
									}
								} else {
									sender.sendMessage(ChatColor.RED + "this player has no modreqs open!");
								}
							} else {
								sender.sendMessage(ChatColor.RED + "something went wrong please reload xEssentials!");
							}
						} else {
							try {
								xEssentialsOfflinePlayer off = new xEssentialsOfflinePlayer(args[1]);
								if(off.hasModreqsOpen()) {
									sender.sendMessage(ChatColor.GOLD + ".oO___[Modreq list of player " + args[1] + "]___Oo.");
									for(Modreq mod : off.getModreqs()) {
										sender.sendMessage(ChatColor.GRAY + "id: " + ChatColor.GREEN + mod.getId() + ChatColor.GRAY + " message: " + ChatColor.GREEN + mod.getMessage() + ChatColor.GRAY + " published: " + ChatColor.GREEN + mod.getDate());
									}
								} else {
									sender.sendMessage(ChatColor.RED + "this player has no modreqs open!");
								}
							} catch(NullPointerException e) {
								Warnings.getWarnings(sender).playerHasNeverPlayedBefore();
							}
						}
					} else {
						Warnings.getWarnings(sender).noPermission();
					}
				} else {
					if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) { 
						Player p = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[0]).getPlayer();
						if(p instanceof Player) {
							if(xEssentials.getManagers().getPlayerManager().isOnline(args[0])) { 
								xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(args[0]);
								if(xp.hasModreqsOpen()) {
									try {
										int id = Integer.parseInt(args[1]);
										if(xp.isValidModreqId(id)) {
											Modreq mod = xp.getModreq(id);
											sender.sendMessage(ChatColor.GOLD + ".oO___[Modreq info for player " + mod.getPlayersName() + " id: " + mod.getId() + "]___Oo.");
											sender.sendMessage(ChatColor.GRAY + "id: " + ChatColor.GREEN + mod.getId() + ChatColor.GRAY + " message: " + ChatColor.GREEN + mod.getMessage() + ChatColor.GRAY + " published: " + ChatColor.GREEN + mod.getDate());
										} else {
											sender.sendMessage(ChatColor.RED + "this is not a valid modreq id!");
										}
									} catch(NumberFormatException e) {
										sender.sendMessage(ChatColor.RED + "this argument needs to be a number!");
									}
								} else {
									sender.sendMessage(ChatColor.RED + "this player has no modreqs open!");
								}
							} else {
								sender.sendMessage(ChatColor.RED + "something went wrong please reload xEssentials!");
							}
						} else {
							try {
								xEssentialsOfflinePlayer off = new xEssentialsOfflinePlayer(args[0]);
								if(off.hasModreqsOpen()) {
									try {
										int id = Integer.parseInt(args[1]);
										if(off.isValidModreqId(id)) {
											Modreq mod = off.getModreq(id);
											sender.sendMessage(ChatColor.GOLD + ".oO___[Modreq info for offline player " + mod.getPlayersName() + " id: " + mod.getId() + "]___Oo.");
											sender.sendMessage(ChatColor.GRAY + "id: " + ChatColor.GREEN + mod.getId() + ChatColor.GRAY + " message: " + ChatColor.GREEN + mod.getMessage() + ChatColor.GRAY + " published: " + ChatColor.GREEN + mod.getDate());
										} else {
											sender.sendMessage(ChatColor.RED + "this is not a valid modreq id!");
										}
									} catch(NumberFormatException e) {
										sender.sendMessage(ChatColor.RED + "this argument needs to be a number!");
									}
								} else {
									sender.sendMessage(ChatColor.RED + "this player has no modreqs open!");
								}
							} catch(NullPointerException e) {
								Warnings.getWarnings(sender).playerHasNeverPlayedBefore();
							}
						}
					} else {
						Warnings.getWarnings(sender).noPermission();
					}
				}
			}
		} else {
			Warnings.getWarnings(sender).noPermission();
		}
		return false;
	}

}
