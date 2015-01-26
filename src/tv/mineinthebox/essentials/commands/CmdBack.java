package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.managers.TeleportManager;

public class CmdBack {
	
	private final TeleportManager manager;
	private final xEssentials pl;
	
	public CmdBack(xEssentials pl) {
		this.pl = pl;
		this.manager = pl.getManagers().getTeleportManager();
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("back")) {
			if(sender.hasPermission(PermissionKey.CMD_BACK.getPermission())) {
				if(args.length == 0) {
					if(sender instanceof Player) {
						Player p = (Player) sender;
						if(manager.hasLastLocation(p.getName())) {
							Location loc = manager.getLastLocation(p.getName());
							loc.getWorld().refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
							p.teleport(loc);
							sender.sendMessage(ChatColor.GREEN + "teleporting to your last location!");
						} else {
							sender.sendMessage(ChatColor.RED + "no location data found for /back");
						}
					} else {
						Warnings.getWarnings(sender).consoleMessage();
					}
				} else if(args.length == 1) {
					Player p = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]).getPlayer();
					if(p instanceof Player) {
						if(manager.hasLastLocation(p.getName())) {
							Location loc = manager.getLastLocation(p.getName());
							loc.getWorld().refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
							p.teleport(loc);
							p.sendMessage(ChatColor.GREEN + sender.getName() + " has teleported you to your last location");
							sender.sendMessage(ChatColor.GREEN + "teleported " + p.getName() + " to his last location");
						} else {
							sender.sendMessage(ChatColor.RED + "no location data found for /back");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "this player is not online!");
					}
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
