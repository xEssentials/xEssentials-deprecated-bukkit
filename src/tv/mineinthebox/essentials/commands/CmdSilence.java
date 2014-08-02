package tv.mineinthebox.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

public class CmdSilence {
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("silence")) {
			if(sender.hasPermission(PermissionKey.CMD_SILENCE.getPermission())) {
				if(args.length == 0) {
					xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
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
