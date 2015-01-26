package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdSetWarp {
	
	private final xEssentials pl;
	
	public CmdSetWarp(xEssentials pl) {
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("setwarp")) {
			if(sender.hasPermission(PermissionKey.CMD_SETWARP.getPermission())) {
				if(args.length == 0) {
					sender.sendMessage(ChatColor.GOLD + ".oO___[Setwarp help]___Oo.");
					sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/setwarp help " + ChatColor.WHITE + ": shows help");
					sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/setwarp <warp> " + ChatColor.WHITE + ": set the warp");
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.RED + "you cannot make a warp named help.");
					} else if(!pl.getManagers().getWarpManager().isWarp(args[0])) {
						if(pl.getManagers().getPlayerManager().isEssentialsPlayer(sender.getName())) {
							XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
							pl.getManagers().getWarpManager().setWarp(args[0], xp.getPlayer(), xp.getPlayer().getLocation());
							sender.sendMessage(ChatColor.GREEN + "you successfully saved your warp!");
						} else {
							Warnings.getWarnings(sender).consoleMessage();
						}
					} else {
						sender.sendMessage(ChatColor.RED + "you cannot set this warp, this warp already exist!");
					}
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
