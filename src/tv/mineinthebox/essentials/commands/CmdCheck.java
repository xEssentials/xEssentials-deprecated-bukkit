package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.Modreq;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdCheck extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdCheck(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(sender.hasPermission(PermissionKey.CMD_CHECK.getPermission())) {
			if(args.length == 0) {
					if(pl.getManagers().getPlayerManager().isOnline(sender.getName())) {
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
						sender.sendMessage(ChatColor.GOLD + ".oO___[currently open Modreqs]___Oo.");
						for(Modreq mod : xp.getModreqs()) {
							sender.sendMessage(ChatColor.GRAY + "id: " + ChatColor.GREEN + mod.getId() + ChatColor.GRAY + " message: " + ChatColor.GREEN + mod.getMessage() + ChatColor.GRAY + " published: " + ChatColor.GREEN + mod.getDate());
						}
					} else {
						sendMessage(ChatColor.RED + "something went wrong please reload xEssentials!");
					}
			} else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("list")) {
					sender.sendMessage(ChatColor.GOLD + ".oO___[Modreq list]___Oo.");
					if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
						for(XOfflinePlayer off : pl.getManagers().getPlayerManager().getOfflinePlayers()) {
							if(off.hasModreqsOpen()) {
								for(Modreq mod : off.getModreqs()) {
									sender.sendMessage(ChatColor.GRAY + "id: " + ChatColor.GREEN + mod.getId() + ChatColor.GRAY + " message: " + ChatColor.GREEN + mod.getMessage() + ChatColor.GRAY + " published: " + ChatColor.GREEN + mod.getDate() + ChatColor.GRAY + " by: " + ChatColor.GREEN + off.getName());	
								}
							}
						}
					} else {
						getWarning(WarningType.NO_PERMISSION);
					}

				} else {
						if(pl.getManagers().getPlayerManager().isOnline(sender.getName())) {
							XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
							try {
								int id = Integer.parseInt(args[0]);
								if(xp.isValidModreqId(id)) {
									Modreq mod = xp.getModreq(id);
									sender.sendMessage(ChatColor.GOLD + ".oO___[Modreq id: " + mod.getId() +"]___Oo.");
									sender.sendMessage(ChatColor.GRAY + "id: " + ChatColor.GREEN + mod.getId() + ChatColor.GRAY + " message: " + ChatColor.GREEN + mod.getMessage() + ChatColor.GRAY + " published: " + ChatColor.GREEN + mod.getDate());
								} else {
									sendMessage(ChatColor.RED + "invalid modreq id!");
								}
							} catch(NumberFormatException e) {
								sendMessage(ChatColor.RED + "this argument needs to be a number!");
							}
						} else {
							sendMessage(ChatColor.RED + "something went wrong please reload xEssentials!");
						}
				}
			} else if(args.length == 2) {
				if(args[0].equalsIgnoreCase("list")) {
					if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
						Player victem = pl.getManagers().getPlayerManager().getOfflinePlayer(args[1]).getPlayer();
						if(victem instanceof Player) {
							if(pl.getManagers().getPlayerManager().isOnline(args[1])) {
								XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[1]);
								if(xp.hasModreqsOpen()) {
									sender.sendMessage(ChatColor.GOLD + ".oO___[Modreq list of player " + args[1] + "]___Oo.");
									for(Modreq mod : xp.getModreqs()) {
										sender.sendMessage(ChatColor.GRAY + "id: " + ChatColor.GREEN + mod.getId() + ChatColor.GRAY + " message: " + ChatColor.GREEN + mod.getMessage() + ChatColor.GRAY + " published: " + ChatColor.GREEN + mod.getDate());
									}
								} else {
									sendMessage(ChatColor.RED + "this player has no modreqs open!");
								}
							} else {
								sendMessage(ChatColor.RED + "something went wrong please reload xEssentials!");
							}
						} else {
							try {
								XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[1]);
								if(off.hasModreqsOpen()) {
									sender.sendMessage(ChatColor.GOLD + ".oO___[Modreq list of player " + args[1] + "]___Oo.");
									for(Modreq mod : off.getModreqs()) {
										sender.sendMessage(ChatColor.GRAY + "id: " + ChatColor.GREEN + mod.getId() + ChatColor.GRAY + " message: " + ChatColor.GREEN + mod.getMessage() + ChatColor.GRAY + " published: " + ChatColor.GREEN + mod.getDate());
									}
								} else {
									sendMessage(ChatColor.RED + "this player has no modreqs open!");
								}
							} catch(NullPointerException e) {
								getWarning(WarningType.NEVER_PLAYED_BEFORE);
							}
						}
					} else {
						getWarning(WarningType.NO_PERMISSION);
					}
				} else {
					if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) { 
						Player p = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]).getPlayer();
						if(p instanceof Player) {
							if(pl.getManagers().getPlayerManager().isOnline(args[0])) { 
								XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[0]);
								if(xp.hasModreqsOpen()) {
									try {
										int id = Integer.parseInt(args[1]);
										if(xp.isValidModreqId(id)) {
											Modreq mod = xp.getModreq(id);
											sender.sendMessage(ChatColor.GOLD + ".oO___[Modreq info for player " + mod.getPlayersName() + " id: " + mod.getId() + "]___Oo.");
											sender.sendMessage(ChatColor.GRAY + "id: " + ChatColor.GREEN + mod.getId() + ChatColor.GRAY + " message: " + ChatColor.GREEN + mod.getMessage() + ChatColor.GRAY + " published: " + ChatColor.GREEN + mod.getDate());
										} else {
											sendMessage(ChatColor.RED + "this is not a valid modreq id!");
										}
									} catch(NumberFormatException e) {
										sendMessage(ChatColor.RED + "this argument needs to be a number!");
									}
								} else {
									sendMessage(ChatColor.RED + "this player has no modreqs open!");
								}
							} else {
								sendMessage(ChatColor.RED + "something went wrong please reload xEssentials!");
							}
						} else {
							try {
								XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
								if(off.hasModreqsOpen()) {
									try {
										int id = Integer.parseInt(args[1]);
										if(off.isValidModreqId(id)) {
											Modreq mod = off.getModreq(id);
											sender.sendMessage(ChatColor.GOLD + ".oO___[Modreq info for offline player " + mod.getPlayersName() + " id: " + mod.getId() + "]___Oo.");
											sender.sendMessage(ChatColor.GRAY + "id: " + ChatColor.GREEN + mod.getId() + ChatColor.GRAY + " message: " + ChatColor.GREEN + mod.getMessage() + ChatColor.GRAY + " published: " + ChatColor.GREEN + mod.getDate());
										} else {
											sendMessage(ChatColor.RED + "this is not a valid modreq id!");
										}
									} catch(NumberFormatException e) {
										sendMessage(ChatColor.RED + "this argument needs to be a number!");
									}
								} else {
									sendMessage(ChatColor.RED + "this player has no modreqs open!");
								}
							} catch(NullPointerException e) {
								getWarning(WarningType.NEVER_PLAYED_BEFORE);
							}
						}
					} else {
						getWarning(WarningType.NO_PERMISSION);
					}
				}
			}
		} else {
			getWarning(WarningType.NO_PERMISSION);
		}
		return false;
	}

}
