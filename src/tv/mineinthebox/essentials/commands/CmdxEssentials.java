package tv.mineinthebox.essentials.commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.utils.LWCRunnable;

public class CmdxEssentials {

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("xEssentials")) {
			if(sender.hasPermission(PermissionKey.CMD_XESSENTIALS.getPermission())) {
				if(args.length == 0) {
					sender.sendMessage(ChatColor.GOLD + ".oO___[xEssentials version " + xEssentials.getPlugin().getDescription().getVersion() + "___Oo.");
					sender.sendMessage(ChatColor.GREEN + "this plugin is written by Xeph0re AKA xize ");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/xEssentials reload " + ChatColor.WHITE + ": reloads the plugin");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/xEssentials help " + ChatColor.WHITE + ": shows help");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/xEssentials tps " + ChatColor.WHITE + ": shows tps of the server");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/xEssentials convert <type> " + ChatColor.WHITE + ": current only supports lwc and iconomy as type");
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[xEssentials version " + xEssentials.getPlugin().getDescription().getVersion() + "___Oo.");
						sender.sendMessage(ChatColor.GREEN + "this plugin is written by Xeph0re AKA xize ");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/xEssentials reload " + ChatColor.WHITE + ": reloads the plugin");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/xEssentials help " + ChatColor.WHITE + ": shows help");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/xEssentials tps " + ChatColor.WHITE + ": shows tps of the server");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/xEssentials convert <type> " + ChatColor.WHITE + ": current only supports lwc and iconomy as type");
					} else if(args[0].equalsIgnoreCase("reload")) {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2[&3xEssentials&2]&f " + ChatColor.GRAY + "reloading xEssentials version " + xEssentials.getPlugin().getDescription().getVersion()));
						Configuration.reloadConfiguration();
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2[&3xEssentials&2]&f " + ChatColor.GRAY + "reload completed!"));
					} else if(args[0].equalsIgnoreCase("tps")) {
						if(sender.hasPermission(PermissionKey.CMD_TPS.getPermission())) {
							ChatColor tpsColor = null;
							sender.sendMessage(ChatColor.GOLD + ".oO___[tps]___Oo.");
							sender.sendMessage(ChatColor.GRAY + "Gc max: " + xEssentials.getManagers().getTPSManager().garbageCollectorMax() + "mb");
							sender.sendMessage(ChatColor.GRAY + "Free memory: " + xEssentials.getManagers().getTPSManager().getFreeMemory() + "mb");
							sender.sendMessage(ChatColor.GRAY + "Max memory " + xEssentials.getManagers().getTPSManager().getMemoryMax() + "mb");
							if(xEssentials.getManagers().getTPSManager().getServerTicks() < 15) {
								tpsColor = ChatColor.GREEN;
							} else if(xEssentials.getManagers().getTPSManager().getServerTicks() > 15 && xEssentials.getManagers().getTPSManager().getServerTicks() < 25) {
								tpsColor = ChatColor.YELLOW;
							} else if(xEssentials.getManagers().getTPSManager().getServerTicks() > 25) {
								tpsColor = ChatColor.RED;
							}
							sender.sendMessage(ChatColor.GRAY + "ticks: " + tpsColor + xEssentials.getManagers().getTPSManager().getServerTicks());
						} else {
							Warnings.getWarnings(sender).noPermission();
						}
					}
				} else if(args.length == 2) {
					if(args[0].equalsIgnoreCase("convert")) {
						if(args[1].equalsIgnoreCase("lwc")) {
							if(Configuration.getProtectionConfig().isProtectionEnabled()) {
								File dir = new File("plugins" + File.separator + "LWC");
								if(dir.isDirectory()) {
									File db = new File("plugins" + File.separator + "LWC" + File.separator + "lwc.db");
									if(db.exists()) {
										xEssentials.getPlugin().log("starting importing lwc database contents in our own database...", LogType.INFO);
										xEssentials.getPlugin().log("setting up a new workers thread, against server crashing.", LogType.INFO);
										sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2[&3xEssentials&2]&f " + ChatColor.GRAY + "starting importing lwc database contents in our own database..."));
										sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2[&3xEssentials&2]&f " + ChatColor.GRAY + "setting up a new workers thread, against server crashing."));
										Thread thread = new Thread(new LWCRunnable(sender));
										thread.start();
										xEssentials.getPlugin().log("thread has been activated, now waiting for response, this could take up to 30 minutes depending on the size of the database.", LogType.INFO);
										sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2[&3xEssentials&2]&f " + ChatColor.GRAY + "thread has been activated, now waiting for response, this could take up to 30 minutes depending on the size of the database."));
									} else {
										sender.sendMessage(ChatColor.RED + "could not find any LWC sqlite database");
									}
								} else {
									sender.sendMessage(ChatColor.RED + "could not find any LWC folder.");
								}
							} else {
								sender.sendMessage(ChatColor.RED + "protection system is not enabled!");
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
