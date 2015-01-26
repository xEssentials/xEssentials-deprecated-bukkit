package tv.mineinthebox.essentials.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;

public class CmdFreeze {
	
	private final xEssentials pl;
	
	public CmdFreeze(xEssentials pl) {
		this.pl = pl;
	}
	
	private List<String> getPlayerByName(String p) {
		List<String> s = new ArrayList<String>();
		for(XOfflinePlayer name : pl.getManagers().getPlayerManager().getOfflinePlayers()) {
			if(name.getUser().toUpperCase().startsWith(p.toUpperCase())) {
				s.add(name.getUser());
			}
		}
		return s;
	}

	public List<String> onTabComplete(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("freeze")) {
			if(args.length == 1) {
				if(sender.hasPermission(PermissionKey.CMD_FREEZE.getPermission())) {
					List<String> list = getPlayerByName(args[0]);
					return list;
				}
			}
		}
		return null;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("freeze")) {
			if(sender.hasPermission(PermissionKey.CMD_FREEZE.getPermission())) {
				if(args.length == 0) {
					sender.sendMessage(ChatColor.GOLD + ".oO___[freeze help]___Oo.");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/freeze <player> " + ChatColor.WHITE + ": freezes the player movements");
				} else if(args.length == 1) {
					if(pl.getManagers().getPlayerManager().isEssentialsPlayer(args[0])) {
						sender.sendMessage(ChatColor.RED + "player is not online!");
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
