package tv.mineinthebox.essentials.configurations;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class ChatConfig extends Configuration {

	public ChatConfig(File f, FileConfiguration con) {
		super(f, con);
		preconfig.put("chat.enable.player-highlights", false);
		preconfig.put("chat.enable.smilleys.enable", false);
		preconfig.put("chat.enable.smilleys.useParticles", false);
		preconfig.put("chat.enable.hashtag", "&e@");
		preconfig.put("chat.enable.anti-addvertise", false);
		preconfig.put("chat.format.group.global.prefix", "&2[citizen]&f");
		preconfig.put("chat.format.group.global.suffix", "&7");
		if(!con.contains("chat.format.group")) {
			preconfig.put("chat.format.group.somegroup.prefix", "&2[somegroup]&f");
			preconfig.put("chat.format.group.somegroup.suffix", "&7");
		}
		preconfig.put("swearfilter.enable", false);
		preconfig.put("swearfilter.warning.enable", false);
		preconfig.put("swearfilter.warning.level", 3);
		preconfig.put("swearfilter.warning.punish-command", "/kick %p you shouldn't spam people!");
		preconfig.put("swearfilter.warning.message", "&cplease dont swear, you are now at warning %w");
		preconfig.put("swearfilter.words", new String[] {
				"fuck",
				"suck",
				"shit",
				"ass"
		});
		preconfig.put("rss.use-rss-broadcast", false);
		preconfig.put("rss.use-rss-url", "https://mojang.com/feed/");
	}

	/**
	 * returns true if chat highlight is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isChatHighLightEnabled() {
		return con.getBoolean("chat.enable.player-highlights");
	}

	/**
	 * returns true if smilley's are enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isSmilleysEnabled() {
		return con.getBoolean("chat.enable.smilleys.enable");
	}
	
	/**
	 * returns true if the smilley particles are enabled otherwise false
	 * 
	 * @author xzie
	 * @return boolean
	 */
	public boolean isSmilleyParticlesEnabled() {
		return con.getBoolean("chat.enable.smilleys.useParticles");
	}

	/**
	 * returns the hashtag defined from the config which will be used to highlight a players name like +someplayer in chat
	 * 
	 * @author xize
	 * @return String
	 */
	public String getHashTag() {
		return ChatColor.translateAlternateColorCodes('&', con.getString("chat.enable.hashtag"));
	}

	/**
	 * returns true if anti addvertise is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isAntiAdvertiseEnabled() {
		return con.getBoolean("chat.enable.anti-addvertise");
	}

	/**
	 * returns true if rss broadcast is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isRssBroadcastEnabled() {
		return con.getBoolean("rss.use-rss-broadcast");
	}

	/**
	 * returns the url formatted in a {@link java.lang.String} from the config
	 * 
	 * @author xize
	 * @return String
	 */
	public String getRssUrl() {
		return con.getString("rss.use-rss-url");
	}

	/**
	 * returns true whenever the swear filter is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isSwearFilterEnabled() {
		return con.getBoolean("swearfilter.enable");
	}

	/**
	 * returns a list of disallowed swear words
	 * 
	 * @author xize
	 * @return String
	 */
	public String getSwearWords() {
		String badword = "(";
		for(String word : con.getStringList("swearfilter.words")) {
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
		return con.getBoolean("swearfilter.warning.enable");
	}

	/**
	 * returns the max amount of warnings a player could get before doing something
	 * 
	 * @author xize
	 * @return Integer
	 */
	public int getMaxWarningLevel() {
		return con.getInt("swearfilter.warning.level");
	}

	/**
	 * returns the warning command, or also known as punish command
	 * 
	 * @author xize
	 * @return String
	 */
	public String getWarningCommand() {
		return con.getString("swearfilter.warning.punish-command").replace("/", "");
	}

	/**
	 * returns the warning message when someone swears
	 * 
	 * @author xize
	 * @return String
	 */
	public String getWarningMessage() {
		return ChatColor.translateAlternateColorCodes('&', con.getString("swearfilter.warning.message"));
	}

	/**
	 * returns the default permission less prefix which is default
	 * 
	 * @author xize
	 * @return String
	 */
	public String getGlobalPrefix() {
		return ChatColor.translateAlternateColorCodes('&', con.getString("chat.format.group.global.prefix"));
	}

	/**
	 * returns the default permission less suffix which is default
	 * 
	 * @author xize
	 * @return String
	 */
	public String getGlobalSuffix() {
		return ChatColor.translateAlternateColorCodes('&', con.getString("chat.format.group.global.suffix"));
	}

	/**
	 * returns the prefix for the player based on his permission, if he doesn't have any permission global will be returned
	 * 
	 * @author xize
	 * @param p - the player
	 * @return String
	 */
	public String getPrefixByPlayer(Player p) {
		String prefix = ChatColor.translateAlternateColorCodes('&', con.getString("chat.format.group.global.prefix"));
		for(String group : con.getConfigurationSection("chat.format.group").getKeys(false)) {
			if(!group.equalsIgnoreCase("global")) {
				if(p.hasPermission("xEssentials.chatgroup."+group)) {
					return ChatColor.translateAlternateColorCodes('&', con.getString("chat.format.group."+group+".prefix"));
				}
			}
		}
		return prefix;
	}

	/**
	 * returns the suffix for the player based on his permission, if he doesn't have any permission global will be returned
	 * 
	 * @author xize
	 * @param p - the player
	 * @return String
	 */
	public String getSuffixByPlayer(Player p) {
		String suffix = ChatColor.translateAlternateColorCodes('&', con.getString("chat.format.group.global.suffix"));
		for(String group : con.getConfigurationSection("chat.format.group").getKeys(false)) {
			if(!group.equalsIgnoreCase("global")) {
				if(p.hasPermission("xEssentials.chatgroup."+group)) {
					return ChatColor.translateAlternateColorCodes('&', con.getString("chat.format.group."+group+".suffix"));
				}
			}
		}
		return suffix;
	}

	@Override
	public String getName() {
		return getType().name();
	}

	@Override
	public ConfigType getType() {
		return ConfigType.CHAT;
	}
	
	@Override
	public boolean hasAlternativeReload() {
		return false;
	}
}
