package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.ConfigType;

public class PlayerConfig implements Configuration {
	
	private final xEssentials pl;
	private final File f;
	private FileConfiguration con;
	
	public PlayerConfig(xEssentials pl, File f, FileConfiguration con) {
		this.pl = pl;
		this.f = f;
		this.con = con;
	}

	/**
	 * returns the size of the offline player cache, this is intended to save performance on big servers where tab completion is used
	 * 
	 * @author xize
	 * @return Integer
	 */
	public int getOfflineCache() {
		return con.getInt("cache-offline-profiles.amount");
	}
	
	/**
	 * returns true if separated inventory's between gamemode's are enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isSeperatedInventorysEnabled() {
		return con.getBoolean("useSeperatedInventorys");
	}

	/**
	 * returns true if the worldborder is activated, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isWorldBorderEnabled() {
		return con.getBoolean("world-border.enable");
	}

	/**
	 * returns the size of the worldborder
	 * 
	 * @author xize
	 * @return Integer
	 */
	public int getWorldBorderSize() {
		return con.getInt("world-border.radius");
	}

	/**
	 * returns true if inventory's should be saved, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isSaveInventoryEnabled() {
		return con.getBoolean("save-playerInventory");
	}

	/**
	 * returns true if godmode in afk mode is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isGodModeInAfkEnabled() {
		return con.getBoolean("godmode-inAfk");
	}

	/**
	 * returns true if hunger has been disabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isHungerCancelled() {
		return con.getBoolean("CancelHunger");
	}

	/**
	 * returns true if players should respawn with their old inventory, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isKeepInventoryOnDeathEnabled() {
		return con.getBoolean("KeepInventoryOnDeath");
	}

	/**
	 * returns true when a non player entity killed the player and now has his face, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isCanEntityStealHatOnPlayersDeath() {
		return con.getBoolean("entitysCanUseHeadOnPlayerDeath");
	}

	/**
	 * returns true if a player can use more homes than default, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean canUseMoreHomes() {
		return con.getBoolean("canDefaultUseMoreHomes");
	}

	/**
	 * returns the amount of homes a default player can set
	 * 
	 * @author xize
	 * @return Integer
	 */
	public int getMaxHomesAllowed() {
		return con.getInt("maxHomes");
	}

	/**
	 * returns true if portal creation is disabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isPortalsDisabled() {
		return con.getBoolean("PortalCreation.DisablePortalCreation");
	}

	/**
	 * returns true if custom sized portals are not allowed to build, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isCustomPortalSizeDisabled() {
		return con.getBoolean("PortalCreation.DisableCustomSizes");
	}

	/**
	 * returns true if player achievements could be broadcasted, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isBroadcastAchievementsEnabled() {
		return con.getBoolean("broadcast-achievements");
	}

	/**
	 * returns true if anvil refresh is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isAutoAnvilEnabled() {
		return con.getBoolean("auto-refresh-anvil");
	}

	/**
	 * returns true if auto respawn is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isAutoRespawnEnabled() {
		return con.getBoolean("force-respawn");
	}

	/**
	 * returns true if the main spawn has been set, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean hasSpawnLocation() {
		if(getSpawnLocation() != null) {
			return true;
		}
		return false;
	}

	/**
	 * returns the main spawn location
	 * 
	 * @author xize
	 * @return Location
	 */
	public Location getSpawnLocation() {
		try {
			File f = new File(pl.getDataFolder() + File.separator + "spawn.yml");
			FileConfiguration con = YamlConfiguration.loadConfiguration(f);
			double x = con.getDouble("x");
			double y = con.getDouble("y");
			double z = con.getDouble("z");
			World w = Bukkit.getWorld(con.getString("world"));
			return new Location(w, x, y, z);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getName() {
		return getType().name();
	}

	@Override
	public ConfigType getType() {
		return ConfigType.PLAYER;
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
			con.set("cache-offline-profiles.amount", 10);
			con.set("world-border.enable", false);
			con.set("world-border.radius", 5000);
			con.set("useSeperatedInventorys", false);
			con.set("save-playerInventory", false);
			con.set("godmode-inAfk", false);
			con.set("entitysCanUseHeadOnPlayerDeath", false);
			con.set("canDefaultUseMoreHomes", false);
			con.set("maxHomes", 3);
			con.set("broadcast-achievements", false);
			con.set("auto-refresh-anvil", false);
			con.set("KeepInventoryOnDeath", false);
			con.set("CancelHunger", false);
			con.set("PortalCreation.DisableCustomSizes", false);
			con.set("PortalCreation.DisablePortalCreation", false);
			con.set("force-respawn", false);
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
