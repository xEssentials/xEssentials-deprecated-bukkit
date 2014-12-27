package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.minigames.MinigameArena;
import tv.mineinthebox.essentials.minigames.MinigameSession;
import tv.mineinthebox.essentials.minigames.MinigameType;
import tv.mineinthebox.essentials.minigames.chickentennis.ChickenTennis;

public class CmdChickenTennis {

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("chickentennis")) {
			if(sender.hasPermission(PermissionKey.CMD_CHICKEN_TENNIS.getPermission())) {
				if(sender instanceof Player) {
					if(Configuration.getMinigameConfig().isMinigamesEnabled()) {
						if(args.length == 0) {
							sender.sendMessage(ChatColor.GOLD + ".oO___[chicken tennis]___Oo.");
							if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
								sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/chickentennis create <arena> " + ChatColor.WHITE + ": creates an area mode");
								sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/chickentennis remove <arena> " + ChatColor.WHITE + ": removes an arena");
								sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/chickentennis undo " + ChatColor.WHITE + ": undos the last block place");
								sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/chickentennis cancel " + ChatColor.WHITE + ": completely cancel arena creation mode");
								sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/chickentennis save " + ChatColor.WHITE + ": saves the arena");
							}
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/chickentennis help " + ChatColor.WHITE + ": shows help");
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/chickentennis list " + ChatColor.WHITE + ": shows a list of available arenas");
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/chickentennis join <arena> " + ChatColor.WHITE + ": lets a player join a arena");
						} else if(args.length == 1) {
							if(args[0].equalsIgnoreCase("help")) {
								sender.sendMessage(ChatColor.GOLD + ".oO___[chicken tennis]___Oo.");
								if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
									sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/chickentennis create <arena> " + ChatColor.WHITE + ": creates an area mode");
									sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/chickentennis remove <arena> " + ChatColor.WHITE + ": removes an arena");
									sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/chickentennis undo " + ChatColor.WHITE + ": undos the last block place");
									sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/chickentennis cancel " + ChatColor.WHITE + ": completely cancel arena creation mode");
									sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/chickentennis save " + ChatColor.WHITE + ": saves the arena");
								}
								sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/chickentennis help " + ChatColor.WHITE + ": shows help");
								sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/chickentennis list " + ChatColor.WHITE + ": shows a list of available arenas");
								sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/chickentennis join <arena> " + ChatColor.WHITE + ": lets a player join a arena");
							} else if(args[0].equalsIgnoreCase("undo")) {
								MinigameSession session = xEssentials.getManagers().getMinigameManager().getMinigameSessions().getChickenTennisSessions();
								if(session.hasSession(sender.getName())) {
									if(session.containsSessionData(sender.getName(), "boundsz")) {
										session.removeSessionData(sender.getName(), "boundsz");
										sender.sendMessage(ChatColor.GRAY + "bounds z removed, please place a block to set the z bounds again!");
									} else if(session.containsSessionData(sender.getName(), "boundsx")) {
										session.removeSessionData(sender.getName(), "boundsx");
										sender.sendMessage(ChatColor.GRAY + "bounds x removed, please place a block to set the x bounds again!");
									} else if(session.containsSessionData(sender.getName(), "chickenloc")) {
										session.removeSessionData(sender.getName(), "chickenloc");
										sender.sendMessage(ChatColor.GRAY + "chickenloc removed, please place a block to set the chicken ball spawn");
									} else if(session.containsSessionData(sender.getName(), "spawn2")) {
										session.removeSessionData(sender.getName(), "spawn2");
										sender.sendMessage(ChatColor.GRAY + "spawn point 2 removed, please place a block to set spawnpoint 2");
									} else if(session.containsSessionData(sender.getName(), "spawn1")) {
										session.removeSessionData(sender.getName(), "spawn point 1 removed, please place a block to set spawnpoint 1");
									}
								} else {
									sender.sendMessage(ChatColor.RED + "you dont have a arena creation session open!");
								}
							} else if(args[0].equalsIgnoreCase("cancel")) {
								MinigameSession session = xEssentials.getManagers().getMinigameManager().getMinigameSessions().getChickenTennisSessions();
								if(session.hasSession(sender.getName())) {
									session.removeSession(sender.getName());
									sender.sendMessage(ChatColor.GRAY + "arena creation session removed");
								} else {
									sender.sendMessage(ChatColor.RED + "you dont have a arena creation session open!");
								}
							} else if(args[0].equalsIgnoreCase("save")) {
								MinigameSession session = xEssentials.getManagers().getMinigameManager().getMinigameSessions().getChickenTennisSessions();
								if(session.hasSession(sender.getName())) {
									if(session.isSessionComplete(sender.getName())) {
										session.saveArena(sender.getName());
									} else {
										session.removeSession(sender.getName());
										sender.sendMessage(ChatColor.RED + "chicken tennis session incomplete, removing session.");
									}
								} else {
									sender.sendMessage(ChatColor.RED + "you don't have a session open!");
								}
							} else if(args[0].equalsIgnoreCase("list")) {
								String buffer = "";
								for(MinigameArena game : xEssentials.getManagers().getMinigameManager().getMinigames().get(MinigameType.CHICKEN_TENNIS)) {
									ChickenTennis tennis = (ChickenTennis)game;
									buffer += (tennis.isFull() ? ChatColor.RED + "[FULL]" + ChatColor.RESET : ChatColor.GREEN + "[OPEN]" + ChatColor.RESET) + tennis.getName() + "\n";
								}
								sender.sendMessage(ChatColor.GOLD + ".oO___[chicken tennis game list]___Oo.");
								sender.sendMessage(buffer);
							}
						} else if(args.length == 2) {
							if(args[0].equalsIgnoreCase("create")) {
								if(!xEssentials.getManagers().getMinigameManager().containsMinigame(args[1])) {
									MinigameSession session = xEssentials.getManagers().getMinigameManager().getMinigameSessions().getChickenTennisSessions();
									session.createSession(sender.getName());
									session.addSessionData(sender.getName(), "name", args[1].toLowerCase());
									session.addSessionData(sender.getName(), "score", 3);
									sender.sendMessage(ChatColor.GRAY + "set spawnpoint 1 by placing a block");
								} else {
									sender.sendMessage(ChatColor.RED + "this minigame id already exist, please use a other name");
								}
							} else if(args[0].equalsIgnoreCase("remove")) {
								
							} else if(args[0].equalsIgnoreCase("join")) {
								
							}
						}
					} else {
						Warnings.getWarnings(sender).consoleMessage();
					}
				} else {
					Warnings.getWarnings(sender).noPermission();
				}
			}
		} else {
			sender.sendMessage(ChatColor.RED + "cannot use minigame command, minigames are disabled on this server!");
		}
		return false;
	}

}
