package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;

public class CmdSmite {
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("smite")) {
			if(sender.hasPermission(PermissionKey.CMD_SMITE.getPermission())) {
				if(args.length == 0) {
					if(sender instanceof Player) {
						Player p = (Player) sender;
						p.getWorld().strikeLightning(p.getLocation());
						p.sendMessage(ChatColor.GRAY + "you have smited yourself!");
					} else {
						Warnings.getWarnings(sender).consoleMessage();
					}
				} else if(args.length == 1) {
					Player p =xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[0]).getPlayer();
					if(p instanceof Player) {
						p.getWorld().strikeLightning(p.getLocation());
						p.sendMessage(ChatColor.GRAY + "you got smited!");
					} else {
						sender.sendMessage(ChatColor.RED + "the player is not online");
					}
				}
			}
		}
		return false;
	}

}
