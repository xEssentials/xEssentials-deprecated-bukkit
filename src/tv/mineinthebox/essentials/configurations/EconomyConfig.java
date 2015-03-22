package tv.mineinthebox.essentials.configurations;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class EconomyConfig extends Configuration {
	
	public EconomyConfig(File f, FileConfiguration con) {
		super(f, con);
		preconfig.put("economy.enable", true);
		preconfig.put("economy.currency", "$");
		preconfig.put("economy.startersAmount", 10.0);
	}
	
	/**
	 * returns true if the xEssentials economy is enabled
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isEconomyEnabled() {
		return con.getBoolean("economy.enable");
	}
	
	/**
	 * returns the currency symbol
	 * 
	 * @author xize
	 * @return String
	 */
	public String getCurency() {
		return con.getString("economy.currency");
	}
	
	/**
	 * returns the starters money what a player receives on first join
	 * 
	 * @author xize
	 * @return double
	 */
	public double getStartersMoney() {
		return con.getDouble("economy.startersAmount");
	}

	@Override
	public String getName() {
		return getType().name();
	}

	@Override
	public ConfigType getType() {
		return ConfigType.ECONOMY;
	}
}
