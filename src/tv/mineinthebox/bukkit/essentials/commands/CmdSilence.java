package tv.mineinthebox.bukkit.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.Warnings;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;
import tv.mineinthebox.bukkit.essentials.interfaces.XPlayer;

public class CmdSilence {
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("silence")) {
			if(sender.hasPermission(PermissionKey.CMD_SILENCE.getPermission())) {
				if(args.length == 0) {
					XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
					if(xp.isSilenced()) {
						xp.setSilenced(false);
						sender.sendMessage(ChatColor.GREEN + "you no longer have chat silenced!");
					} else {
						xp.setSilenced(true);
						sender.sendMessage(ChatColor.GREEN + "you successfully silenced chat!");
					}
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("all")) {
						if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							if(Configuration.isSilenceToggled) {
								Configuration.isSilenceToggled = false;
								Bukkit.broadcastMessage(ChatColor.GREEN + "All server chat is now unhalted!");
							} else {
								Configuration.isSilenceToggled = true;
								Bukkit.broadcastMessage(ChatColor.GREEN + "All server chat is now halted!");
							}
						} else {
							Warnings.getWarnings(sender).noPermission();
						}
					}
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
