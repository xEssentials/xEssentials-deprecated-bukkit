package tv.mineinthebox.bukkit.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.bukkit.essentials.Warnings;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;
import tv.mineinthebox.bukkit.essentials.interfaces.XPlayer;

public class CmdFirefly {
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("firefly")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_FIREFLY.getPermission())) {
					if(xEssentials.getManagers().getPlayerManager().isOnline(sender.getName())) {
						XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
						if(xp.isFirefly()) {
							xp.setFirefly(false);
							sender.sendMessage(ChatColor.GREEN + "you successfully disabled firefly!");
						} else {
							xp.setFirefly(true);
							sender.sendMessage(ChatColor.GREEN + "you successfully enabled firefly!");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "you don't seems to be listed at our global list, please reload xEssentials");
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
