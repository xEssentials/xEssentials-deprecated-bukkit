package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdWall {
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("wall")) {
			if(sender.hasPermission(PermissionKey.CMD_WALL.getPermission())) {
				if(sender instanceof Player) {
					if(args.length == 0) {
						XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
						if(xp.isWallMode()) {
							xp.setWallMode(false, xp.getWallModeRange());
							sender.sendMessage(ChatColor.GRAY + "wall mode disabled!");
						} else {
							xp.setWallMode(true, 8);
							sender.sendMessage(ChatColor.GRAY + "wall mode is enabled!");
						}
					} else if(args.length == 1) {
						if(args[0].equalsIgnoreCase("help")) {
							sender.sendMessage(ChatColor.GOLD + ".oO___[wall]___Oo.");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/wall " + ChatColor.WHITE + ": creates a wall within a block section");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/wall <number>" + ChatColor.WHITE + ": creates a wall within a range in a block section");
						} else {
							XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
							int range = Integer.parseInt(args[0]);
							xp.setWallMode(true, range);
							sender.sendMessage(ChatColor.GRAY + "wall mode is enabled!");
						}
					}
				} else {
					Warnings.getWarnings(sender).consoleMessage();
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
