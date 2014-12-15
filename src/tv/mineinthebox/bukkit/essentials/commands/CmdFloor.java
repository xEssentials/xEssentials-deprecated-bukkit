package tv.mineinthebox.bukkit.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.bukkit.essentials.Warnings;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;
import tv.mineinthebox.bukkit.essentials.interfaces.XPlayer;

public class CmdFloor {

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("floor")) {
			if(sender.hasPermission(PermissionKey.CMD_FLOOR.getPermission())) {
				if(sender instanceof Player) {
					if(args.length == 0) {
						XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
						if(xp.isFloorMode()) {
							xp.setFloorMode(false, xp.getFloorModeRange());
							sender.sendMessage(ChatColor.GRAY + "floor mode disabled!");
						} else {
							xp.setFloorMode(true, 8);
							sender.sendMessage(ChatColor.GRAY + "floor mode is enabled!");
						}
					} else if(args.length == 1) {
						if(args[0].equalsIgnoreCase("help")) {
							sender.sendMessage(ChatColor.GOLD + ".oO___[floor]___Oo.");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/floor " + ChatColor.WHITE + ": creates a floor within a block section");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/floor <number>" + ChatColor.WHITE + ": creates a floor within a range in a block section");
						} else {
							XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
							int range = Integer.parseInt(args[0]);
							xp.setFloorMode(true, range);
							sender.sendMessage(ChatColor.GRAY + "floor mode is enabled!");
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
