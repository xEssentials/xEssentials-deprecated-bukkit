package tv.mineinthebox.essentials.configurations;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class BanConfig {
	
	/**
	 * 
	 * @author xize
	 * @param returns whenever PwnAge security is enabled
	 * @return boolean
	 * 
	 */
	public boolean isPwnAgeEnabled() {
		Boolean bol = (Boolean) Configuration.getConfigValue(ConfigType.BAN, "enablePwnAgeProtection");
		return bol;
	}
	
	/**
	 * 
	 * @author xize
	 * @param returns whenever flood spam checks are enabled!
	 * @return boolean
	 *
	 */
	public boolean isFloodSpamEnabled() {
		Boolean bol = (Boolean) Configuration.getConfigValue(ConfigType.BAN, "enableAntiFloodSpam");
		return bol;
	}
	
	/**
	 * 
	 * @author xize
	 * @param returns whenever human spam is enabled
	 * @return boolean
	 * 
	 */
	public boolean isHumanSpamEnabled() {
		Boolean bol = (Boolean) Configuration.getConfigValue(ConfigType.BAN, "enableHumanSpamProtection");
		return bol;
	}
	
	/**
	 * 
	 * @author xize
	 * @param returns ban message for pwnAge spam
	 * @return String
	 * 
	 */
	public String getPwnAgeSpamBanMessage() {
		String s = (String) Configuration.getConfigValue(ConfigType.BAN, "PwnAgeProtectionBanMessage");
		return s;
	}
	
	/**
	 * 
	 * @author xize
	 * @param returns the ban message for flood spam 
	 * @return String
	 * 
	 */
	public String getFloodSpamBanMessage() {
		String s = (String) Configuration.getConfigValue(ConfigType.BAN, "AntiFloodSpamBanMessage");
		return s;
	}
	
	/**
	 * 
	 * @author xize
	 * @param returns the ban message for human spam
	 * @return String
	 */
	public String getHumanSpamBanMessage() {
		String s = (String) Configuration.getConfigValue(ConfigType.BAN, "HumanSpamProtectionBanMessage");
		return s;
	}
	
	/**
	 * 
	 * @author xize
	 * @param returns the boolean if the alternate accounts are enabled!
	 * @returns boolean
	 * 
	 */
	public boolean isAlternateAccountsEnabled() {
		Boolean bol = (Boolean) Configuration.getConfigValue(ConfigType.BAN, "showAlternateAccounts");
		return bol;
	}
	
	/**
	 * @author xize
	 * @param returns true if fishbans lookup service is enabled!
	 * @return Boolean
	 */
	public boolean isFishbansEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.BAN, "fishbans");
	}
}
