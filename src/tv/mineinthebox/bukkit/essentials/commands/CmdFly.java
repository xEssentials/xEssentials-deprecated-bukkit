package tv.mineinthebox.bukkit.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.bukkit.essentials.Warnings;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;
import tv.mineinthebox.bukkit.essentials.interfaces.XPlayer;

public class CmdFly {
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("fly")) {
			if(sender.hasPermission(PermissionKey.CMD_FLY.getPermission())) {
				if(args.length == 0) {
					if(sender instanceof Player) {
						Player p = (Player) sender;
						if(xEssentials.getManagers().getPlayerManager().isOnline(sender.getName())) {
							XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
							if(xp.isFlying()) {
								xp.setFlying(false);
								sender.sendMessage(ChatColor.GREEN + "successfully disabled fly mode");
								p.setFlying(false);
								p.setAllowFlight(false);
							} else {
								xp.setFlying(true);
								sender.sendMessage(ChatColor.GREEN + "successfully enabled fly mode");
								p.setAllowFlight(true);
								p.setFlying(true);
							}
						} else {
							sender.sendMessage(ChatColor.RED + "something went wrong, please reload xEssentials");
						}
					} else {
						Warnings.getWarnings(sender).consoleMessage();
					}
				} else if(args.length == 1 ) {
					
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
