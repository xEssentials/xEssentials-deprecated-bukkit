package tv.mineinthebox.bukkit.essentials.configurations;

import org.bukkit.ChatColor;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.enums.ConfigType;

public class ShopConfig {
	
	public boolean isShopEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.SHOP, "enable");
	}
	
	public String getAdminShopPrefix() {
		return ChatColor.translateAlternateColorCodes('&', ((String) Configuration.getConfigValue(ConfigType.SHOP, "adminshopprefix")));
	}

}
