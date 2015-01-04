package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.managers.MinigameManager;
import tv.mineinthebox.essentials.minigames.MinigameArena;
import tv.mineinthebox.essentials.minigames.MinigameSession;
import tv.mineinthebox.essentials.minigames.MinigameType;
import tv.mineinthebox.essentials.minigames.football.FootballSession;

public class CmdFootball {

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("football")) {
			if(Configuration.getMinigameConfig().isMinigamesEnabled()) {
				if(sender.hasPermission(PermissionKey.CMD_FOOTBALL.getPermission())) {
					if(args.length == 0) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[football help]___Oo.");
						if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/football create <arena> " + ChatColor.WHITE + ": creates a new arena");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/football save red " + ChatColor.WHITE + ": saves the red arena");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/football save blue " + ChatColor.WHITE + ": saves the blue arena");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/football undo " + ChatColor.WHITE + ": undos previous action");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/football save " + ChatColor.WHITE + ": saves the full arena");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/football cancel " + ChatColor.WHITE + ": removes the arena creation session");
							sender.sendMessage(ChatColor.RED + "Admin: " +ChatColor.GRAY + "/football remove <arena> " + ChatColor.WHITE + ": removes a arena by name");
						}
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/football help " + ChatColor.WHITE + ": shows help");
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/football join <arena> " + ChatColor.WHITE + ": join a arena by name");
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/football list " + ChatColor.WHITE + ": shows a list of arenas");
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/football leave " + ChatColor.WHITE + ": leave the arena");
					} else if(args.length == 1) {
						if(args[0].equalsIgnoreCase("help")) {
							sender.sendMessage(ChatColor.GOLD + ".oO___[football help]___Oo.");
							if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
								sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/football create <arena> " + ChatColor.WHITE + ": creates a new arena");
								sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/football save red " + ChatColor.WHITE + ": saves the red arena");
								sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/football save blue " + ChatColor.WHITE + ": saves the blue arena");
								sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/football undo " + ChatColor.WHITE + ": undos previous action");
								sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/football save " + ChatColor.WHITE + ": saves the full arena");
								sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/football cancel " + ChatColor.WHITE + ": removes the arena creation session");
								sender.sendMessage(ChatColor.RED + "Admin: " +ChatColor.GRAY + "/football remove <arena> " + ChatColor.WHITE + ": removes a arena by name");
							}
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/football help " + ChatColor.WHITE + ": shows help");
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/football join <arena> " + ChatColor.WHITE + ": join a arena by name");
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/football list " + ChatColor.WHITE + ": shows a list of arenas");
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/football leave " + ChatColor.WHITE + ": leave the arena");
						} else if(args[0].equalsIgnoreCase("undo")) {
							if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
								FootballSession session = (FootballSession) xEssentials.getManagers().getMinigameManager().getMinigameSessions().getFootballSessions();
								if(session.hasSession(sender.getName())) {
									if(session.isBlueGoalFinished(sender.getName())) {
										session.setBlueGoalFinished(sender.getName(), false);
										sender.sendMessage(ChatColor.GRAY + "place blocks to represent the goal of the blue team, type then /save blue");
									} else if(session.isRedGoalFinished(sender.getName())) {
										session.setRedGoalFinished(sender.getName(), false);
										sender.sendMessage(ChatColor.GRAY + "place blocks to represent the goal of the red team, type then /save red");
									} else if(session.containsSessionData(sender.getName(), "blueteamspawn")) {
										session.removeSessionData(sender.getName(), "blueteamspawn");
										sender.sendMessage(ChatColor.GRAY + "now place a block where blue team should spawn!");
									} else if(session.containsSessionData(sender.getName(), "redteamspawn")) {
										session.removeSessionData(sender.getName(), "redteamspawn");
										sender.sendMessage(ChatColor.GRAY + "now place a block where red team should spawn!");
									} else if(session.containsSessionData(sender.getName(), "slimespawn")) {
										session.removeSessionData(sender.getName(), "slimespawn");
										sender.sendMessage(ChatColor.GRAY + "now place a block where the football should spawn");
									} else {
										sender.sendMessage(ChatColor.RED + "cannot undo anything, if you want to cancel this session type /football cancel");
									}
								} else {
									sender.sendMessage(ChatColor.RED + "you dont have a football arena session open!");
								}
							} else {
								Warnings.getWarnings(sender).noPermission();
							}
						} else if(args[0].equalsIgnoreCase("save"))  {
							if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
								MinigameSession session = xEssentials.getManagers().getMinigameManager().getMinigameSessions().getFootballSessions();
								if(session.hasSession(sender.getName())) {
									if(session.isSessionComplete(sender.getName())) {
										session.saveArena(sender.getName());
										sender.sendMessage(ChatColor.GRAY + "football arena is saved");
									} else {
										session.removeSession(sender.getName());
										sender.sendMessage(ChatColor.RED + "session invalid!, deleting current session");
									}
								} else {
									sender.sendMessage(ChatColor.RED + "you dont have a football arena session open!");
								}
							} else {
								Warnings.getWarnings(sender).noPermission();
							}
						} else if(args[0].equalsIgnoreCase("cancel")) {
							if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
								MinigameSession session = xEssentials.getManagers().getMinigameManager().getMinigameSessions().getFootballSessions();
								if(session.hasSession(sender.getName())) {
									session.removeSession(sender.getName());
									sender.sendMessage(ChatColor.GRAY + "arena session removed!");
								} else {
									sender.sendMessage(ChatColor.RED + "you dont have a football arena session open!");
								}
							} else {
								Warnings.getWarnings(sender).noPermission();
							}
						} else if(args[0].equalsIgnoreCase("list")) {
							sender.sendMessage(ChatColor.GOLD + ".oO___[football arenas]___Oo.");
							String s = "";
							for(MinigameArena arena : xEssentials.getManagers().getMinigameManager().getMinigames().get(MinigameType.FOOT_BALL)) {
								s = (arena.isFull() ? ChatColor.RED + "[FULL]" + ChatColor.RESET : ChatColor.GREEN + "[OPEN]" + ChatColor.RESET) + arena.getName() + "\n";
							}
							sender.sendMessage(s);
						} else if(args[0].equalsIgnoreCase("leave")) {
							if(sender instanceof Player) {
								Player p = (Player) sender;
								if(p.hasMetadata("gameType")) {
									MinigameType type = (MinigameType) p.getMetadata("gameType").get(0).value();
									if(type == MinigameType.FOOT_BALL) {
										String arenaname = p.getMetadata("game").get(0).asString();
										MinigameArena arena = xEssentials.getManagers().getMinigameManager().getArenaByName(type, arenaname);
										arena.reset();
										p.removeMetadata("gameType", xEssentials.getPlugin());
										p.removeMetadata("game", xEssentials.getPlugin());
										sender.sendMessage(ChatColor.GRAY + "you successfully quited the arena!");
									} else {
										sender.sendMessage(ChatColor.RED + "you are trying to quit a arena through the wrong command");
									}
								} else {
									sender.sendMessage(ChatColor.RED + "you are not in any arena!");
								}
							} else {
								Warnings.getWarnings(sender).consoleMessage();
							}
						}
					} else if(args.length == 2) {
						if(args[0].equalsIgnoreCase("save")) {
							if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
								FootballSession session = (FootballSession)xEssentials.getManagers().getMinigameManager().getMinigameSessions().getFootballSessions();
								if(session.hasSession(sender.getName())) {
									if(args[1].equalsIgnoreCase("red")) {
										session.setRedGoalFinished(sender.getName(), true);
										sender.sendMessage(ChatColor.GRAY + "red teams goal set, now set the blue teams goal and type /football save blue");
									} else if(args[1].equalsIgnoreCase("blue")) {
										session.setBlueGoalFinished(sender.getName(), true);
										sender.sendMessage(ChatColor.GRAY + "blue teams goal set, now type /football save to save the arena!");
									} else {
										sender.sendMessage(ChatColor.RED + "you dont have a football arena session open!");
									}
								} else {
									Warnings.getWarnings(sender).noPermission();
								}
							}
						} else if(args[0].equalsIgnoreCase("remove")) {
							if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
								MinigameManager manager = xEssentials.getManagers().getMinigameManager();
								if(manager.containsMinigame(args[1])) {
									MinigameArena arena = manager.getArenaByName(MinigameType.FOOT_BALL, args[1]);
									arena.remove();
									sender.sendMessage(ChatColor.GRAY + "arena successfully removed!");
								} else {
									sender.sendMessage(ChatColor.RED + "no minigame exist with that name!");
								}
							} else {
								Warnings.getWarnings(sender).noPermission();
							}
						} else if(args[0].equalsIgnoreCase("create")) {
							if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
								if(sender instanceof Player) {
									MinigameSession session = xEssentials.getManagers().getMinigameManager().getMinigameSessions().getFootballSessions();
									if(session.hasSession(sender.getName())) {
										sender.sendMessage(ChatColor.RED + "you are already involved inside a session!");
									} else {
										if(!xEssentials.getManagers().getMinigameManager().containsMinigame(args[1])) {
											session.createSession(sender.getName());
											session.addSessionData(sender.getName(), "name", args[1]);
											sender.sendMessage(ChatColor.GRAY + "session created, now place a block where the football should spawn!");
										} else {
											sender.sendMessage(ChatColor.RED + "there is already a minigame with that name!");
										}
									}
								} else {
									Warnings.getWarnings(sender).consoleMessage();
								}
							} else {
								Warnings.getWarnings(sender).noPermission();
							}
						} else if(args[0].equalsIgnoreCase("join")) {
							MinigameManager manager = xEssentials.getManagers().getMinigameManager();
							if(manager.containsMinigame(args[1])) {
								MinigameArena arena = manager.getArenaByName(MinigameType.FOOT_BALL, args[1]);
								if(!arena.isFull()) {
									Player p = (Player) sender;
									p.setMetadata("gameType", new FixedMetadataValue(xEssentials.getPlugin(), MinigameType.FOOT_BALL));
									p.setMetadata("game", new FixedMetadataValue(xEssentials.getPlugin(), arena.getName()));
									arena.addPlayer(xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName()));
								} else {
									sender.sendMessage(ChatColor.RED + "the arena is full!");
								}
							} else {
								sender.sendMessage(ChatColor.RED + "no minigame exist with that name!");
							}
						}
					}
				} else {
					Warnings.getWarnings(sender).noPermission();
				}
			} else {
				sender.sendMessage(ChatColor.RED + "minigames are not enabled on this server!");
			}
		}
		return false;
	}
}
