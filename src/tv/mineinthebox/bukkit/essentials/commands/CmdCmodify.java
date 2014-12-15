package tv.mineinthebox.bukkit.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.bukkit.essentials.Warnings;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.events.protection.ModifyProtectionEvent;
import tv.mineinthebox.bukkit.essentials.interfaces.XOfflinePlayer;

public class CmdCmodify {
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("cmodify")) {
			if(args.length == 0) {
				sender.sendMessage(ChatColor.RED + "/cmodify user : adds the user");
			} else if(args.length == 1) {
				if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(args[0])) {
					XOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
					ModifyProtectionEvent.players.put(sender.getName(), off.getUser());
					sender.sendMessage(ChatColor.GREEN + "right click on a block to change permissions");
				} else {
					Warnings.getWarnings(sender).playerHasNeverPlayedBefore();
				}
			}
		}
		return false;
	}

}
