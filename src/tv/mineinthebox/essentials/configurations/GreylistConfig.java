package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class GreylistConfig implements Configuration {
	
	private final File f;
	private final FileConfiguration con;
	
	public GreylistConfig(File f, FileConfiguration con) {
		this.f = f;
		this.con = con;
	}
	
	/**
	 * returns true if the greylist system is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isEnabled() {
		return con.getBoolean("greylist.enable");
	}
	
	/**
	 * returns the greylist servers port
	 * 
	 * @author xize
	 * @return Integer
	 */
	public int getPort() {
		return con.getInt("greylist.serverport");
	}
	
	/**
	 * returns the group where the player should be placed to
	 * 
	 * @author xize
	 * @return String
	 */
	public String getGroup() {
		return con.getString("greylist.group");
	}

	@Override
	public String getName() {
		return getType().name();
	}

	@Override
	public ConfigType getType() {
		return ConfigType.GREYLIST;
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
			FileConfiguration con = YamlConfiguration.loadConfiguration(f);
			con.set("greylist.enable", false);
			con.set("greylist.serverport", 8001);
			con.set("greylist.group", "citizen");
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
