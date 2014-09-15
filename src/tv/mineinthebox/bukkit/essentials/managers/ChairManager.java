package tv.mineinthebox.bukkit.essentials.managers;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Iterator;

import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import tv.mineinthebox.bukkit.essentials.xEssentials;

public class ChairManager {

	private final HashMap<String, Entity> hash = new HashMap<String, Entity>();

	public void addChicken(Player p, Chicken chicken) {
		hash.put(p.getName(), chicken);
	}
	
	public void removeChicken(String player) {
		hash.get(player).remove();
		hash.remove(player);
	}
	
	public boolean containsPlayer(String s) {
		return hash.containsKey(s);
	}
	
	public boolean containsChicken(Chicken chicken) {
		return hash.containsValue(chicken);
	}
	
	public void killAll() {
		for(String player : xEssentials.getManagers().getChairManager().getPlayers()) {
			xEssentials.getManagers().getChairManager().removeChicken(player);
		}
	}
	
	private String[] getPlayers() {
		return hash.keySet().toArray(new String[hash.size()]);
	}
	
	public Iterator<Entry<String, Entity>> getIterator() {
		return hash.entrySet().iterator();
	}
}
