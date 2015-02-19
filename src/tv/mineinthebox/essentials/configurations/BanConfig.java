package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class BanConfig implements Configuration {
	
	private final File f;
	private final FileConfiguration con;
	
	public BanConfig(File f, FileConfiguration con) {
		this.f = f;
		this.con = con;
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
			con.set("ban.system.enablePwnAgeProtection", false);
			con.set("ban.system.enableAntiFloodSpam", false);
			con.set("ban.system.enableHumanSpamProtection", false);
			con.set("ban.system.PwnAgeProtection.banMessage", "[PwnAge] spam hacks");
			con.set("ban.system.AntiFloodSpam.banMessage", "[FloodSpam] spam hacks");
			con.set("ban.system.HumanSpamProtection.banMessage", "[normal spam] dont spam!");
			con.set("ban.system.showAlternateAccounts", false);
			con.set("ban.system.services.fishbans", false);
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
