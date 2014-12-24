package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdKnock {
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("knock")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_KNOCK.getPermission())) {
					XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
					if(xp.isKnock()) {
						sender.sendMessage(ChatColor.GREEN + "you disabled antiknockback!");
						xp.setKnock(false);
					} else {
						sender.sendMessage(ChatColor.GREEN + "you enabled antiknockback!");
						xp.setKnock(true);
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
