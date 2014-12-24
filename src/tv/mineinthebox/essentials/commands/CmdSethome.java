package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdSethome {

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("sethome")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_SET_HOME.getPermission())) {
					if(args.length == 0) {
						if(xEssentials.getManagers().getPlayerManager().isOnline(sender.getName())) {
							XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
							xp.setHome("default");
							sender.sendMessage(ChatColor.GREEN + "successfully set default home ;-)");
						} else {
							sender.sendMessage(ChatColor.RED + "something went wrong, please relog");
						}
					} else if(args.length == 1) {
						if(args[0].equalsIgnoreCase("help")) {
							sender.sendMessage(ChatColor.GOLD + ".oO___[sethome help]___Oo.");
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/sethome " + ChatColor.WHITE + ": allows you to set your home");
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/sethome <home name>" + ChatColor.WHITE + ": allows you to set your extra custom home");
						} else if(args[0].equalsIgnoreCase("list")) {
							sender.sendMessage(ChatColor.RED + "you cannot use this name!");
						} else if(args[0].equalsIgnoreCase("delete")) {
							sender.sendMessage(ChatColor.RED + "you cannot use this name!");
						} else if(args[0].equalsIgnoreCase("remove")) {
							sender.sendMessage(ChatColor.RED + "you cannot use this name!");
						} else if(args[0].equalsIgnoreCase("default")) {
							sender.sendMessage(ChatColor.RED + "you cannot use this name!, use /sethome instead");
						} else {
							if(sender.hasPermission(PermissionKey.MULTIPLE_HOMES.getPermission()) || Configuration.getPlayerConfig().canUseMoreHomes()) {
								if(!xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(args[0])) {
									XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
									if(xp.isStaff()) {
										xp.setHome(args[0]);
										sender.sendMessage(ChatColor.GREEN + "you successfully set your new custom home!");
									} else if(!(xp.getAmountOfHomes() > (Configuration.getPlayerConfig().getMaxHomesAllowed())-1)) {
										xp.setHome(args[0]);
										sender.sendMessage(ChatColor.GREEN + "you successfully set your new custom home!");
									} else {
										sender.sendMessage(ChatColor.RED + "you reached above the limit to set homes!");
									}
								} else {
									sender.sendMessage(ChatColor.RED + "you cannot set a home on a players name!");
								}
							} else {
								Warnings.getWarnings(sender).noPermission();
							}
						}
					}
				} else {
					Warnings.getWarnings(sender).noPermission();
				}
			} else {
				Warnings.getWarnings(sender).consoleMessage();
			}
		}
		return false;
	}

}
