package tv.mineinthebox.bukkit.essentials.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.bukkit.essentials.Warnings;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;
import tv.mineinthebox.bukkit.essentials.hook.Hooks;
import tv.mineinthebox.bukkit.essentials.hook.WorldGuardHook;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsOfflinePlayer;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsPlayer;

public class CmdTeleport {

	private List<String> getPlayerByName(String p) {
		List<String> s = new ArrayList<String>();
		for(xEssentialsOfflinePlayer name : xEssentials.getManagers().getPlayerManager().getOfflinePlayers()) {
			if(name.getUser().toUpperCase().startsWith(p.toUpperCase())) {
				s.add(name.getUser());
			}
		}
		return s;
	}

	public List<String> onTabComplete(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("teleport")) {
			if(sender.hasPermission(PermissionKey.CMD_TELEPORT.getPermission())) {
				if(args.length == 1) {
					List<String> list = getPlayerByName(args[0]);
					return list;
				}
			}
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("teleport")) {
			if(args.length == 0) {
				if(sender.hasPermission(PermissionKey.CMD_TELEPORT.getPermission())) {
					sendHelp(sender);
				} else {
					Warnings.getWarnings(sender).noPermission();
				}
			} else if(args.length == 1) {
				if(sender.hasPermission(PermissionKey.CMD_TELEPORT.getPermission())) {
					if(args[0].equalsIgnoreCase("help")) {
						sendHelp(sender);
					} else {
						if(sender instanceof Player) {
							xEssentialsPlayer p = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
							Player to = Bukkit.getPlayer(args[0]);
							if(to instanceof Player) {
								xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(args[0]);
								if(xp.isVanished()) {
									if(p.getPlayer().hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
										p.vanish();
										p.getPlayer().sendMessage(ChatColor.GREEN + "the player you teleported to has been vanished, now you are vanished to!");	
									}
								}
								p.getPlayer().teleport(to.getPlayer());
								sender.sendMessage(ChatColor.GREEN + "teleporting to online location of player " + xp.getUser() + " ;-)");
							} else {
								try {
									xEssentialsOfflinePlayer offliner = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
									offliner.getLocation().getWorld().refreshChunk(offliner.getLocation().getChunk().getX(), offliner.getLocation().getChunk().getZ());
									if(offliner.isVanished()) {
										if(offliner.isVanished()) {
											if(p.getPlayer().hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
												p.vanish();
												p.getPlayer().sendMessage(ChatColor.GREEN + "the player you teleported to has been vanished, now you are vanished to!");	
											}
										}	
									}
									p.getPlayer().teleport(offliner.getLocation());
									sender.sendMessage(ChatColor.GREEN + "teleporting to last offline location of player " + offliner.getUser() + " ;-)");
								} catch(NullPointerException e) {
									Warnings.getWarnings(sender).playerHasNeverPlayedBefore();
								}
							}
						} else {
							Warnings.getWarnings(sender).consoleMessage();
						}
					}
				} else {
					Warnings.getWarnings(sender).noPermission();
				}
			} else if(args.length == 2) {
				if(args[0].equalsIgnoreCase("crate")) {
					if(sender.hasPermission(PermissionKey.CMD_TELEPORT.getPermission())) {
						if(sender instanceof Player) {
							Player to = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[1]).getPlayer();
							Player p = (Player) sender;
							if(to instanceof Player) {
								if(Hooks.isManCoEnabled()) {
									if(xEssentials.getManagers().getManCoManager().hasCrate(to)) {
										Location loc = xEssentials.getManagers().getManCoManager().getCrateLocation(to);
										p.teleport(loc);
										sender.sendMessage(ChatColor.GREEN + "teleporting to " + to.getName() + " his ManCo crate ;-)");
									} else {
										sender.sendMessage(ChatColor.RED + "this player doesn't own any ManCo crate!");
									}
								} else {
									sender.sendMessage(ChatColor.RED + "ManCo is not enabled on this server!\nsee:\nhttp://dev.bukkit.org/bukkit-plugins/manco-supply-crates/");
								}
							} else {
								sender.sendMessage(ChatColor.RED + "player is not online so the crate has been destroyed!");
							}
						} else {
							Warnings.getWarnings(sender).consoleMessage();
						}
					} else {
						Warnings.getWarnings(sender).noPermission();
					}
				} else if(args[0].equalsIgnoreCase("region")) {
					if(sender.hasPermission(PermissionKey.CMD_TELEPORT.getPermission())) {
						if(sender instanceof Player) {
							if(Hooks.isWorldGuardEnabled()) {
								Player p = (Player) sender;
								if(WorldGuardHook.isValidRegion(args[1], p.getWorld())) {
									Location loc = WorldGuardHook.getRegionLocation(args[1], p.getWorld());
									loc.getWorld().refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
									p.teleport(loc);
									sender.sendMessage(ChatColor.GREEN + "teleporting to region " + args[1] + " ;-)");
								} else {
									sender.sendMessage(ChatColor.RED + "region with that name whas not found to teleport to!");
								}
							} else {
								sender.sendMessage(ChatColor.RED + "WorldGuard and WorldEdit are not enabled on this server!\nsee:\nhttp://dev.bukkit.org/bukkit-plugins/worldedit/");
							}
						} else {
							Warnings.getWarnings(sender).consoleMessage();
						}
					} else {
						Warnings.getWarnings(sender).noPermission();
					}
				} else {
					if(sender.hasPermission(PermissionKey.CMD_TELEPORT.getPermission())) {
						Player p1 = Bukkit.getPlayer(args[0]);
						Player p2 = Bukkit.getPlayer(args[1]);
						if(p1 instanceof Player) {
							if(p2 instanceof Player) {
								p1.teleport(p2);
								p1.sendMessage(ChatColor.GREEN + sender.getName() + " teleports you to " + p2.getName() + " ;-)");
								p2.sendMessage(ChatColor.GREEN + p1.getName() + " has been teleported to you by " + sender.getName());
							} else {
								sender.sendMessage(ChatColor.RED + "this player is not online!");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "this player is not online!");
						}
					} else {
						Warnings.getWarnings(sender).noPermission();
					}
				}
			} else if(args.length == 3) {
				if(sender.hasPermission(PermissionKey.CMD_TELEPORT.getPermission())) {
					if(sender instanceof Player) {
						try {
							Player p = (Player) sender;
							Double x = Double.parseDouble(args[0]);
							Double y = Double.parseDouble(args[1]);
							Double z = Double.parseDouble(args[2]);
							Location loc = new Location(p.getWorld(), x, y, z);
							loc.getWorld().refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
							p.teleport(loc);
							sender.sendMessage(ChatColor.GREEN + "teleporting to x:"+x + " y:"+y + " z:"+z + " ;-)");
						} catch(NumberFormatException e) {
							sender.sendMessage(ChatColor.RED + "these are invalid coordinates to teleport to!");
						}
					} else {
						Warnings.getWarnings(sender).consoleMessage();
					}
				} else {
					Warnings.getWarnings(sender).noPermission();
				}
			} else if(args.length == 4) {
				if(sender.hasPermission(PermissionKey.CMD_TELEPORT.getPermission())) {
					World w = Bukkit.getWorld(args[0]);
					Player victem = Bukkit.getPlayer(args[0]);
					if(w instanceof World) {
						if(sender instanceof Player) {
							Player p = (Player) sender;
							try {
								Double x = Double.parseDouble(args[1]);
								Double y = Double.parseDouble(args[2]);
								Double z = Double.parseDouble(args[3]);
								Location loc = new Location(w, x, y, z);
								loc.getWorld().refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
								p.teleport(loc);
								sender.sendMessage(ChatColor.GREEN + "teleporting to " + w.getName() + " x:" + x + " y:" + y + " z:"+z+" ;-)");
							} catch(NumberFormatException e) {
								sender.sendMessage(ChatColor.RED + "these are invalid coordinates to teleport to!");
							}
						} else {
							Warnings.getWarnings(sender).consoleMessage();
						}
					} else if(victem instanceof Player) {
						try {
							Double x = Double.parseDouble(args[1]);
							Double y = Double.parseDouble(args[2]);
							Double z = Double.parseDouble(args[3]);
							Location loc = new Location(victem.getWorld(), x, y, z);
							loc.getWorld().refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
							victem.teleport(loc);
							victem.sendMessage(ChatColor.GREEN + "you are teleported by " + sender.getName() + "to x:" + x + " y:" + y + " z:" + z + " ;-)");
							sender.sendMessage(ChatColor.GREEN + "successfully teleported " + victem.getName() + "to x:" + x + " y:" + y + " z:" + z + " ;-)");
						} catch(NumberFormatException e) {
							sender.sendMessage(ChatColor.RED + "these are invalid coordinates to teleport to!");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "we are sorry we don't know about these arguments!");
					}
				} else {
					Warnings.getWarnings(sender).noPermission();
				}
			} else if(args.length == 5) {
				Player victem = Bukkit.getPlayer(args[0]);
				if(victem instanceof Player){
					try {
						World w = Bukkit.getWorld(args[1]);
						if(w instanceof World) {
							Double x = Double.parseDouble(args[2]);
							Double y = Double.parseDouble(args[3]);
							Double z = Double.parseDouble(args[4]);
							Location loc = new Location(w, x, y, z);
							loc.getWorld().refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
							victem.teleport(loc);
							victem.sendMessage(ChatColor.GREEN + "you are teleported by " + sender.getName() + "to world: " + w.getName() + " x:" + x + " y:" + y + " z:" + z + " ;-)");
							sender.sendMessage(ChatColor.GREEN + "successfully teleported " + victem.getName() + "to world: " + w.getName() + " x:" + x + " y:" + y + " z:" + z + " ;-)");
						} else {
							sender.sendMessage(ChatColor.RED + "invalid world to teleport to!");
						}
					} catch(NumberFormatException e) {
						sender.sendMessage(ChatColor.RED + "these are invalid coordinates to teleport to!");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "we don't know much about these arguments!");
				}
			}
		}
		return false;
	}

	public void sendHelp(CommandSender sender) {
		sender.sendMessage(ChatColor.GOLD + ".oO___[teleport help]___Oo.");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tp <player> " + ChatColor.WHITE + ": teleport to a player");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tp <player1> <player2> " + ChatColor.WHITE + ": teleports player1 to player2");
		if(Bukkit.getPluginManager().isPluginEnabled("ManCo")){
			sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tp crate <player>" + ChatColor.WHITE + ": teleport to a players ManCo crate");
		}
		if(Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
			sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tp region <region name> " + ChatColor.WHITE + ": teleport to a worldguards region");
		}
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tp x y z " + ChatColor.WHITE + ": teleport to a location inside the same world");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tp world x y z " + ChatColor.WHITE + ": teleport to a location in a other world");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tp player x y z " + ChatColor.WHITE + ": teleports a player to specific coordinates");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tp player world x y z " + ChatColor.WHITE + ": teleports a player to specific coordinates and a specified world");
	}

}
