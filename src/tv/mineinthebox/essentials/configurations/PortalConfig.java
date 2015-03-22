package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.ConfigType;
import tv.mineinthebox.essentials.instances.Portal;

public class PortalConfig extends Configuration {
	
	private HashMap<String, Portal> portals;
	
	public PortalConfig(xEssentials pl, File f, FileConfiguration con) {
		super(pl, f, con);
		preconfig.put("portals.enable", false);
		preconfig.put("portals.cooldown", 30);
	}

	/**
	 * returns true if custom portals are enabled
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isPortalEnabled() {
		return con.getBoolean("portals.enable");
	}

	/**
	 * returns the cooldown time the player should wait before teleporting again
	 * 
	 * @author xize
	 * @return Integer
	 */
	public int getCooldown() {
		return con.getInt("portals.cooldown");
	}

	/**
	 * returns a HashMap whereas the key is the name of the portal and the value its contents
	 * 
	 * @author xize
	 * @return HashMap<String, Portal>
	 */
	public HashMap<String, Portal> getPortals() {
		if(!(portals instanceof HashMap)) {
			portals = new HashMap<String, Portal>();
			File dir = new File(pl.getDataFolder() + File.separator + "portals");
			if(dir.isDirectory()) {
				File[] list = dir.listFiles();
				for(File f : list) {
					FileConfiguration con = YamlConfiguration.loadConfiguration(f);
					Portal portal = new Portal(con, f, pl);
					portals.put(portal.getPortalName(), portal);
				}
			}
		} else {
			File dir = new File(pl.getDataFolder() + File.separator + "portals");
			if(dir.isDirectory()) {
				File[] list = dir.listFiles();
				if(list.length == portals.size()) {
					return portals;
				} else {
					portals.clear();
					for(File f : list) {
						FileConfiguration con = YamlConfiguration.loadConfiguration(f);
						Portal portal = new Portal(con, f, pl);
						portals.put(portal.getPortalName(), portal);
					}
				}
			}
		}
		return portals;
	}

	/**
	 * returns the portal by name
	 * 
	 * @author xize
	 * @param name - the name of the possible portal
	 * @return Portal
	 * @throws NullPointerException when the portal does not exist
	 */
	public Portal getPortal(String name) throws NullPointerException {
		if(getPortals().containsKey(name)) {
			return getPortals().get(name);
		}
		throw new NullPointerException("portal name does not exist!");
	}

	@Override
	public String getName() {
		return getType().name();
	}

	@Override
	public ConfigType getType() {
		return ConfigType.PORTAL;
	}
}
