package tv.mineinthebox.bukkit.essentials.configurations;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.enums.ConfigType;

public class SignConfig {

	public boolean isColorSignEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.SIGN, "color");
	}
	
	public boolean isFireworkSignEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.SIGN, "firework");
	}
	
	public boolean isFreeSignEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.SIGN, "freesign");
	}
	
	public boolean isGetYourHeadSignEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.SIGN, "headsign");
	}
	
	public boolean isBoomSignEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.SIGN, "boom");
	}
	
	public boolean isWarpSignEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.SIGN, "warp");
	}
	
	public boolean isWildSignEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.SIGN, "wild");
	}

}
