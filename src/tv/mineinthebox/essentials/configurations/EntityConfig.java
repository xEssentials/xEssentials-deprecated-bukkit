package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.block.Biome;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.ConfigType;
import tv.mineinthebox.essentials.enums.LogType;


public class EntityConfig extends Configuration {

	private final HashMap<String, Map<Boolean, String[]>> entitys = new HashMap<String, Map<Boolean, String[]>>();

	public EntityConfig(File f, FileConfiguration con) {
		super(f, con);
		if(isGenerated()) {
			for(String key : con.getConfigurationSection("mobs.allowToSpawn").getKeys(false)) {
				Map<Boolean, String[]> map = new HashMap<Boolean, String[]>();
				boolean bol = con.getBoolean("mobs.allowToSpawn." + key + ".canSpawn");
				String[] biomes = con.getStringList("mobs.allowToSpawn." + key + ".allowedBiomes").toArray(new String[0]);
				map.put(bol, biomes);
				entitys.put(key, map);
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
	 * returns the entity configuration which could spawn and which not
	 * 
	 * @author xize
	 * @return HashMap<String, Map<Boolean, String[]>>
	 */
	public HashMap<String, Map<Boolean, String[]>> getEntitySpawnMap() {
		return entitys;
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

	@Override
	public boolean isGenerated() {
		return f.exists();
	}

	@Override
	public boolean isGeneratedOnce() {
		return false;
	}

	@Override
	public void generateConfig() {
		if(!isGenerated()) {
			con.set("disable-weather", false);
			con.set("disable-firespread", false);
			con.set("disable-explosion", false);
			con.set("disable-firework", false);
			con.set("disable-wither-grief", false);
			con.set("disable-enderman-grief", false);
			con.set("disable-enderdragon-grief", false);
			con.set("disable-leave-decay", false);
			con.set("regen-explosions", false);
			con.set("zombie-custom-aggro.enable", false);
			con.set("zombie-custom-aggro.range", 10);
			con.set("disable-spawneggs", false);
			con.set("log.spawnEggs", false);
			con.set("realistic-blood", false);
			con.set("realistic-trees", false);
			con.set("realistic-water", false);
			con.set("realistic-glass", false);
			con.set("cleanup-on-chunkunload", false);
			con.set("remove-flying-projectiles-on-chunkload", false);
			con.set("disable-stone-pressureplates-for-mobs", false);
			for(EntityType entity : EntityType.values()) {
				if(entity.isAlive()) {
					if(entity != EntityType.PLAYER && !entity.name().contains("ARMOR")) {
						//con.set("mobs.allowToSpawn." + serialize_name(entity.name()), true);
						con.set("mobs.allowToSpawn."+serialize_name(entity.name()) + ".canSpawn", true);

						String biomes = "";

						for(Biome biome : Biome.values()) {
							biomes += (biome.name() + ",");
						}

						con.set("mobs.allowToSpawn."+serialize_name(entity.name()) + ".allowedBiomes", Arrays.asList(biomes.split(",")).toArray());
					}
				}
			}
			try {
				con.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			//because if the file exist we go check if the entitys are up to date if its not we surely update it.
			FileConfiguration con = YamlConfiguration.loadConfiguration(f);
			List<String> entitys = Arrays.asList(con.getConfigurationSection("mobs.allowToSpawn").getKeys(false).toArray(new String[0]));
			List<String> newentities = new ArrayList<String>();
			for(EntityType entity : EntityType.values()) {
				if(entity.isAlive()) {
					if(entity != EntityType.PLAYER) {
						newentities.add(serialize_name(entity.name()));
					}
				}
			}
			if(entitys.size() != newentities.size()) {
				xEssentials.log("new entities detected!, adding them right now inside the entity config!", LogType.INFO);
				for(String entity : newentities) {
					if(!entitys.contains(newentities)) {
						xEssentials.log("found new entity: " + entity + " adding now to entity.yml", LogType.INFO);
						con.set("mobs.allowToSpawn."+serialize_name(entity) + ".canSpawn", true);

						String biomes = "";

						for(Biome biome : Biome.values()) {
							biomes += (biome.name() + ",");
						}

						con.set("mobs.allowToSpawn."+serialize_name(entity) + ".allowedBiomes", Arrays.asList(biomes.split(",")).toArray());
					}
				}
				try {
					con.save(f);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				xEssentials.log("there where no newer entitys found to be added in entity.yml", LogType.INFO);
			}
		}
	}

	@Override
	public void reload() {
		entitys.clear();
		try {
			con.load(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}

		for(String key : con.getConfigurationSection("mobs.allowToSpawn").getKeys(false)) {
			Map<Boolean, String[]> map = new HashMap<Boolean, String[]>();
			boolean bol = con.getBoolean("mobs.allowToSpawn." + key + ".canSpawn");
			String[] biomes = con.getStringList("mobs.allowToSpawn." + key + ".allowedBiomes").toArray(new String[0]);
			map.put(bol, biomes);
			entitys.put(key, map);
		}

	}

	private String serialize_name(String mob) {
		return mob.toString().toLowerCase();
	}

}
