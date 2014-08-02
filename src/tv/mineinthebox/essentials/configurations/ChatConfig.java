package tv.mineinthebox.essentials.configurations;

import org.bukkit.ChatColor;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class ChatConfig {
	
	/**
	 * @author xize
	 * @param returns whenever the chat system is enabled!
	 * @return Boolean
	 */
	public boolean isChatHighLightEnabled() {
		Boolean bol = (Boolean) Configuration.getConfigValue(ConfigType.CHAT, "enable");
		return bol;
	}
	
	/**
	 * @author xize
	 * @param returns whenever smilleys are enabled
	 * @return Boolean
	 */
	public boolean isSmilleysEnabled() {
		Boolean bol = (Boolean) Configuration.getConfigValue(ConfigType.CHAT, "smilleysEnable");
		return bol;
	}
	
	/**
	 * @author xize
	 * @param returns the hashTag
	 * @return String
	 */
	public String getHashTag() {
		String hashTag = ChatColor.translateAlternateColorCodes('&', (String)Configuration.getConfigValue(ConfigType.CHAT, "hashTag"));
		return hashTag;
	}
	
	/**
	 * @author xize
	 * @param returns the boolean whenever advertise is disabled
	 * @return boolean
	 */
	public boolean isAntiAdvertiseEnabled() {
		Boolean bol = (Boolean) Configuration.getConfigValue(ConfigType.CHAT, "antiAddvertiseEnabled");
		return bol;
	}
	
	/**
	 * @author xize
	 * @param when enabled our custom event will trigger and broadcast the lastest news thread!
	 * @return Boolean
	 */
	public boolean isRssBroadcastEnabled() {
		Boolean bol = (Boolean) Configuration.getConfigValue(ConfigType.CHAT, "RssEnabled");
		return bol;
	}
	
	/**
	 * @author xize
	 * @param this will returns the global url list where we try to get our RSS news
	 * @return String
	 */
	public String getRssUrl() {
		return (String)Configuration.getConfigValue(ConfigType.CHAT, "RssUrl");
	}
	
	/**
	 * @author xize
	 * @param when enabled, players get automatic a broadcast depending on the situation of auth servers, session servers and skin servers in minecraft
	 * @return Boolean
	 */
	public boolean isMojangStatusEnabled() {
		return (Boolean)Configuration.getConfigValue(ConfigType.CHAT, "MojangStatus");
	}

}
