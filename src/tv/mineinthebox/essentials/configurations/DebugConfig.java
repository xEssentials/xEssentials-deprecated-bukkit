package tv.mineinthebox.essentials.configurations;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.ConfigType;

public class DebugConfig {
	
	private final xEssentials pl;
	
	public DebugConfig(xEssentials pl) {
		this.pl = pl;
	}
	
	/**
	 * @author xize
	 * @param returns true if debug mode is enabled, else false
	 * @return Boolean
	 */
	public boolean isEnabled() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.DEBUG, "debug");
	}

}
