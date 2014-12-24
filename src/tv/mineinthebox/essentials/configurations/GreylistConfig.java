package tv.mineinthebox.essentials.configurations;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.ConfigType;

public class GreylistConfig {
	
	/**
	 * @author xize
	 * @param returns true whenever its enabled
	 * @return boolean
	 */
	public boolean isEnabled() {
		Boolean bol = (Boolean) Configuration.getConfigValue(ConfigType.GREYLIST, "enable");
		return bol;
	}
	
	/**
	 * @author xize
	 * @param change the internal system for greylists.
	 * @param boolean
	 */
	public void setEnabled(boolean bol) {
		try {
			File f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "greylist.yml");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				con.set("greylist.enable", bol);
				con.save(f);
				Configuration.reloadConfiguration();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author xize
	 * @param returns the greylist servers port
	 * @return int
	 */
	public int getPort() {
		int port = (Integer) Configuration.getConfigValue(ConfigType.GREYLIST, "port");
		return port;
	}
	
	/**
	 * @author xize
	 * @param returns the group where the player gets set.
	 * @return String
	 */
	public String getGroup() {
		String group = (String) Configuration.getConfigValue(ConfigType.GREYLIST, "group");
		return group;
	}

}
