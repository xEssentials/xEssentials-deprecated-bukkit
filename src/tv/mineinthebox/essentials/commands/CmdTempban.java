package tv.mineinthebox.essentials.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.BanList.Type;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.xEssentialsOfflinePlayer;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

public class CmdTempban {

	@SuppressWarnings("deprecation")
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("tempban")) {
			if(sender.hasPermission(PermissionKey.CMD_TEMP_BAN.getPermission())) {
				if(args.length == 0) {
					sender.sendMessage(ChatColor.GOLD + ".oO___[tempban help]___Oo.");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tempban <player> " + ChatColor.WHITE + ": tempban a player");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tempban <player> <message> " + ChatColor.WHITE + ": tempban a player with a desired message!");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tempban <player> <message> <1D> " + ChatColor.WHITE + "tempban a player with a disered message for one day!");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "variables for ban time are: 1Y, 1M, 1D");
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[tempban help]___Oo.");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tempban <player> " + ChatColor.WHITE + ": tempban a player");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tempban <player> <message> " + ChatColor.WHITE + ": tempban a player with a desired message!");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tempban <player> <message> <1D> " + ChatColor.WHITE + "tempban a player with a disered message for one day!");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "variables for ban time are: 1Y, 1M, 1D");
					} else {
						if(xEssentials.getManagers().getPlayerManager().isOnline(args[0])) {
							xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(args[0]);
							Date date = new Date(System.currentTimeMillis());
							date.setDate(date.getDate()+1);
							xp.setTempbanned(date.getTime(), "the ban hammer has  spoken!", sender.getName());
							xp.getPlayer().kickPlayer("the ban hammer has spoken!");
							sender.sendMessage(ChatColor.GREEN + "player successfully tempbanned for 1 day");
						} else {
							try {
								xEssentialsOfflinePlayer off = new xEssentialsOfflinePlayer(args[0]);
								Date date = new Date(System.currentTimeMillis());
								date.setDate(date.getDate()+1);
								off.setTempbanned(date.getTime(), "the ban hammer has spoken!", sender.getName());
								sender.sendMessage(ChatColor.GREEN + "player successfully tempbanned for 1 day");
							} catch(NullPointerException e) {						
								try {
									File f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "players" + File.separator + args[0].toLowerCase() + ".yml");
									FileConfiguration con = YamlConfiguration.loadConfiguration(f);
									Date date = new Date(System.currentTimeMillis());
									date.setDate(date.getDate()+1);
									Bukkit.getServer().getBanList(Type.NAME).addBan(args[0], "the ban hammer has been spoken!", date, sender.getName()).save();
									con.set("isDefault", true);
									con.set("ip", "unknown");
									con.set("user", args[0]);
									con.set("banned.isBanned", false);
									con.set("banned.reason", "none");
									con.set("tempbanned.isBanned", true);
									con.set("tempbanned.till", date.getTime());
									con.set("tempbanned.message", "the ban hammer has been spoken!");
									con.set("fly", false);
									con.set("torch", false);
									con.set("firefly", false);
									con.save(f);
									sender.sendMessage(ChatColor.GREEN + "successfully temp banned player " + args[0] + " however keep in mind this player has never played before");
								} catch(Exception r) {
									r.printStackTrace();
								}
							}
						}
					}
				} else if(args.length > 1) {
					if(xEssentials.getManagers().getPlayerManager().isOnline(args[0])) {
						xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(args[0]);
						Date date = convertArgsToDate(args);
						String[] newArgs = getClearDescription(args);
						String banMessage = Arrays.toString(newArgs).replace(args[0], "").replace("[", "").replace(",", "").replace("]", "");
						xp.setTempbanned(date.getTime(), banMessage, sender.getName());
						xp.getPlayer().kickPlayer(banMessage);
						sender.sendMessage(ChatColor.GREEN + "successfully tempbanned player " + xp.getUser() + " till " + date.toString());
					} else {
						try {
							xEssentialsOfflinePlayer off = new xEssentialsOfflinePlayer(args[0]);
							Date date = convertArgsToDate(args);
							String[] newArgs = getClearDescription(args);
							String banMessage = Arrays.toString(newArgs).replace(args[0], "").replace("[", "").replace(",", "").replace("]", "");
							off.setTempbanned(date.getTime(), banMessage, sender.getName());
							sender.sendMessage(ChatColor.GREEN + "successfully tempbanned player " + off.getUser() + " till " + date.toString());
						} catch(NullPointerException e) {
							try {
								File f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "players" + File.separator + args[0].toLowerCase() + ".yml");
								FileConfiguration con = YamlConfiguration.loadConfiguration(f);
								Date date = convertArgsToDate(args);
								String[] newArgs = getClearDescription(args);
								String banMessage = Arrays.toString(newArgs).replace(args[0], "").replace("[", "").replace(",", "").replace("]", "");
								Bukkit.getServer().getBanList(Type.NAME).addBan(args[0], banMessage, date, sender.getName()).save();
								con.set("isDefault", true);
								con.set("ip", "unknown");
								con.set("user", args[0]);
								con.set("banned.isBanned", false);
								con.set("banned.reason", "none");
								con.set("tempbanned.isBanned", true);
								con.set("tempbanned.till", date.getTime());
								con.set("tempbanned.message", banMessage);
								con.set("fly", false);
								con.set("torch", false);
								con.set("firefly", false);
								con.save(f);
								sender.sendMessage(ChatColor.GREEN + "successfully temp banned player " + args[0] + " however keep in mind this player has never played before");
							} catch(Exception r) {
								r.printStackTrace();
							}
						}
					}
				}
			}
		} else {
			Warnings.getWarnings(sender).noPermission();
		}
		return false;
	}
	
	@SuppressWarnings({ "deprecation" })
	private Date convertArgsToDate(String[] args) {
		Date date = new Date(System.currentTimeMillis());
		for(String argument : args) {
			if(argument.length() == 2) {
				String TimeType = argument.substring(1);
				String number = argument.replace(TimeType, "");
				if(isNumeric(number)) {
					Integer i = Integer.parseInt(number);
					if(TimeType.equalsIgnoreCase("d")) {
						date.setDate(date.getDate()+i);
					} else if(TimeType.equalsIgnoreCase("y")) {
						date.setYear(date.getYear()+i);
					} else if(TimeType.equalsIgnoreCase("m")) {
						date.setMonth(date.getMonth()+i);
					}
				}
			} else if(argument.length() == 3) {
				String TimeType = argument.substring((argument.length()-1));
				String number = argument.replace(TimeType, "");
				if(isNumeric(number)) {
					Integer i = Integer.parseInt(number);
					if(TimeType.equalsIgnoreCase("d")) {
						date.setDate(date.getDate()+i);
					} else if(TimeType.equalsIgnoreCase("y")) {
						date.setYear(date.getYear()+i);
					} else if(TimeType.equalsIgnoreCase("m")) {
						date.setMonth(date.getMonth()+i);
					}
				}
			} else if(argument.length() == 4) {
				String TimeType = argument.substring((argument.length()-1));
				String number = argument.replace(TimeType, "");
				if(isNumeric(number)) {
					Integer i = Integer.parseInt(number);
					if(TimeType.equalsIgnoreCase("d")) {
						date.setDate(date.getDate()+i);
					} else if(TimeType.equalsIgnoreCase("y")) {
						date.setYear(date.getYear()+i);
					} else if(TimeType.equalsIgnoreCase("m")) {
						date.setMonth(date.getMonth()+i);
					}
				}
			} else {
				xEssentials.getPlugin().log("Player is not tempbanned because, you used to many arguments!", LogType.SEVERE);
			}
		}
		return date;
	}
	
	private String[] getClearDescription(String[] args) {
		ArrayList<String> newArgs = new ArrayList<String>();
		for(String argument : args) {
			if(argument.length() == 2) {
				String TimeType = argument.substring(1);
				String number = argument.replace(TimeType, "");
				if(!isNumeric(number)) {
					newArgs.add(argument);
				}
			} else {
				newArgs.add(argument);
			}
		}
		return newArgs.toArray(new String[newArgs.size()]);
	}
	
	@SuppressWarnings("unused")
	private boolean isNumeric(String number) {
		try {
			Integer i = Integer.parseInt(number);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}

}
