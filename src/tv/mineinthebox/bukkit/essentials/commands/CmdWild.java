package tv.mineinthebox.bukkit.essentials.commands;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.bukkit.essentials.Warnings;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;

public class CmdWild {
	
	private int xRadius = 10000;
	private int zRadius = 10000;
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("wild")) {
			if(sender.hasPermission(PermissionKey.CMD_WILD.getPermission())) {
				if(args.length == 0) {
					if(sender instanceof Player) {
						Player p = (Player) sender;
						Random randx = new Random();
						Random randz = new Random();
						int x = randx.nextInt(xRadius)+p.getLocation().getBlockX();
						int z = randz.nextInt(zRadius)+p.getLocation().getBlockZ();
						Location loc = new Location(p.getWorld(), x, p.getWorld().getHighestBlockYAt(x, z), z);
						loc.getWorld().refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
						p.teleport(loc);
						sender.sendMessage(ChatColor.GREEN + "you successfully has teleported to the wild!");
					} else {
						Warnings.getWarnings(sender).consoleMessage();
					}
				} else if(args.length == 1) {
					Player p = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[0]).getPlayer();
					if(p instanceof Player) {
						Random randx = new Random(xRadius);
						Random randz = new Random(zRadius);
						int x = randx.nextInt()+p.getLocation().getBlockX();
						int z = randz.nextInt()+p.getLocation().getBlockX();
						Location loc = new Location(p.getWorld(), x, p.getWorld().getHighestBlockYAt(x, z), z);
						loc.getWorld().refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
						p.teleport(loc);
						p.sendMessage(ChatColor.GREEN + "you successfully has teleported to the wild!");	
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
