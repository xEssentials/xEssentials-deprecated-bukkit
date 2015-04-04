package tv.mineinthebox.essentials.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.hook.Hooks;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdTeleport extends CommandTemplate {

	private final xEssentials pl;

	public CmdTeleport(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	private List<String> getPlayerByName(String p) {
		List<String> s = new ArrayList<String>();
		for(XOfflinePlayer name : pl.getManagers().getPlayerManager().getOfflinePlayers()) {
			if(name.getName().toUpperCase().startsWith(p.toUpperCase())) {
				s.add(name.getName());
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
					showHelp();
				} else {
					getWarning(WarningType.NO_PERMISSION);
				}
			} else if(args.length == 1) {
				if(sender.hasPermission(PermissionKey.CMD_TELEPORT.getPermission())) {
					if(args[0].equalsIgnoreCase("help")) {
						showHelp();
					} else {
						if(sender instanceof Player) {
							XPlayer p = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
							Player to = Bukkit.getPlayer(args[0]);
							if(to instanceof Player) {
								XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[0]);
								if(xp.isVanished()) {
									if(p.getBukkitPlayer().hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
										p.vanish();
										sendMessageTo(p.getBukkitPlayer(), "the player you teleported to has been vanished, now you are vanished to!");	
									}
								}
								p.getBukkitPlayer().teleport(to.getPlayer());
								sendMessage("teleporting to online location of player " + xp.getName() + " ;-)");
							} else {
								if(pl.getManagers().getPlayerManager().isEssentialsPlayer(args[0])) {
									XOfflinePlayer offliner = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
									offliner.getLocation().getWorld().refreshChunk(offliner.getLocation().getChunk().getX(), offliner.getLocation().getChunk().getZ());
									p.getBukkitPlayer().teleport(offliner.getLocation(), TeleportCause.COMMAND);
									sendMessage("teleporting to last offline location of player " + offliner.getName() + " ;-)");
								} else {
									getWarning(WarningType.NEVER_PLAYED_BEFORE);	
								}
							}
						} else {
							getWarning(WarningType.PLAYER_ONLY);
						}
					}
				} else {
					getWarning(WarningType.NO_PERMISSION);
				}
			} else if(args.length == 2) {
				if(args[0].equalsIgnoreCase("crate")) {
					if(sender.hasPermission(PermissionKey.CMD_TELEPORT.getPermission())) {
						if(sender instanceof Player) {
							Player to = pl.getManagers().getPlayerManager().getOfflinePlayer(args[1]).getBukkitPlayer();
							Player p = (Player) sender;
							if(to instanceof Player) {
								if(Hooks.isManCoEnabled()) {
									if(pl.getManagers().getManCoManager().hasCrate(to)) {
										Location loc = pl.getManagers().getManCoManager().getCrateLocation(to);
										p.teleport(loc, TeleportCause.COMMAND);
										sendMessage("teleporting to " + to.getName() + " his ManCo crate ;-)");
									} else {
										sendMessage("this player doesn't own any ManCo crate!");
									}
								} else {
									sendMessage("ManCo is not enabled on this server!\nsee:\nhttp://dev.bukkit.org/bukkit-plugins/manco-supply-crates/");
								}
							} else {
								sendMessage("player is not online so the crate has been destroyed!");
							}
						} else {
							getWarning(WarningType.PLAYER_ONLY);
						}
					} else {
						getWarning(WarningType.NO_PERMISSION);
					}
				} else if(args[0].equalsIgnoreCase("region")) {
					if(sender.hasPermission(PermissionKey.CMD_TELEPORT.getPermission())) {
						if(sender instanceof Player) {
							if(Hooks.isWorldGuardEnabled()) {
								Player p = (Player) sender;
								if(pl.getManagers().getWorldGuardManager().isValidRegion(args[1], p.getWorld())) {
									Location loc = pl.getManagers().getWorldGuardManager().getRegionLocation(args[1], p.getWorld());
									loc.getWorld().refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
									p.teleport(loc, TeleportCause.COMMAND);
									sendMessage("teleporting to region " + args[1] + " ;-)");
								} else {
									sendMessage("region with that name whas not found to teleport to!");
								}
							} else {
								sendMessage("WorldGuard and WorldEdit are not enabled on this server!\nsee:\nhttp://dev.bukkit.org/bukkit-plugins/worldedit/");
							}
						} else {
							getWarning(WarningType.PLAYER_ONLY);
						}
					} else {
						getWarning(WarningType.NO_PERMISSION);
					}
				} else {
					if(sender.hasPermission(PermissionKey.CMD_TELEPORT.getPermission())) {
						Player p1 = Bukkit.getPlayer(args[0]);
						Player p2 = Bukkit.getPlayer(args[1]);
						if(p1 instanceof Player) {
							if(p2 instanceof Player) {
								p1.teleport(p2);
								sendMessageTo(p1, sender.getName() + " teleports you to " + p2.getName() + " ;-)");
								sendMessageTo(p2, p1.getName() + " has been teleported to you by " + sender.getName());
								sendMessage("you successfully teleported player " + p1.getName() + " to player " + p2.getName());
							} else {
								sendMessage("this player is not online!");
							}
						} else {
							sendMessage("this player is not online!");
						}
					} else {
						getWarning(WarningType.NO_PERMISSION);
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
							p.teleport(loc, TeleportCause.COMMAND);
							sendMessage("teleporting to x:"+x + " y:"+y + " z:"+z + " ;-)");
						} catch(NumberFormatException e) {
							sendMessage("these are invalid coordinates to teleport to!");
						}
					} else {
						getWarning(WarningType.PLAYER_ONLY);
					}
				} else {
					getWarning(WarningType.NO_PERMISSION);
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
								p.teleport(loc, TeleportCause.COMMAND);
								sendMessage("teleporting to " + w.getName() + " x:" + x + " y:" + y + " z:"+z+" ;-)");
							} catch(NumberFormatException e) {
								sendMessage("these are invalid coordinates to teleport to!");
							}
						} else {
							getWarning(WarningType.PLAYER_ONLY);
						}
					} else if(victem instanceof Player) {
						try {
							Double x = Double.parseDouble(args[1]);
							Double y = Double.parseDouble(args[2]);
							Double z = Double.parseDouble(args[3]);
							Location loc = new Location(victem.getWorld(), x, y, z);
							loc.getWorld().refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
							victem.teleport(loc, TeleportCause.COMMAND);
							sendMessageTo(victem, "you are teleported by " + sender.getName() + "to x:" + x + " y:" + y + " z:" + z + " ;-)");
							sendMessage("successfully teleported " + victem.getName() + "to x:" + x + " y:" + y + " z:" + z + " ;-)");
						} catch(NumberFormatException e) {
							sender.sendMessage("these are invalid coordinates to teleport to!");
						}
					} else {
						sendMessage("we are sorry we don't know about these arguments!");
					}
				} else {
					getWarning(WarningType.NO_PERMISSION);
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
							victem.teleport(loc, TeleportCause.COMMAND);
							sendMessageTo(victem, "you are teleported by " + sender.getName() + "to world: " + w.getName() + " x:" + x + " y:" + y + " z:" + z + " ;-)");
							sendMessage("successfully teleported " + victem.getName() + "to world: " + w.getName() + " x:" + x + " y:" + y + " z:" + z + " ;-)");
						} else {
							sendMessage("invalid world to teleport to!");
						}
					} catch(NumberFormatException e) {
						sendMessage("these are invalid coordinates to teleport to!");
					}
				} else {
					sendMessage("we don't know much about these arguments!");
				}
			}
		}
		return false;
	}

	public void showHelp() {
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
