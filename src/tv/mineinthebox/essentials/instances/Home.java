package tv.mineinthebox.essentials.instances;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class Home {
	
	private final FileConfiguration con;
	private final String HomeId;
	
	/**
	 * @author xize
	 * @param FileConfiguration or YamlConfiguration (needs to be the yml file of the player)
	 * @param String (the id name of the home)
	 * @return a home interface
	 */
	
	public Home(FileConfiguration con, String HomeId) {
		this.con = con;
		this.HomeId = HomeId;
	}
	
	/**
	 * 
	 * @author xize
	 * @param get the home of the owner
	 * @return String
	 * 
	 */
	public String getOwner() {
		return con.getString("user");
	}
	
	/**
	 * 
	 * @author xize
	 * @param get the x coordinate
	 * @return Double
	 * 
	 */
	public Double getX() {
		return con.getDouble("homes."+HomeId+".x");
	}
	
	/**
	 * 
	 * @author xize
	 * @param get the y coordinate
	 * @return Double
	 * 
	 */
	public Double getY() {
		return con.getDouble("homes."+HomeId+".y");
	}
	
	/**
	 * 
	 * @author xize
	 * @param get the z coordinate
	 * @return Double
	 * 
	 */
	public Double getZ() {
		return con.getDouble("homes."+HomeId+".z");
	}
	
	/**
	 * 
	 * @author xize
	 * @param get the yaw
	 * @return Integer
	 * 
	 */
	public int getYaw() {
		return con.getInt("homes."+HomeId+".yaw");
	}
	
	/**
	 * 
	 * @author xize
	 * @param get the pitch
	 * @return Integer
	 * 
	 */
	public int getPitch() {
		return con.getInt("homes."+HomeId+".pitch");
	}
	
	/**
	 * 
	 * @author xize
	 * @param get the World of this home
	 * @return World
	 * 
	 */
	public World getWorld() {
		return Bukkit.getWorld(con.getString("homes."+HomeId+".world"));
	}
	
	/**
	 * 
	 * @author xize
	 * @param get the full location object
	 * @return Location
	 */
	public Location getLocation() {
		return new Location(getWorld(), getX(), getY(), getZ(), getYaw(), getPitch());
	}
	
	/**
	 * @author xize
	 * @param shows home name!
	 * @return String
	 */
	public String getHomeName() {
		return HomeId;
	}

}
