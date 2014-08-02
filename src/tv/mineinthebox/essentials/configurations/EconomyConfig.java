package tv.mineinthebox.essentials.configurations;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class EconomyConfig {
	
	/**
	 * @author xize
	 * @param returns true whenever the economy system is enabled
	 * @return Boolean
	 */
	public boolean isEconomyEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.ECONOMY, "enable");
	}
	
	/**
	 * @author xize
	 * @param returns the currency in a string
	 * @return String
	 */
	public String getCurency() {
		return (String) Configuration.getConfigValue(ConfigType.ECONOMY, "currency");
	}
	
	/**
	 * @author xize
	 * @param returns the starters amount of money for new players
	 * @return Double
	 */
	public Double getStartersMoney() {
		return (Double) Configuration.getConfigValue(ConfigType.ECONOMY, "startersAmount");
	}

}
