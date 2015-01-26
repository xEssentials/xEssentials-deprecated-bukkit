package tv.mineinthebox.essentials.configurations;

import org.bukkit.ChatColor;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.ConfigType;

public class ShopConfig {
	
	private final xEssentials pl;
	
	public ShopConfig(xEssentials pl) {
		this.pl = pl;
	}
	
	public boolean isShopEnabled() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.SHOP, "enable");
	}
	
	public String getAdminShopPrefix() {
		return ChatColor.translateAlternateColorCodes('&', ((String) pl.getConfiguration().getConfigValue(ConfigType.SHOP, "adminshopprefix")));
	}

}
