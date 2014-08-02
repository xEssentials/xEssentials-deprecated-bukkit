package tv.mineinthebox.essentials.managers;

import java.io.File;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.xEssentialsOfflinePlayer;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

public class xEssentialsPlayerManager {

	private final HashMap<String, xEssentialsPlayer> players = new HashMap<String, xEssentialsPlayer>();
	private xEssentialsOfflinePlayer[] offliners;
	
	/**
	 * @author xize
	 * @param name - returns the player.
	 * @return xEssentialsPlayer
	 * @throws NullPointerException when the player is not found.
	 */
	public xEssentialsPlayer getPlayer(String name) {
		if(players.containsKey(name.toLowerCase())) {
			return players.get(name.toLowerCase());
		}
		throw new NullPointerException("xEssentialsPlayer: " + name + " is not online!");
	}
	
	/**
	 * @author xize
	 * @param adds the player into our list.
	 * @param name - the players name
	 * @param xp - the xEssentialsPlayer instance.
	 */
	public void addPlayer(String name, xEssentialsPlayer xp) {
		players.put(name.toLowerCase(), xp);
	}
	
	/**
	 * @author xize
	 * @param removes the player
	 * @param player - the players name
	 */
	public void removePlayer(String player) {
		if(players.containsKey(player.toLowerCase())) {
			players.remove(player.toLowerCase());
		}
	}
	
	/**
	 * @author xize
	 * @param gets all the xEssentialsPlayers in a array
	 * @return xEssentialsPlayer[]
	 */
	public xEssentialsPlayer[] getPlayers() {
		xEssentialsPlayer[] users = new xEssentialsPlayer[players.size()];
		int i = 0;
		for(xEssentialsPlayer xp : players.values()) {
			users[i] = xp;
			i++;
		}
		return users;
	}

	/**
	 * @author xize
	 * @param get all the offline players in a array
	 * @return xEssentialsOfflinePlayer[]
	 */
	public xEssentialsOfflinePlayer[] getOfflinePlayers() {
		if(!(offliners instanceof xEssentialsOfflinePlayer[])) {
			try {
				File dir = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "players");
				File[] list = dir.listFiles();
				xEssentialsOfflinePlayer[] offs = new xEssentialsOfflinePlayer[list.length];
				int i = 0;
				for(File f : list) {
					FileConfiguration con = YamlConfiguration.loadConfiguration(f);
					if(con.isSet("user")) {
						xEssentialsOfflinePlayer off = new xEssentialsOfflinePlayer(con.getString("user"));
						offs[i] = off;
						i++;
					}
				}
				offliners = offs;
				return offliners;
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			File dir = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "players");
			File[] list = dir.listFiles();
			if(list.length == offliners.length) {
				//since its equals we don't need to for loop again!
				return offliners;
			} else {
				//since there are new offlineplayers we need to reset our system.
				try {
					xEssentialsOfflinePlayer[] offs = new xEssentialsOfflinePlayer[list.length];
					int i = 0;
					for(File f : list) {
						FileConfiguration con = YamlConfiguration.loadConfiguration(f);
						if(con.isSet("user")) {
							xEssentialsOfflinePlayer off = new xEssentialsOfflinePlayer(con.getString("user"));
							offs[i] = off;
							i++;
						}
					}
					offliners = offs;
					return offliners;
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/**
	 * @author xize
	 * @param player - the name of the player
	 * @return xEssentialsOfflinePlayer
	 */
	public xEssentialsOfflinePlayer getOfflinePlayer(String player) {
		try {
			File dir = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "players");
			if(dir.isDirectory()) {
				File[] list = dir.listFiles();
				for(File f : list) {
					FileConfiguration con = YamlConfiguration.loadConfiguration(f);
					if(con.isSet("user")) {
						if(con.getString("user").equalsIgnoreCase(player)) {
							xEssentialsOfflinePlayer off = new xEssentialsOfflinePlayer(player);
							return off;
						}
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @author xize
	 * @param player - returns the file of the desired name
	 * @return File
	 * @throws NullPointerException when the file is null
	 */
	public File getOfflinePlayerFile(String player) {
		try {
			File dir = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "players");
			if(dir.isDirectory()) {
				File[] list = dir.listFiles();
				for(File f : list) {
					FileConfiguration con = YamlConfiguration.loadConfiguration(f);
					if(con.isSet("user")) {
						if(con.getString("user").equalsIgnoreCase(player)) {
							return f;
						}
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @author xize
	 * @param name - the name of the possible player
	 * @return boolean
	 */
	public boolean isEssentialsPlayer(String name) {
		for(xEssentialsOfflinePlayer off : getOfflinePlayers()) {
			if(off.getUser().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @author xize
	 * @param returns true if the player is online.
	 * @param player - the name
	 * @return Boolean
	 */
	public boolean isOnline(String player) {
		return players.containsKey(player.toLowerCase());
	}
	
	/**
	 * @author xize
	 * @param clears the player list.
	 */
	public void clear() {
		players.clear();
	}
	
	/**
	 * @author xize
	 * @param reloads all user input for onEnable
	 * @return void
	 */
	public void reloadPlayerBase() {
		players.clear();
		for(Player p : xEssentials.getOnlinePlayers()) {
			xEssentialsPlayer xp = new xEssentialsPlayer(p, xEssentials.getManagers().getPlayerManager().getOfflinePlayer(p.getName()).getUniqueId().replaceAll("-", ""));
			players.put(p.getName().toLowerCase(), xp);
		}
	}

}
