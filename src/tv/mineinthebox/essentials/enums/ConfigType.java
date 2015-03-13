package tv.mineinthebox.essentials.enums;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

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

	ENTITY("Entity", "entity.yml"),
	PLAYER("Player", "player.yml"),
	BAN("Ban", "ban.yml"),
	MOTD("Motd", "motd.yml"),
	BROADCAST("Broadcast", "broadcast.yml"),
	CHAT("Chat", "chat.yml"),
	PVP("Pvp", "pvp.yml"),
	RULES("Rules", "rules.yml"),
	GREYLIST("Greylist", "greylist.yml"),
	BLOCKS("Blocks", "blocks.yml"),
	KITS("Kits", "kits.yml"),
	COMMAND("Command", "commands.yml"),
	ECONOMY("Economy", "economy.yml"),
	SHOP("Shop", "shops.yml"),
	PROTECTION("Protection", "protection.yml"),
	PORTAL("Portal", "portal.yml"),
	MISC("Misc", "misc.yml"),
	SIGN("Sign", "signs.yml"),
	VOTE("Vote", "vote.yml"),
	DEBUG("Debug", "debug.yml");

	private final String name;
	private final String nicename;
	
	private ConfigType(String nicename, String name) {
		this.nicename = nicename;
		this.name = name;
	}

	/**
	 * returns the new configuration from the enum
	 * 
	 * @author xize
	 * @param pl - the plugin instance
	 * @return Configuration
	 */
	public static Configuration getNewConfiguration(xEssentials pl, File f, FileConfiguration con, ConfigType type) {
		switch(type) {
		case BAN:
			return new BanConfig(f, con);
		case BLOCKS:
			return new BlockConfig(f, con);
		case BROADCAST:
			return new BroadcastConfig(f, con);
		case CHAT:
			return new ChatConfig(f, con);
		case COMMAND:
			return new CommandConfig(pl, f, con);
		case DEBUG:
			return new DebugConfig(f, con);
		case ECONOMY:
			return new EconomyConfig(f, con);
		case ENTITY:
			return new EntityConfig(f, con);
		case GREYLIST:
			return new GreylistConfig(f, con);
		case KITS:
			return new KitConfig(f, con);
		case MISC:
			return new MiscConfig(f, con);
		case MOTD:
			return new MotdConfig(f, con);
		case PLAYER:
			return new PlayerConfig(pl, f, con);
		case PORTAL:
			return new PortalConfig(pl, f, con);
		case PROTECTION:
			return new ProtectionConfig(f, con);
		case PVP:
			return new PvpConfig(f, con);
		case RULES:
			return new RulesConfig(f, con);
		case SHOP:
			return new ShopConfig(f, con);
		case SIGN:
			return new SignConfig(f, con);
		case VOTE:
			return new VoteConfig(f, con);
		default:
			return null;
			
		}
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
	
	@Override
	public String toString() {
		return nicename;
	}
}
