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

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;

public class CmdSpawn extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdSpawn(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("spawn")) {
			if(args.length == 0) {
				if(sender instanceof Player) {
					if(sender.hasPermission(PermissionKey.CMD_SPAWN.getPermission())) {
						try {
							File f = new File(pl.getDataFolder() + File.separator + "spawn.yml");
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
									sendMessage(ChatColor.GREEN + "teleporting to spawn ;-)");
								} else {
									sendMessage(ChatColor.RED + "the world does not exists for the spawn!");
								}
							} else {
								sendMessage(ChatColor.RED + "warning the spawn is not set");
							}
						} catch(Exception e) {
							e.printStackTrace();
						}
					} else {
						getWarning(WarningType.NO_PERMISSION);
					}
				} else {
					getWarning(WarningType.PLAYER_ONLY);
				}
			} else if(args.length == 1) {
				if(sender.hasPermission(PermissionKey.CMD_SPAWN_OTHERS.getPermission())) {
					Player victem = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]).getPlayer();
					if(victem instanceof Player) {
						try {
							File f = new File(pl.getDataFolder() + File.separator + "spawn.yml");
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
									sendMessageTo(victem, ChatColor.GREEN + sender.getName() +  " teleported you to spawn ;-)");
									sendMessage(ChatColor.GREEN + "successfully teleported " + victem.getName() + " to spawn ;-)");
								} else {
									sendMessage(ChatColor.RED + "the world does not exists for the spawn!");
								}
							} else {
								sendMessage(ChatColor.RED + "warning the spawn is not set");
							}
						} catch(Exception e) {
							e.printStackTrace();
						}
					} else {
						sendMessage(ChatColor.RED + "this player is not online!");
					}
				} else {
					getWarning(WarningType.NO_PERMISSION);
				}
			}
		}
		return false;
	}

}
