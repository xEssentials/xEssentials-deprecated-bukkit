package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdSpeed {
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("speed")) {
			if(sender.hasPermission(PermissionKey.CMD_SPEED.getPermission())) {
				if(sender instanceof Player) {
					XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
					if(xp.isSpeedEnabled()) {
						xp.removeSpeed();
						sender.sendMessage(ChatColor.GREEN + "speed disabled!");
					} else {
						xp.setSpeed(1);
						sender.sendMessage(ChatColor.GREEN + "speed enabled!");
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
