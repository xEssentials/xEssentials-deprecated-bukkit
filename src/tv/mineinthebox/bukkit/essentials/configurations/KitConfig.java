package tv.mineinthebox.bukkit.essentials.configurations;

import java.util.HashMap;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.enums.ConfigType;
import tv.mineinthebox.bukkit.essentials.instances.Kit;

public class KitConfig {
	
	public boolean isCooldownEnabled() {
		return (Boolean)Configuration.getConfigValue(ConfigType.KITS, "isCooldown");
	}
	
	public int getCoolDown() {
		return (Integer) Configuration.getConfigValue(ConfigType.KITS, "cooldownTime");
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Kit> getConfigKits() {
		return (HashMap<String, Kit>) Configuration.getConfigValue(ConfigType.KITS, "kits");
	}

}
