package tv.mineinthebox.bukkit.essentials.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.bukkit.essentials.Warnings;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;
import tv.mineinthebox.bukkit.essentials.instances.Warp;
import tv.mineinthebox.bukkit.essentials.interfaces.XPlayer;

public class CmdDelWarp {
	
	private List<String> getwarps(CommandSender sender, String p, Boolean playerOnly) {
		List<String> tabs = new ArrayList<String>();
		if(!playerOnly) {
			List<Warp> s = new ArrayList<Warp>(Arrays.asList(xEssentials.getManagers().getWarpManager().getWarps()));
			for(Warp warp : s) {
				if(warp.getWarpName().toLowerCase().startsWith(p.toLowerCase())) {
					tabs.add(warp.getWarpName());
				}
			}	
		} else {
			if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(sender.getName())) {
				XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
				for(Warp warp : xEssentials.getManagers().getWarpManager().getWarps(xp.getPlayer())) {
					if(warp.getWarpName().toLowerCase().startsWith(p.toLowerCase())) {
						tabs.add(warp.getWarpName());
					}
				}
			}
		}
		return tabs;
	}
	
	public List<String> onTabComplete(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("delwarp")) {
			if(args.length == 1) {
				if(sender.hasPermission(PermissionKey.CMD_DELWARP.getPermission())) {
					if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
						List<String> list = getwarps(sender, args[0], false);
						return list;	
					} else {
						List<String> list = getwarps(sender, args[0], true);
						return list;	
					}
				}
			}
		}
		return null;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("delwarp")) {
			if(sender.hasPermission(PermissionKey.CMD_DELWARP.getPermission())) {
				if(args.length == 0) {
					sender.sendMessage(ChatColor.GOLD + ".oO___[delwarp help]___Oo.");
					sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/delwarp help " + ChatColor.WHITE + ": shows help");
					if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/delwarp <warp> " + ChatColor.WHITE + ": deletes the warp");
					} else {
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/delwarp <warp> " + ChatColor.WHITE + ": deletes the warp you own");
					}
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[delwarp help]___Oo.");
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/delwarp help " + ChatColor.WHITE + ": shows help");
						if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/delwarp <warp> " + ChatColor.WHITE + ": deletes the warp");
						} else {
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/delwarp <warp> " + ChatColor.WHITE + ": deletes the warp you own");
						}
					} else if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
						if(xEssentials.getManagers().getWarpManager().isWarp(args[0].toLowerCase())) {
							Warp warp = xEssentials.getManagers().getWarpManager().getWarp(args[0].toLowerCase(), (Player)sender);
							if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
								warp.removeWarp();
								sender.sendMessage(ChatColor.GREEN + "you successfully removed the warp ");
							} else {
								sender.sendMessage(ChatColor.RED + "you dont have permission to remove the warp");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "warp does not exist!");
						}
					} else {
						if(xEssentials.getManagers().getWarpManager().isWarp(args[0].toLowerCase())) {
							Warp warp = xEssentials.getManagers().getWarpManager().getWarp(args[0].toLowerCase(), (Player)sender);
							if(warp.isOwner()) {
								sender.sendMessage(ChatColor.GREEN + "you successfully removed the warp ");
							} else {
								sender.sendMessage(ChatColor.RED + "you are not the owner of this warp!");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "warp does not exist!");
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
