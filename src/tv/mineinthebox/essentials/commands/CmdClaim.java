package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.Modreq;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

public class CmdClaim {

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("claim")) {
			if(sender.hasPermission(PermissionKey.CMD_CLAIM.getPermission())) {
				if(args.length == 0) {
					sender.sendMessage(ChatColor.GOLD + ".oO___[claim modreq help]___Oo.");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/claim help " + ChatColor.WHITE + ": shows help");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/claim <player> <id> " + ChatColor.WHITE + ": claim a modreq of a player!");
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[claim modreq help]___Oo.");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/claim help " + ChatColor.WHITE + ": shows help");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/claim <player> <id> " + ChatColor.WHITE + ": claim a modreq of a player!");	
					} else {
						sender.sendMessage(ChatColor.RED + "you forgot to use the tickets ID as second argument");
					}
				} else {
					if(xEssentials.getManagers().getPlayerManager().isOnline(args[0])) {
						xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(args[0]);
						if(xp.hasModreqsOpen()) {
							try {
								int id = Integer.parseInt(args[1]);
								if(xp.isValidModreqId(id)) {
									Modreq mod = xp.getModreq(id);
									Player p = xp.getPlayer();
									p.sendMessage(ChatColor.GREEN + "[Modreq]" + ChatColor.GRAY + sender.getName() + " has claimed your modreq with id: "+ChatColor.GREEN + mod.getId());
									sender.sendMessage(ChatColor.GREEN + "[Modreq]" + ChatColor.GRAY + "successfully claimed modreq of player " + p.getName() + " with id: " + ChatColor.GREEN + mod.getId());
								} else {
									sender.sendMessage(ChatColor.RED + "this is a invalid modreq id");
								}
							} catch(NumberFormatException e) {
								sender.sendMessage(ChatColor.RED + "second argument need to be a number only!");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "could not claim any modreq ticket");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "could not claim a ticket when a player is offline!");
					}
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
