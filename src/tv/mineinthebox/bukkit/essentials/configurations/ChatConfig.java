package tv.mineinthebox.bukkit.essentials.configurations;

import java.util.List;

import org.bukkit.ChatColor;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.enums.ConfigType;

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
	
	/**
	 * @author xize
	 * @param returns true if the anti swear system is enabled
	 * @return boolean
	 */
	public boolean isSwearFilterEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.CHAT, "swearenable");
	}
	
	/**
	 * @author xize
	 * @param returns the list of swear words as lowercase
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String getSwearWords() {
		String badword = "(";
		for(String word : (List<String>)Configuration.getConfigValue(ConfigType.CHAT, "swearwords")) {
			badword += word +"|";
		}
		badword += ")";
		return badword;
	}
	
	/**
	 * @author xize
	 * @param returns the warning mode of the censor system
	 * @return boolean
	 */
	public boolean isSwearWarningEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.CHAT, "swearwarningenable");
	}
	
	/**
	 * @author xize
	 * @param returns the max level of warnings a player could get before getting punished.
	 * @return int
	 */
	public int getMaxWarningLevel() {
		return (Integer) Configuration.getConfigValue(ConfigType.CHAT, "swearwarninglevel");
	}
	
	/**
	 * @author xize
	 * @param returns the punish command, when the user is getting over the warning limit
	 * @return String
	 */
	public String getWarningCommand() {
		return ((String) Configuration.getConfigValue(ConfigType.CHAT, "swearwarningpunish")).replace("/", "");
	}
	
	/**
	 * @author xize
	 * @param returns the warning message
	 * @return String
	 */
	public String getWarningMessage() {
		return ChatColor.translateAlternateColorCodes('&', (String)Configuration.getConfigValue(ConfigType.CHAT, "swearwarningmessage"));
	}

	
}
