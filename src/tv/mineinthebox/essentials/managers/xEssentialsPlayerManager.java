package tv.mineinthebox.essentials.managers;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.xEssentialsOfflinePlayer;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class xEssentialsPlayerManager {

	private final HashMap<String, XPlayer> players = new HashMap<String, XPlayer>();
	
	private final xEssentials pl;
	
	public xEssentialsPlayerManager(xEssentials pl) {
		this.pl = pl;
	}

	/**
	 * @author xize
	 * @param name - returns the player.
	 * @return xEssentialsPlayer
	 * @throws NullPointerException when the player is not found.
	 */
	public XPlayer getPlayer(String name) {
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
	public void addPlayer(String name, XPlayer xp) {
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
	public XPlayer[] getPlayers() {
		XPlayer[] users = new XPlayer[players.size()];
		int i = 0;
		for(XPlayer xp : players.values()) {
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
	public XOfflinePlayer[] getOfflinePlayers() {
		File dir = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "players");
		File[] list = dir.listFiles();
		Arrays.sort(list, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				if(o1.lastModified() > o2.lastModified()) {
					return -1;
				} else if(o1.lastModified() < o2.lastModified()) {
					return +1;
				} else {
					return 0;
				}
			}
		});
		int playerSize = (list.length < pl.getConfiguration().getPlayerConfig().getOfflineCache() ? list.length : pl.getConfiguration().getPlayerConfig().getOfflineCache());
		int index = 0;
		XOfflinePlayer[] offliners = new XOfflinePlayer[playerSize];
		for(int i = 0; i < list.length; i++) {
			if(index != playerSize) {
				File f = list[i];
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				if(con.contains("user")) {
					String user = con.getString("user");
					if(!players.containsKey(user)) {
						offliners[index] = getOfflinePlayer(user);
						index++;
					}
				}
			} else {
				break;
			}
		}
		return offliners;
	}

	/**
	 * @author xize
	 * @param player - the name of the player
	 * @return xEssentialsOfflinePlayer
	 */
	public XOfflinePlayer getOfflinePlayer(String player) {
		try {
			if(isOnline(player)) {
				return getPlayer(player);
			} else {
				File dir = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "players");
				if(dir.isDirectory()) {
					File[] list = dir.listFiles();
					for(File f : list) {
						FileConfiguration con = YamlConfiguration.loadConfiguration(f);
						if(con.contains("user")) {
							if(con.getString("user").equalsIgnoreCase(player)) {
								if(players.containsKey(con.getString("user"))) {
									return players.get(con.getString("user"));
								} else {
									xEssentialsOfflinePlayer off = new xEssentialsOfflinePlayer(con.getString("user"), pl);
									return off;	
								}
							}
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
		XOfflinePlayer off = getOfflinePlayer(name);
		if(off instanceof XOfflinePlayer) {
			return true;
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
		for(XPlayer xp : pl.getManagers().getPlayerManager().getPlayers()) {
			xp.save();
		}
		players.clear();
		for(Player p : pl.getOnlinePlayers()) {
			XPlayer xp = new xEssentialsPlayer(p, pl.getManagers().getPlayerManager().getOfflinePlayer(p.getName()).getUniqueId().replaceAll("-", ""), pl);
			players.put(p.getName().toLowerCase(), xp);
		}
	}

}
