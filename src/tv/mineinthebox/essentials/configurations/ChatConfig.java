package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class ChatConfig extends Configuration {
	
	public ChatConfig(File f, FileConfiguration con) {
		super(f, con);
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
		return con.getBoolean("chat.enable.smilleys");
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
	public boolean isGenerated() {
		return f.exists();
	}
	
	@Override
	public boolean isGeneratedOnce() {
		return true;
	}

	@Override
	public void generateConfig() {
		if(!isGenerated()) {

			FileConfigurationOptions opt = con.options();
			opt.header("when defining a new chat format group, each group has its own permission\nif we would create one called somegroup the permission would be xEssentials.chatgroup.somegroup\nif the player has not any permission he gets the global prefix and suffix\nnote that if the player has * permissions his prefix and suffix should be straight under the global prefix and suffix as it works hierarchical");
			con.set("chat.enable.player-highlights", false);
			con.set("chat.enable.smilleys", false);
			con.set("chat.enable.hashtag", "&e@");
			con.set("chat.enable.anti-addvertise", false);
			con.set("chat.format.group.global.prefix", "&2[citizen]&f");
			con.set("chat.format.group.global.suffix", "&7");
			con.set("chat.format.group.somegroup.prefix", "&2[somegroup]&f");
			con.set("chat.format.group.somegroup.suffix", "&7");
			con.set("swearfilter.enable", false);
			con.set("swearfilter.warning.enable", false);
			con.set("swearfilter.warning.level", 3);
			con.set("swearfilter.warning.punish-command", "/kick %p you shouldn't spam people!");
			con.set("swearfilter.warning.message", "&cplease dont swear, you are now at warning %w");
			con.set("swearfilter.words", new String[] {
					"fuck",
					"suck",
					"shit",
					"ass"
			});
			con.set("rss.use-rss-broadcast", false);
			con.set("rss.use-rss-url", "https://mojang.com/feed/");
			try {
				con.save(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void reload() {
		try {
			con.load(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	
}
