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
import tv.mineinthebox.bukkit.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.bukkit.essentials.interfaces.XPlayer;

public class CmdProc {
	
	private List<String> getPlayerByName(String p) {
		List<String> s = new ArrayList<String>();
		for(XOfflinePlayer name : xEssentials.getManagers().getPlayerManager().getOfflinePlayers()) {
			if(name.getUser().toUpperCase().startsWith(p.toUpperCase())) {
				s.add(name.getUser());
			}
		}
		return s;
	}

	public List<String> onTabComplete(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("proc")) {
			if(args.length == 1) {
				if(sender.hasPermission(PermissionKey.CMD_PROC.getPermission())) {
					List<String> list = getPlayerByName(args[0]);
					return list;
				}
			}
		}
		return null;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("proc")) {
			if(sender.hasPermission(PermissionKey.CMD_PROC.getPermission())) {
				if(args.length == 0) {
					if(sender instanceof Player) {
						XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
						if(xp.hasProc()) {
							xp.setProc(false);
							sender.sendMessage(ChatColor.GREEN + "proc has been disabled");
						} else {
							xp.setProc(true);
							sender.sendMessage(ChatColor.GREEN + "proc has been enabled!");
						}
					} else {
						Warnings.getWarnings(sender).consoleMessage();
					}
				} else if(args.length == 1) {
					if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(args[0])) {
						if(xEssentials.getManagers().getPlayerManager().isOnline(args[0])) {
							XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(args[0]);
							if(xp.hasProc()) {
								xp.setProc(false);
								sender.sendMessage(ChatColor.GREEN + "proc has been disabled for player " + xp.getUser() + "!");
							} else {
								xp.setProc(true);
								sender.sendMessage(ChatColor.GREEN + "proc has been enabled for player " + xp.getUser() + "!");
							}
						}
					} else {
						Warnings.getWarnings(sender).playerHasNeverPlayedBefore();
					}
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
