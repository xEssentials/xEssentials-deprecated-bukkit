package tv.mineinthebox.bukkit.essentials.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.bukkit.essentials.Warnings;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;
import tv.mineinthebox.bukkit.essentials.instances.Modreq;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsOfflinePlayer;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsPlayer;

public class CmdTpId {

	private List<String> getPlayerByName(String p) {
		List<String> s = new ArrayList<String>();
		for(xEssentialsOfflinePlayer name : xEssentials.getManagers().getPlayerManager().getOfflinePlayers()) {
			if(name.getUser().toUpperCase().startsWith(p.toUpperCase())) {
				s.add(name.getUser());
			}
		}
		return s;
	}

	private List<String> getModreqs(String player, String id) {
		List<String> s = new ArrayList<String>();
		if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(player)) {
			xEssentialsOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(player);
			for(Modreq mod : off.getModreqs()) {
				String id2 = mod.getId()+"";
				if(id2.startsWith(id)) {
					s.add(id2);
				}
			}
		}
		return s;
	}

	public List<String> onTabComplete(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("tp-id")) {
			if(sender.hasPermission(PermissionKey.CMD_TP_ID.getPermission())) {
				if(args.length == 1) {
					return getPlayerByName(args[0]);
				} else if(args.length == 2) {
					return getModreqs(args[0], args[1]);
				}
			}
		}
		return null;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("tp-id")) {
			if(sender.hasPermission(PermissionKey.CMD_TP_ID.getPermission())) {
				if(args.length == 0) {
					sender.sendMessage(ChatColor.GOLD + ".oO___[tp-id help]___Oo.");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tp-id help " + ChatColor.WHITE + ": shows help");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tp-id <player> <id> " + ChatColor.WHITE + ": teleport to the modreq location");
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[tp-id help]___Oo.");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tp-id help " + ChatColor.WHITE + ": shows help");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tp-id <player> <id> " + ChatColor.WHITE + ": teleport to the modreq location");
					}
				} else if(args.length == 2) {
					if(sender instanceof Player) {
						Player p = (Player) sender;
						if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(args[0])) {
							if(xEssentials.getManagers().getPlayerManager().isOnline(args[0])) {
								xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(args[0]);
								if(xp.hasModreqsOpen()) {
									if(isNumberic(args[1])) {
										int id = Integer.parseInt(args[1]);
										if(xp.isValidModreqId(id)) {
											Modreq mod = xp.getModreq(id);
											Location loc = mod.getModreqLocation();
											loc.getWorld().refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
											p.teleport(loc);
											sender.sendMessage(ChatColor.GREEN + "teleporting to " + args[0] + " his modreq with id: " + id);
										} else {
											sender.sendMessage(ChatColor.RED + "not a valid modreq id!");
										}
									} else {
										sender.sendMessage(ChatColor.RED + "the second argument needs to be a number!");
									}
								}
							} else {
								xEssentialsOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
								if(off.hasModreqsOpen()) {
									if(isNumberic(args[1])) {
										int id = Integer.parseInt(args[1]);
										if(off.isValidModreqId(id)) {
											Modreq mod = off.getModreq(id);
											Location loc = mod.getModreqLocation();
											loc.getWorld().refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
											p.teleport(loc);
											sender.sendMessage(ChatColor.GREEN + "teleporting to " + args[0] + " his modreq with id: " + id);
										} else {
											sender.sendMessage(ChatColor.RED + "not a valid modreq id!");
										}
									} else {
										sender.sendMessage(ChatColor.RED + "the second argument needs to be a number!");
									}
								}
							}
						} else {
							sender.sendMessage(ChatColor.RED + "this player has never played before!");
						}
					} else {
						Warnings.getWarnings(sender).consoleMessage();
					}
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

	private boolean isNumberic(String s) {
		try {
			Integer num = Integer.parseInt(s);
			if(num != null) {
				return true;
			}
		} catch(NumberFormatException e) {
			return false;
		}
		return false;
	}

}
