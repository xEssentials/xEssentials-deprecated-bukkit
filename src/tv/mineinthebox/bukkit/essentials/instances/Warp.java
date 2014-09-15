package tv.mineinthebox.bukkit.essentials.instances;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.bukkit.essentials.xEssentials;

public class Warp {
	
	private FileConfiguration con;
	private String ownername;
	private String warpname;
	
	public Warp(FileConfiguration con, String ownername, String warpname) {
		this.con = con;
		this.ownername = ownername;
		this.warpname = warpname;
	}
	
	/**
	 * @author xize
	 * @param returns the warp name
	 * @return String
	 */
	public String getWarpName() {
		return warpname;
	}
	
	/**
	 * @author xize
	 * @param returns the owner name
	 * @return String
	 */
	public String getOwner() {
		return ownername;
	}
	
	/**
	 * @author xize
	 * @param returns the warp location
	 * @return Location
	 * @throws NullPointerException if the world is null
	 */
	public Location getWarpLocation() {
		int x = con.getInt("warp."+warpname+".x");
		int y = con.getInt("warp."+warpname+".y");
		int z = con.getInt("warp."+warpname+".z");
		World world = Bukkit.getWorld(con.getString("warp."+warpname+".world"));
		if(world instanceof World) {
			return new Location(world, x, y, z);
		}
		throw new NullPointerException("world cannot be null on the warp name: " + warpname);
	}
	
	/**
	 * @author xize
	 * @param get the xEssentialsOfflinePlayer instance
	 * @return xEssentialsOfflinePlayer
	 */
	public xEssentialsOfflinePlayer getEssentialsOfflinePlayer() {
		return xEssentials.getManagers().getPlayerManager().getOfflinePlayer(con.getString("user"));
	}

}
