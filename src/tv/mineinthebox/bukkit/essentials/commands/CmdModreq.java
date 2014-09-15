package tv.mineinthebox.bukkit.essentials.commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.bukkit.essentials.Warnings;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;
import tv.mineinthebox.bukkit.essentials.instances.Modreq;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsPlayer;

public class CmdModreq {

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("modreq")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_MODREQ.getPermission())) {
					if(args.length == 0) {
						sendHelp(sender);
					} else if(args.length == 1) {
						if(args[0].equalsIgnoreCase("help")) {
							sendHelp(sender);
						} else {
							if(xEssentials.getManagers().getPlayerManager().isOnline(sender.getName())) {
								xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
								xp.createModreq(args[0]);
								int lastID = xp.getModreqs().length;
								Modreq mod = xp.getModreq((lastID-1));
								for(Player p : Bukkit.getOnlinePlayers()) {
									if(p.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
										Bukkit.broadcastMessage(ChatColor.GREEN + "[Modreq]" + ChatColor.GRAY  + sender.getName() + ChatColor.GRAY + " has created a new modreq!");
										Bukkit.broadcastMessage(ChatColor.GREEN + "[Modreq]" + ChatColor.GRAY + "id: " + ChatColor.GREEN + mod.getId() + ChatColor.GRAY + " message: " + ChatColor.GREEN + mod.getMessage() + ChatColor.GRAY + " date: " + ChatColor.GREEN + mod.getDate());
									}
								}
								sender.sendMessage(ChatColor.GREEN + "[Modreq]" + ChatColor.GRAY + "successfully created a modreq!");
							} else {
								sender.sendMessage(ChatColor.RED + "something went wrong please reload xEssentials!");
							}
						}
					} else {
						String message = Arrays.toString(args).replace("[", "").replace("]", "").replace(",", "");
						if(xEssentials.getManagers().getPlayerManager().isOnline(sender.getName())) {
							xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
							xp.createModreq(message);
							int lastID = xp.getModreqs().length;
							Modreq mod = xp.getModreq((lastID-1));
							for(Player p : Bukkit.getOnlinePlayers()) {
								if(p.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
									Bukkit.broadcastMessage(ChatColor.GREEN + "[Modreq]" + ChatColor.GRAY  + sender.getName() + ChatColor.GRAY + " has created a new modreq!");
									Bukkit.broadcastMessage(ChatColor.GREEN + "[Modreq]" + ChatColor.GRAY + "id: " + ChatColor.GREEN + mod.getId() + ChatColor.GRAY + " message: " + ChatColor.GREEN + mod.getMessage() + ChatColor.GRAY + " date: " + ChatColor.GREEN + mod.getDate());
								}
							}
							sender.sendMessage(ChatColor.GREEN + "[Modreq]" + ChatColor.GRAY + "successfully created a modreq!");
						} else {
							sender.sendMessage(ChatColor.RED + "something went wrong please reload xEssentials!");
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
	
	private void sendHelp(CommandSender sender) {
		sender.sendMessage(ChatColor.GOLD + ".oO___[modreq help]___Oo.");
		sender.sendMessage(ChatColor.GRAY + "Default: " + ChatColor.GRAY + "/modreq " + ChatColor.WHITE + ": shows help");
		sender.sendMessage(ChatColor.GRAY + "Default: " + ChatColor.GRAY + "/modreq help " + ChatColor.WHITE + ": shows help");
		sender.sendMessage(ChatColor.GRAY + "Default: " + ChatColor.GRAY + "/modreq <message> " + ChatColor.WHITE + ": creates a modreq");
	}

}
