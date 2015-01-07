package tv.mineinthebox.essentials.minigames.parkour;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.hook.Hooks;
import tv.mineinthebox.essentials.interfaces.XPlayer;
import tv.mineinthebox.essentials.minigames.MinigameArena;
import tv.mineinthebox.essentials.minigames.MinigameType;

public class ParkourArena implements MinigameArena {

	private final HashMap<XPlayer, Location> players = new HashMap<XPlayer, Location>();
	
	private final File f;
	private final FileConfiguration con;
	private Trail[] trails;
	private Location[] spawnpoints;
	private Location finish;
	
	public ParkourArena(File f, FileConfiguration con) {
		this.f = f;
		this.con = con;
		this.trails = new Trail[0];
		ConfigurationSection sec = null;
		int id = 0;
		while((sec = con.getConfigurationSection("game.trails."+id)) != null) {
			this.trails[id] = new Trail(sec);
			this.trails[id].startTrail();
			id++;
		}
		this.spawnpoints = new Location[con.getStringList("game.spawnpoints").size()];
		int num = 0;
		for(String s : con.getStringList("game.spawnpoints")) {
			String[] split = s.split(":");
			World w = Bukkit.getWorld(split[0]);
			int x = Integer.parseInt(split[1]);
			int y = Integer.parseInt(split[2]);
			int z = Integer.parseInt(split[3]);
			this.spawnpoints[num] = new Location(w, x, y, z);
			num++;
		}
		
		World w = Bukkit.getWorld(con.getString("game.finish.world"));
		int x = con.getInt("game.finish.x");
		int y = con.getInt("game.finish.y");
		int z = con.getInt("game.finish.z");
		this.finish = new Location(w, x, y, z);
	}
	
	public Location[] getSpawnPoints() {
		return spawnpoints;
	}
	
	public boolean isSpawnPoint(Location loc) {
		for(Location s : getSpawnPoints()) {
			if(s.equals(loc)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isFinishPoint(Location loc) {
		if(loc.equals(finish)) {
			return true;
		}
		return false;
	}
	
	public void setSpawnPoint(XPlayer xp, Location loc) {
		players.put(xp, loc);
	}
	
	public Location getLastSpawnPoint(XPlayer xp) {
		return players.get(xp);
	}
	
	public Trail[] getTrails() {
		return trails;
	}
	
	@Override
	public MinigameType getType() {
		return MinigameType.PARKOUR;
	}

	@Override
	public String getName() {
		return f.getName().replace(".yml", "");
	}

	@Override
	public void remove() {
		xEssentials.getManagers().getMinigameManager().removeMinigame(getType(), this);
		f.delete();
	}

	@Override
	public void addPlayer(XPlayer xp) {
		xp.saveInventory();
		xp.getPlayer().getInventory().setArmorContents(null);
		xp.getPlayer().getInventory().clear();
		xp.getPlayer().teleport(getSpawnPoints()[0]);
		players.put(xp, getSpawnPoints()[0]);
	}

	@Override
	public void removePlayer(XPlayer xp) {
		players.remove(xp);
		xp.loadInventory();
		xp.getPlayer().chat("/spawn");
	}

	@Override
	public boolean isFull() {
		return false;
	}

	@Override
	public void reset() {
		Iterator<XPlayer> it = players.keySet().iterator();
		while(it.hasNext()) {
			removePlayer(it.next());
		}
	}

	@Override
	public void broadcastMessage(String message) {
		for(XPlayer xp : players.keySet()) {
			xp.getPlayer().sendMessage(message);
		}
	}

	@Override
	public void sentReward(XPlayer xp) {
		if(Hooks.isVaultEnabled()) {
			xEssentials.getManagers().getVaultManager().desposit(xp.getPlayer(), getReward());
		}
	}

	@Override
	public double getReward() {
		return con.getDouble("reward");
	}

}
