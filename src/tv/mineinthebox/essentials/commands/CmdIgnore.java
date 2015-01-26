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

public class CmdIgnore {
	
	private final xEssentials pl;
	
	public CmdIgnore(xEssentials pl) {
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
		if(cmd.getName().equalsIgnoreCase("ignore")) {
			if(args.length == 1) {
				if(sender.hasPermission(PermissionKey.CMD_IGNORE.getPermission())) {
					return getPlayerByName(args[0]);
				}
			}
		}
		return null;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("ignore")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_IGNORE.getPermission())) {
					if(args.length == 0) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[ignore help]___Oo.");
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/ignore <player> " + ChatColor.WHITE + ": toggles ignore for the specified player");
					} else if(args.length == 1) {
						if(!sender.getName().equalsIgnoreCase(args[0])) {
							if(pl.getManagers().getPlayerManager().isEssentialsPlayer(args[0])) {
								String name = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]).getUser();
								XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
								if(xp.hasIgnoredPlayers()) {
									if(xp.getIgnoredPlayers().contains(name)) {
										xp.removeIgnoredPlayer(name);
										sender.sendMessage(ChatColor.GREEN + "you successfully stopped ignoring player " + name);
									} else {
										xp.addIgnoredPlayer(name);
										sender.sendMessage(ChatColor.GREEN + "you are now ignoring player " + name);
									}
								} else {
									xp.addIgnoredPlayer(name);
									sender.sendMessage(ChatColor.GREEN + "you are now ignoring player " + name);
								}
							} else {
								Warnings.getWarnings(sender).playerHasNeverPlayedBefore();
							}
						} else {
							sender.sendMessage(ChatColor.RED + "you cannot ignore yourself...");
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
