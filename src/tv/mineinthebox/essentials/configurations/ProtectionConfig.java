package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class ProtectionConfig implements Configuration {

	private final File f;
	private final FileConfiguration con;

	public ProtectionConfig(File f, FileConfiguration con) {
		this.f = f;
		this.con = con;
	}

	/**
	 * returns true if the protection is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isProtectionEnabled() {
		return con.getBoolean("protection.enable");
	}

	/**
	 * returns true if chest protection is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isChestProtectionEnabled() {
		return con.getBoolean("protection.protect.chests");
	}

	/**
	 * returns true if sign protection is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isSignProtectionEnabled() {
		return con.getBoolean("protection.protect.signs");
	}

	/**
	 * returns true if furnace protection is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isFurnaceProtectionEnabled() {
		return con.getBoolean("protection.protect.furnace");
	}

	/**
	 * returns true if jukebox protection is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isJukeboxProtectionEnabled() {
		return con.getBoolean("protection.protect.jukebox");
	}

	/**
	 * returns true if dispenser protection is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isDispenserEnabled() {
		return con.getBoolean("protection.protect.dispenser");
	}

	/**
	 * returns the formatted disallow message
	 * 
	 * @author xize
	 * @return String
	 */
	public String getDisallowMessage() {
		return ChatColor.translateAlternateColorCodes('&', con.getString("protection.message.disallow"));
	}

	@Override
	public String getName() {
		return getType().name();
	}

	@Override
	public ConfigType getType() {
		return ConfigType.PROTECTION;
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
			con.set("protection.enable", true);
			con.set("protection.protect.signs", true);
			con.set("protection.protect.chests", true);
			con.set("protection.protect.furnace", true);
			con.set("protection.protect.jukebox", true);
			con.set("protection.protect.dispenser", true);
			con.set("protection.message.disallow", "&cthis %BLOCK% has been protected by a spell");
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
