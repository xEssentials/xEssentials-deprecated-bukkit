package tv.mineinthebox.bukkit.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.bukkit.essentials.Warnings;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsPlayer;

public class CmdNick {
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("nick")) {
			if(sender.hasPermission(PermissionKey.CMD_NICK.getPermission())) {
				if(args.length == 0) {
					if(sender instanceof Player) {
						xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
						xp.setCustomName(xp.getUser());
						sender.sendMessage(ChatColor.GREEN + "successfully setted your name back to default.");
						for(Player p : xEssentials.getOnlinePlayers()) {
							if(p.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
								p.sendMessage(ChatColor.GRAY + xp.getCustomName() + ChatColor.GRAY + " has changed his name back to default.");
							}
						}
					} else {
						Warnings.getWarnings(sender).consoleMessage();
					}
				} else if(args.length == 1) {
					xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
					xp.setCustomName(ChatColor.translateAlternateColorCodes('&', args[0]));
					sender.sendMessage(ChatColor.GREEN + "successfully setted your name back to " + xp.getCustomName());
					for(Player p : xEssentials.getOnlinePlayers()) {
						if(p.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							p.sendMessage(ChatColor.GRAY + xp.getUser() + ChatColor.GRAY + " has changed his name to " + xp.getCustomName());
						}
					}
				} else if(args.length == 2) {
					if(xEssentials.getManagers().getPlayerManager().isOnline(args[0])) {
						xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(args[0]);
						xp.setCustomName(ChatColor.translateAlternateColorCodes('&', args[1]));
						sender.sendMessage(ChatColor.GREEN + "successfully setted "+xp.getUser()+" his name back to " + xp.getCustomName());
						for(Player p : xEssentials.getOnlinePlayers()) {
							if(p.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
								p.sendMessage(ChatColor.GRAY + xp.getUser() + ChatColor.GRAY + " has changed his name to " + xp.getCustomName());
							}
						}	
					} else {
						sender.sendMessage(ChatColor.RED + "player is not online.");
					}
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
