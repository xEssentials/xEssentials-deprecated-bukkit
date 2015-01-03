package tv.mineinthebox.essentials.minigames.tennis;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.minigames.MinigameArena;
import tv.mineinthebox.essentials.minigames.MinigameSession;
import tv.mineinthebox.essentials.minigames.MinigameType;

public class TennisSessions implements MinigameSession {
	
	private final HashMap<String, HashMap<String, Object>> hash = new HashMap<String, HashMap<String, Object>>();
	
	public void createSession(String player) {
		hash.put(player, new HashMap<String, Object>());
	}
	
	public boolean hasSession(String player) {
		if(hash.containsKey(player)) {
			return true;
		}
		return false;
	}
	
	public void removeSession(String player) {
		hash.remove(player);
	}
	
	public boolean containsSessionData(String player, String key) {
		return hash.get(player).containsKey(key);
	}
	
	public void addSessionData(String player, String key, Object obj) {
		hash.get(player).put(key, obj);
	}
	
	public void removeSessionData(String player, String key) {
		hash.get(player).remove(key);
	}
	
	public boolean isSessionComplete(String player) {
		String[] keys = {"name", "spawn1", "spawn2", "chickenloc", "score", "boundsx", "boundsz"};
		for(String key : keys) {
			if(!hash.get(player).containsKey(key)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean saveArena(String player) {
		String name = ((String)hash.get(player).get("name")).toLowerCase();
		Location spawn1 = (Location)hash.get(player).get("spawn1");
		Location spawn2 = (Location)hash.get(player).get("spawn2");
		Location chickenloc = (Location) hash.get(player).get("chickenloc");
		int score = (Integer) hash.get(player).get("score");
		int boundsx = (Integer) hash.get(player).get("boundsx");
		int boundsz = (Integer) hash.get(player).get("boundsz");
		
		try {
			File f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "minigames" + File.separator + name + ".yml");
			if(f.exists()) {
				return false;
			} else {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				con.set("game.spawnpoint1.world", spawn1.getWorld().getName());
				con.set("game.spawnpoint1.x", spawn1.getBlockX());
				con.set("game.spawnpoint1.y", spawn1.getBlockY());
				con.set("game.spawnpoint1.z", spawn1.getBlockZ());
				con.set("game.spawnpoint1.yaw", spawn1.getYaw());
				con.set("game.spawnpoint1.pitch", spawn1.getPitch());
				
				con.set("game.spawnpoint2.world", spawn2.getWorld().getName());
				con.set("game.spawnpoint2.x", spawn2.getBlockX());
				con.set("game.spawnpoint2.y", spawn2.getBlockY());
				con.set("game.spawnpoint2.z", spawn2.getBlockZ());
				con.set("game.spawnpoint2.yaw", spawn2.getYaw());
				con.set("game.spawnpoint2.pitch", spawn2.getPitch());
				
				con.set("game.chicken.world", chickenloc.getWorld().getName());
				con.set("game.chicken.x", chickenloc.getBlockX());
				con.set("game.chicken.y", chickenloc.getBlockY());
				con.set("game.chicken.z", chickenloc.getBlockZ());
				
				con.set("bounds.x", boundsx);
				con.set("bounds.z", boundsz);
				
				con.set("reward", 10.0);
				con.set("score", score);
				con.set("type", MinigameType.TENNIS.name());
				con.save(f);
				MinigameArena arena = new TennisArena(f, con);
				if(xEssentials.getManagers().getMinigameManager().getMinigames().containsKey(MinigameType.TENNIS)) {
					xEssentials.getManagers().getMinigameManager().getMinigames().get(MinigameType.TENNIS).add(arena);	
				} else {
					xEssentials.getManagers().getMinigameManager().getMinigames().put(MinigameType.TENNIS, new ArrayList<MinigameArena>());
					xEssentials.getManagers().getMinigameManager().getMinigames().get(MinigameType.TENNIS).add(arena);
				}
				removeSession(player);
				return true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	

}
