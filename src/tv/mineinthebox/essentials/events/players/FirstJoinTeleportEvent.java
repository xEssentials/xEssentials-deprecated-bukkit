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
import tv.mineinthebox.essentials.enums.LogType;

public class FirstJoinTeleportEvent implements Listener {

	//fixes the problem where the player teleports to vanilla spawn.
	
	private final xEssentials pl;
	
	public FirstJoinTeleportEvent(xEssentials pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if(!e.getPlayer().hasPlayedBefore()) {
			
			if(pl.getConfiguration().getDebugConfig().isEnabled()) {
				xEssentials.log("player " + e.getPlayer().getName() + " has not played before!, teleporting to spawn", LogType.DEBUG);
			}
			
			try {
				File f = new File(pl.getDataFolder() + File.separator + "spawn.yml");
				if(f.exists()) {
					
					if(pl.getConfiguration().getDebugConfig().isEnabled()) {
						xEssentials.log("spawn file exist!", LogType.DEBUG);
					}
					
					FileConfiguration con = YamlConfiguration.loadConfiguration(f);
					Double x = con.getDouble("x");
					Double y = con.getDouble("y");
					Double z = con.getDouble("z");
					float yaw = con.getInt("yaw");
					String world = con.getString("world");
					World w = Bukkit.getWorld(world);
					if(w instanceof World) {
						Location loc = new Location(w, x, y, z, yaw, e.getPlayer().getLocation().getPitch());
						boolean bol = e.getPlayer().teleport(loc);
						if(pl.getConfiguration().getDebugConfig().isEnabled()) {
							xEssentials.log("did teleportation for first joining player succeeded?: " + (bol ? "success" : "failure"), LogType.DEBUG);
						}
					} else {
						e.getPlayer().sendMessage(ChatColor.RED + "the world does not exists for the spawn!");
					}
				} else {
					e.getPlayer().sendMessage(ChatColor.RED + "warning the spawn is not set");
				}
			} catch(Exception r) {
				r.printStackTrace();
			}
		} else {
			if(pl.getConfiguration().getDebugConfig().isEnabled()) {
				xEssentials.log("player " + e.getPlayer().getName() + " seems to exist already, not teleporting to spawn", LogType.DEBUG);
			}
		}
	}
}
