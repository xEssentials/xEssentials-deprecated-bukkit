package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.XPlayer;
import tv.mineinthebox.essentials.minigames.MinigameArena;
import tv.mineinthebox.essentials.minigames.MinigameSession;
import tv.mineinthebox.essentials.minigames.MinigameType;

public class CmdParkour {
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("parkour")) {
			if(sender.hasPermission(PermissionKey.CMD_PARKOUR.getPermission())) {
				if(args.length == 0) {
					sender.sendMessage(ChatColor.GOLD + ".oO___[parkour help]___Oo.");
					if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/parkour create <name> " + ChatColor.WHITE + ": creates a new parkour");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/parkour add trail " + ChatColor.WHITE + ": creates a parkour trail");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/parkour save trail " + ChatColor.WHITE + ": saves a parkour trail");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/parkour add checkpoint " + ChatColor.WHITE + ": sets a checkpoint");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/parkour remove <id> " + ChatColor.WHITE + ": removes a parkour");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/parkour save " + ChatColor.WHITE + ": saves the parkour");
					}
					sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/parkour join <name> " + ChatColor.WHITE + ": joins a parkour");
					sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/parkour list " + ChatColor.WHITE + ": returns a list of parkour arenas");
					sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/parkour leave " + ChatColor.WHITE + ": leaves the parkour arena");
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[parkour help]___Oo.");
						if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/parkour create <name> " + ChatColor.WHITE + ": creates a new parkour");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/parkour add trail " + ChatColor.WHITE + ": creates a parkour trail");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/parkour save trail " + ChatColor.WHITE + ": saves a parkour trail");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/parkour add checkpoint " + ChatColor.WHITE + ": sets a checkpoint");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/parkour remove <id> " + ChatColor.WHITE + ": removes a parkour");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/parkour save " + ChatColor.WHITE + ": saves the parkour");
						}
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/parkour join <name> " + ChatColor.WHITE + ": joins a parkour");
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/parkour list " + ChatColor.WHITE + ": returns a list of parkour arenas");
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/parkour leave " + ChatColor.WHITE + ": leaves the parkour arena");
					} else if(args[0].equalsIgnoreCase("save")) {
						if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							MinigameSession session = xEssentials.getManagers().getMinigameManager().getMinigameSessions().getParkourSessions();
							if(session.hasSession(sender.getName())) {
								if(session.isSessionComplete(sender.getName())) {
									session.saveArena(sender.getName());
								} else {
									sender.sendMessage(ChatColor.RED + "minigmae session invalid!, removing session now!");
									session.removeSession(sender.getName());
								}
							} else {
								sender.sendMessage(ChatColor.RED + "you dont have an minigame session open!");
							}
						} else {
							Warnings.getWarnings(sender).noPermission();
						}
					} else if(args[0].equalsIgnoreCase("list")) {
						String buffer = "";
						if(xEssentials.getManagers().getMinigameManager().getMinigames().containsKey(MinigameType.PARKOUR)) {
							for(MinigameArena arena : xEssentials.getManagers().getMinigameManager().getMinigames().get(MinigameType.PARKOUR)) {
								buffer += (arena.isFull() ? ChatColor.RED + "[FULL]" + ChatColor.RESET : ChatColor.GREEN + "[OPEN]" + ChatColor.RESET) + arena.getName() + "\n";
							}
							sender.sendMessage(ChatColor.GOLD + ".oO___[parkour game list]___Oo.");
							sender.sendMessage(buffer);
						} else {
							sender.sendMessage(ChatColor.GOLD + ".oO___[parkour game list]___Oo.");
							sender.sendMessage(ChatColor.RED + "no minigames found for parkour!");	
						}
					} else if(args[0].equalsIgnoreCase("leave")) {
						XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
						if(xp.getPlayer().hasMetadata("gameType") && xp.getPlayer().hasMetadata("game")) {
							MinigameType type = (MinigameType)xp.getPlayer().getMetadata("gameType").get(0).value();
							if(type == MinigameType.PARKOUR) {
								String name = xp.getPlayer().getMetadata("game").get(0).asString();
								MinigameArena arena = xEssentials.getManagers().getMinigameManager().getArenaByName(type, name);
								arena.removePlayer(xp);
							} else {
								sender.sendMessage(ChatColor.RED + "you are trying to quit a arena through the wrong command");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "you are not joined in any minigame!");
						}
					}
				} else if(args.length == 2) {
					
				} else if(args.length == 3) {
					
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
