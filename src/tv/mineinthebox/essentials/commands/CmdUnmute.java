package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdUnmute {

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("unmute")) {
			if(sender.hasPermission(PermissionKey.CMD_UNMUTE.getPermission())) {
				if(args.length == 0) {
					sender.sendMessage(ChatColor.GOLD + ".oO___[unmute help]___Oo.");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/unmute help " + ChatColor.WHITE + ": shows help");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/unmute <player> " + ChatColor.WHITE + "unmute a player!");
				} else if(args.length == 1) {
					if(xEssentials.getManagers().getPlayerManager().isOnline(args[0])) {
						XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(args[0]);
						if(xp.isMuted()) {
							xp.unmute();
							sender.sendMessage(ChatColor.GREEN + "you successfully unmuted the player " + xp.getUser());
							xp.getPlayer().sendMessage(ChatColor.GREEN + sender.getName() + " has unmuted your chat!");
						} else {
							sender.sendMessage(ChatColor.RED + "this player is already unmuted!");
						}
					} else {
						try {
							XOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
							if(off.isMuted()) {
								off.unmute();
								sender.sendMessage(ChatColor.GREEN + "you successfully unmuted the offline player " + off.getUser() + "!");
							} else {
								sender.sendMessage(ChatColor.RED + "this offline player is already unmuted!");
							}
						} catch(NullPointerException e) {
							Warnings.getWarnings(sender).playerHasNeverPlayedBefore();
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
