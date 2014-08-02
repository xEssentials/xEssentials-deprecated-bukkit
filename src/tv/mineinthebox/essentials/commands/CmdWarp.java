package tv.mineinthebox.essentials.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.Warp;

public class CmdWarp {
	
	private List<String> getwarps(String p) {
		List<Warp> s = new ArrayList<Warp>(Arrays.asList(xEssentials.getManagers().getWarpManager().getWarps()));
		List<String> tabs = new ArrayList<String>();
		for(Warp warp : s) {
			if(warp.getWarpName().toLowerCase().startsWith(p.toLowerCase())) {
				tabs.add(warp.getWarpName());
			}
		}
		return tabs;
	}
	
	public List<String> onTabComplete(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("warp")) {
			if(args.length == 1) {
				if(sender.hasPermission(PermissionKey.CMD_WARP.getPermission())) {
					List<String> list = getwarps(args[0]);
					return list;
				}
			}
		}
		return null;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("warp")) {
			if(sender.hasPermission(PermissionKey.CMD_WARP.getPermission())) {
				if(args.length == 0) {
					sender.sendMessage(ChatColor.GOLD + ".oO___[warp help]___Oo.");
					sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/warp help " + ChatColor.WHITE + ": shows help");
					sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/warp <warp> " + ChatColor.WHITE + ": allows you to teleport to the warp");
					if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/warp <player> <warp> " + ChatColor.WHITE + ": teleports a player to a warp");
					}
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[warp help]___Oo.");
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/warp help " + ChatColor.WHITE + ": shows help");
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/warp <warp> " + ChatColor.WHITE + ": allows you to teleport to the warp");
						if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/warp <player> <warp> " + ChatColor.WHITE + ": teleports a player to a warp");
						}
					} else {
						if(sender instanceof Player) {
							Player p = (Player) sender;
							if(xEssentials.getManagers().getWarpManager().doesWarpExist(args[0])) {
								Warp warp = xEssentials.getManagers().getWarpManager().getWarpByName(args[0]);
								warp.getWarpLocation().getWorld().refreshChunk(warp.getWarpLocation().getChunk().getX(), warp.getWarpLocation().getChunk().getZ());
								p.teleport(warp.getWarpLocation());
								sender.sendMessage(ChatColor.GREEN + "teleporting to warp " + warp.getWarpName());
							} else {
								sender.sendMessage(ChatColor.RED + "warp doesn't exist!");
							}
						} else {
							Warnings.getWarnings(sender).consoleMessage();
						}
					}
				} else if(args.length == 2) {
					Player p = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[0]).getPlayer();
					if(p instanceof Player) {
						if(xEssentials.getManagers().getWarpManager().doesWarpExist(args[1])) {
							Warp warp = xEssentials.getManagers().getWarpManager().getWarpByName(args[1]);
							warp.getWarpLocation().getWorld().refreshChunk(warp.getWarpLocation().getChunk().getX(), warp.getWarpLocation().getChunk().getZ());
							p.teleport(warp.getWarpLocation());
							p.sendMessage(ChatColor.GREEN + "teleporting to warp " + warp.getWarpName());
						} else {
							sender.sendMessage(ChatColor.RED + "warp doesn't exist!");
						}
					} else {
						Warnings.getWarnings(sender).playerHasNeverPlayedBefore();
					}
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
