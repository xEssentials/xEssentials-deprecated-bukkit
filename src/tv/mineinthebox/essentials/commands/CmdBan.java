package tv.mineinthebox.essentials.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdBan {

	private xEssentials pl;
	
	public CmdBan(xEssentials pl) {
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
		if(cmd.getName().equalsIgnoreCase("ban")) {
			if(args.length == 1) {
				if(sender.hasPermission(PermissionKey.CMD_BAN.getPermission())) {
					List<String> list = getPlayerByName(args[0]);
					return list;
				}
			} else if(args.length > 1) {
				if(sender.hasPermission(PermissionKey.CMD_BAN.getPermission())) {
					List<String> list = getPlayerByName(args[0]);
					return list;
				}
			}
		}
		return null;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("ban")) {
			if(sender.hasPermission(PermissionKey.CMD_BAN.getPermission())) {
				if(args.length == 0) {
					sender.sendMessage(ChatColor.GOLD + ".oO___[ban help]___Oo.");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/ban <player> " + ChatColor.WHITE + ": bans a player with a default message");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/ban <player> <message> " + ChatColor.WHITE + ": bans a player with a given up description");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/ban help " + ChatColor.WHITE + ": shows help");
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[ban help]___Oo.");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/ban <player> " + ChatColor.WHITE + ": bans a player with a default message");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/ban <player> <message> " + ChatColor.WHITE + ": bans a player with a given up description");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/ban help " + ChatColor.WHITE + ": shows help");
					} else {
						if(pl.getManagers().getPlayerManager().isOnline(args[0])) {
							XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[0]);
							xp.setPermBanned("The ban hammer has spoken!", sender.getName());
							sender.sendMessage(ChatColor.GREEN + "successfully banned player " + xp.getUser());
							xp.getPlayer().kickPlayer(xp.getBanMessage());
						} else {
							try {
								XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
								off.setPermBanned("The ban hammer has spoken!", sender.getName());
								sender.sendMessage(ChatColor.GREEN + "successfully banned player " + off.getUser());
							} catch(NullPointerException e) {
								try {
									File f = new File(pl.getDataFolder() + File.separator + "players" + File.separator + args[0].toLowerCase() + ".yml");
									FileConfiguration con = YamlConfiguration.loadConfiguration(f);
									Bukkit.getServer().getBanList(Type.NAME).addBan(args[0], "The ban Hammer has been spoken!", null, sender.getName()).save();
									con.set("isDefault", true);
									con.set("ip", "unknown");
									con.set("user", args[0]);
									con.set("banned.isBanned", true);
									con.set("banned.reason", "none");
									con.set("tempbanned.isBanned", false);
									con.set("tempbanned.till", System.currentTimeMillis());
									con.set("tempbanned.message", "none");
									con.set("fly", false);
									con.set("torch", false);
									con.set("firefly", false);
									con.save(f);
									sender.sendMessage(ChatColor.GREEN + "successfully banned player " + args[0] + " however keep in mind this player has never played before");
								} catch(Exception r) {
									r.printStackTrace();
								}
							}
						}
					}
				} else if(args.length > 1) {
					if(pl.getManagers().getPlayerManager().isOnline(args[0])) {
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[0]);
						String message = Arrays.toString(args).replace(args[0], "").replace("[", "").replace(",", "").replace("]", "");
						xp.setPermBanned(message, sender.getName());
						xp.getPlayer().kickPlayer(message);
					} else {
						try {
							XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
							String message = Arrays.toString(args).replace(args[0], "").replace("[", "").replace(",", "").replace("]", "");
							off.setPermBanned(message, sender.getName());
						} catch(NullPointerException e) {
							try {
								File f = new File(pl.getDataFolder() + File.separator + "players" + File.separator + args[0].toLowerCase() + ".yml");
								FileConfiguration con = YamlConfiguration.loadConfiguration(f);
								String message = Arrays.toString(args).replace(args[0], "").replace("[", "").replace(",", "").replace("]", "");
								Bukkit.getServer().getBanList(Type.NAME).addBan(args[0], message, null, sender.getName()).save();
								con.set("isDefault", true);
								con.set("ip", "unknown");
								con.set("user", args[0]);
								con.set("banned.isBanned", true);
								con.set("banned.reason", "none");
								con.set("tempbanned.isBanned", false);
								con.set("tempbanned.till", System.currentTimeMillis());
								con.set("tempbanned.message", "none");
								con.set("fly", false);
								con.set("torch", false);
								con.set("firefly", false);
								con.save(f);
								sender.sendMessage(ChatColor.GREEN + "successfully banned player " + args[0] + " however keep in mind this player has never played before");
							} catch(Exception r) {
								r.printStackTrace();
							}
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
