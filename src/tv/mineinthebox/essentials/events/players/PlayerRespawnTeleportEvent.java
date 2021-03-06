package tv.mineinthebox.essentials.events.players;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.EventTemplate;

public class PlayerRespawnTeleportEvent extends EventTemplate implements Listener {
	
	public PlayerRespawnTeleportEvent(xEssentials pl) {
		super(pl, "Respawn");
	}
	
	@EventHandler
	public void RespawnPlayer(PlayerRespawnEvent e) {
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
					Location loc = new Location(w, x, y, z, yaw, e.getPlayer().getLocation().getPitch());
					e.setRespawnLocation(loc);
					sendMessage(e.getPlayer(), ChatColor.GREEN + "teleporting to spawn ;-)");
				} else {
					sendMessage(e.getPlayer(), ChatColor.RED + "the world does not exists for the spawn!");
				}
			} else {
				sendMessage(e.getPlayer(), ChatColor.RED + "warning the spawn is not set");
			}
		} catch(Exception r) {
			r.printStackTrace();
		}
	}

}
