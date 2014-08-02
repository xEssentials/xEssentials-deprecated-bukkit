package tv.mineinthebox.essentials.arenas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.MinigameType;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

public class Minigame {

	//super interface for minigames

	private HashSet<String> players = new HashSet<String>();

	private final String name;
	private final FileConfiguration con;
	private final File f;
	private int id = 0;

	public Minigame(File f, FileConfiguration con) {
		if(f.exists()) {
			this.f = f;
			this.con = con;
			this.name = this.con.getString("arena.name");
		} else {
			throw new NullPointerException("arena file does not exist!");
		}
	}

	/**
	 * @author xize
	 * @param gets the arena name
	 * @return String
	 */
	public String getArenaName() {
		return name;
	}

	/**
	 * @author xize
	 * @param returns all the spawnpoints where players possible can spawn.
	 * @return Location[]
	 */
	public Location[] getSpawnPoints() {
		List<Location> locs = new ArrayList<Location>();
		for(String s : con.getStringList("arena.spawnpoints")) {
			String[] args = s.split(":");
			World w = Bukkit.getWorld(args[0]);
			int x = Integer.parseInt(args[1]);
			int y = Integer.parseInt(args[2]);
			int z = Integer.parseInt(args[3]);
			float yaw = Float.parseFloat(args[4]);
			float pitch = Float.parseFloat(args[5]);
			locs.add(new Location(w, x, y, z, yaw, pitch));
		}
		return locs.toArray(new Location[locs.size()]);
	}
	
	/**
	 * @author xize
	 * @param add a spawnpoint to the arena
	 * @param loc - the location
	 */
	public void setSpawnPoint(Location loc) {
		String seriallize = loc.getWorld().getName()+":"+loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ()+":"+loc.getYaw()+":"+loc.getPitch();
		List<String> list = new ArrayList<String>(con.getStringList("arena.spawnpoints"));
		list.add(seriallize);
		con.set("arena.spawnpoints", list.toArray());
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		load();
	}
	
	/**
	 * @author xize
	 * @param removes a spawnpoint
	 * @param id - the id number based on the lists order
	 * @throws IndexOutOfBoundsExceptions
	 */
	public void removeSpawnPoint(int id) throws IndexOutOfBoundsException {
		List<String> list = new ArrayList<String>(con.getStringList("arena.spawnpoints"));
		if(list.size() >= id) {
			list.remove(id);
			con.set("arena.spawnpoints", list.toArray());
			try {
				con.save(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			load();	
		} else {
			throw new IndexOutOfBoundsException("you cannot remove a spawnpoint id which is obviously higher than the current spawnpoints.");
		}
	}

	/**
	 * @author xize
	 * @param returns the max players allowed
	 * @return Integer
	 */
	public int getMaxPlayersAllowed() {
		return getSpawnPoints().length;
	}

	/**
	 * @author xize
	 * @param returns the correct spawnpoint based on the joined id.
	 * @param p - the player
	 * @return Location
	 */
	public Location getPlayerSpawnPoint(Player p) {
		for(String s : players) {
			if(s.equalsIgnoreCase(p.getName())) {
				return getSpawnPoints()[id];
			}
			id++;
		}
		throw new NullPointerException("failed to retrieve spawnpoint location player"+ p.getName() +" doesn't exist in the arena!");
	}

	/**
	 * @author xize
	 * @param returns true if the game is already started
	 * @return boolean
	 */
	public boolean isRunning() {
		if(con.contains("arena.isrunning")) {
			return con.getBoolean("arena.isrunning");
		}
		return false;
	}

	/**
	 * @author xize
	 * @param runs the minigame arena
	 * @param bol - the boolean if true the arena is running else false.
	 */
	public void setRunning(boolean bol) {
		con.set("arena.isrunning", bol);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		load();
	}

	/**
	 * @author xize
	 * @param alllows the player to join this game!
	 * @param xp - the essentials player
	 * @return boolean
	 */
	public boolean addPlayer(xEssentialsPlayer xp) {
		if(players.size() < getMaxPlayersAllowed() || isRunning()) {
			players.add(xp.getUser());
			return true;
		}
		return false;
	}

	/**
	 * @author xize
	 * @param removes the player from the arena.
	 */
	public void removePlayer(String name) {
		if(players.contains(name)) {
			players.remove(name);
		}
	}

	/**
	 * @author xize
	 * @param returns the amount of players currently inside the arena
	 * @return Integer
	 */
	public int getJoinedCount() {
		return players.size();
	}

	/**
	 * @author xize
	 * @param name - the players name
	 * @return boolean
	 */
	public boolean isPlayerJoined(String name) {
		return players.contains(name);
	}

	/**
	 * @author xize
	 * @param returns the minigame type.
	 * @return
	 */
	public MinigameType getType() {
		return MinigameType.valueOf(con.getString("type"));
	}

	/**
	 * @author xize
	 * @param returns true if the arena is active, if there aren't spawnpoints defined return false.
	 * @return Boolean
	 */
	public boolean isActive() {
		try {
			if(getSpawnPoints()[1] instanceof Location) {
				return true;
			}
		} catch(IndexOutOfBoundsException e) {
			return false;	
		}
		return false;
	}
	
	/**
	 * @author xize
	 * @param removes a arena
	 */
	public void remove() {
		players.clear();
		Configuration.getMinigameMap().get(getType()).remove(getArenaName());
		f.delete();
	}
	
	public FileConfiguration getValue() {
		return con;
	}
	
	public void writeValue(String key, Object obj) {
		con.set(key, obj);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		load();
	}

	private void load() {
		try {
			con.load(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
