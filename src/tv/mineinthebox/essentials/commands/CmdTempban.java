package tv.mineinthebox.essentials.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdTempban extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdTempban(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	@SuppressWarnings("deprecation")
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("tempban")) {
			if(sender.hasPermission(PermissionKey.CMD_TEMP_BAN.getPermission())) {
				if(args.length == 0) {
					showHelp();
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						showHelp();
					} else {
						if(pl.getManagers().getPlayerManager().isOnline(args[0])) {
							XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[0]);
							Date date = new Date(System.currentTimeMillis());
							date.setDate(date.getDate()+1);
							xp.setTempbanned(date.getTime(), "the ban hammer has  spoken!", sender.getName());
							xp.getBukkitPlayer().kickPlayer("the ban hammer has spoken!");
							sendMessage("player successfully tempbanned for 1 day");
						} else {
							try {
								XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
								Date date = new Date(System.currentTimeMillis());
								date.setDate(date.getDate()+1);
								off.setTempbanned(date.getTime(), "the ban hammer has spoken!", sender.getName());
								sendMessage("player successfully tempbanned for 1 day");
							} catch(NullPointerException e) {						
								try {
									File f = new File(pl.getDataFolder() + File.separator + "players" + File.separator + args[0].toLowerCase() + ".yml");
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
									sendMessage("successfully temp banned player " + args[0] + " however keep in mind this player has never played before");
								} catch(Exception r) {
									r.printStackTrace();
								}
							}
						}
					}
				} else if(args.length > 1) {
					if(pl.getManagers().getPlayerManager().isOnline(args[0])) {
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[0]);
						Date date = convertArgsToDate(args);
						String[] newArgs = getClearDescription(args);
						String banMessage = Arrays.toString(newArgs).replace(args[0], "").replace("[", "").replace(",", "").replace("]", "");
						xp.setTempbanned(date.getTime(), banMessage, sender.getName());
						xp.getBukkitPlayer().kickPlayer(banMessage);
						sendMessage("successfully tempbanned player " + xp.getName() + " till " + date.toString());
					} else {
						try {
							XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
							Date date = convertArgsToDate(args);
							String[] newArgs = getClearDescription(args);
							String banMessage = Arrays.toString(newArgs).replace(args[0], "").replace("[", "").replace(",", "").replace("]", "");
							off.setTempbanned(date.getTime(), banMessage, sender.getName());
							sendMessage("successfully tempbanned player " + off.getName() + " till " + date.toString());
						} catch(NullPointerException e) {
							try {
								File f = new File(pl.getDataFolder() + File.separator + "players" + File.separator + args[0].toLowerCase() + ".yml");
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
								sendMessage("successfully temp banned player " + args[0] + " however keep in mind this player has never played before");
							} catch(Exception r) {
								r.printStackTrace();
							}
						}
					}
				}
			}
		} else {
			getWarning(WarningType.NO_PERMISSION);
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
				xEssentials.log("Player is not tempbanned because, you used to many arguments!", LogType.SEVERE);
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
	
	@Override
	public void showHelp() {
		sender.sendMessage(ChatColor.GOLD + ".oO___[tempban help]___Oo.");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tempban <player> " + ChatColor.WHITE + ": tempban a player");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tempban <player> <message> " + ChatColor.WHITE + ": tempban a player with a desired message!");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tempban <player> <message> <1D> " + ChatColor.WHITE + "tempban a player with a disered message for one day!");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "variables for ban time are: 1Y, 1M, 1D");
	}

}
