package tv.mineinthebox.bukkit.essentials.configurations;

import java.io.File;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.ConfigType;

public class PlayerConfig {

	/**
	 * @author xize
	 * @param when enabled players have different inventories per game-mode
	 * @return boolean
	 */
	public boolean isSeperatedInventorysEnabled() {
		Boolean bol = (Boolean) Configuration.getConfigValue(ConfigType.PLAYER, "useSeperatedInventorys");
		return bol;
	}

	/**
	 * @author xize
	 * @param returns true if the world border is enabled
	 * @return boolean
	 */
	public boolean isWorldBorderEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.PLAYER, "worldborder-enable");
	}

	/**
	 * @author xize
	 * @param returns the world border size
	 * @return boolean
	 */
	public int getWorldBorderSize() {
		return (Integer) Configuration.getConfigValue(ConfigType.PLAYER, "worldborder-radius");
	}

	/**
	 * @author xize
	 * @param returns true if its supported else false
	 * @return boolean
	 */
	public boolean isVanillaBorderSupported() {
		try {
			Class<?> w = Class.forName("org.bukkit.World");
			Method[] methods = w.getMethods();
			for(int i = 0; i < methods.length; i++) {
				if(methods[i].getName().equalsIgnoreCase("getWorldBorder")) {
					return true;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @author xize
	 * @param when enabled the inventories of players get saved for /invsee (offline checking)
	 * @return boolean
	 */
	public boolean isSaveInventoryEnabled() {
		Boolean bol = (Boolean) Configuration.getConfigValue(ConfigType.PLAYER, "savePlayerInventory");
		return bol;
	}

	/**
	 * @author xize
	 * @param when enabled players are automatic god-mode while in afk, mobs will not target you.
	 * @return boolean
	 */
	public boolean isGodModeInAfkEnabled() {
		Boolean bol = (Boolean) Configuration.getConfigValue(ConfigType.PLAYER, "godmodeInAfk");
		return bol;
	}

	/**
	 * @author xize
	 * @param when true the hunger is cancelled
	 * @return Boolean
	 */
	public boolean isHungerCancelled() {
		Boolean bol = (Boolean) Configuration.getConfigValue(ConfigType.PLAYER, "hunger");
		return bol;
	}

	/**
	 * @author xize
	 * @param when true the players keeps the items in his inventory even while being death!
	 * @return Boolean
	 */
	public boolean isKeepInventoryOnDeathEnabled() {
		Boolean bol = (Boolean) Configuration.getConfigValue(ConfigType.PLAYER, "keepinv");
		return bol;
	}

	/**
	 * @author xize
	 * @param when enabled hostile entitys can steal players their head when the player died due the cause of them
	 * @return boolean
	 */
	public boolean isCanEntityStealHatOnPlayersDeath() {
		Boolean bol = (Boolean) Configuration.getConfigValue(ConfigType.PLAYER, "entitysCanUseHeadOnPlayerDeath");
		return bol;
	}

	/**
	 * @author xize
	 * @param returns true whenever the option is enabled and players are allowed to set custom homes till the max
	 * @return boolean
	 */
	public boolean canUseMoreHomes() {
		Boolean bol = (Boolean) Configuration.getConfigValue(ConfigType.PLAYER, "canDefaultUseMoreHomes");
		return bol;
	}

	/**
	 * @author xize
	 * @param returns the amount of homes a default player is allowed to set
	 * @return int
	 */
	public int getMaxHomesAllowed() {
		int i = (Integer) Configuration.getConfigValue(ConfigType.PLAYER, "maxHomes");
		return i;
	}

	/**
	 * @author xize
	 * @param returns true whenever portal creation is disabled
	 * @return Boolean
	 */
	public boolean isPortalsDisabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.PLAYER, "DisablePortals");
	}

	/**
	 * @author xize
	 * @param returns true whenever the custom size of portals is disabled!
	 * @return Boolean
	 */
	public boolean isCustomPortalSizeDisabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.PLAYER, "DisableCustomSize");
	}

	/**
	 * @author xize
	 * @param whenever if it is disabled, no achievements get broadcasted!
	 * @return boolean
	 */
	public boolean isBroadcastAchievementsEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.PLAYER, "PlayerAchievements");
	}

	/**
	 * @author xize
	 * @param returns true when enabled otherwise false
	 * @return booelan
	 */
	public boolean isAutoAnvilEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.PLAYER, "anvil");
	}

	/**
	 * @author xize
	 * @param returns true if auto respawn is enabled
	 * @return boolean
	 * @return
	 */
	public boolean isAutoRespawnEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.PLAYER, "force-respawn");
	}

	/**
	 * @author xize
	 * @param returns true if the spawn location is set else false
	 * @return boolean
	 */
	public boolean hasSpawnLocation() {
		if(getSpawnLocation() != null) {
			return true;
		}
		return false;
	}

	/**
	 * @author xize
	 * @param returns the Location of the spawn
	 * @return Location
	 */
	public Location getSpawnLocation() {
		try {
			File f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "spawn.yml");
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

}
