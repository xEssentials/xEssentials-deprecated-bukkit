package tv.mineinthebox.essentials.configurations;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

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
