package tv.mineinthebox.essentials.configurations;

import org.bukkit.ChatColor;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class ShopConfig {
	
	public boolean isShopEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.SHOP, "enable");
	}
	
	public String getAdminShopPrefix() {
		return ChatColor.translateAlternateColorCodes('&', ((String) Configuration.getConfigValue(ConfigType.SHOP, "adminshopprefix")));
	}

}
