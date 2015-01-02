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
import tv.mineinthebox.essentials.interfaces.XPlayer;
import tv.mineinthebox.essentials.managers.MinigameManager;
import tv.mineinthebox.essentials.minigames.MinigameArena;
import tv.mineinthebox.essentials.minigames.MinigameSession;
import tv.mineinthebox.essentials.minigames.MinigameType;
import tv.mineinthebox.essentials.minigames.tennis.TennisArena;

public class CmdTennis {

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("tennis")) {
			if(sender.hasPermission(PermissionKey.CMD_TENNIS.getPermission())) {
				if(sender instanceof Player) {
					if(Configuration.getMinigameConfig().isMinigamesEnabled()) {
						if(args.length == 0) {
							sender.sendMessage(ChatColor.GOLD + ".oO___[tennis]___Oo.");
							if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
								sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tennis create <arena> " + ChatColor.WHITE + ": creates an area mode");
								sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tennis remove <arena> " + ChatColor.WHITE + ": removes an arena");
								sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tennis undo " + ChatColor.WHITE + ": undos the last block place");
								sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tennis cancel " + ChatColor.WHITE + ": completely cancel arena creation mode");
								sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tennis save " + ChatColor.WHITE + ": saves the arena");
							}
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/tennis help " + ChatColor.WHITE + ": shows help");
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/tennis list " + ChatColor.WHITE + ": shows a list of available arenas");
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/tennis leave " + ChatColor.WHITE + ": leave the arena you are currently in");
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/tennis join <arena> " + ChatColor.WHITE + ": lets a player join a arena");
						} else if(args.length == 1) {
							if(args[0].equalsIgnoreCase("help")) {
								sender.sendMessage(ChatColor.GOLD + ".oO___[tennis]___Oo.");
								if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
									sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tennis create <arena> " + ChatColor.WHITE + ": creates an area mode");
									sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tennis remove <arena> " + ChatColor.WHITE + ": removes an arena");
									sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tennis undo " + ChatColor.WHITE + ": undos the last block place");
									sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tennis cancel " + ChatColor.WHITE + ": completely cancel arena creation mode");
									sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tennis save " + ChatColor.WHITE + ": saves the arena");
								}
								sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/tennis help " + ChatColor.WHITE + ": shows help");
								sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/tennis list " + ChatColor.WHITE + ": shows a list of available arenas");
								sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/tennis leave " + ChatColor.WHITE + ": leave the arena you are currently in");
								sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/tennis join <arena> " + ChatColor.WHITE + ": lets a player join a arena");
							} else if(args[0].equalsIgnoreCase("undo")) {
								MinigameSession session = xEssentials.getManagers().getMinigameManager().getMinigameSessions().getTennisSessions();
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
								MinigameSession session = xEssentials.getManagers().getMinigameManager().getMinigameSessions().getTennisSessions();
								if(session.hasSession(sender.getName())) {
									session.removeSession(sender.getName());
									sender.sendMessage(ChatColor.GRAY + "arena creation session removed");
								} else {
									sender.sendMessage(ChatColor.RED + "you dont have a arena creation session open!");
								}
							} else if(args[0].equalsIgnoreCase("leave")) {
								Player p = (Player)sender;
								if(p.hasMetadata("gameType")) {
									MinigameType type = (MinigameType) p.getMetadata("gameType").get(0).value();
									if(type == MinigameType.TENNIS) {
										String arenaname = p.getMetadata("game").get(0).asString();
										MinigameArena arena = xEssentials.getManagers().getMinigameManager().getArenaByName(type, arenaname);
										arena.reset();
										sender.sendMessage(ChatColor.GRAY + "you successfully quited the arena!");
									} else {
										sender.sendMessage(ChatColor.RED + "you are trying to quit a arena through the wrong command");
									}
								} else {
									sender.sendMessage(ChatColor.RED + "you aren't joined into a arena!");
								}
							} else if(args[0].equalsIgnoreCase("save")) {
								MinigameSession session = xEssentials.getManagers().getMinigameManager().getMinigameSessions().getTennisSessions();
								if(session.hasSession(sender.getName())) {
									if(session.isSessionComplete(sender.getName())) {
										sender.sendMessage(ChatColor.GRAY + "successfully saved arena!");
										session.saveArena(sender.getName());
									} else {
										session.removeSession(sender.getName());
										sender.sendMessage(ChatColor.RED + "tennis session incomplete, removing session.");
									}
								} else {
									sender.sendMessage(ChatColor.RED + "you don't have a session open!");
								}
							} else if(args[0].equalsIgnoreCase("list")) {
								String buffer = "";
								for(MinigameArena game : xEssentials.getManagers().getMinigameManager().getMinigames().get(MinigameType.TENNIS)) {
									TennisArena tennis = (TennisArena)game;
									buffer += (tennis.isFull() ? ChatColor.RED + "[FULL]" + ChatColor.RESET : ChatColor.GREEN + "[OPEN]" + ChatColor.RESET) + tennis.getName() + "\n";
								}
								sender.sendMessage(ChatColor.GOLD + ".oO___[tennis game list]___Oo.");
								sender.sendMessage(buffer);
							}
						} else if(args.length == 2) {
							if(args[0].equalsIgnoreCase("create")) {
								if(!xEssentials.getManagers().getMinigameManager().containsMinigame(args[1])) {
									MinigameSession session = xEssentials.getManagers().getMinigameManager().getMinigameSessions().getTennisSessions();
									session.createSession(sender.getName());
									session.addSessionData(sender.getName(), "name", args[1].toLowerCase());
									session.addSessionData(sender.getName(), "score", 3);
									sender.sendMessage(ChatColor.GRAY + "set spawnpoint 1 by placing a block");
								} else {
									sender.sendMessage(ChatColor.RED + "this minigame id already exist, please use a other name");
								}
							} else if(args[0].equalsIgnoreCase("remove")) {
								MinigameManager manager = xEssentials.getManagers().getMinigameManager();
								if(manager.containsMinigame(args[1])) {
									MinigameArena arena = manager.getArenaByName(MinigameType.TENNIS, args[1]);
									arena.remove();
									sender.sendMessage(ChatColor.GRAY + "arena successfully removed!");
								} else {
									sender.sendMessage(ChatColor.RED + "unknown minigame name");
								}
							} else if(args[0].equalsIgnoreCase("join")) {
								if(xEssentials.getManagers().getMinigameManager().containsMinigame(args[1])) {
									MinigameArena arena = xEssentials.getManagers().getMinigameManager().getArenaByName(MinigameType.TENNIS, args[1]);
									if(!arena.isFull()) {
										sender.sendMessage(ChatColor.GRAY + "joining arena " + arena.getName());
										XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
										xp.getPlayer().setMetadata("gameType", new FixedMetadataValue(xEssentials.getPlugin(), arena.getType()));
										xp.getPlayer().setMetadata("game", new FixedMetadataValue(xEssentials.getPlugin(), arena.getName()));
										xp.getPlayer().setMetadata("gameScore", new FixedMetadataValue(xEssentials.getPlugin(), 0));
										arena.addPlayer(xp);
									} else {
										sender.sendMessage(ChatColor.RED + "this arena is full!");
									}
								} else {
									sender.sendMessage(ChatColor.RED + "unknown minigame name");
								}
							}
						}
					} else {
						sender.sendMessage(ChatColor.RED + "cannot use minigame command, minigames are disabled on this server!");
					}
				} else {
					Warnings.getWarnings(sender).consoleMessage();
				}
			}
		} else {
			Warnings.getWarnings(sender).noPermission();
		}
		return false;
	}

}
