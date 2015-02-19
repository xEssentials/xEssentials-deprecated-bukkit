package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class MotdConfig extends Configuration {

	public MotdConfig(File f, FileConfiguration con) {
		super(f, con);
	}

	/**
	 * returns true if the normal motd system is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isNormalMotdEnabled() {
		return con.getBoolean("motd.normal.enable");
	}

	/**
	 * returns true if the random motd system is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isRandomMotdEnabled() {
		return con.getBoolean("motd.random.enable");
	}

	/**
	 * returns all random motd messages
	 * 
	 * @author xize
	 * @return ListIterator<String>
	 */
	public ListIterator<String> getMotdMessages() {
		return con.getStringList("motd.messages").listIterator();
	}

	/**
	 * returns a singular motd message
	 * 
	 * @author xize
	 * @return String
	 * @see #isNormalMotdEnabled()
	 */
	public String getMotdMessage() {
		return con.getString("motd.message");
	}

	@Override
	public String getName() {
		return getType().name();
	}

	@Override
	public ConfigType getType() {
		return ConfigType.MOTD;
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
			list.add("message 1");
			list.add("message 2");
			con.set("motd.normal.enable", false);
			con.set("motd.random.enable", false);
			con.set("motd.messages", list);
			con.set("motd.message", "default motd for xEssentials");
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
