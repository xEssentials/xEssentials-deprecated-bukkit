package tv.mineinthebox.bukkit.essentials.configurations;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.enums.ConfigType;

public class ShopConfig {
	
	public boolean isShopsEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.SHOP, "enable");
	}
	
	public String getAdminPrefix() {
		return (String) Configuration.getConfigValue(ConfigType.SHOP, "AdminShopPrefix");
	}
	
	public boolean isShopMessagesDisabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.SHOP, "disableMessages");
	}

}
