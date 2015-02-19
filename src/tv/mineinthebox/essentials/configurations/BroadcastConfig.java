package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class BroadcastConfig extends Configuration {
	
	public BroadcastConfig(File f, FileConfiguration con) {
		super(f, con);
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
		return con.getString("broadcast.prefix");
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
			ArrayList<String> list = new ArrayList<String>();
			list.add("&ebroadcast 1");
			list.add("&ebroadcast 2");
			list.add("&ebroadcast 3");
			con.set("broadcast.enable", false);
			con.set("broadcast.prefix", "&e[broadcast]");
			con.set("broadcast.suffix", "&2");
			con.set("broadcast.messages", list);
			try {
				con.save(f);
			} catch (IOException e) {
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
