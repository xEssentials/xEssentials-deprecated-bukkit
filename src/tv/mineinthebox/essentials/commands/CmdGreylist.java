package tv.mineinthebox.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.GreyListCause;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.events.customevents.OfflinePlayerGreyListedEvent;
import tv.mineinthebox.essentials.events.customevents.PlayerGreyListedEvent;
import tv.mineinthebox.essentials.hook.Hooks;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;
public class CmdGreylist {
	
	private final xEssentials pl;
	
	public CmdGreylist(xEssentials pl) {
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("greylist")) {
			if(sender.hasPermission(PermissionKey.CMD_GREYLIST.getPermission())) {
				if(args.length == 0) {
					sender.sendMessage(ChatColor.GOLD + ".oO___[greylist help]___Oo.");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/greylist help " + ChatColor.WHITE + ": shows help");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/greylist add <player> " + ChatColor.WHITE + ": excempt the player to the greylist");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/greylist remove <player> " + ChatColor.WHITE + ": remove the player from the greylist");
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[greylist help]___Oo.");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/greylist help " + ChatColor.WHITE + ": shows help");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/greylist add <player> " + ChatColor.WHITE + ": excempt the player to the greylist");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/greylist remove <player> " + ChatColor.WHITE + ": remove the player from the greylist");
					}
				} else if(args.length == 2) {
					if(args[0].equalsIgnoreCase("add")) {
						if(pl.getManagers().getPlayerManager().isEssentialsPlayer(args[1])) {
							if(pl.getManagers().getPlayerManager().isOnline(args[1])) {
								XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[1]);
								xp.setGreyListed(true);
								if(Hooks.isVaultPermissionsEnabled()) {
									String oldGroup = pl.getManagers().getVaultManager().getGroup(xp.getPlayer());
									String newGroup = pl.getConfiguration().getGrayListConfig().getGroup();
									pl.getManagers().getVaultManager().setGroup(Bukkit.getWorlds().get(0), xp.getUser(), newGroup);
									Bukkit.getPluginManager().callEvent(new PlayerGreyListedEvent(xp.getPlayer(), newGroup, oldGroup, GreyListCause.COMMAND, pl));
								} else {
									sender.sendMessage(ChatColor.RED + "no vault installed!");
									return false;
								}
								sender.sendMessage(ChatColor.GREEN + "you successfully greylisted " + xp.getUser());
							} else {
								XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[1]);
								off.setGreyListed(true);
								if(Hooks.isVaultPermissionsEnabled()) {
									String oldGroup = pl.getManagers().getVaultManager().getGroup(Bukkit.getWorlds().get(0), off.getUser());
									String newGroup = pl.getConfiguration().getGrayListConfig().getGroup();
									pl.getManagers().getVaultManager().setGroup(Bukkit.getWorlds().get(0), off.getUser(), newGroup);
									Bukkit.getPluginManager().callEvent(new OfflinePlayerGreyListedEvent(off.getUser(), newGroup, oldGroup, GreyListCause.COMMAND, pl));
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
						if(pl.getManagers().getPlayerManager().isEssentialsPlayer(args[1])) {
							if(pl.getManagers().getPlayerManager().isOnline(args[1])) {
								XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[1]);
								xp.setGreyListed(false);
								if(Hooks.isVaultPermissionsEnabled()) {
									String DefaultGroup = pl.getManagers().getVaultManager().getDefaultGroup();
									pl.getManagers().getVaultManager().setGroup(Bukkit.getWorlds().get(0), xp.getUser(), DefaultGroup);
								} else {
									sender.sendMessage(ChatColor.RED + "no vault intalled!");
									return false;
								}
								sender.sendMessage(ChatColor.GREEN + "you have successfully removed " + xp.getUser() + " from the greylist!");
							} else {
								XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[1]);
								off.setGreyListed(false);
								if(Hooks.isVaultPermissionsEnabled()) {
									String DefaultGroup = pl.getManagers().getVaultManager().getDefaultGroup();
									pl.getManagers().getVaultManager().setGroup(Bukkit.getWorlds().get(0), off.getUser(), DefaultGroup);
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
