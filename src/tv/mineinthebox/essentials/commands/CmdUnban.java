package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdUnban {
	
	private final xEssentials pl;
	
	public CmdUnban(xEssentials pl) {
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("unban")) {
			if(sender.hasPermission(PermissionKey.CMD_UNBAN.getPermission())) {
				if(args.length == 0) {
					sender.sendMessage(ChatColor.GOLD + ".oO___[unban help]___Oo.");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/unban <player> " + ChatColor.WHITE + ": unbans a player with a default message");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/unban help " + ChatColor.WHITE + ": shows help");
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[unban help]___Oo.");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/unban <player> " + ChatColor.WHITE + ": unbans a player with a default message");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/unban help " + ChatColor.WHITE + ": shows help");
					} else {
						if(pl.getManagers().getPlayerManager().isOnline(args[0])) {
							XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[0]);
							if(xp.isPermBanned() || xp.isTempBanned()) {
								xp.unban();
								sender.sendMessage(ChatColor.GREEN + xp.getUser() + " has been unbanned!");
							} else {
								sender.sendMessage(ChatColor.RED + "this player is not banned!");
							}
						} else {
							try {
								XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
								if(off.isPermBanned() || off.isTempBanned()) {
									off.unban();
									sender.sendMessage(ChatColor.GREEN + off.getUser() + " has been unbanned!");
								} else {
									sender.sendMessage(ChatColor.RED + "this player is not banned!");
								}
							} catch(NullPointerException e) {
								Warnings.getWarnings(sender).playerHasNeverPlayedBefore();
							}
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
