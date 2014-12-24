package tv.mineinthebox.essentials.configurations;

import java.util.List;

import org.bukkit.ChatColor;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class RulesConfig {

	/**
	 * @author xize
	 * @param returns the prefix
	 * @return String
	 */
	public String getPrefix() {
		String s = ChatColor.translateAlternateColorCodes('&', (String) Configuration.getConfigValue(ConfigType.RULES, "prefix"));
		return s;
	}
	
	/**
	 * @author xize
	 * @param returns the suffix
	 * @return String
	 */
	public String getSuffix() {
		String s = ChatColor.translateAlternateColorCodes('&', (String) Configuration.getConfigValue(ConfigType.RULES, "suffix"));
		return s;
	}
	
	/**
	 * @author xize
	 * @param returns all rules
	 * @return List<String>()
	 */
	@SuppressWarnings("unchecked")
	public List<String> getRules() {
		List<String> list = (List<String>)Configuration.getConfigValue(ConfigType.RULES, "rules");
		return list;
	}
}
