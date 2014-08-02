package tv.mineinthebox.essentials.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.arenas.Minigame;
import tv.mineinthebox.essentials.enums.MinigameType;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.events.spleef.CreateSpleefArenaEvent;
import tv.mineinthebox.essentials.instances.SpleefArena;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

public class CmdSpleef {

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("spleef")) {
			if(sender.hasPermission(PermissionKey.CMD_SPLEEF.getPermission())) {
				if(args.length == 0) {
					sender.sendMessage(ChatColor.GOLD + ".oO___[Spleef help]___Oo.");
					sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/spleef help " + ChatColor.WHITE + ": shows help");
					sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/spleef list " + ChatColor.WHITE + ": shows all arenas");
					sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/spleef join <arena> " + ChatColor.WHITE + ": allows you to join a arena");
					sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/spleef quit " + ChatColor.WHITE + ": can only be used if there is no other player");
					if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/spleef create <arena> " + ChatColor.WHITE + ": create a arena envorinment based on pos1 and pos2");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/spleef remove <arena> " + ChatColor.WHITE + ": removes the arena!");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/spleef sp add <arena> " + ChatColor.WHITE + ": adds a spawnpoint");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/spleef sp remove <id> <arena> " + ChatColor.WHITE + ": remove a spawnpoint from the arena");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/spleef sp list <arena> " + ChatColor.WHITE + ": shows all the spawnpoint id's");
					}	
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[Spleef help]___Oo.");
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/spleef help " + ChatColor.WHITE + ": shows help");
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/spleef list " + ChatColor.WHITE + ": shows all arenas");
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/spleef join <arena> " + ChatColor.WHITE + ": allows you to join a arena");
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/spleef quit " + ChatColor.WHITE + ": can only be used if there is no other player");
						if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/spleef create <arena> " + ChatColor.WHITE + ": create a arena envorinment based on pos1 and pos2");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/spleef remove <arena> " + ChatColor.WHITE + ": removes the arena!");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/spleef sp add <arena> " + ChatColor.WHITE + ": adds a spawnpoint");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/spleef sp remove <id> <arena> " + ChatColor.WHITE + ": remove a spawnpoint from the arena");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/spleef sp list <arena> " + ChatColor.WHITE + ": shows all the spawnpoint id's");
						}	
					} else if(args[0].equalsIgnoreCase("list")) {
						StringBuilder build = new StringBuilder();
						SpleefArena[] arenas = xEssentials.getManagers().getMinigameManager().getAllSpleefArenas();
						for(int i = 0; i < arenas.length; i++) {
							if(i == arenas.length) {
								SpleefArena arena = arenas[i];
								build.append((arena.isActive() ? MinigameType.SPLEEF.getPrefix() + arena.getArenaName() : ChatColor.GRAY + "[not active]" + arena.getArenaName()));
							} else {
								SpleefArena arena = arenas[i];
								build.append((arena.isActive() ? MinigameType.SPLEEF.getPrefix() + arena.getArenaName() + ChatColor.WHITE + ", " : ChatColor.GRAY + "[not active]" + arena.getArenaName() + ChatColor.WHITE + ", "));
							}
						}
					} else if(args[0].equalsIgnoreCase("quit")) {
						if(sender instanceof Player) {
							xEssentialsPlayer xp  = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName()); 
							if(xEssentials.getManagers().getMinigameManager().isPlayerInArea(xp.getPlayer())) {
								Minigame obj = xEssentials.getManagers().getMinigameManager().getArenaFromPlayer(xp.getPlayer());
								if(obj instanceof SpleefArena) {
									SpleefArena arena = (SpleefArena) obj;
									if(arena.getJoinedCount() == 1) {
										arena.removePlayer(sender.getName());
										sender.sendMessage(ChatColor.GREEN + "you successfully left the arena!");
										arena.setRunning(false);
										xp.getPlayer().performCommand("spawn");
									}
								}
							} else {
								sender.sendMessage(ChatColor.RED + "you aren't joined inside a arena!");
							}
						} else {
							Warnings.getWarnings(sender).consoleMessage();
						}
					}
				} else if(args.length == 2) {
					if(args[0].equalsIgnoreCase("join")) {
						if(sender instanceof Player) {
							xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
							if(xEssentials.getManagers().getMinigameManager().isArena(args[1])) {
								Minigame arena = xEssentials.getManagers().getMinigameManager().getArena(args[1]);
								if(arena instanceof SpleefArena) {
									if(arena.isActive()) {
										if(arena.addPlayer(xp)) {
											Location loc = arena.getPlayerSpawnPoint(xp.getPlayer());
											loc.getWorld().refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
											xp.getPlayer().teleport(loc);
											sender.sendMessage(ChatColor.GREEN + "you have successfully joined the " + arena.getArenaName() + " arena!");
											
										} else {
											sender.sendMessage(ChatColor.RED + "the arena is full!");
										}
									} else {
										sender.sendMessage(ChatColor.RED + "this arena is under construction, the spawnpoints aren't set yet.");
									}
								}
							} else {
								sender.sendMessage(ChatColor.RED + "this arena does not exist!");
							}
						} else {
							Warnings.getWarnings(sender).consoleMessage();
						}
					} else if(args[0].equalsIgnoreCase("create")) {
						if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							if(sender instanceof Player) {
								if(!(xEssentials.getManagers().getMinigameManager().isArena(args[1]))) {
									CreateSpleefArenaEvent.hash.put(sender.getName(), args[1]);
									sender.sendMessage(ChatColor.GREEN + "please select the x pos, and then the z pos by right clicking.");
								} else {
									sender.sendMessage(ChatColor.RED + "the arena does already exist with that name!");
								}
							} else {
								Warnings.getWarnings(sender).consoleMessage();
							}
						} else {
							Warnings.getWarnings(sender).noPermission();
						}
					} else if(args[0].equalsIgnoreCase("remove")) {
						if(xEssentials.getManagers().getMinigameManager().isArena(args[0])) {
							Minigame game = (Minigame) xEssentials.getManagers().getMinigameManager().getArena(args[0]);
							game.remove();
							sender.sendMessage(ChatColor.GREEN + "arena has been successfully removed!");
						} else {
							sender.sendMessage(ChatColor.RED + "arena name does not exist!");
						}
					}
				} else if(args.length == 3) {
					if(args[0].equalsIgnoreCase("sp") && args[1].equalsIgnoreCase("add")) {
						if(sender instanceof Player) {
							Player p = (Player) sender;
							Block snow = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
							if(xEssentials.getManagers().getMinigameManager().isArena(args[2])) {
								Minigame obj = xEssentials.getManagers().getMinigameManager().getArena(args[2]);
								if(obj instanceof SpleefArena) {
									SpleefArena arena = (SpleefArena) obj;
									for(Block block : arena.getSnowLayer()) {
										if(block.getLocation().equals(snow.getLocation())) {
											arena.setSpawnPoint(p.getLocation());
											sender.sendMessage(ChatColor.GREEN + "spawnpoint successfully set for arena " + arena.getArenaName());
											return false;
										}
									}
									sender.sendMessage(ChatColor.RED + "this is not a arena block!");
								} else {
									sender.sendMessage(ChatColor.RED + "invalid arena type given in!, this is not a Spleef arena!");	
								}
							} else {
								sender.sendMessage(ChatColor.RED + "this arena does not exist!");
							}
						} else {
							Warnings.getWarnings(sender).consoleMessage();
						}
					} else if(args[0].equalsIgnoreCase("sp") && args[1].equalsIgnoreCase("list")) {
						if(xEssentials.getManagers().getMinigameManager().isArena(args[2])) {
							Minigame arena = (Minigame) xEssentials.getManagers().getMinigameManager().getArena(args[2]);
							if(arena instanceof SpleefArena) {
								StringBuilder build = new StringBuilder();
								List<Location> list = Arrays.asList(arena.getSpawnPoints());
								if(!list.isEmpty()) {
									sender.sendMessage(ChatColor.GOLD + ".oO___[Spleef spawnpoint list]___Oo.");
									for(int i = 0; i < list.size(); i++) {
										Location loc = list.get(i);
										build.append(ChatColor.GRAY + "ID:"+ChatColor.GREEN+i+ChatColor.GRAY+":"+loc.getWorld().getName() + " x:" + loc.getBlockX() + " y:" + loc.getBlockY() + " z:" + loc.getBlockZ());
									}
									sender.sendMessage(build.toString());
								} else {
									sender.sendMessage(ChatColor.RED + "no spawnpoints defined!");
								}
							} else {
								sender.sendMessage(ChatColor.RED + "this arena is not a SpleefArena!");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "this arena does not exist");
						}
					}
				} else if(args.length == 4) {
					if(args[0].equalsIgnoreCase("sp") && args[1].equalsIgnoreCase("remove")) {
						try {
							int id = Integer.parseInt(args[2]);
							if(xEssentials.getManagers().getMinigameManager().isArena(args[3])) {
								Minigame arena = (Minigame) xEssentials.getManagers().getMinigameManager().getArena(args[3]);
								if(arena instanceof SpleefArena) {
									try {
										arena.removeSpawnPoint(id);
										sender.sendMessage(ChatColor.GREEN + "you have successfully removed spawnpoint " + id);
									} catch(IndexOutOfBoundsException e) {
										sender.sendMessage(ChatColor.RED + "invalid id number!");
									}
								} else {
									sender.sendMessage(ChatColor.RED + "this is an Arena but not a SpleefArena");
								}
							} else {
								sender.sendMessage(ChatColor.RED + "this arena does not exist!");
							}
						} catch(NumberFormatException e) {
							e.printStackTrace();
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
