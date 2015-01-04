package tv.mineinthebox.essentials.minigames.football;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.minigames.MinigameArena;
import tv.mineinthebox.essentials.minigames.MinigameSession;
import tv.mineinthebox.essentials.minigames.MinigameType;

public class FootballSession implements MinigameSession {

	private final HashMap<String, HashMap<String, Object>> sessiondata = new HashMap<String, HashMap<String, Object>>();
	
	@Override
	public void createSession(String player) {
		HashMap<String, Object> hash = new HashMap<String, Object>();
		sessiondata.put(player, hash);
	}
	
	public void setRedGoalFinished(String player, boolean bol) {
		sessiondata.get(player).put("red-goal-finished", bol);
	}
	
	public boolean isRedGoalFinished(String player) {
		return (sessiondata.get(player).containsKey("red-goal-finished") ? (Boolean)sessiondata.get(player).get("red-goal-finished") : false);
	}
	
	public void setBlueGoalFinished(String player, boolean bol) {
		sessiondata.get(player).put("blue-goal-finished", bol);
	}
	
	public boolean isBlueGoalFinished(String player) {
		return (sessiondata.get(player).containsKey("blue-goal-finished") ? (Boolean)sessiondata.get(player).get("blue-goal-finished") : false);
	}
	
	public void addGoalBlock(String player, Location loc) {
		if(!isRedGoalFinished(player)) {
			if(sessiondata.get(player).containsKey("redteamgoal")) {
				Location[] org = (Location[])sessiondata.get(player).get("redteamgoal");
				Location[] locs = Arrays.copyOf(org, org.length+1);
				locs[locs.length-1] = loc;
				sessiondata.get(player).put("redteamgoal", locs);
			} else {
				sessiondata.get(player).put("redteamgoal", new Location[] {loc});
			}
		} else if(!isBlueGoalFinished(player)) {
			if(sessiondata.get(player).containsKey("blueteamgoal")) {
				Location[] org = (Location[])sessiondata.get(player).get("blueteamgoal");
				Location[] locs = Arrays.copyOf(org, org.length+1);
				locs[locs.length-1] = loc;
				sessiondata.get(player).put("blueteamgoal", locs);
			} else {
				sessiondata.get(player).put("blueteamgoal", new Location[] {loc});
			}
		}
	}

	@Override
	public boolean hasSession(String player) {
		return sessiondata.containsKey(player);
	}

	@Override
	public void removeSession(String player) {
		sessiondata.remove(player);
	}

	@Override
	public boolean containsSessionData(String player, String key) {
		return sessiondata.get(player).containsKey(key);
	}

	@Override
	public void addSessionData(String player, String key, Object obj) {
		sessiondata.get(player).put(key, obj);
	}

	@Override
	public void removeSessionData(String player, String key) {
		sessiondata.get(player).remove(key);
	}

	@Override
	public boolean isSessionComplete(String player) {
		String[] keys = {"name", "redteamspawn", "redteamgoal", "blueteamspawn","blueteamgoal", "slimespawn"};
		for(String key : keys) {
			if(!sessiondata.get(player).containsKey(key)) {
				System.out.print("key: " + key + " was not found inside the map.");
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean saveArena(String player) {
		String name = ((String)sessiondata.get(player).get("name")).toLowerCase();
		Location redteamspawn = (Location) sessiondata.get(player).get("redteamspawn");
		Location[] redteamgoal = (Location[])sessiondata.get(player).get("redteamgoal");
		Location blueteamspawn = (Location) sessiondata.get(player).get("blueteamspawn");
		Location[] blueteamgoal = (Location[])sessiondata.get(player).get("blueteamgoal");
		Location slimeloc = (Location) sessiondata.get(player).get("slimespawn");
		
		try {
			File f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "minigames" + File.separator + name + ".yml");
			if(f.exists()) {
				return false;
			} else {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				
				con.set("game.red.spawn.world", redteamspawn.getWorld().getName());
				con.set("game.red.spawn.x", redteamspawn.getX());
				con.set("game.red.spawn.y", redteamspawn.getY());
				con.set("game.red.spawn.z", redteamspawn.getZ());
				con.set("game.red.spawn.yaw", redteamspawn.getYaw());
				con.set("game.red.spawn.pitch", redteamspawn.getPitch());
				
				String[] serialized_red_locations = new String[redteamgoal.length];
				for(int i = 0; i < serialized_red_locations.length; i++) {
					Location loc = redteamgoal[i];
					String world = loc.getWorld().getName();
					int x = loc.getBlockX();
					int y = loc.getBlockY();
					int z = loc.getBlockZ();
					serialized_red_locations[i] = world+":"+x+":"+y+":"+z;
				}
				
				con.set("game.red.locations", serialized_red_locations);
				
				con.set("game.blue.spawn.world", blueteamspawn.getWorld().getName());
				con.set("game.blue.spawn.x", blueteamspawn.getX());
				con.set("game.blue.spawn.y", blueteamspawn.getY());
				con.set("game.blue.spawn.z", blueteamspawn.getZ());
				con.set("game.blue.spawn.yaw", blueteamspawn.getYaw());
				con.set("game.blue.spawn.pitch", blueteamspawn.getPitch());
				
				String[] serialized_blue_locations = new String[blueteamgoal.length];
				for(int i = 0; i < serialized_blue_locations.length; i++) {
					Location loc = blueteamgoal[i];
					String world = loc.getWorld().getName();
					int x = loc.getBlockX();
					int y = loc.getBlockY();
					int z = loc.getBlockZ();
					serialized_blue_locations[i] = world+":"+x+":"+y+":"+z;
				}
				
				con.set("game.blue.locations", serialized_blue_locations);
				
				con.set("game.ball.world", slimeloc.getWorld().getName());
				con.set("game.ball.x", slimeloc.getBlockX());
				con.set("game.ball.y", slimeloc.getBlockY());
				con.set("game.ball.z", slimeloc.getBlockZ());
				
				con.set("game.maxplayers-allowed", 2);
				
				con.set("reward", 10.0);
				con.set("score", 3);
				con.set("type", MinigameType.FOOT_BALL.name());
				con.save(f);
				MinigameArena arena = new FootballArena(f, con);
				if(xEssentials.getManagers().getMinigameManager().getMinigames().containsKey(MinigameType.FOOT_BALL)) {
					xEssentials.getManagers().getMinigameManager().getMinigames().get(MinigameType.FOOT_BALL).add(arena);	
				} else {
					xEssentials.getManagers().getMinigameManager().getMinigames().put(MinigameType.FOOT_BALL, new ArrayList<MinigameArena>());
					xEssentials.getManagers().getMinigameManager().getMinigames().get(MinigameType.FOOT_BALL).add(arena);
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
