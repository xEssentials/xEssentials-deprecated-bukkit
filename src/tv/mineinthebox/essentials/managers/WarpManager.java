package tv.mineinthebox.essentials.managers;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.Warp;


public class WarpManager {
	
	private final xEssentials pl;
	
	public WarpManager(xEssentials pl) {
		this.pl = pl;
	}
	
	/**
	 * @author xize
	 * @param name - the name of the warp
	 * @return boolean
	 */
	public boolean isWarp(String name) {
		File f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "warps" + File.separator + name.toLowerCase() + ".yml");
		return f.exists();
	}
	
	/**
	 * @author xize
	 * @param warpname - the name of the warp
	 * @return Warp
	 */
	public Warp getWarp(String warpname, Player p) {
		if(isWarp(warpname)) {
			File f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "warps" + File.separator + warpname.toLowerCase()+".yml");
			FileConfiguration con = YamlConfiguration.loadConfiguration(f);
			return new Warp(con, f, p, pl);
		}
		throw new NullPointerException("warp does not exist");
	}
	
	public void setWarp(String warpname, Player p, Location loc) {
		try {
			File f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "warps" + File.separator + warpname.toLowerCase() + ".yml");
			FileConfiguration con = YamlConfiguration.loadConfiguration(f);
			con.set("warp.name", warpname);
			con.set("warp.owner", p.getName());
			con.set("warp.uuid", p.getUniqueId().toString());
			con.set("warp.x", loc.getBlockX());
			con.set("warp.y", loc.getBlockY());
			con.set("warp.z", loc.getBlockZ());
			con.set("warp.world", loc.getWorld().getName());
			con.save(f);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Warp[] getWarps() {
		File dir = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "warps");
		File[] files = dir.listFiles();
		Warp[] warps = new Warp[files.length];
		int i = 0;
		for(File f : files) {
			FileConfiguration con = YamlConfiguration.loadConfiguration(f);
			warps[i] = new Warp(con, f, pl);
			i++;
		}
		return warps;
	}
	
	public Warp[] getWarps(Player p) {
		File dir = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "warps");
		File[] files = dir.listFiles();
		int i = 0;
		Warp[] warps = new Warp[i];
		for(File f : files) {
			FileConfiguration con = YamlConfiguration.loadConfiguration(f);
			Warp warp = new Warp(con, f, p, pl);
			if(warp.isOwner()) {
				warps[i] = warp;
			}
			i++;
		}
		return warps;
	}

}
