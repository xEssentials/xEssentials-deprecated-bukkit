package tv.mineinthebox.essentials.configurations;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class PvpConfig extends Configuration {
	
	public PvpConfig(File f, FileConfiguration con) {
		super(f, con);
		preconfig.put("disable-pvp.enable", false);
		preconfig.put("disable-pvp.fakepvp", false);
		preconfig.put("createClientSideGraveyard", false);
		preconfig.put("killBounty.enable", false);
		preconfig.put("killBounty.earn", 5.0);
		preconfig.put("npcReplaceLoggers", false);
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
	public boolean hasAlternativeReload() {
		return false;
	}
	
}
