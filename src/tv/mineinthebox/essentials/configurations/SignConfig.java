package tv.mineinthebox.essentials.configurations;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class SignConfig extends Configuration {
	
	public SignConfig(File f, FileConfiguration con) {
		super(f, con);
		preconfig.put("signs.colorsign.enable", true);
		preconfig.put("signs.fireworksign.enable", true);
		preconfig.put("signs.freesign.enable", true);
		preconfig.put("signs.getyourheadsign.enable", true);
		preconfig.put("signs.signboom.enable", true);
		preconfig.put("signs.warpsign.enable", true);
		preconfig.put("signs.wildsign.enable", true);
		preconfig.put("signs.dispenser.enable", true);
	}

	/**
	 * returns true if color on signs are allowed, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isColorSignEnabled() {
		return con.getBoolean("signs.colorsign.enable");
	}
	
	/**
	 * returns true if firework signs are enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isFireworkSignEnabled() {
		return con.getBoolean("signs.fireworksign.enable");
	}
	
	/**
	 * returns true if free signs are enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isFreeSignEnabled() {
		return con.getBoolean("signs.freesign.enable");
	}
	
	/**
	 * returns true if get your head signs are enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isGetYourHeadSignEnabled() {
		return con.getBoolean("signs.getyourheadsign.enable");
	}
	
	/**
	 * returns true if boom signs are enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isBoomSignEnabled() {
		return con.getBoolean("signs.signboom.enable");
	}
	
	/**
	 * returns true if warp signs are enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isWarpSignEnabled() {
		return con.getBoolean("signs.warpsign.enable");
	}
	
	/**
	 * returns true if wildsigns are enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isWildSignEnabled() {
		return con.getBoolean("signs.wildsign.enable");
	}
	
	/**
	 * returns true if dispenser auto refill signs are enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isDispenserEnabled() {
		return con.getBoolean("signs.dispenser.enable");
	}

	@Override
	public String getName() {
		return getType().name();
	}

	@Override
	public ConfigType getType() {
		return ConfigType.SIGN;
	}
}
