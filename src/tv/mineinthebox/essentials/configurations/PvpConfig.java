package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class PvpConfig implements Configuration {
	
	private final File f;
	private final FileConfiguration con;
	
	public PvpConfig(File f, FileConfiguration con) {
		this.f = f;
		this.con = con;
	}
	
	/**
	 * returns true if pvp is disabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isPvpDisabled() {
		return con.getBoolean("disable-pvp.enable");
	}
	
	/**
	 * returns true if fake pvp is enabled and people can attack each other but cannot kill each other, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isFakePvpEnabled() {
		return con.getBoolean("disable-pvp.fakepvp");
	}
	
	/**
	 * returns true if client side graves are enabled, once when a player dies
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isClientGravesEnabled() {
		return con.getBoolean("createClientSideGraveyard");
	}
	
	/**
	 * returns true if killbounty is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isKillBountyEnabled() {
		return con.getBoolean("killBounty.enable");
	}
	
	/**
	 * returns the amount of money a player can earn by killing a entity
	 * 
	 * @author xize
	 * @return double
	 */
	public Double getKillBountyPrice() {
		return con.getDouble("killBounty.earn");
	}

	/**
	 * returns true if combat log is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isReplaceNpcEnabled() {
		return con.getBoolean("npcReplaceLoggers");
	}

	@Override
	public String getName() {
		return getType().name();
	}

	@Override
	public ConfigType getType() {
		return ConfigType.PVP;
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
			con.set("disable-pvp.enable", false);
			con.set("disable-pvp.fakepvp", false);
			con.set("createClientSideGraveyard", false);
			con.set("killBounty.enable", false);
			con.set("killBounty.earn", 5.0);
			con.set("npcReplaceLoggers", false);
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
