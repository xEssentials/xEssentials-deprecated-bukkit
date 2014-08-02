package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.events.protection.UnregisterProtectionEvent;

public class CmdCremove {
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("cremove")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_CREMOVE.getPermission())) {
					UnregisterProtectionEvent.players.add(sender.getName());
					sender.sendMessage(ChatColor.GREEN + "right click the block you want to unregister!");
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
