package tv.mineinthebox.essentials.configurations;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class PvpConfig {
	
	/**
	 * @author xize
	 * @param returns true when pvp is disabled
	 * @return boolean
	 */
	public boolean isPvpDisabled() {
		Boolean bol = (Boolean) Configuration.getConfigValue(ConfigType.PVP, "disablePvp");
		return bol;
	}
	
	/**
	 * @author xize
	 * @param returns when client graves are enabled
	 * @return boolean
	 */
	public boolean isClientGravesEnabled() {
		Boolean bol = (Boolean) Configuration.getConfigValue(ConfigType.PVP, "createClientSideGraveyard");
		return bol;
	}
	
	/**
	 * @author xize
	 * @param returns when killBounty is enabled
	 * @return boolean
	 */
	public boolean isKillBountyEnabled() {
		Boolean bol = (Boolean) Configuration.getConfigValue(ConfigType.PVP, "killBountyEnable");
		return bol;
	}
	
	/**
	 * @author xize
	 * @param returns the price of killbountys
	 * @return Double
	 */
	public Double getKillBountyPrice() {
		Double d = (Double) Configuration.getConfigValue(ConfigType.PVP, "killBountyEarn");
		return d;
	}
	
	/**
	 * @author xize
	 * @param returns true when players needs to be replaced with npcs
	 * @return boolean
	 */
	public boolean isReplaceNpcEnabled() {
		Boolean bol = (Boolean) Configuration.getConfigValue(ConfigType.PVP, "npcReplaceLoggers");
		return bol;
	}

}
