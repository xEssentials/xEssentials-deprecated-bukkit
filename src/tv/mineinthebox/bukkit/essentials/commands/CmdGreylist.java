package tv.mineinthebox.bukkit.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.Warnings;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.GreyListCause;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;
import tv.mineinthebox.bukkit.essentials.events.customevents.OfflinePlayerGreyListedEvent;
import tv.mineinthebox.bukkit.essentials.events.customevents.PlayerGreyListedEvent;
import tv.mineinthebox.bukkit.essentials.hook.Hooks;
import tv.mineinthebox.bukkit.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.bukkit.essentials.interfaces.XPlayer;
public class CmdGreylist {

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("greylist")) {
			if(sender.hasPermission(PermissionKey.CMD_GREYLIST.getPermission())) {
				if(args.length == 0) {
					sender.sendMessage(ChatColor.GOLD + ".oO___[greylist help]___Oo.");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/greylist help " + ChatColor.WHITE + ": shows help");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/greylist on " + ChatColor.WHITE + ": activates greylist");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/greylist off " + ChatColor.WHITE + ": disable greylist");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/greylist add <player> " + ChatColor.WHITE + ": excempt the player to the greylist");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/greylist remove <player> " + ChatColor.WHITE + ": remove the player from the greylist");
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[greylist help]___Oo.");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/greylist help " + ChatColor.WHITE + ": shows help");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/greylist on " + ChatColor.WHITE + ": activates greylist");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/greylist off " + ChatColor.WHITE + ": disable greylist");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/greylist add <player> " + ChatColor.WHITE + ": excempt the player to the greylist");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/greylist remove <player> " + ChatColor.WHITE + ": remove the player from the greylist");
					} else if(args[0].equalsIgnoreCase("on")) {
						if(!Configuration.getGrayListConfig().isEnabled()) {
							Configuration.getGrayListConfig().setEnabled(true);
							sender.sendMessage(ChatColor.GREEN + "you have successfully enabled the greylist server!");
						} else {
							sender.sendMessage(ChatColor.RED + "the greylist server whas already active!");
						}
					} else if(args[0].equalsIgnoreCase("off")) {
						if(Configuration.getGrayListConfig().isEnabled()) {
							Configuration.getGrayListConfig().setEnabled(false);
							sender.sendMessage(ChatColor.GREEN + "you have successfully disabled the greylist server!");
						} else {
							sender.sendMessage(ChatColor.RED + "the greylist server whas already shuted down!");
						}
					}
				} else if(args.length == 2) {
					if(args[0].equalsIgnoreCase("add")) {
						if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(args[1])) {
							if(xEssentials.getManagers().getPlayerManager().isOnline(args[1])) {
								XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(args[1]);
								xp.setGreyListed(true);
								if(Hooks.isVaultEnabled()) {
									String oldGroup = xEssentials.getManagers().getVaultManager().getGroup(xp.getPlayer());
									String newGroup = Configuration.getGrayListConfig().getGroup();
									xEssentials.getManagers().getVaultManager().setGroup(Bukkit.getWorlds().get(0), xp.getUser(), newGroup);
									Bukkit.getPluginManager().callEvent(new PlayerGreyListedEvent(xp.getPlayer(), newGroup, oldGroup, GreyListCause.COMMAND));
								} else {
									sender.sendMessage(ChatColor.RED + "no vault installed!");
									return false;
								}
								sender.sendMessage(ChatColor.GREEN + "you successfully greylisted " + xp.getUser());
							} else {
								XOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[1]);
								off.setGreyListed(true);
								if(Hooks.isVaultEnabled()) {
									String oldGroup = xEssentials.getManagers().getVaultManager().getGroup(Bukkit.getWorlds().get(0), off.getUser());
									String newGroup = Configuration.getGrayListConfig().getGroup();
									xEssentials.getManagers().getVaultManager().setGroup(Bukkit.getWorlds().get(0), off.getUser(), newGroup);
									Bukkit.getPluginManager().callEvent(new OfflinePlayerGreyListedEvent(off.getUser(), newGroup, oldGroup, GreyListCause.COMMAND));
								} else {
									sender.sendMessage(ChatColor.RED + "no vault installed!");
									return false;
								}
								sender.sendMessage(ChatColor.GREEN + "you successfully greylisted offline player " + off.getUser());
							}
						} else {
							sender.sendMessage(ChatColor.RED + "this player has never played before!");
						}
					} else if(args[0].equalsIgnoreCase("remove")) {
						if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(args[1])) {
							if(xEssentials.getManagers().getPlayerManager().isOnline(args[1])) {
								XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(args[1]);
								xp.setGreyListed(false);
								if(Hooks.isVaultEnabled()) {
									String DefaultGroup = xEssentials.getManagers().getVaultManager().getDefaultGroup();
									xEssentials.getManagers().getVaultManager().setGroup(Bukkit.getWorlds().get(0), xp.getUser(), DefaultGroup);
								} else {
									sender.sendMessage(ChatColor.RED + "no vault intalled!");
									return false;
								}
								sender.sendMessage(ChatColor.GREEN + "you have successfully removed " + xp.getUser() + " from the greylist!");
							} else {
								XOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[1]);
								off.setGreyListed(false);
								if(Hooks.isVaultEnabled()) {
									String DefaultGroup = xEssentials.getManagers().getVaultManager().getDefaultGroup();
									xEssentials.getManagers().getVaultManager().setGroup(Bukkit.getWorlds().get(0), off.getUser(), DefaultGroup);
								} else {
									sender.sendMessage(ChatColor.RED + "no vault intalled!");
									return false;
								}
								sender.sendMessage(ChatColor.GREEN + "you have successfully removed " + off.getUser() + " from the greylist!");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "this player has never played before!");
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
