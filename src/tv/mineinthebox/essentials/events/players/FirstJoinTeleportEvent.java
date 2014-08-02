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
import org.bukkit.event.player.PlayerJoinEvent;

import tv.mineinthebox.essentials.xEssentials;

public class FirstJoinTeleportEvent implements Listener {

	//fixes the problem where the player teleports to vanilla spawn.

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if(!e.getPlayer().hasPlayedBefore()) {
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
						Location loc = new Location(w, x, y, z, yaw, e.getPlayer().getLocation().getPitch());
						e.getPlayer().teleport(loc);
					} else {
						e.getPlayer().sendMessage(ChatColor.RED + "the world does not exists for the spawn!");
					}
				} else {
					e.getPlayer().sendMessage(ChatColor.RED + "warning the spawn is not set");
				}
			} catch(Exception r) {
				r.printStackTrace();
			}
		}
	}
}
