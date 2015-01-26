package tv.mineinthebox.essentials.configurations;

import java.util.List;

import org.bukkit.ChatColor;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.ConfigType;

public class BroadcastConfig {
	
	private final xEssentials pl;
	
	public BroadcastConfig(xEssentials pl) {
		this.pl = pl;
	}
	
	/**
	 * 
	 * @author xize
	 * @param returns boolean whenever our broadcast system is enabled
	 * @return boolean
	 * 
	 */
	public boolean isBroadcastEnabled() {
		Boolean bol = (Boolean) pl.getConfiguration().getConfigValue(ConfigType.BROADCAST, "enable");
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
		String s = ChatColor.translateAlternateColorCodes('&', (String)pl.getConfiguration().getConfigValue(ConfigType.BROADCAST, "prefix"));
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
		String s = ChatColor.translateAlternateColorCodes('&', (String)pl.getConfiguration().getConfigValue(ConfigType.BROADCAST, "suffix"));
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
		List<String> list = (List<String>)pl.getConfiguration().getConfigValue(ConfigType.BROADCAST, "messages");
		return list;
	}

}
