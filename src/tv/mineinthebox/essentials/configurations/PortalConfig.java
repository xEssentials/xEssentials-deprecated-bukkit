package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.util.HashMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.ConfigType;
import tv.mineinthebox.essentials.instances.Portal;

public class PortalConfig {
	
	/**
	 * @author xize
	 * @param returns true whenever the portal is enabled
	 * @return Boolean
	 */
	public boolean isPortalEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.PORTAL, "enable");
	}
	
	/**
	 * @author xize
	 * @param returns the cooldown time
	 * @return Integer
	 */
	public int getCooldown() {
		return (Integer) Configuration.getConfigValue(ConfigType.PORTAL, "cooldown");
	}
	
	private HashMap<String, Portal> portals;
	
	/**
	 * @author xize
	 * @param returns a HashMap of Portals
	 * @return HashMap<String, Portal>()
	 */
	public HashMap<String, Portal> getPortals() {
		if(!(portals instanceof HashMap)) {
			portals = new HashMap<String, Portal>();
			File dir = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "portals");
			if(dir.isDirectory()) {
				File[] list = dir.listFiles();
				for(File f : list) {
					FileConfiguration con = YamlConfiguration.loadConfiguration(f);
					Portal portal = new Portal(con, f);
					portals.put(portal.getPortalName(), portal);
				}
			}
		} else {
			File dir = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "portals");
			File[] list = dir.listFiles();
			if(list.length == portals.size()) {
				return portals;
			} else {
				portals.clear();
				for(File f : list) {
					FileConfiguration con = YamlConfiguration.loadConfiguration(f);
					Portal portal = new Portal(con, f);
					portals.put(portal.getPortalName(), portal);
				}
			}
		}
		return portals;
	}
	
	/**
	 * @author xize
	 * @param name - the name of the portal
	 * @return Portal
	 * @throws NullPointerException - when the name does not exist
	 */
	public Portal getPortal(String name) throws Exception {
		if(getPortals().containsKey(name)) {
			return getPortals().get(name);
		}
		throw new NullPointerException("portal name does not exist!");
	}

}
