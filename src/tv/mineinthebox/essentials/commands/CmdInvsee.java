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

public class CmdInvsee {
	
	private final xEssentials pl;
	
	public CmdInvsee(xEssentials pl) {
		this.pl = pl;
	}
	
	private List<String> getPlayerByName(String p) {
		List<String> s = new ArrayList<String>();
		for(XOfflinePlayer name : pl.getManagers().getPlayerManager().getOfflinePlayers()) {
			if(name.getUser().toUpperCase().startsWith(p.toUpperCase())) {
				s.add(name.getUser());
			} else if("help".toUpperCase().startsWith(p.toUpperCase())) {
				s.add("help");
			}
		}
		return s;
	}
	
	public List<String> onTabComplete(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("invsee")) {
			if(args.length == 1) {
				if(sender.hasPermission(PermissionKey.CMD_INVSEE.getPermission())) {
					List<String> list = getPlayerByName(args[0]);
					return list;
				}
			}
		}
		return null;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("invsee")) {
			if(args.length == 0) {
				if(sender.hasPermission(PermissionKey.CMD_INVSEE.getPermission())) {
					sendHelp(sender);
				} else {
					Warnings.getWarnings(sender).noPermission();
				}
			} else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("help")) {
					if(sender.hasPermission(PermissionKey.CMD_INVSEE.getPermission())) {
						sendHelp(sender);
					} else {
						Warnings.getWarnings(sender).noPermission();
					}
				} else {
					if(sender instanceof Player) {
						if(sender.hasPermission(PermissionKey.CMD_INVSEE.getPermission())) {
							Player p = (Player) sender;
							Player a = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]).getPlayer();
							if(a instanceof Player) {
								if(pl.getManagers().getPlayerManager().isOnline(args[0])) {
									XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[0]);
									p.openInventory(xp.getOnlineInventory());
									sender.sendMessage(ChatColor.GREEN + "opening live inventory of player " + xp.getUser());
								} else {
									sender.sendMessage(ChatColor.RED + "this player does not exist in the global HashMap please reload xEssentials");
								}
							} else {
								try {
									XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
									if(off.hasOfflineInventory()) {
										p.openInventory(off.getOfflineInventory(p));
										sender.sendMessage(ChatColor.GREEN + "opening offline inventory of player " + off.getUser());
									} else {
										sender.sendMessage(ChatColor.RED + "this player does not have a saved inventory");
									}
								} catch(NullPointerException e) {
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
			}
		}
		return false;
	}

	private void sendHelp(CommandSender sender) {
		sender.sendMessage(ChatColor.GOLD + ".oO___[invsee help]___Oo.");
		sender.sendMessage(ChatColor.GRAY + "when the configuration option save-playerInventory in player.yml is enabled you can also look into a offline inventory!");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/invsee " + ChatColor.WHITE + ": shows help");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/invsee help " + ChatColor.WHITE + ": shows help");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/invsee <player> " + ChatColor.WHITE + ": open the inventory of a player");
	}

}
