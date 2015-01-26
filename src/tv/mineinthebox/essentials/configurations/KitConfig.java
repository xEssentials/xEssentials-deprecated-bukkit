package tv.mineinthebox.essentials.configurations;

import java.util.HashMap;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.ConfigType;
import tv.mineinthebox.essentials.instances.Kit;

public class KitConfig {
	
	private final xEssentials pl;
	
	public KitConfig(xEssentials pl) {
		this.pl = pl;
	}
	
	public boolean isCooldownEnabled() {
		return (Boolean)pl.getConfiguration().getConfigValue(ConfigType.KITS, "isCooldown");
	}
	
	public int getCoolDown() {
		return (Integer) pl.getConfiguration().getConfigValue(ConfigType.KITS, "cooldownTime");
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Kit> getConfigKits() {
		return (HashMap<String, Kit>) pl.getConfiguration().getConfigValue(ConfigType.KITS, "kits");
	}

}
