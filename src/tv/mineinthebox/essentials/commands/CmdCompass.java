package tv.mineinthebox.essentials.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdCompass {

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
		if(cmd.getName().equalsIgnoreCase("compass")) {
			if(sender.hasPermission(PermissionKey.CMD_COMPASS.getPermission())) {
				if(args.length == 1) {
					List<String> list = getPlayerByName(args[0]);
					return list;
				}
			}
		}
		return null;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("compass")) {
			if(sender.hasPermission(PermissionKey.CMD_COMPASS.getPermission())) {
				if(sender instanceof Player) {
					XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
					if(args.length == 0) {
						if(xp.hasCompass()) {
							xp.removeCompass();
							sender.sendMessage(ChatColor.GREEN + "you have successfully unbinded the player from your clock!");
						} else {
							sender.sendMessage(ChatColor.RED + "you don't have any player set on your compass!, use /compass player instead use /compass alone to unbind the command from your clock");
						}
					} else if(args.length == 1) {
						if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(args[0])) {
							XOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
							xp.setCompass(off.getUser());
							sender.sendMessage(ChatColor.GREEN + "you have successfully added " + off.getUser() + " to your clock!");
						} else {
							sender.sendMessage(ChatColor.RED + "this player has never played before!");
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
