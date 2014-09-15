package tv.mineinthebox.bukkit.essentials.configurations;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.enums.ConfigType;

public class DebugConfig {
	
	/**
	 * @author xize
	 * @param returns true if debug mode is enabled, else false
	 * @return Boolean
	 */
	public boolean isEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.DEBUG, "debug");
	}

}
