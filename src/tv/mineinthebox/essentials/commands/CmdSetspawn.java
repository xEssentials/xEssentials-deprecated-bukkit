package tv.mineinthebox.essentials.commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;

public class CmdSetspawn {
	
	private final xEssentials pl;
	
	public CmdSetspawn(xEssentials pl) {
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("setspawn")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_SET_SPAWN.getPermission())) {
					try {
						Player p = (Player) sender;
						File f = new File(pl.getDataFolder() + File.separator + "spawn.yml");
						FileConfiguration con = YamlConfiguration.loadConfiguration(f);
						con.set("x", p.getLocation().getX());
						con.set("y", p.getLocation().getY());
						con.set("z", p.getLocation().getZ());
						con.set("yaw", p.getLocation().getYaw());
						con.set("world", p.getWorld().getName());
						con.save(f);
						p.getWorld().setSpawnLocation(p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
						sender.sendMessage(ChatColor.GREEN + "spawn successfully set ;-)");
					} catch(Exception e) {
						e.printStackTrace();
					}
				} else {
					Warnings.getWarnings(sender).noPermission();
				}
			} else {
				Warnings.getWarnings(sender).consoleMessage();
			}
		}
		return false;
	}

}
