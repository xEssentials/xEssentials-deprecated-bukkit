package tv.mineinthebox.bukkit.essentials.commands;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.bukkit.essentials.Warnings;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;
import tv.mineinthebox.bukkit.essentials.enums.PlayerTaskEnum;
import tv.mineinthebox.bukkit.essentials.interfaces.XOfflinePlayer;

public class CmdTask {
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("task")) {
			if(sender.hasPermission(PermissionKey.CMD_TASK.getPermission())) {
				if(args.length == 0) {
					sender.sendMessage(ChatColor.GOLD + ".oO___[task help]___Oo.");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/task " + ChatColor.WHITE + "shows help");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/task help " + ChatColor.WHITE + "shows help");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/task player <player> command" + ChatColor.WHITE + " do a task on a players command without \"/\"");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/task console <player> command " + ChatColor.WHITE + ": do a console command on a player without \"/\"");
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[task help]___Oo.");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/task " + ChatColor.WHITE + "shows help");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/task help " + ChatColor.WHITE + "shows help");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/task player <player> command" + ChatColor.WHITE + " do a task on a players command without \"/\"");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/task console <player> command " + ChatColor.WHITE + ": do a console command on a player without \"/\"");
					}
				} else if(args.length > 2) {
					if(args[0].equalsIgnoreCase("player")) {
						if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(args[1])) {
							XOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[1]);
							PlayerTaskEnum playerenum = PlayerTaskEnum.PLAYER;
							String command = Arrays.toString(args).replace("[", "").replace(",", "").replace("]", "").replace(args[0]+" ", "").replace(args[1] + " ", "");
							off.PrepareLoginTask(command, playerenum);
							sender.sendMessage(ChatColor.GREEN + "successfully saved Player command for relog for player " + off.getUser() + "!");
						} else {
							sender.sendMessage(ChatColor.RED + "this player has never played on this server!");
						}
					} else if(args[0].equalsIgnoreCase("console")) {
						if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(args[1])) {
							XOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[1]);
							PlayerTaskEnum playerenum = PlayerTaskEnum.CONSOLE;
							String command = Arrays.toString(args).replace("[", "").replace(",", "").replace("]", "").replace(args[0] + " ", "").replace(args[1]+ " ", "");
							off.PrepareLoginTask(command, playerenum);
							sender.sendMessage(ChatColor.GREEN + "successfully saved CONSOLE command for relog for player " + off.getUser() + "!");
						} else {
							sender.sendMessage(ChatColor.RED + "this player has never played on this server!");
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
