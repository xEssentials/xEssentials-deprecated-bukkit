package tv.mineinthebox.essentials.configurations;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class ProtectionConfig extends Configuration {

	public ProtectionConfig(File f, FileConfiguration con) {
		super(f, con);
		preconfig.put("protection.enable", false);
		preconfig.put("protection.protect.signs", false);
		preconfig.put("protection.protect.chests", false);
		preconfig.put("protection.protect.furnace", false);
		preconfig.put("protection.protect.jukebox", false);
		preconfig.put("protection.protect.dispenser", false);
		preconfig.put("protection.message.disallow", "&cthis %BLOCK% has been protected by a spell");
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
	public boolean hasAlternativeReload() {
		return false;
	}
}
