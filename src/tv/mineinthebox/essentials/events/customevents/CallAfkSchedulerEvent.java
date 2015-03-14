package tv.mineinthebox.essentials.events.customevents;

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
import tv.mineinthebox.essentials.interfaces.EventTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CallAfkSchedulerEvent extends EventTemplate implements Listener {
	
	public CallAfkSchedulerEvent(xEssentials pl) {
		super(pl, "Afk");
	}

	private HashMap<String, Location> PlayerLocations = new HashMap<String, Location>();

	public void onStartAfkScheduler() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, new Runnable() {

			@Override
			public void run() {
				for(Player p : pl.getOnlinePlayers()) {
					if(PlayerLocations.containsKey(p.getName())) {
						Location currentLocation = p.getLocation();
						if(PlayerLocations.get(p.getName()).equals(currentLocation)) {
							XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(p.getName());
							if(!xp.isVanished()) {
								if(!xp.isFishing()) {
									if(!xp.isAfk()) {
										broadcast(ChatColor.GREEN + p.getName() + " has been afk");
										xp.setAfk("no reason given in");
										Bukkit.getPluginManager().callEvent(new PlayerAfkEvent(xp.getPlayer(), true, false, pl));
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
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isAfk()) {
				xp.removeAfk();
				Bukkit.broadcastMessage(ChatColor.GREEN + e.getPlayer().getName() + " is no longer afk");
				Bukkit.getPluginManager().callEvent(new PlayerAfkEvent(xp.getPlayer(), false, true, pl));
			}
			PlayerLocations.remove(e.getPlayer().getName());
		}
	}

	@EventHandler
	public void onQuit(PlayerKickEvent e) {
		if(PlayerLocations.containsKey(e.getPlayer().getName())) {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isAfk()) {
				xp.removeAfk();
				Bukkit.broadcastMessage(ChatColor.GREEN + e.getPlayer().getName() + " is no longer afk");
				Bukkit.getPluginManager().callEvent(new PlayerAfkEvent(xp.getPlayer(), false, true,pl));
			}
			PlayerLocations.remove(e.getPlayer().getName());
		}
	}


}
