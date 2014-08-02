package tv.mineinthebox.essentials.events.customEvents;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

public class CallAfkSchedulerEvent implements Listener {

	private HashMap<String, Location> PlayerLocations = new HashMap<String, Location>();

	public void onStartAfkScheduler() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(xEssentials.getPlugin(), new Runnable() {

			@Override
			public void run() {
				for(Player p : xEssentials.getOnlinePlayers()) {
					if(PlayerLocations.containsKey(p.getName())) {
						Location currentLocation = p.getLocation();
						if(PlayerLocations.get(p.getName()).equals(currentLocation)) {
							xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(p.getName());
							if(!xp.isVanished()) {
								if(!xp.isFishing()) {
									if(!xp.isAfk()) {
										Bukkit.broadcastMessage(ChatColor.GREEN + p.getName() + " has been afk");
										xp.setAfk("no reason given in");
										Bukkit.getPluginManager().callEvent(new PlayerAfkEvent(xp.getPlayer(), true, false));
									}
								}
							}
						} else {
							PlayerLocations.put(p.getName(), p.getLocation());
						}
					} else {
						PlayerLocations.put(p.getName(), p.getLocation());
					}
				}
			}

		},100, 3000);
	}


	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(PlayerLocations.containsKey(e.getPlayer().getName())) {
			xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isAfk()) {
				xp.removeAfk();
				Bukkit.broadcastMessage(ChatColor.GREEN + e.getPlayer().getName() + " is no longer afk");
				Bukkit.getPluginManager().callEvent(new PlayerAfkEvent(xp.getPlayer(), false, true));
			}
			PlayerLocations.remove(e.getPlayer().getName());
		}
	}

	@EventHandler
	public void onQuit(PlayerKickEvent e) {
		if(PlayerLocations.containsKey(e.getPlayer().getName())) {
			xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isAfk()) {
				xp.removeAfk();
				Bukkit.broadcastMessage(ChatColor.GREEN + e.getPlayer().getName() + " is no longer afk");
				Bukkit.getPluginManager().callEvent(new PlayerAfkEvent(xp.getPlayer(), false, true));
			}
			PlayerLocations.remove(e.getPlayer().getName());
		}
	}


}
