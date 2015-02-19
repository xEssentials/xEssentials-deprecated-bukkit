package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class ShopConfig extends Configuration {
	
	public ShopConfig(File f, FileConfiguration con) {
		super(f, con);
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
	public boolean isGenerated() {
		return f.exists();
	}
	
	@Override
	public boolean isGeneratedOnce() {
		return true;
	}

	@Override
	public void generateConfig() {
		if(!isGenerated()) {
			con.set("shop.enable", false);
			con.set("shop.admin.shop-admin-prefix", "Admin Shop");
			try {
				con.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void reload() {
		try {
			con.load(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

}
