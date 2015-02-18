package tv.mineinthebox.essentials.configurations;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.ConfigType;

public class GreylistConfig {
	
	private final xEssentials pl;
	
	public GreylistConfig(xEssentials pl) {
		this.pl = pl;
	}
	
	/**
	 * returns true if the greylist system is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isEnabled() {
		Boolean bol = (Boolean) pl.getConfiguration().getConfigValue(ConfigType.GREYLIST, "enable");
		return bol;
	}
	
	/**
	 * returns the greylist servers port
	 * 
	 * @author xize
	 * @return Integer
	 */
	public int getPort() {
		int port = (Integer) pl.getConfiguration().getConfigValue(ConfigType.GREYLIST, "port");
		return port;
	}
	
	/**
	 * returns the group where the player should be placed to
	 * 
	 * @author xize
	 * @return String
	 */
	public String getGroup() {
		String group = (String) pl.getConfiguration().getConfigValue(ConfigType.GREYLIST, "group");
		return group;
	}

}
