package tv.mineinthebox.essentials.configurations;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.ConfigType;

public class SignConfig {
	
	private final xEssentials pl;
	
	public SignConfig(xEssentials pl) {
		this.pl = pl;
	}

	public boolean isColorSignEnabled() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.SIGN, "color");
	}
	
	public boolean isFireworkSignEnabled() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.SIGN, "firework");
	}
	
	public boolean isFreeSignEnabled() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.SIGN, "freesign");
	}
	
	public boolean isGetYourHeadSignEnabled() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.SIGN, "headsign");
	}
	
	public boolean isBoomSignEnabled() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.SIGN, "boom");
	}
	
	public boolean isWarpSignEnabled() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.SIGN, "warp");
	}
	
	public boolean isWildSignEnabled() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.SIGN, "wild");
	}
	
	public boolean isDispenserEnabled() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.SIGN, "dispenser");
	}

}
