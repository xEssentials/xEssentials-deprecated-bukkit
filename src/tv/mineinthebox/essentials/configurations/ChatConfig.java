package tv.mineinthebox.essentials.configurations;

import java.util.List;

import org.bukkit.ChatColor;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.ConfigType;

public class ChatConfig {
	
	private final xEssentials pl;
	
	public ChatConfig(xEssentials pl) {
		this.pl = pl;
	}
	
	/**
	 * returns true if chat highlight is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isChatHighLightEnabled() {
		Boolean bol = (Boolean) pl.getConfiguration().getConfigValue(ConfigType.CHAT, "enable");
		return bol;
	}

	/**
	 * returns true if smilley's are enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isSmilleysEnabled() {
		Boolean bol = (Boolean) pl.getConfiguration().getConfigValue(ConfigType.CHAT, "smilleysEnable");
		return bol;
	}
	
	/**
	 * returns the hashtag defined from the config which will be used to highlight a players name like +someplayer in chat
	 * 
	 * @author xize
	 * @return String
	 */
	public String getHashTag() {
		String hashTag = ChatColor.translateAlternateColorCodes('&', (String)pl.getConfiguration().getConfigValue(ConfigType.CHAT, "hashTag"));
		return hashTag;
	}
	
	/**
	 * returns true if anti addvertise is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isAntiAdvertiseEnabled() {
		Boolean bol = (Boolean) pl.getConfiguration().getConfigValue(ConfigType.CHAT, "antiAddvertiseEnabled");
		return bol;
	}
	
	/**
	 * returns true if rss broadcast is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isRssBroadcastEnabled() {
		Boolean bol = (Boolean) pl.getConfiguration().getConfigValue(ConfigType.CHAT, "RssEnabled");
		return bol;
	}
	
	/**
	 * returns the url formatted in a {@link java.lang.String} from the config
	 * 
	 * @author xize
	 * @return String
	 */
	public String getRssUrl() {
		return (String)pl.getConfiguration().getConfigValue(ConfigType.CHAT, "RssUrl");
	}
	
	/**
	 * returns true whenever the swear filter is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isSwearFilterEnabled() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.CHAT, "swearenable");
	}
	
	/**
	 * returns a list of disallowed swear words
	 * 
	 * @author xize
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String getSwearWords() {
		String badword = "(";
		for(String word : (List<String>)pl.getConfiguration().getConfigValue(ConfigType.CHAT, "swearwords")) {
			badword += word +"|";
		}
		badword += ")";
		return badword;
	}
	
	/**
	 * returns true whenever a warning has been set for swear word usage
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isSwearWarningEnabled() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.CHAT, "swearwarningenable");
	}
	
	/**
	 * 
	 * 
	 * @author xize
	 * @return int
	 */
	public int getMaxWarningLevel() {
		return (Integer) pl.getConfiguration().getConfigValue(ConfigType.CHAT, "swearwarninglevel");
	}
	
	/**
	 * returns the warning command, or also known as punish command
	 * 
	 * @author xize
	 * @return String
	 */
	public String getWarningCommand() {
		return ((String) pl.getConfiguration().getConfigValue(ConfigType.CHAT, "swearwarningpunish")).replace("/", "");
	}
	
	/**
	 * returns the warning message when someone swears
	 * 
	 * @author xize
	 * @return String
	 */
	public String getWarningMessage() {
		return ChatColor.translateAlternateColorCodes('&', (String)pl.getConfiguration().getConfigValue(ConfigType.CHAT, "swearwarningmessage"));
	}

	
}
