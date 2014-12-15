package tv.mineinthebox.bukkit.essentials.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.Warnings;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;
import tv.mineinthebox.bukkit.essentials.instances.Home;
import tv.mineinthebox.bukkit.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.bukkit.essentials.interfaces.XPlayer;

public class CmdHome {

	private List<String> getPlayers(String p) {
		List<String> players = new ArrayList<String>();
		for(XOfflinePlayer off : xEssentials.getManagers().getPlayerManager().getOfflinePlayers()) {
			if(off.getUser().toUpperCase().startsWith(p.toUpperCase())) {
				players.add(off.getUser());
			}
		}
		return players;
	}

	private List<String> getHomes(String sender, String homename) {
		List<String> homes = new ArrayList<String>();
		XOfflinePlayer xp = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(sender);
		for(Home home : xp.getAllHomes()) {
			if(home.getHomeName().toUpperCase().startsWith(homename.toUpperCase())) {
				homes.add(home.getHomeName());
			}
		}
		return homes;
	}

	public List<String> onTabComplete(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("home")) {
			if(args.length == 1) {
				if(!sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
					if(sender.hasPermission(PermissionKey.MULTIPLE_HOMES.getPermission())) {
						return getHomes(sender.getName(), args[0]);
					}
				} else {
					return getPlayers(args[0]);
				}
			} else if(args.length == 2) {
				if(!args[0].equalsIgnoreCase("list")) {
					if(args[0].equalsIgnoreCase("remove")) {
						if(sender.hasPermission(PermissionKey.MULTIPLE_HOMES.getPermission())) {
							return getHomes(sender.getName(), args[1]);
						}
					} else {
						if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(args[0])) {
								return getHomes(args[0], args[1]);	
							}
						}
					}
				} else {
					if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
						return getPlayers(args[1]);
					}
				}
			}
		}
		return null;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("home")) {
			if(sender.hasPermission(PermissionKey.CMD_HOME.getPermission())) {
				if(args.length == 0) {
					if(sender instanceof Player) {
						XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
						if(xp.hasHome()) {
							Home home = xp.getHome("default");
							home.getLocation().getWorld().refreshChunk(home.getLocation().getChunk().getX(), home.getLocation().getChunk().getZ());
							if(xp.getPlayer().isInsideVehicle()) {
								if(xp.getPlayer().getVehicle() instanceof LivingEntity) {
									LivingEntity entity = (LivingEntity) xp.getPlayer().getVehicle();
									xp.getPlayer().getVehicle().eject();
									xp.getPlayer().teleport(home.getLocation());
									entity.teleport(home.getLocation());
									entity.setPassenger(xp.getPlayer());
									sender.sendMessage(ChatColor.GREEN + "teleporting to your default home!");
								} else {
									xp.getPlayer().getVehicle().eject();
									xp.getPlayer().teleport(home.getLocation());
									sender.sendMessage(ChatColor.GREEN + "teleporting to your default home!");
								}
							} else {
								xp.getPlayer().teleport(home.getLocation());
								sender.sendMessage(ChatColor.GREEN + "teleporting to your default home!");	
							}
						} else {
							sender.sendMessage(ChatColor.RED + "you don't have set any home!");
						}
					} else {
						Warnings.getWarnings(sender).consoleMessage();
					}
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[homes help]___Oo.");
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/home " + ChatColor.WHITE + ": teleport to your default home");
						if(sender.hasPermission(PermissionKey.MULTIPLE_HOMES.getPermission()) || Configuration.getPlayerConfig().canUseMoreHomes()) {
							if(sender instanceof Player) {
								XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
								sender.sendMessage(ChatColor.GREEN + "you can use max " + ChatColor.GRAY + xp.getAmountOfHomes() + "/" + Configuration.getPlayerConfig().getMaxHomesAllowed() + ChatColor.GREEN + " homes!");
							}
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/home list " + ChatColor.WHITE + ": shows a list of all homes you own!");
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/home <home name> " + ChatColor.WHITE + ": allows you to teleport to a custom home");
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/home remove <home name> " + ChatColor.WHITE + ": allows you to remove a home");
						}
						if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/home <player> " + ChatColor.WHITE + ": allows you to teleport to a players home");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/home list <player> " + ChatColor.WHITE + ": shows a list of all homes owned by this player");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/home <player> <home name> " + ChatColor.WHITE + ": allows you to teleport to a players custom home");
						}
					} else if(args[0].equalsIgnoreCase("list")) {
						if(sender instanceof Player) {
							XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
							if(xp.hasHome()) {
								List<String> list = new ArrayList<String>();
								for(Home home : xp.getAllHomes()) {
									list.add(home.getHomeName());
								}
								String HomeNames = Arrays.toString(list.toArray(new String[list.size()])).replace("[", "").replace("]", "");
								sender.sendMessage(ChatColor.GOLD + ".oO___[home list]___Oo.");
								sender.sendMessage(ChatColor.GRAY + HomeNames);
							} 
						} else {
							Warnings.getWarnings(sender).consoleMessage();
						}
					} else {
						if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(args[0])) {
							if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
								if(sender instanceof Player) {
									Player p = (Player) sender;
									XOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
									if(!off.hasHome()) {
										sender.sendMessage(ChatColor.RED + off.getUser() + " has no home set!");
										return false;
									}
									Location loc = off.getHome("default").getLocation();
									loc.getWorld().refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
									if(p.isInsideVehicle()) {
										if(p.getVehicle() instanceof LivingEntity) {
											LivingEntity entity = (LivingEntity) p.getVehicle();
											p.getVehicle().eject();
											p.teleport(loc);
											entity.teleport(loc);
											entity.setPassenger(p);
											sender.sendMessage(ChatColor.GREEN + "teleporting to " + off.getUser() + " his default home!");
										} else {
											p.getPlayer().getVehicle().eject();
											sender.sendMessage(ChatColor.GREEN + "teleporting to " + off.getUser() + " his default home!");
											p.teleport(loc);
										}
									} else {
										sender.sendMessage(ChatColor.GREEN + "teleporting to " + off.getUser() + " his default home!");
										p.teleport(loc);	
									}
								} else {
									Warnings.getWarnings(sender).consoleMessage();
								}
							} else {
								sender.sendMessage(ChatColor.RED + "your home is a players name now which is not allowed");
							}
						} else {
							if(sender.hasPermission(PermissionKey.MULTIPLE_HOMES.getPermission()) || Configuration.getPlayerConfig().canUseMoreHomes()) {
								if(sender instanceof Player) {
									XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
									if(xp.hasHome()) {
										if(xp.isValidHome(args[0])) {
											Location loc = xp.getHome(args[0]).getLocation();
											loc.getWorld().refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
											if(xp.getPlayer().isInsideVehicle()) {
												if(xp.getPlayer().getVehicle() instanceof LivingEntity) {
													LivingEntity entity = (LivingEntity) xp.getPlayer().getVehicle();
													xp.getPlayer().getVehicle().eject();
													xp.getPlayer().teleport(loc);
													entity.teleport(loc);
													entity.setPassenger(xp.getPlayer());
													sender.sendMessage(ChatColor.GREEN + "teleporting to your custom home " + args[0]);
												} else {
													xp.getPlayer().getVehicle().eject();
													sender.sendMessage(ChatColor.GREEN + "teleporting to your custom home " + args[0]);
													xp.getPlayer().teleport(loc);
												}
											} else {
												sender.sendMessage(ChatColor.GREEN + "teleporting to your custom home " + args[0]);
												xp.getPlayer().teleport(loc);	
											}
										} else {
											sender.sendMessage(ChatColor.RED + "invalid home!");
										}
									} else {
										sender.sendMessage(ChatColor.RED + "you don't have any home set!");
									}
								} else {
									Warnings.getWarnings(sender).consoleMessage();
								}
							} else {
								Warnings.getWarnings(sender).noPermission();
							}
						}
					}
				} else if(args.length == 2) {
					if(args[0].equalsIgnoreCase("remove")) {
						if(sender instanceof Player) {
							XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
							if(xp.isValidHome(args[1])) {
								xp.removeHome(args[1]);
								sender.sendMessage(ChatColor.GREEN + "home successfully removed!");
							} else {
								sender.sendMessage(ChatColor.RED + "invalid home name!");
							}
						} else {
							Warnings.getWarnings(sender).consoleMessage();
						}
					} else if(args[0].equalsIgnoreCase("list")) {
						if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(args[1])) {
								XOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[1]);
								if(off.hasHome()) {
									List<String> list = new ArrayList<String>();
									for(Home home : off.getAllHomes()) {
										list.add(home.getHomeName());
									}
									String[] homes = list.toArray(new String[list.size()]);
									sender.sendMessage(ChatColor.GOLD + ".oO___[home list for player " + off.getUser() + " ]___Oo.");
									sender.sendMessage(ChatColor.GRAY + Arrays.toString(homes).replace("[", "").replace("]", ""));
								}
							} else {
								sender.sendMessage(ChatColor.RED + "this player has never played before!");
							}
						} else {
							Warnings.getWarnings(sender).noPermission();
						}
					} else {
						if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							if(sender instanceof Player) {
								Player p = (Player) sender;
								if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(args[0])) {
									XOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
									if(off.hasHome()) {
										if(off.isValidHome(args[1])) {
											Location loc = off.getHome(args[1]).getLocation();
											loc.getWorld().refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
											if(p.isInsideVehicle()) {
												if(p.getVehicle() instanceof LivingEntity) {
													LivingEntity entity = (LivingEntity) p.getVehicle();
													p.getVehicle().eject();
													p.teleport(loc);
													entity.teleport(loc);
													entity.setPassenger(p);
													sender.sendMessage(ChatColor.GREEN + "teleporting to " + off.getUser() + " his custom home " + args[1]);
												} else {
													p.getVehicle().eject();
													sender.sendMessage(ChatColor.GREEN + "teleporting to " + off.getUser() + " his custom home " + args[1]);
													p.teleport(loc);
												}
											} else {
												sender.sendMessage(ChatColor.GREEN + "teleporting to " + off.getUser() + " his custom home " + args[1]);
												p.teleport(loc);	
											}
										} else {
											sender.sendMessage(ChatColor.RED + "invalid home!");
										}
									} else {
										sender.sendMessage(ChatColor.RED + off.getUser() + " has no homes set!");
									}
								} else {
									sender.sendMessage(ChatColor.RED + "this player has never played before!");
								}
							} else {
								Warnings.getWarnings(sender).consoleMessage();
							}
						} else {
							Warnings.getWarnings(sender).noPermission();
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
