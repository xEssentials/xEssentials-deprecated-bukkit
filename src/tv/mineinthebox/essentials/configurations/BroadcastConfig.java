package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class BroadcastConfig extends Configuration {
	
	public BroadcastConfig(File f, FileConfiguration con) {
		super(f, con);
		ArrayList<String> list = new ArrayList<String>();
		list.add("&ebroadcast 1");
		list.add("&ebroadcast 2");
		list.add("&ebroadcast 3");
		preconfig.put("broadcast.enable", false);
		preconfig.put("broadcast.prefix", "&e[broadcast]");
		preconfig.put("broadcast.suffix", "&2");
		preconfig.put("broadcast.messages", list);
	}
	
	/**
	 * returns true if broadcast system is enabeld otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isBroadcastEnabled() {
		return con.getBoolean("broadcast.enable");
	}
	
	/**
	 * returns the prefix being given in the broadcast config
	 * 
	 * @author xize
	 * @return String
	 */
	public String getPrefix() {
		return ChatColor.translateAlternateColorCodes('&', con.getString("broadcast.prefix"));
	}
	
	/**
	 * returns the suffix being given in the broadcast config
	 * 
	 * @author xize
	 * @return String
	 */
	public String getSuffix() {
		String s = ChatColor.translateAlternateColorCodes('&', con.getString("broadcast.suffix"));
		return s;
	}
	
	/**
	 * returns all broadcast messages
	 * 
	 * @author xize
	 * @return List<String>
	 */
	public List<String> getMessages() {
		return con.getStringList("broadcast.messages");
	}

	@Override
	public String getName() {
		return getType().name();
	}

	@Override
	public ConfigType getType() {
		return ConfigType.BROADCAST;
	}

}
