package tv.mineinthebox.essentials.enums;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.configurations.BanConfig;
import tv.mineinthebox.essentials.configurations.BlockConfig;
import tv.mineinthebox.essentials.configurations.BroadcastConfig;
import tv.mineinthebox.essentials.configurations.ChatConfig;
import tv.mineinthebox.essentials.configurations.CommandConfig;
import tv.mineinthebox.essentials.configurations.DebugConfig;
import tv.mineinthebox.essentials.configurations.EconomyConfig;
import tv.mineinthebox.essentials.configurations.EntityConfig;
import tv.mineinthebox.essentials.configurations.GreylistConfig;
import tv.mineinthebox.essentials.configurations.KitConfig;
import tv.mineinthebox.essentials.configurations.MiscConfig;
import tv.mineinthebox.essentials.configurations.MotdConfig;
import tv.mineinthebox.essentials.configurations.PlayerConfig;
import tv.mineinthebox.essentials.configurations.PortalConfig;
import tv.mineinthebox.essentials.configurations.ProtectionConfig;
import tv.mineinthebox.essentials.configurations.PvpConfig;
import tv.mineinthebox.essentials.configurations.RulesConfig;
import tv.mineinthebox.essentials.configurations.ShopConfig;
import tv.mineinthebox.essentials.configurations.SignConfig;
import tv.mineinthebox.essentials.configurations.VoteConfig;


public enum ConfigType {

	ENTITY("entity.yml",  EntityConfig.class),
	PLAYER("player.yml", PlayerConfig.class),
	BAN("ban.yml", BanConfig.class),
	MOTD("motd.yml", MotdConfig.class),
	BROADCAST("broadcast.yml", BroadcastConfig.class),
	CHAT("chat.yml", ChatConfig.class),
	PVP("pvp.yml", PvpConfig.class),
	RULES("rules.yml", RulesConfig.class),
	GREYLIST("greylist.yml", GreylistConfig.class),
	BLOCKS("blocks.yml", BlockConfig.class),
	KITS("kits.yml", KitConfig.class),
	COMMAND("commands.yml", CommandConfig.class),
	ECONOMY("economy.yml", EconomyConfig.class),
	SHOP("shops.yml", ShopConfig.class),
	PROTECTION("protection.yml", ProtectionConfig.class),
	PORTAL("portal.yml", PortalConfig.class),
	MISC("misc.yml", MiscConfig.class),
	SIGN("signs.yml", SignConfig.class),
	VOTE("vote.yml", VoteConfig.class),
	DEBUG("debug.yml", DebugConfig.class);

	private final String name;
	private final Class<? extends Configuration> clazz;

	private ConfigType(String name, Class<? extends Configuration> clazz) {
		this.name = name;
		this.clazz = clazz;
	}

	/**
	 * returns the new configuration from the enum
	 * 
	 * @author xize
	 * @param pl - the plugin instance
	 * @return Configuration
	 * @deprecated this method is very redurant, and we believe reflection is not a way to accomplish this in any way, instead we need to come on a better OO design than this.
	 */
	public Configuration getNewConfiguration(xEssentials pl) {
		try {
			final Constructor<?> constr = clazz.getConstructors()[0];
			
			List<Class<?>> classes = new ArrayList<Class<?>>() { private static final long serialVersionUID = 1L; {
				for(Class<?> aclass : constr.getParameterTypes()) {
					add(aclass);
				}
			}};
			
			if(constr.getParameterTypes().length == 2) {
				if(classes.contains(File.class) && classes.contains(FileConfiguration.class)) {
					
					File f = new File(pl.getDataFolder() + File.separator + name);
					FileConfiguration con = YamlConfiguration.loadConfiguration(f);
					
					
					if(constr.getParameterTypes()[0] == File.class) {
						return (Configuration) constr.newInstance(new Object[] {f, con});
					} else if(constr.getParameterTypes()[0] == FileConfiguration.class) {
						return (Configuration) constr.newInstance(new Object[] {con, f});
					}
					
				} else {
					throw new Exception("configuration " + constr.getClass().getName() + " has an invalid constructor");
				}
			} else if(constr.getParameterTypes().length == 3) {
				if(classes.contains(xEssentials.class) && classes.contains(File.class) && classes.contains(FileConfiguration.class)) {
					File f = new File(pl.getDataFolder() + File.separator + name);
					FileConfiguration con = YamlConfiguration.loadConfiguration(f);
					
					if(constr.getParameterTypes()[0] == xEssentials.class) {
						if(constr.getParameterTypes()[1] == File.class) {
							return (Configuration)constr.newInstance(new Object[] {pl, f, con});
						} else if(constr.getParameterTypes()[1] == FileConfiguration.class) {
							return (Configuration)constr.newInstance(new Object[] {pl, con, f});
						}
					} else if(constr.getParameterTypes()[0] == File.class) {
						if(constr.getParameterTypes()[1] == xEssentials.class) {
							return (Configuration)constr.newInstance(new Object[] {f, pl, con});
						} else if(constr.getParameterTypes()[1] == FileConfiguration.class) {
							return (Configuration)constr.newInstance(new Object[] {f, con, pl});
						}
					} else if(constr.getParameterTypes()[0] == FileConfiguration.class) {
						if(constr.getParameterTypes()[1] == xEssentials.class) {
							return (Configuration)constr.newInstance(new Object[] {con, pl, f});
						} else if(constr.getParameterTypes()[1] == File.class) {
							return (Configuration)constr.newInstance(new Object[] {con, f, pl});
						}
					}
					
				} else {
					throw new Exception("configuration " + constr.getClass().getName() + " has an invalid constructor");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * returns the file name
	 * 
	 * @author xize
	 * @return String
	 */
	public String getFileName() {
		return name;
	}
}
