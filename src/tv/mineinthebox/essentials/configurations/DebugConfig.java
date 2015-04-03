package tv.mineinthebox.essentials.configurations;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class DebugConfig extends Configuration {
	
	public DebugConfig(File f, FileConfiguration con) {
		super(f, con);
		preconfig.put("debug-mode", false);
	}
	
	/**
	 * when enabled debug messages in the console are shown, if disabled fall back to normal behavior without debug lines in the console
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isEnabled() {
		return con.getBoolean("debug-mode");
	}

	@Override
	public String getName() {
		return getType().name();
	}

	@Override
	public ConfigType getType() {
		return ConfigType.DEBUG;
	}
	
	@Override
	public boolean hasAlternativeReload() {
		return false;
	}

}
