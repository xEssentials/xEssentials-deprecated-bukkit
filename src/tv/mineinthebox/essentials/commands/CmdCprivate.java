package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.events.protection.RegisterProtectionEvent;

public class CmdCprivate {
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("cprivate")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_CPRIVATE.getPermission())) {
					RegisterProtectionEvent.players.add(sender.getName());
					sender.sendMessage(ChatColor.GREEN + "right click the block you want to register!");
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
