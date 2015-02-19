package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

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
		return con.getBoolean("chat.enable.playerHighlights");
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
		return con.getBoolean("chat.enable.antiAddvertise");
	}
	
	/**
	 * returns true if rss broadcast is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isRssBroadcastEnabled() {
		return con.getBoolean("rss.useRssBroadcast");
	}
	
	/**
	 * returns the url formatted in a {@link java.lang.String} from the config
	 * 
	 * @author xize
	 * @return String
	 */
	public String getRssUrl() {
		return con.getString("rss.useRssUrl");
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
			con.set("chat.enable.playerHighlights", false);
			con.set("chat.enable.smilleys", false);
			con.set("chat.enable.hashtag", "&e@");
			con.set("chat.enable.antiAddvertise", false);
			con.set("swearfilter.enable", false);
			con.set("swearfilter.words", new String[] {
					"fuck",
					"suck",
					"shit",
					"ass"
			});
			con.set("swearfilter.warning.enable", false);
			con.set("swearfilter.warning.level", 3);
			con.set("swearfilter.warning.punish-command", "/kick %p you shouldn't spam people!");
			con.set("swearfilter.warning.message", "&cplease dont swear, you are now at warning %w");
			con.set("rss.useRssBroadcast", false);
			con.set("rss.useRssUrl", "https://mojang.com/feed/");
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
