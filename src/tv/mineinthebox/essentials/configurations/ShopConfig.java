package tv.mineinthebox.essentials.configurations;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

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
