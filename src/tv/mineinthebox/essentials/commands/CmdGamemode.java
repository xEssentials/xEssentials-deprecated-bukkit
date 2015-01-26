package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.helpers.EnumHelper;
import tv.mineinthebox.essentials.hook.Hooks;
import tv.mineinthebox.essentials.hook.WorldEditHook;

public class CmdGamemode {
	
	private final xEssentials pl;
	
	public CmdGamemode(xEssentials pl) {
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("gamemode")) {
			if(args.length == 0) {
				if(sender instanceof Player) {
					if(sender.hasPermission(PermissionKey.CMD_GAMEMODE.getPermission())) {
						Player p = (Player) sender;
						sendHelp(p);
					} else {
						Warnings.getWarnings(sender).noPermission();
					}
				} else {
					Warnings.getWarnings(sender).consoleMessage();
				}
			} else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("help")) {
					if(sender instanceof Player) {
						if(sender.hasPermission(PermissionKey.CMD_GAMEMODE.getPermission())) {
							Player p = (Player) sender;
							sendHelp(p);
						} else {
							Warnings.getWarnings(sender).noPermission();
						}
					} else {
						Warnings.getWarnings(sender).consoleMessage();
					}
				} else if(args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("spectate") || args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("3")) {
					if(sender instanceof Player) {
						if(sender.hasPermission(PermissionKey.CMD_GAMEMODE.getPermission())) {
							Player p = (Player) sender;
							if(args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("0")) {
								if(p.getGameMode() != GameMode.SURVIVAL) {
									p.setGameMode(GameMode.SURVIVAL);
									if(Hooks.isWorldeditEnabled()) {
										WorldEditHook.turnOffWand(p);
									}
									sender.sendMessage(ChatColor.GREEN + "you successfully set your gamemode to survival!");
								} else {
									sender.sendMessage(ChatColor.RED + "you are already set in this gamemode");
								}
							} else if(args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("1")) {
								if(p.getGameMode() != GameMode.CREATIVE) {
									p.setGameMode(GameMode.CREATIVE);
									if(Hooks.isWorldeditEnabled()) {
										WorldEditHook.turnOffWand(p);
									}
									sender.sendMessage(ChatColor.GREEN + "you successfully set your gamemode to creative!");
								} else {
									sender.sendMessage(ChatColor.RED + "you are already set in this gamemode");
								}
							} else if(args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("2")) {
								if(EnumHelper.isDefined(GameMode.class, "ADVENTURE")) {
									if(p.getGameMode() != EnumHelper.getEnum(GameMode.class, "ADVENTURE")) {
										p.setGameMode(EnumHelper.getEnum(GameMode.class, "ADVENTURE"));
										sender.sendMessage(ChatColor.GREEN + "you successfully set your gamemode to adventure!");
									} else {
										sender.sendMessage(ChatColor.RED + "you are already set in this gamemode");
									}
								} else {
									sender.sendMessage(ChatColor.RED + "adventure seems not to be existed in your server!");
								}
							} else if(args[0].equalsIgnoreCase("spectate") || args[0].equalsIgnoreCase("3")) {
								if(EnumHelper.isDefined(GameMode.class, "SPECTATOR")) {
									if(p.getGameMode() != EnumHelper.getEnum(GameMode.class, "SPECTATOR")) {
										p.setGameMode(EnumHelper.getEnum(GameMode.class, "SPECTATOR"));
										sender.sendMessage(ChatColor.GREEN + "you successfully set your gamemode to spectate!");
									} else {
										sender.sendMessage(ChatColor.RED + "you are already set in this gamemode");
									}
								} else {
									sender.sendMessage(ChatColor.RED + "spectate seems not be existed in your server!");
								}
							}
						} else {
							Warnings.getWarnings(sender).noPermission();
						}
					} else {
						Warnings.getWarnings(sender).consoleMessage();
					}
				} else {
					if(sender.hasPermission(PermissionKey.CMD_GAMEMODE.getPermission())) {
						sender.sendMessage(ChatColor.RED + "warning a invalid gamemode argument!");
					} else {
						Warnings.getWarnings(sender).noPermission();
					}
				}
			} else if(args.length == 2) {
				if(sender.hasPermission(PermissionKey.CMD_GAMEMODE.getPermission())) {
					Player p = pl.getManagers().getPlayerManager().getOfflinePlayer(args[1]).getPlayer();
					if(p instanceof Player) {
						if(args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("0")) {
							if(p.getGameMode() != GameMode.SURVIVAL) {
								p.setGameMode(GameMode.SURVIVAL);
								if(Hooks.isWorldeditEnabled()) {
									WorldEditHook.turnOffWand(p);
								}
								p.sendMessage(ChatColor.GREEN + sender.getName() + " has set your gamemode to survival!");
								sender.sendMessage(ChatColor.GREEN + "you successfully set the gamemode to survival for player " + p.getName() + "!");
							} else {
								sender.sendMessage(ChatColor.RED + "the player is already set in this gamemode");
							}
						} else if(args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("1")) {
							if(p.getGameMode() != GameMode.CREATIVE) {
								p.setGameMode(GameMode.CREATIVE);
								if(Hooks.isWorldeditEnabled()) {
									WorldEditHook.turnOffWand(p);
								}
								p.sendMessage(ChatColor.GREEN + sender.getName() + " has set your gamemode to creative!");
								sender.sendMessage(ChatColor.GREEN + "you successfully set the gamemode to creative for player " + p.getName() + "!");
							} else {
								sender.sendMessage(ChatColor.RED + "the player is already set in this gamemode");
							}
						} else if(args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("2")) {
							if(EnumHelper.isDefined(GameMode.class, "ADVENTURE")) {
								if(p.getGameMode() != EnumHelper.getEnum(GameMode.class, "ADVENTURE")) {
									p.setGameMode((GameMode) EnumHelper.getEnum(GameMode.class, "ADVENTURE"));
									p.sendMessage(ChatColor.GREEN + sender.getName() + " has set your gamemode to adventure!");
									sender.sendMessage(ChatColor.GREEN + "you successfully set the gamemode to adventure for player " + p.getName() + "!");
								} else {
									sender.sendMessage(ChatColor.RED + "the player is already set in this gamemode");
								}
							} else {
								sender.sendMessage(ChatColor.RED + "adventure seems not be existed in your server!");
							}
						}  else if(args[0].equalsIgnoreCase("spectate") || args[0].equalsIgnoreCase("3")) {
							if(EnumHelper.isDefined(GameMode.class, "SPECTATOR")) {
								if(p.getGameMode() != EnumHelper.getEnum(GameMode.class, "SPECTATOR")) {
									p.setGameMode((GameMode) EnumHelper.getEnum(GameMode.class, "SPECTATOR"));
									p.sendMessage(ChatColor.GREEN + sender.getName() + " has set your gamemode to spectate!");
									sender.sendMessage(ChatColor.GREEN + "you successfully set the gamemode to spectate for player " + p.getName() + "!");
								} else {
									sender.sendMessage(ChatColor.RED + "the player is already set in this gamemode");
								}
							} else {
								sender.sendMessage(ChatColor.RED + "spectate seems not be existed in your server!");
							}
						}
					} else {
						sender.sendMessage(ChatColor.RED + "this player is not online");
					}
				} else {
					Warnings.getWarnings(sender).noPermission();
				}
			}
		}
		return false;
	}


	private void sendHelp(Player p) {
		p.sendMessage(ChatColor.GOLD + ".oO___[gamemode help]___Oo.");
		p.sendMessage(ChatColor.GREEN + "your gamemode is " + p.getGameMode().name().toLowerCase());
		p.sendMessage(ChatColor.GRAY + "a notice this plugin does not enables Adventure mode and we won't support it");
		p.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/gamemode s " + ChatColor.WHITE + ": sets your gamemode to survival");
		p.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/gamemode c " + ChatColor.WHITE + ": sets your gamemode to creative");
		p.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/gamemode s <player> " + ChatColor.WHITE + ": sets players gamemode to survival");
		p.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/gamemode c <player> " + ChatColor.WHITE + ": sets players gamemode to creative");
	}
}
