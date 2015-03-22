package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;


public class EntityConfig extends Configuration {

	public EntityConfig(File f, FileConfiguration con) {
		super(f, con);
		preconfig.put("disable-weather", false);
		preconfig.put("disable-firespread", false);
		preconfig.put("disable-explosion", false);
		preconfig.put("disable-firework", false);
		preconfig.put("disable-wither-grief", false);
		preconfig.put("disable-enderman-grief", false);
		preconfig.put("disable-enderdragon-grief", false);
		preconfig.put("disable-leave-decay", false);
		preconfig.put("regen-explosions", false);
		preconfig.put("zombie-custom-aggro.enable", false);
		preconfig.put("zombie-custom-aggro.range", 10);
		preconfig.put("disable-spawneggs", false);
		preconfig.put("log.spawnEggs", false);
		preconfig.put("realistic-blood", false);
		preconfig.put("realistic-trees", false);
		preconfig.put("realistic-water", false);
		preconfig.put("realistic-glass", false);
		preconfig.put("cleanup-on-chunkunload", false);
		preconfig.put("remove-flying-projectiles-on-chunkload", false);
		preconfig.put("disable-stone-pressureplates-for-mobs", false);

		for(EntityType type : EntityType.values()) {
			if(type.isAlive() && type.isSpawnable()) {
				String[] biomes = new String[Biome.values().length];
				for(int i = 0; i < biomes.length; i++) {
					biomes[i] = Biome.values()[i].name();
				}
				String mob = serialize_name(type.name());
				preconfig.put("mob-allow-spawn."+mob+".can-spawn", true);
				preconfig.put("mob-allow-spawn."+mob+".biomes", Arrays.toString(biomes));
			}
		}
	}

	/**
	 * returns true if weather is disabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isWeatherDisabled() {
		return con.getBoolean("disable-weather");
	}

	/**
	 * returns true if fire spread is enabled, otherwise false
	 * 
	 * @author xize
	 * @return
	 */
	public boolean isFireSpreadDisabled() {
		return con.getBoolean("disable-firespread");
	}

	/**
	 * returns true if realistic glass is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isRealisticGlassEnabled() {
		return con.getBoolean("realistic-glass");
	}

	/**
	 * returns true if leaf decay is disabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isLeaveDecayDisabled() {
		return con.getBoolean("disable-leave-decay");
	}

	/**
	 * returns true if explosions are disabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isExplosionsDisabled() {
		return con.getBoolean("disable-explosion");
	}

	/**
	 * returns true if fireworks are disabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isFireworksDisabled() {
		return con.getBoolean("disable-firework");
	}

	/**
	 * returns true if wither grief is disabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isWitherGriefDisabled() {
		return con.getBoolean("disable-wither-grief");
	}

	/**
	 * returns true if enderman grief is disabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isEnderManGriefDisabled() {
		return con.getBoolean("disable-enderman-grief");
	}

	/**
	 * returns true if ender dragon grief is disabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isEnderDragonGriefDisabled() {
		return con.getBoolean("disable-enderdragon-grief");
	}

	/**
	 * returns true if custom zombie range has been enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isCustomZombieAggroRangeEnabled() {
		return con.getBoolean("zombie-custom-aggro.enable");
	}

	/**
	 * returns the radius range where the zombie pathfinding should trigger for its target
	 * 
	 * @author xize
	 * @return Integer
	 */
	public int getCustomZombieAggroRange() {
		return con.getInt("zombie-custom-aggro.range");
	}

	/**
	 * returns true if spawn eggs are disabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isSpawnEggsDisabled() {
		return con.getBoolean("disable-spawneggs");
	}

	/**
	 * returns true if logging of spawn egg activity, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isLoggingSpawnEggsEnabled() {
		return con.getBoolean("log.spawnEggs");
	}

	/**
	 * returns true if realistic blood is enabled, othewise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isBloodEnabled() {
		return con.getBoolean("realistic-blood");
	}

	/**
	 * returns true if cleanup on chunk unload is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isCleanUpOnChunkUnloadEnabled() {
		return con.getBoolean("cleanup-on-chunkunload");
	}

	/**
	 * returns true if projectile protection is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isProjectileRemovalEnabled() {
		return con.getBoolean("remove-flying-projectiles-on-chunkload");
	}
	
	/**
	 * returns true if the entity can spawn otherwise false
	 * 
	 * @author xize
	 * @param type - the Entity type
	 * @param biome - the biome tpye
	 * @return boolean
	 */
	public boolean canEntitySpawn(EntityType type, Biome biome) {
		boolean bol1 = con.getBoolean("mob-allow-spawn."+serialize_name(type.name())+".can-spawn");
		String cleanbiomes = con.getString("mob-allow-spawn."+serialize_name(type.name())+".biomes").replace("[", "").replace("]", "");
		String[] biomes = cleanbiomes.split(", ");
		List<String> biomelist = new ArrayList<String>();
		for(String biomee : biomes) {
			biomelist.add(biomee);
		}
		if(bol1) {
			if(biomelist.contains(biome.name())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * returns true if explosion regen is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isExplosionRegenEnabled() {
		return con.getBoolean("regen-explosions");
	}

	/**
	 * returns true if mobs should not activate stone pressure plates, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isStonePressurePlatesDisabledForMobs() {
		return con.getBoolean("disable-stone-pressureplates-for-mobs");
	}

	/**
	 * returns true if realistic water is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isRealisticWaterEnabled() {
		return con.getBoolean("realistic-water");
	}

	/**
	 * returns true if realistic trees are enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isRealisticTreesEnabled() {
		return con.getBoolean("realistic-trees");
	}

	@Override
	public String getName() {
		return getType().name();
	}

	@Override
	public ConfigType getType() {
		return ConfigType.ENTITY;
	}

	private String serialize_name(String mob) {
		return mob.toString().toLowerCase();
	}

}
