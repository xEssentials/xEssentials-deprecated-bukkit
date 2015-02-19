package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class EconomyConfig extends Configuration {
	
	public EconomyConfig(File f, FileConfiguration con) {
		super(f, con);
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
			con.set("economy.enable", true);
			con.set("economy.currency", "$");
			con.set("economy.startersAmount", 10.0);
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
