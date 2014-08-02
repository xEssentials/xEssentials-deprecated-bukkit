package tv.mineinthebox.essentials.commands;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.enums.PermissionKey;

public class CmdArrow {
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("arrow")) {
			if(sender.hasPermission(PermissionKey.CMD_ARROW.getPermission())) {
				if(sender instanceof Player) {
					Player p = (Player) sender;
					
					Location eye = p.getEyeLocation();
					int range = 2;
					int loops = 30;
					
					Random rand = new Random();
					
					for(Entity entity : p.getNearbyEntities(20, 20, 20)) {
						if(entity instanceof Player) {
							Player p1 = (Player) entity;
							p1.sendMessage(ChatColor.GRAY + sender.getName() + " used a arrow machine gun uopen them all >:) !");
						}
					}
					
					p.sendMessage(ChatColor.GRAY + sender.getName() + " used a arrow machine gun uopen them all >:) !");
					
					for(int i = 0; i < loops; i++) {
						int x = rand.nextInt(range);
						int y = 0;
						int z = rand.nextInt(range);
						
						Arrow arrow = p.getWorld().spawnArrow(eye.add(x, y, z), p.getLocation().getDirection(), 1, 1);
						arrow.setCritical(true);
						arrow.setShooter(p);
						
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
