package tv.mineinthebox.essentials.configurations;

import org.bukkit.ChatColor;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class ProtectionConfig {
	
	/**
	 * @author xize
	 * @param returns true whenever the global protection is enabled
	 * @return Boolean
	 */
	public boolean isProtectionEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.PROTECTION, "enable");
	}
	
	public boolean isChestProtectionEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.PROTECTION, "chestEnable");
	}
	
	/**
	 * @author xize
	 * @param returns true whenever the sign protection is set to default
	 * @return Boolean
	 */
	public boolean isSignProtectionEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.PROTECTION, "signEnable");
	}
	
	/**
	 * @author xize
	 * @param returns true whenever the default furnace protection is enabled
	 * @return Boolean
	 */
	public boolean isFurnaceProtectionEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.PROTECTION, "furnaceEnable");
	}
	
	/**
	 * @author xize
	 * @param returns true whenever the default jukebox protection is enabled
	 * @return Boolean
	 */
	public boolean isJukeboxProtectionEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.PROTECTION, "jukeboxEnable");
	}
	
	/**
	 * @author xize
	 * @param returns true whenever the default dispenser protection is enabled
	 * @return Boolean
	 */
	public boolean isDispenserEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.PROTECTION, "dispenserEnable");
	}
	/**
	 * @author xize
	 * @param returns the disallow message
	 * @return String
	 */
	public String getDisallowMessage() {
		return ChatColor.translateAlternateColorCodes('&', ((String) Configuration.getConfigValue(ConfigType.PROTECTION, "messageDisallow")));
	}

}
