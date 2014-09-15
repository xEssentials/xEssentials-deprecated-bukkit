package tv.mineinthebox.bukkit.essentials.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.bukkit.essentials.Warnings;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsOfflinePlayer;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsPlayer;

public class CmdDoubleJump {
	
	private List<String> getPlayerByName(String p) {
		List<String> s = new ArrayList<String>();
		for(xEssentialsOfflinePlayer name : xEssentials.getManagers().getPlayerManager().getOfflinePlayers()) {
			if(name.getUser().toUpperCase().startsWith(p.toUpperCase())) {
				s.add(name.getUser());
			}
		}
		return s;
	}

	public List<String> onTabComplete(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("doublejump")) {
			if(args.length == 1) {
				if(sender.hasPermission(PermissionKey.CMD_DOUBLEJUMP.getPermission())) {
					List<String> list = getPlayerByName(args[0]);
					return list;
				}
			}
		}
		return null;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("doublejump")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_DOUBLEJUMP.getPermission())) {
					if(args.length == 0) {
						xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
						if(xp.hasDoubleJump()) {
							xp.setDoubleJump(false);
							sender.sendMessage(ChatColor.GREEN + "disabled double jump");
						} else {
							xp.setDoubleJump(true);
							sender.sendMessage(ChatColor.GREEN + "enabled double jump");
						}
					} else if(args.length == 1) {
						if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(args[0])) {
							if(xEssentials.getManagers().getPlayerManager().isOnline(args[0])) {
								xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(args[0]);
								if(xp.hasDoubleJump()) {
									xp.setDoubleJump(false);
									xp.getPlayer().sendMessage(ChatColor.GREEN + sender.getName() + " has turned off your double jump");
									sender.sendMessage(ChatColor.GREEN + "disabled double jump on online player " + xp.getUser());
								} else {
									xp.setDoubleJump(false);
									xp.getPlayer().sendMessage(ChatColor.GREEN + sender.getName() + " has turned on your double jump");
									sender.sendMessage(ChatColor.GREEN + "enabled double jump on online player " + xp.getUser());
								}
							} else {
								xEssentialsOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
								if(off.hasDoubleJump()) {
									off.setDoubleJump(false);
									sender.sendMessage(ChatColor.GREEN + "disabled double jump on offline player " + off.getUser());
								} else {
									off.setDoubleJump(true);
									sender.sendMessage(ChatColor.GREEN + "enabled double jump on offline player " + off.getUser());
								}
							}
						} else {
							Warnings.getWarnings(sender).playerHasNeverPlayedBefore();
						}
					}
				} else {
					Warnings.getWarnings(sender).noPermission();
				}
			} else {
				Warnings.getWarnings(sender).consoleMessage();
			}
		}
		return false;
	}

}
