package tv.mineinthebox.bukkit.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.bukkit.essentials.Warnings;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;

public class CmdWarps {

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("warps")) {
			if(sender.hasPermission(PermissionKey.CMD_WARPS.getPermission())) {
				if(xEssentials.getManagers().getWarpManager().getWarps().length != 0) {
					StringBuilder build = new StringBuilder();
					for(int i = 0; i < xEssentials.getManagers().getWarpManager().getWarps().length; i++) {
						if(i == xEssentials.getManagers().getWarpManager().getWarps().length) {
							build.append(xEssentials.getManagers().getWarpManager().getWarps()[i].getWarpName());
						} else {
							build.append(xEssentials.getManagers().getWarpManager().getWarps()[i].getWarpName() + ", ");
						}
					}
					sender.sendMessage(ChatColor.GOLD + ".oO___[Warp list]___Oo.");
					sender.sendMessage(ChatColor.GRAY + build.toString());
				} else {
					sender.sendMessage(ChatColor.GOLD + ".oO___[Warp list]___Oo.");
					sender.sendMessage(ChatColor.RED + "no warps could be found!");
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
