package tv.mineinthebox.essentials.configurations;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class BanConfig extends Configuration {
	
	public BanConfig(File f, FileConfiguration con) {
		super(f, con);
		preconfig.put("ban.system.enablePwnAgeProtection", false);
		preconfig.put("ban.system.enableAntiFloodSpam", false);
		preconfig.put("ban.system.enableHumanSpamProtection", false);
		preconfig.put("ban.system.PwnAgeProtection.banMessage", "[PwnAge] spam hacks");
		preconfig.put("ban.system.AntiFloodSpam.banMessage", "[FloodSpam] spam hacks");
		preconfig.put("ban.system.HumanSpamProtection.banMessage", "[normal spam] dont spam!");
		preconfig.put("ban.system.showAlternateAccounts", false);
		preconfig.put("ban.system.services.fishbans", false);
	}
	
	/**
	 * returns true if anti pwnage is enabled otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isPwnAgeEnabled() {
		return con.getBoolean("ban.system.enablePwnAgeProtection");
	}
	
	/**
	 * returns true if anti flood spam is enabled otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isFloodSpamEnabled() {
		return con.getBoolean("ban.system.enableAntiFloodSpam");
	}
	
	/**
	 * returns true if anti human spam is enabled otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isHumanSpamEnabled() {
		return con.getBoolean("ban.system.enableHumanSpamProtection");
	}
	
	/**
	 * returns the anti pwnage spam ban message
	 * 
	 * @author xize
	 * @return String
	 */
	public String getPwnAgeSpamBanMessage() {
		return con.getString("ban.system.PwnAgeProtection.banMessage");
	}
	
	/**
	 * returns the anti flood spam ban message
	 * 
	 * @author xize
	 * @return String
	 */
	public String getFloodSpamBanMessage() {
		return con.getString("ban.system.AntiFloodSpam.banMessage");
	}
	
	/**
	 * returns the anti human spam ban message
	 * 
	 * @author xize
	 * @return String
	 */
	public String getHumanSpamBanMessage() {
		return con.getString("ban.system.HumanSpamProtection.banMessage");
	}
	
	/**
	 * returns true if alternate account system is enabled otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isAlternateAccountsEnabled() {
		return con.getBoolean("ban.system.showAlternateAccounts");
	}
	
	/**
	 * returns true if fishbans lookup is enabled otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isFishbansEnabled() {
		return con.getBoolean("ban.system.services.fishbans");
	}

	@Override
	public String getName() {
		return getType().name();
	}

	@Override
	public ConfigType getType() {
		return ConfigType.BAN;
	}
}
