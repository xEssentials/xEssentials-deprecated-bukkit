package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class DebugConfig extends Configuration {
	
	public DebugConfig(File f, FileConfiguration con) {
		super(f, con);
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
			opt.header("when enabled, your server might run a bit slower than expected\nthis is because the logger cause slight lag when printing out debugging info.");
			con.set("debug-mode", false);
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
