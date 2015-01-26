package tv.mineinthebox.essentials.configurations;

import java.util.HashMap;
import java.util.Map;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.ConfigType;


public class EntityConfig {
	
	private final xEssentials pl;
	
	public EntityConfig(xEssentials pl) {
		this.pl = pl;
	}

	/**
	 * @author xize
	 * @param returns whenever the weather is disabled or enabled
	 * @return boolean
	 */
	public boolean isWeatherDisabled() {
		Boolean bol = (Boolean) pl.getConfiguration().getConfigValue(ConfigType.ENTITY, "disableWeather");
		return bol;
	}

	/**
	 * @author xize
	 * @param returns true when firespread is disabled
	 * @return boolean
	 */
	public boolean isFireSpreadDisabled() {
		Boolean bol = (Boolean) pl.getConfiguration().getConfigValue(ConfigType.ENTITY, "disableFirespread");
		return bol;
	}
	
	/**
	 * @author xize
	 * @param returns true if realisticglass is enabled
	 * @return boolean
	 */
	public boolean isRealisticGlassEnabled() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.ENTITY, "realisticglass");
	}

	/**
	 * @author xize
	 * @param returns true if the leave decay is disabled
	 * @return boolean
	 */
	public boolean isLeaveDecayDisabled() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.ENTITY, "disableleavedecay");
	}
	
	/**
	 * @author xize
	 * @param returns true when explosions are disabled
	 * @return boolean
	 */
	public boolean isExplosionsDisabled() {
		Boolean bol = (Boolean) pl.getConfiguration().getConfigValue(ConfigType.ENTITY, "disableExplosion");
		return bol;
	}

	/**
	 * @author xize
	 * @param returns true when fireworks are disabled
	 * @return boolean
	 */
	public boolean isFireworksDisabled() {
		Boolean bol = (Boolean) pl.getConfiguration().getConfigValue(ConfigType.ENTITY, "disableFirework");
		return bol;
	}

	/**
	 * @author xize
	 * @param returns true when wither grief is disabled
	 * @return boolean
	 */
	public boolean isWitherGriefDisabled() {
		Boolean bol = (Boolean) pl.getConfiguration().getConfigValue(ConfigType.ENTITY, "disableWitherGrief");
		return bol;
	}

	/**
	 * @author xize
	 * @param returns true when enderman grief is disabled
	 * @return boolean
	 */
	public boolean isEnderManGriefDisabled() {
		Boolean bol = (Boolean) pl.getConfiguration().getConfigValue(ConfigType.ENTITY, "disableEndermanGrief");
		return bol;
	}

	/**
	 * @author xize
	 * @param returns true when enderdragon grief is disabled
	 * @return boolean
	 */
	public boolean isEnderDragonGriefDisabled() {
		Boolean bol = (Boolean) pl.getConfiguration().getConfigValue(ConfigType.ENTITY, "disableEnderdragonGrief");
		return bol;
	}

	/**
	 * @author xize
	 * @param returns true when the custom aggro spawn is enabled
	 * @return boolean
	 */
	public boolean isCustomZombieAggroRangeEnabled() {
		Boolean bol = (Boolean) pl.getConfiguration().getConfigValue(ConfigType.ENTITY, "zombieCustomAggroEnable");
		return bol;
	}

	/**
	 * @author xize
	 * @param returns the current custom aggro range of zombies
	 * @return Integer
	 */
	public Integer getCustomZombieAggroRange() {
		int number = (Integer) pl.getConfiguration().getConfigValue(ConfigType.ENTITY, "zombieCustomAggroRange");
		return number;
	}

	/**
	 * @author xize
	 * @param returns true when spawn eggs are disabled
	 * @return boolean
	 */
	public boolean isSpawnEggsDisabled() {
		Boolean bol = (Boolean) pl.getConfiguration().getConfigValue(ConfigType.ENTITY, "disableSpawnEggs");
		return bol;
	}

	/**
	 * @author xize
	 * @param returns true when logging for eggs is enabled
	 * @return boolean
	 */
	public boolean isLoggingSpawnEggsEnabled() {
		Boolean bol = (Boolean) pl.getConfiguration().getConfigValue(ConfigType.ENTITY, "logSpawnEggs");
		return bol;
	}

	/**
	 * @author xize
	 * @param returns true when criticals are enabled
	 * @return boolean
	 */
	public boolean isBloodEnabled() {
		Boolean bol = (Boolean) pl.getConfiguration().getConfigValue(ConfigType.ENTITY, "blood");
		return bol;
	}

	/**
	 * @author xize
	 * @param returns true when the clean up on unloaded chunks is enabled
	 * @return Boolean
	 */
	public boolean isCleanUpOnChunkUnloadEnabled() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.ENTITY, "cleanup");
	}

	/**
	 * @author xize
	 * @param returns true whenever the pl.getConfiguration() 'Remove-Flying-Projectiles-On-ChunkLoad' is enabled this will enable the functional base to remove witherskulls and fireballs on chunk load so the server cannot be abused nor get laggy due this. 
	 * @return boolean
	 */
	public boolean isChunkProtectionEnabled() {
		Boolean bol = (Boolean) pl.getConfiguration().getConfigValue(ConfigType.ENTITY, "AntiFireball");
		return bol;
	}

	/**
	 * @author xize
	 * @param returns a HashMap containing all configurated entitys
	 * @return HashMap<String, Boolean>()
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Map<Boolean, String[]>> getEntitys() {
		return (HashMap<String, Map<Boolean, String[]>>) pl.getConfiguration().getConfigValue(ConfigType.ENTITY, "allowToSpawn");
	}

	/**
	 * @author xize
	 * @param returns true whenever the explosion regen is enabled
	 * @return Boolean
	 */
	public boolean isExplosionRegenEnabled() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.ENTITY, "regen");
	}

	/**
	 * @author xize
	 * @param when enabled we go back to the old school days, where stone pressure plates can only be activated by players.
	 * @return Boolean
	 */
	public boolean isStonePressurePlate() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.ENTITY, "plates");
	}

	/**
	 * @author xize
	 * @param when enabled, fish entity will spawn in water.
	 * @return Boolean
	 */
	public boolean isRealisticWaterEnabled() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.ENTITY, "realisticwater");
	}

	/**
	 * @author xize
	 * @param returns true when realistic tree falling is enabled
	 * @return Boolean
	 */
	public boolean isRealisticTreesEnabled() {
		return (Boolean)pl.getConfiguration().getConfigValue(ConfigType.ENTITY, "realistictrees");
	}

}
