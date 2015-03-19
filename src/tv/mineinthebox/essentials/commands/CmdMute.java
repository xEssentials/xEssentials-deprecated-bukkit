package tv.mineinthebox.essentials.commands;

import java.util.Arrays;
import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdMute extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdMute(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}
	
	@SuppressWarnings("deprecation")
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("mute")) {
			if(sender.hasPermission(PermissionKey.CMD_MUTE.getPermission())) {
				if(args.length == 0) {
					showHelp();
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						showHelp();
					} else {
						if(pl.getManagers().getPlayerManager().isOnline(args[0])) {
							XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[0]);
							Date date = new Date(System.currentTimeMillis());
							date.setDate(date.getDate() + 1);
							xp.mute(date.getTime());
							sendMessage(ChatColor.GREEN + "successfully muted player " + xp.getPlayer().getName() + " for one day!");
							sendMessageTo(xp.getPlayer(), ChatColor.GREEN + sender.getName() + " has muted you for one day!");
						} else {
							try {
								XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
								Date date = new Date(System.currentTimeMillis());
								date.setDate(date.getDate() + 1);
								off.mute(date.getTime());
								sendMessage(ChatColor.GREEN + "successfully muted offline player " + off.getName() + " for one day!");
							} catch(NullPointerException e) {
								getWarning(WarningType.NEVER_PLAYED_BEFORE);
							}
						}
					}
				} else {
					if(pl.getManagers().getPlayerManager().isOnline(args[0])) {
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[0]);
						Date date = new Date(System.currentTimeMillis());
						String[] newArgs = Arrays.toString(args).replace(args[0]+",", "").replace("[", "").replace("]", "").split(",");
						for(int i = 0; i < newArgs.length;i++) {
							if(newArgs[i].length() == 2) {
								String TimeType = newArgs[i].substring(1);
								String number = newArgs[i].substring(0);
								if(isNumeric(number)) {
									int time = Integer.parseInt(number);
									if(TimeType.equalsIgnoreCase("D")) {
										date.setDate(date.getDate()+time);
									} else if(TimeType.equalsIgnoreCase("M")) {
										date.setMonth(date.getMonth()+time);
									} else if(TimeType.equalsIgnoreCase("Y")) {
										date.setYear(date.getYear()+time);
									}
								}
							}
						}
						xp.mute(date.getTime());
						sendMessage(ChatColor.GREEN + "successfully muted player " + xp.getName() + " till " + date.toString() + "!");
						sendMessageTo(xp.getPlayer(), ChatColor.GREEN + sender.getName() + " has muted you till " + date.toString());
					} else {
						try {
							XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
							Date date = new Date(System.currentTimeMillis());
							String[] newArgs = Arrays.toString(args).replace(args[0]+",", "").replace("[", "").replace("]", "").split(",");
							for(int i = 0; i < newArgs.length;i++) {
								if(newArgs[i].length() == 2) {
									String TimeType = newArgs[i].substring(1);
									String number = newArgs[i].substring(0);
									if(isNumeric(number)) {
										int time = Integer.parseInt(number);
										if(TimeType.equalsIgnoreCase("D")) {
											date.setDate(date.getDate()+time);
										} else if(TimeType.equalsIgnoreCase("M")) {
											date.setMonth(date.getMonth()+time);
										} else if(TimeType.equalsIgnoreCase("Y")) {
											date.setYear(date.getYear()+time);
										}
									}
								}
							}
							off.mute(date.getTime());
							sendMessage(ChatColor.GREEN + "successfully muted offline player " + off.getName() + " till " + date.toString() + "!");
						} catch(NullPointerException e) {
							getWarning(WarningType.NEVER_PLAYED_BEFORE);
						}
					}
				}
			} else {
				getWarning(WarningType.NO_PERMISSION);
			}
		}
		return false;
	}
	
	@SuppressWarnings("unused")
	private boolean isNumeric(String number) {
		try {
			int i = Integer.parseInt(number);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}

	@Override
	public void showHelp() {
		sender.sendMessage(ChatColor.GOLD + ".oO___[mute help]___Oo.");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/mute help " + ChatColor.WHITE + ": shows help");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/mute <player> " + ChatColor.WHITE + ": mutes a players for one day");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/mute <player> <params> " + ChatColor.WHITE + ": mutes a player followed by the params");
		sender.sendMessage(ChatColor.RED + "Params: " + ChatColor.GRAY + "1D, 1M, 1Y");
	}

}
