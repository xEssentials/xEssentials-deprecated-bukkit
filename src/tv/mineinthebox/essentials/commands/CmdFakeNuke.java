package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

public class CmdFakeNuke {
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("fakenuke")) {
			if(sender.hasPermission(PermissionKey.CMD_FAKE_NUKE.getPermission())) {
				if(args.length == 0) {
					if(sender instanceof Player) {
						xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
						for(Entity entity : xp.getPlayer().getNearbyEntities(40, 40, 40)) {
							if(entity instanceof Player) {
								Player p = (Player) entity;
								p.sendMessage(ChatColor.GRAY + "May death rain upon them");
							}
						}
						sender.sendMessage(ChatColor.GRAY + "May death rain upon them");
						xp.getPlayer().getWorld().playSound(xp.getPlayer().getLocation(), Sound.FUSE, 0.98F, 0.98F);
						xp.fakenuke();
					} else {
						Warnings.getWarnings(sender).consoleMessage();
					}
				} else if(args.length == 1) {
					Player p = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[0]).getPlayer();
					if(p instanceof Player) {
						xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(p.getName());
						for(Entity entity : xp.getPlayer().getNearbyEntities(40, 40, 40)) {
							if(entity instanceof Player) {
								Player r = (Player) entity;
								r.sendMessage(ChatColor.GRAY + "May death rain upon them");
							}
						}
						sender.sendMessage(ChatColor.GRAY + "May death rain upon them");
						xp.getPlayer().getWorld().playSound(xp.getPlayer().getLocation(), Sound.FUSE, 0.98F, 0.98F);
						xp.fakenuke();
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
