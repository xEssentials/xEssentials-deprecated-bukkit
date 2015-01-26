package tv.mineinthebox.essentials.managers;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.World;
import org.bukkit.block.Block;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;
import tv.mineinthebox.essentials.enums.ProtectionType;

public class ProtectionDBManager {
	
	private final HashMap<String, Object[]> session = new HashMap<String, Object[]>();

	public ProtectionDBManager() {
		File f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "databases");
		if(!f.isDirectory()) {
			f.mkdir();
		}
		try {
			Class.forName("org.sqlite.JDBC");
			if(isNewDatabase()) {
				xEssentials.log("there whas no protection database found, creating a new one.", LogType.INFO);
				createTables();
			} else {
				xEssentials.log("connected to the protection database!", LogType.INFO);
			}
		} catch (Exception e) {
			xEssentials.log("couldn't find sqlite in craftbukkit, this is probably because you are running a outdated build!", LogType.SEVERE);
		}
	}
	
	public void addSession(String player, ProtectionType type) {
		Object[] obj = new Object[2];
		obj[0] = type;
		session.put(player.toLowerCase(), obj);
	}
	
	public void addSession(String player, String otherplayer, ProtectionType type) {
		Object[] obj = new Object[2];
		obj[0] = type;
		obj[1] = otherplayer;
		session.put(player.toLowerCase(), obj);
	}
	
	public Object[] getSessionData(String player) {
		return session.get(player);
	}
	
	public boolean hasSession(String player) {
		return session.containsKey(player.toLowerCase());
	}
	
	public void removeSession(String player) {
		session.remove(player.toLowerCase());
	}

	/**
	 * @author xize
	 * @param returns true whenever the block protection belongs to the corresponded player, else false
	 * @param p - the player
	 * @param target - the block which the player hits
	 * @return Boolean
	 */
	public boolean isOwner(String name, Block target) {
		try {
			Connection con = getConnection();
			String blockuid = generateBlockUUID(target).toString().replace("-", "");
			String query = "SELECT * FROM blocks WHERE uuid='" + blockuid + "'";
			PreparedStatement state = con.prepareStatement(query);
			ResultSet set = state.executeQuery();
			while(set.next()) {
				if(set.getString("username").equalsIgnoreCase(name)) {
					state.close();
					set.close();		
					con.close();
					return true;
				}
			}
			state.close();
			set.close();
			con.close();
		} catch(Exception e) {
			return false;
		}
		return false;
	}


	/**
	 * @author xize
	 * @param target - block
	 * @return List<String>()
	 */
	public List<String> getOwners(Block target) {
		List<String> list = new ArrayList<String>();
		Connection con = getConnection();
		try {
			String blockuid = generateBlockUUID(target).toString().replace("-", "");
			String query = "SELECT * FROM blocks WHERE uuid='" + blockuid + "'";
			PreparedStatement state = con.prepareStatement(query);
			ResultSet set = state.executeQuery();
			while(set.next()) {
				list.add(set.getString("username"));
			}
			state.close();
			set.close();
			con.close();
		} catch(Exception e) {
			return list;
		}
		return list;
	}

	/**
	 * @author xize
	 * @param player - the players name
	 * @param target - Block
	 * @return Boolean
	 */
	public boolean unregister(String player, Block target) {
		Connection con = getConnection();
		try {
			String blockuid = generateBlockUUID(target).toString().replace("-", "");
			String query = "DELETE FROM blocks WHERE uuid='" + blockuid + "'";
			PreparedStatement state = con.prepareStatement(query);
			state.executeUpdate();
			state.close();
			
			con.close();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @author xize
	 * @param player - the player instance
	 * @param target - the block which get registered
	 * @return Boolean
	 */
	public boolean register(String player, Block target) {
		if(isNewDatabase()) {
			createTables();
		}
		Connection con = getConnection();
		try {
			String blockuid = generateBlockUUID(target).toString().replace("-", "");
			String values = setValueString(new String[] {blockuid, player, Integer.toString(target.getX()), Integer.toString(target.getY()), Integer.toString(target.getZ()), target.getWorld().getName()});
			String query = "INSERT INTO blocks(uuid, username, x, y, z, world) VALUES(" + values + ")";
			PreparedStatement state = con.prepareStatement(query);
			state.executeUpdate();
			state.close();
			
			con.close();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @author xize
	 * @param returns true if the block exist in the database, else false
	 * @param target - block
	 * @return Boolean
	 */
	public boolean isRegistered(Block target) {
		Connection con = getConnection();
		try {
			String blockuid = generateBlockUUID(target).toString().replace("-", "");
			String query = "SELECT * FROM blocks WHERE uuid='" + blockuid + "'";
			PreparedStatement state = con.prepareStatement(query);
			ResultSet set = state.executeQuery();
			if(set.isBeforeFirst()) {
				state.close();
				set.close();
				
				con.close();
				return true;
			}
		}catch (Exception e) {
			return false;
		}
		return false;
	}


	/**
	 * @author xize 
	 * @param creates the tables for the protections
	 */
	private void createTables() {	
		Connection con = getConnection();
		try {
			String table = "CREATE TABLE IF NOT EXISTS `blocks` ("+ 
					"`id_` INTEGER," +
					"`uuid` TEXT NOT NULL, " +
					"`username` text NOT NULL, " +
					"`x` int NOT NULL, " +
					"`y` int NOT NULL, " +
					"`z` int NOT NULL, " +
					"`world` text NOT NULL, " +
					"PRIMARY KEY (`id_`) " +
					")";
			PreparedStatement state = con.prepareStatement(table);
			state.executeUpdate();
			state.close();
			
			con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author xize
	 * @param returns true if the player exist in the database
	 * @param name - the players name
	 * @return Boolean
	 */
	public boolean hasProtectedBlocks(String name) {
		try {
			Connection con = getConnection();
			PreparedStatement state = con.prepareStatement("SELECT * FROM blocks WHERE username='" + name + "'");
			ResultSet set = state.executeQuery();
			if(set.first()) {
				state.close();
				set.close();
				
				con.close();
				return true;
			}
		} catch(Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * @author xize
	 * @param returns true whenever the database is new, else false
	 * @return Boolean
	 */
	private boolean isNewDatabase() {
		File f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "databases" + File.separator + "protection.db");
		return !f.exists();
	}


	/**
	 * @author xize
	 * @param this will return a unique id based on the world and coordinates which cannot be duplicated.
	 * @param block - the block represented in minecraft
	 * @return UUID
	 */
	public UUID generateBlockUUID(Block block) {
		int x = block.getX();
		int y = block.getY();
		int z = block.getZ();
		World w = block.getWorld();
		byte[] bytes = (w.getName()+x+y+z).getBytes();
		UUID uid = UUID.nameUUIDFromBytes(bytes);
		return uid;
	}

	/**
	 * @author xize
	 * @param returns the correct syntax for sql for VALUES() in mysql
	 * @return String
	 */
	private String setValueString(String[] args) {
		StringBuilder build = new StringBuilder();
		for(int i = 0; i < args.length; i++) {
			if(i == (args.length-1)) {
				build.append("'" + args[i] + "'");
			} else {
				build.append("'" + args[i] + "',");
			}
		}
		return build.toString();
	}

	/**
	 * @author xize
	 * @param update all tables with the new corresponded name.
	 * @param oldname - the old players name
	 * @param newname - the new players name
	 */
	public void updatePlayer(String oldname, String newname) {
		Connection con = getConnection();
		try {
			if(hasProtectedBlocks(oldname)) {
				String query = "UPDATE blocks SET username='" + newname + "' WHERE username='" + oldname + "'";
				PreparedStatement state = con.prepareStatement(query);
				state.executeUpdate(query);
				state.close();
				con.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * @author xize
	 * @param returns the clean Connection object, since Connection likely works as a Iterator with only one use per time, this factory method will solve it.
	 * @return Connection
	 */
	public Connection getConnection() {
		try {
			Connection con = DriverManager.getConnection("jdbc:sqlite:plugins/xEssentials/databases/protection.db");
			return con;
		} catch (Exception e) {
			xEssentials.log("couldn't find sqlite in craftbukkit, this is probably because you are running a outdated build!", LogType.SEVERE);
		}
		return null;
	}

}
