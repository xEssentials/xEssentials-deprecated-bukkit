package tv.mineinthebox.essentials.managers;

import java.util.HashMap;

import org.bukkit.Location;

public class TeleportManager {
	
	private final HashMap<String, Location> locations = new HashMap<String, Location>();
	
	public boolean hasLastLocation(String player) {
		return locations.containsKey(player.toLowerCase());
	}
	
	public void addLastLocation(String player, Location loc) {
		locations.put(player.toLowerCase(), loc);
	}
	
	public Location getLastLocation(String player) {
		return locations.get(player.toLowerCase());
	}
	
	public void removeLastLocationData(String player) {
		locations.remove(player.toLowerCase());
	}

}
