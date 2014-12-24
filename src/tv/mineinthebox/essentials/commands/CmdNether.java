package tv.mineinthebox.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.enums.PermissionKey;

public class CmdNether {
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("nether")) {
			if(sender.hasPermission(PermissionKey.CMD_NETHER.getPermission())) {
				if(sender instanceof Player) {
					if(args.length == 0) {
						Player p = (Player)sender;
						Location loc = p.getLocation();
						loc.setX((loc.getX()/8));
						loc.setZ((loc.getZ()/8));
						
						World w = Bukkit.getWorld(loc.getWorld().getName()+"_nether");
						if(w instanceof World) {
							loc.setWorld(Bukkit.getWorld(loc.getWorld().getName()+"_nether"));	
						} else {
							p.sendMessage(ChatColor.RED + "it doesn't looks that this world has a nether maybe you are in the END?");
							return false;
						}
						p.sendMessage(ChatColor.GRAY + "teleporting to the exact nether location!");
						p.teleport(loc);	
					} else if(args.length == 1) {
						if(args[0].equalsIgnoreCase("help")) {
							sender.sendMessage(ChatColor.GOLD + ".oO___[nether]___Oo.");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/nether " + ChatColor.WHITE + ": attemps to teleport to the nether on exact /8 coords where you stay");
						} else {
							sender.sendMessage(ChatColor.RED + "unknown argument!");
						}
					}
				} else {
					Warnings.getWarnings(sender).consoleMessage();
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
