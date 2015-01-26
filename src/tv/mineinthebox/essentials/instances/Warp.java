package tv.mineinthebox.essentials.instances;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;

public class Warp {
	
	private final FileConfiguration con;
	private final File f;
	private Player p;
	private boolean isWarpOwner = false;
	private final xEssentials pl;
	
	public Warp(FileConfiguration con, File f, xEssentials pl) {
		this.con = con;
		this.f = f;
		this.pl = pl;
	}
	
	public Warp(FileConfiguration con, File f, Player p, xEssentials pl) {
		this.con = con;
		this.f = f;
		this.p = p;
		this.pl = pl;
		if(this.con.getString("warp.uuid").equalsIgnoreCase(p.getUniqueId().toString())) {
			this.isWarpOwner = true;
			if(!p.getName().equalsIgnoreCase(this.con.getString("warp.owner"))) {
				this.con.set("warp.owner", p.getName());
				try {
					this.con.save(this.f);
				} catch (IOException e) {
					e.printStackTrace();
				}
				update();
			}
		}
	}
	
	/**
	 * @author xize
	 * @param returns the warp name
	 * @return String
	 */
	public String getWarpName() {
		return con.getString("warp.name");
	}
	
	/**
	 * @author xize
	 * @param returns the player being included in this object
	 * @return Player
	 */
	public Player getPlayer() {
		return p;
	}
	
	/**
	 * @author xize
	 * @param returns true if the player is a owner else false
	 * @return boolean
	 */
	public boolean isOwner() {
		return isWarpOwner;
	}
	
	/**
	 * @author xize
	 * @param returns the owner name
	 * @return String
	 */
	public String getOwner() {
		return con.getString("warp.owner");
	}
	
	/**
	 * @author xize
	 * @param returns the unique id of the warp owner!
	 * @return String
	 */
	public String getUniqueId() {
		return con.getString("warp.uuid");
	}
	
	/**
	 * @author xize
	 * @param returns the warp location
	 * @return Location
	 * @throws NullPointerException if the world is null
	 */
	public Location getWarpLocation() {
		int x = con.getInt("warp.x");
		int y = con.getInt("warp.y");
		int z = con.getInt("warp.z");
		World world = Bukkit.getWorld(con.getString("warp.world"));
		if(world instanceof World) {
			return new Location(world, x, y, z);
		}
		throw new NullPointerException("world cannot be null on the warp name: " + getWarpName());
	}
	
	/**
	 * @author xize
	 * @param get the xEssentialsOfflinePlayer instance
	 * @return xEssentialsOfflinePlayer
	 */
	public XOfflinePlayer getWarpOwner() {
		return pl.getManagers().getPlayerManager().getOfflinePlayer(con.getString("user"));
	}
	
	public void removeWarp() {
		f.delete();
	}
	
	public void update() {
		try {
			con.load(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

}
