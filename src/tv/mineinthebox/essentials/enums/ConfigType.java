package tv.mineinthebox.essentials.enums;

import java.io.File;
import java.lang.reflect.Constructor;

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
	 */
	public Configuration getNewConfiguration(xEssentials pl) {
		try {
			Constructor<?> constr = clazz.getConstructors()[0];
			if(constr.getParameterTypes().length == 2) {
				File f = new File(pl.getDataFolder() + File.separator + name);
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				return (Configuration) constr.newInstance(new Object[] {f, con});
			} else if(constr.getParameterTypes().length == 3) {
				File f = new File(pl.getDataFolder() + File.separator + name);
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				return (Configuration) constr.newInstance(new Object[] {pl, f, con});
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
