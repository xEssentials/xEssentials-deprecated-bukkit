package tv.mineinthebox.essentials.configurations;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class GreylistConfig extends Configuration {
	
	public GreylistConfig(File f, FileConfiguration con) {
		super(f, con);
		preconfig.put("greylist.enable", false);
		preconfig.put("greylist.serverport", 8001);
		preconfig.put("greylist.group", "citizen");
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
	public boolean hasAlternativeReload() {
		return false;
	}

}
