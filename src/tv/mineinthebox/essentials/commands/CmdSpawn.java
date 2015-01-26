package tv.mineinthebox.essentials.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;

public class CmdSpawn {
	
	private final xEssentials pl;
	
	public CmdSpawn(xEssentials pl) {
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("spawn")) {
			if(args.length == 0) {
				if(sender instanceof Player) {
					if(sender.hasPermission(PermissionKey.CMD_SPAWN.getPermission())) {
						try {
							File f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "spawn.yml");
							if(f.exists()) {
								Player p = (Player) sender;
								FileConfiguration con = YamlConfiguration.loadConfiguration(f);
								Double x = con.getDouble("x");
								Double y = con.getDouble("y");
								Double z = con.getDouble("z");
								int yaw = con.getInt("yaw");
								String world = con.getString("world");
								World w = Bukkit.getWorld(world);
								if(w instanceof World) {
									Location loc = new Location(w, x, y, z, yaw, p.getLocation().getPitch());
									p.teleport(loc, TeleportCause.COMMAND);
									p.sendMessage(ChatColor.GREEN + "teleporting to spawn ;-)");
								} else {
									sender.sendMessage(ChatColor.RED + "the world does not exists for the spawn!");
								}
							} else {
								sender.sendMessage(ChatColor.RED + "warning the spawn is not set");
							}
						} catch(Exception e) {
							e.printStackTrace();
						}
					} else {
						Warnings.getWarnings(sender).noPermission();
					}
				} else {
					Warnings.getWarnings(sender).consoleMessage();
				}
			} else if(args.length == 1) {
				if(sender.hasPermission(PermissionKey.CMD_SPAWN_OTHERS.getPermission())) {
					Player victem = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]).getPlayer();
					if(victem instanceof Player) {
						try {
							File f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "spawn.yml");
							if(f.exists()) {
								FileConfiguration con = YamlConfiguration.loadConfiguration(f);
								Double x = con.getDouble("x");
								Double y = con.getDouble("y");
								Double z = con.getDouble("z");
								int yaw = con.getInt("yaw");
								String world = con.getString("world");
								World w = Bukkit.getWorld(world);
								if(w instanceof World) {
									Location loc = new Location(w, x, y, z, yaw, victem.getLocation().getPitch());
									victem.teleport(loc, TeleportCause.COMMAND);
									victem.sendMessage(ChatColor.GREEN + sender.getName() +  " teleported you to spawn ;-)");
									sender.sendMessage(ChatColor.GREEN + "successfully teleported " + victem.getName() + " to spawn ;-)");
								} else {
									sender.sendMessage(ChatColor.RED + "the world does not exists for the spawn!");
								}
							} else {
								sender.sendMessage(ChatColor.RED + "warning the spawn is not set");
							}
						} catch(Exception e) {
							e.printStackTrace();
						}
					} else {
						sender.sendMessage(ChatColor.RED + "this player is not online!");
					}
				} else {
					Warnings.getWarnings(sender).noPermission();
				}
			}
		}
		return false;
	}

}
