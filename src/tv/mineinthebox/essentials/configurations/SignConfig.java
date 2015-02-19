package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class SignConfig extends Configuration {
	
	public SignConfig(File f, FileConfiguration con) {
		super(f, con);
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
			con.set("signs.colorsign.enable", true);
			con.set("signs.fireworksign.enable", true);
			con.set("signs.freesign.enable", true);
			con.set("signs.getyourheadsign.enable", true);
			con.set("signs.signboom.enable", true);
			con.set("signs.warpsign.enable", true);
			con.set("signs.wildsign.enable", true);
			con.set("signs.dispenser.enable", true);
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
