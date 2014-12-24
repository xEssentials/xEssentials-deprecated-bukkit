package tv.mineinthebox.essentials.configurations;

import java.util.List;

import org.bukkit.ChatColor;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class BroadcastConfig {
	
	/**
	 * 
	 * @author xize
	 * @param returns boolean whenever our broadcast system is enabled
	 * @return boolean
	 * 
	 */
	public boolean isBroadcastEnabled() {
		Boolean bol = (Boolean) Configuration.getConfigValue(ConfigType.BROADCAST, "enable");
		return bol;
	}
	
	/**
	 * 
	 * @author xize
	 * @param returns the prefix for the broadcast
	 * @return String
	 * 
	 */
	public String getPrefix() {
		String s = ChatColor.translateAlternateColorCodes('&', (String)Configuration.getConfigValue(ConfigType.BROADCAST, "prefix"));
		return s;
	}
	
	/**
	 * 
	 * @author xize
	 * @param returns the suffix for the broadcast
	 * @return String
	 * 
	 */
	public String getSuffix() {
		String s = ChatColor.translateAlternateColorCodes('&', (String)Configuration.getConfigValue(ConfigType.BROADCAST, "suffix"));
		return s;
	}
	
	/**
	 * 
	 * @author xize
	 * @param gets the list containing all broadcast messages
	 * @return List<String>
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<String> getMessages() {
		List<String> list = (List<String>)Configuration.getConfigValue(ConfigType.BROADCAST, "messages");
		return list;
	}

}
