package tv.mineinthebox.bukkit.essentials.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.Warnings;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;
import tv.mineinthebox.bukkit.essentials.instances.AlternateAccount;
import tv.mineinthebox.bukkit.essentials.instances.RestrictedCommand;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsOfflinePlayer;

public class CmdPlayerInfo {
	
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
		if(cmd.getName().equalsIgnoreCase("playerinfo")) {
			if(args.length == 1) {
				if(sender.hasPermission(PermissionKey.CMD_PLAYER_INFO.getPermission())) {
					if(!args[0].equalsIgnoreCase("help")) {
						List<String> list = getPlayerByName(args[0]);
						return list;	
					}
				}
			}
		}
		return null;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("playerinfo")) {
			if(sender.hasPermission(PermissionKey.CMD_PLAYER_INFO.getPermission())) {
				if(args.length == 0) {
					sender.sendMessage(ChatColor.GOLD + ".oO___[Player info help]___Oo.");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/playerinfo <player> " + ChatColor.WHITE + ": shows all the information of this player!");
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[Player info help]___Oo.");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/playerinfo <player> " + ChatColor.WHITE + ": shows all the information of this player!");
					} else {
						if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(args[0])) {
							xEssentialsOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
							sender.sendMessage(ChatColor.GOLD + ".oO___[showing information about " + off.getUser() + "]___Oo.");
							sender.sendMessage(ChatColor.GRAY + "file name: " + ChatColor.GREEN + off.getUniqueId()+".yml");
							if(Configuration.getGrayListConfig().isEnabled()) {
								sender.sendMessage(ChatColor.GRAY + "is greylisted: " + off.isGreyListed());
							}
							sender.sendMessage(ChatColor.GRAY + "ip: " +ChatColor.GREEN+ off.getIp());
							sender.sendMessage(ChatColor.GRAY + "isBanned: " +ChatColor.GREEN+ off.isPermBanned());
							sender.sendMessage(ChatColor.GRAY + "isTempbanned: " +ChatColor.GREEN+ off.isTempBanned());
							sender.sendMessage(ChatColor.GRAY + "amount of homes set: " +ChatColor.GREEN+ off.getAmountOfHomes());
							sender.sendMessage(ChatColor.GRAY + "has alternate accounts: " +ChatColor.GREEN+ off.hasAlternateAccounts());
							if(off.hasAlternateAccounts()) {
								AlternateAccount acount = off.getAlternateAccounts();
								sender.sendMessage(ChatColor.GRAY + "alternate accounts: " + ChatColor.GREEN + acount.getAltsDetailed());
							}
							sender.sendMessage(ChatColor.GRAY + "has ignored players: " +ChatColor.GREEN+ off.hasIgnoredPlayers());
							if(off.hasIgnoredPlayers()) {
								sender.sendMessage(ChatColor.GRAY + "ignored players:");
								for(int i = 0; i < off.getIgnoredPlayers().size(); i++) {	
									sender.sendMessage(ChatColor.GREEN + "- " + off.getIgnoredPlayers().get(i));
								}
							}
							sender.sendMessage(ChatColor.GRAY + "has silenced chat: " +ChatColor.GREEN+ off.isSilenced());
							sender.sendMessage(ChatColor.GRAY + "has command restrictions: " +ChatColor.GREEN+ off.hasCommandRestrictions());
							if(off.hasCommandRestrictions()) {
								sender.sendMessage(ChatColor.GRAY + "command restrictions:");
								for(RestrictedCommand c : off.getCommandRestrictions()) {
									sender.sendMessage(ChatColor.GREEN + "- " + c.getCommand());
								}
							}
							if(off.hasNameHistory()) {
								sender.sendMessage(ChatColor.GREEN + "has name history:");
								for(String s : off.getNameHistory()) {
									sender.sendMessage(ChatColor.GREEN + "- " + s);
								}
							}
						} else {
							Warnings.getWarnings(sender).playerHasNeverPlayedBefore();
						}
					}
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
