package tv.mineinthebox.essentials.configurations;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.ConfigType;

public class EconomyConfig {
	
	private final xEssentials pl;
	
	public EconomyConfig(xEssentials pl) {
		this.pl = pl;
	}
	
	/**
	 * @author xize
	 * @param returns true whenever the economy system is enabled
	 * @return Boolean
	 */
	public boolean isEconomyEnabled() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.ECONOMY, "enable");
	}
	
	/**
	 * @author xize
	 * @param returns the currency in a string
	 * @return String
	 */
	public String getCurency() {
		return (String) pl.getConfiguration().getConfigValue(ConfigType.ECONOMY, "currency");
	}
	
	/**
	 * @author xize
	 * @param returns the starters amount of money for new players
	 * @return Double
	 */
	public Double getStartersMoney() {
		return (Double) pl.getConfiguration().getConfigValue(ConfigType.ECONOMY, "startersAmount");
	}

}
