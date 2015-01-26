package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdNuke {
	
	private final xEssentials pl;
	
	public CmdNuke(xEssentials pl) {
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("nuke")) {
			if(sender.hasPermission(PermissionKey.CMD_NUKE.getPermission())) {
				if(args.length == 0) {
					if(sender instanceof Player) {
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
						for(Entity entity : xp.getPlayer().getNearbyEntities(40, 40, 40)) {
							if(entity instanceof Player) {
								Player p = (Player) entity;
								p.sendMessage(ChatColor.GRAY + "May death rain upon them");
							}
						}
						sender.sendMessage(ChatColor.GRAY + "May death rain upon them");
						xp.nuke();
					} else {
						Warnings.getWarnings(sender).consoleMessage();
					}
				} else if(args.length == 1) {
					Player p = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]).getPlayer();
					if(p instanceof Player) {
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(p.getName());
						for(Entity entity : xp.getPlayer().getNearbyEntities(40, 40, 40)) {
							if(entity instanceof Player) {
								Player r = (Player) entity;
								r.sendMessage(ChatColor.GRAY + "May death rain upon them");
							}
						}
						sender.sendMessage(ChatColor.GRAY + "May death rain upon them");
						xp.nuke();
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
