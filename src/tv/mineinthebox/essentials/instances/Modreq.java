package tv.mineinthebox.essentials.instances;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class Modreq {
	
	private final FileConfiguration con;
	private int id;
	
	/**
	 * @author xize
	 * @param use the players configuration, and the id of the modreq.
	 * @throws NullPointerException when no modreq node exists in this configuration.
	 */
	public Modreq(FileConfiguration con, int id) {
		if(con.contains("modreqs."+"modreq"+id)) {
			this.con = con;
			this.id = id;	
		} else {
			throw new NullPointerException();
		}
	}
	
	/**
	 * @author xize
	 * @param return the id of the players node;)
	 * @return int
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @author xize
	 * @param returns the repporter of this modreq
	 * @return String
	 */
	public String getPlayersName() {
		return con.getString("user");
	}
	
	/**
	 * @author xize
	 * @param gets the message of this specific modreq
	 * @return String
	 */
	public String getMessage() {
		return con.getString("modreqs."+"modreq"+id+".message");
	}
	
	/**
	 * @author xize
	 * @param returns the location of this particular modreq
	 * @return Location
	 */
	public Location getModreqLocation() {
		Double x = con.getDouble("modreqs."+"modreq"+id+".location.x");
		Double y = con.getDouble("modreqs."+"modreq"+id+".location.y");
		Double z = con.getDouble("modreqs."+"modreq"+id+".location.z");
		int yaw = con.getInt("modreqs."+"modreq"+id+".location.yaw");
		int pitch = con.getInt("modreqs."+"modreq"+id+".location.pitch");
		World w = Bukkit.getWorld(con.getString("modreqs."+"modreq"+id+".location.world"));
		if(w instanceof World) {
			Location loc = new Location(w, x, y, z, yaw, pitch);
			return loc;
		} else {
			throw new NullPointerException("the world you tried to use in this modreq is no longer loaded!");
		}
	}
	
	/**
	 * @author xize
	 * @param get the date returned when this modreq whas sent.
	 * @return Date
	 */
	public Date getDate() {
		Date date = new Date(con.getLong("modreqs."+"modreq"+id+".getDate"));
		return date;
	}

}
