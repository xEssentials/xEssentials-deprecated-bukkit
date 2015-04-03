package tv.mineinthebox.essentials.configurations;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class ShopConfig extends Configuration {
	
	public ShopConfig(File f, FileConfiguration con) {
		super(f, con);
		preconfig.put("shop.enable", false);
		preconfig.put("shop.admin.shop-admin-prefix", "Admin Shop");
	}
	
	/**
	 * returns true if the shops are enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isShopEnabled() {
		return con.getBoolean("shop.enable");
	}
	
	/**
	 * returns the Admin Shop sign prefix
	 * 
	 * @author xize
	 * @return String
	 */
	public String getAdminShopPrefix() {
		return ChatColor.translateAlternateColorCodes('&', con.getString("shop.admin.shop-admin-prefix"));
	}

	@Override
	public String getName() {
		return getType().name();
	}

	@Override
	public ConfigType getType() {
		return ConfigType.SHOP;
	}
	
	@Override
	public boolean hasAlternativeReload() {
		return false;
	}
}
