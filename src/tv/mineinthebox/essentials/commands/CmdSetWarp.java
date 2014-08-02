package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

public class CmdSetWarp {
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("setwarp")) {
			if(sender.hasPermission(PermissionKey.CMD_SETWARP.getPermission())) {
				if(args.length == 0) {
					sender.sendMessage(ChatColor.GOLD + ".oO___[Setwarp help]___Oo.");
					sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/setwarp help " + ChatColor.WHITE + ": shows help");
					sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/setwarp <warp> " + ChatColor.WHITE + ": set the warp");
				} else if(args.length == 1) {
					if(!xEssentials.getManagers().getWarpManager().doesWarpExist(args[0])) {
						if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(sender.getName())) {
							xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
							xp.setWarp(args[0].toLowerCase(), xp.getPlayer().getLocation());
							sender.sendMessage(ChatColor.GREEN + "you successfully saved your warp!");
						} else {
							Warnings.getWarnings(sender).consoleMessage();
						}
					} else {
						sender.sendMessage(ChatColor.RED + "you cannot set this warp, this warp already exist!");
					}
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
