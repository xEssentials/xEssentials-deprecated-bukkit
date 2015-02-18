package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;

public class CmdxEssentials {
	
	private final xEssentials pl;
	
	public CmdxEssentials(xEssentials pl) {
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("xEssentials")) {
			if(sender.hasPermission(PermissionKey.CMD_XESSENTIALS.getPermission())) {
				if(args.length == 0) {
					sender.sendMessage(ChatColor.GOLD + ".oO___[xEssentials version " + pl.getDescription().getVersion() + "___Oo.");
					sender.sendMessage(ChatColor.GREEN + "this plugin is written by Xeph0re AKA xize ");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/xEssentials reload " + ChatColor.WHITE + ": reloads the plugin");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/xEssentials help " + ChatColor.WHITE + ": shows help");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/xEssentials tps " + ChatColor.WHITE + ": shows tps of the server");
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[xEssentials version " + pl.getDescription().getVersion() + "___Oo.");
						sender.sendMessage(ChatColor.GREEN + "this plugin is written by Xeph0re AKA xize ");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/xEssentials reload " + ChatColor.WHITE + ": reloads the plugin");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/xEssentials help " + ChatColor.WHITE + ": shows help");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/xEssentials tps " + ChatColor.WHITE + ": shows tps of the server");
					} else if(args[0].equalsIgnoreCase("reload")) {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2[&3xEssentials&2]&f " + ChatColor.GRAY + "reloading xEssentials version " + pl.getDescription().getVersion()));
						pl.getConfiguration().reload();
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2[&3xEssentials&2]&f " + ChatColor.GRAY + "reload completed!"));
					} else if(args[0].equalsIgnoreCase("tps")) {
						if(sender.hasPermission(PermissionKey.CMD_TPS.getPermission())) {
							ChatColor tpsColor = null;
							sender.sendMessage(ChatColor.GOLD + ".oO___[tps]___Oo.");
							sender.sendMessage(ChatColor.GRAY + "Gc max: " + pl.getManagers().getTPSManager().garbageCollectorMax() + "mb");
							sender.sendMessage(ChatColor.GRAY + "Free memory: " + pl.getManagers().getTPSManager().getFreeMemory() + "mb");
							sender.sendMessage(ChatColor.GRAY + "Max memory " + pl.getManagers().getTPSManager().getMemoryMax() + "mb");
							if(pl.getManagers().getTPSManager().getServerTicks() < 15) {
								tpsColor = ChatColor.GREEN;
							} else if(pl.getManagers().getTPSManager().getServerTicks() > 15 && pl.getManagers().getTPSManager().getServerTicks() < 25) {
								tpsColor = ChatColor.YELLOW;
							} else if(pl.getManagers().getTPSManager().getServerTicks() > 25) {
								tpsColor = ChatColor.RED;
							}
							sender.sendMessage(ChatColor.GRAY + "ticks: " + tpsColor + pl.getManagers().getTPSManager().getServerTicks());
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
