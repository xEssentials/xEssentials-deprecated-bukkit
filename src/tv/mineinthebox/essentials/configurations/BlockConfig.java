package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class BlockConfig extends Configuration {
	
	public BlockConfig(File f, FileConfiguration con) {
		super(f, con);
		preconfig.put("disable-bedrock-place", false);
		preconfig.put("disable-bedrock-break", false);
		preconfig.put("notify-admin-on-block-break.enable", false);
		preconfig.put("notify-admin-on-block-break.message", "&2%PLAYER% &7has tried to break &2%BLOCK%&7 at &2%LOCATION%");
		String[] blocks = {Material.GLASS.name(), Material.DIAMOND_ORE.name(), "2:0", Material.COBBLESTONE.name()+":2"};
		preconfig.put("notify-admin-on-block-break.blocks", Arrays.asList(blocks).toArray());
		preconfig.put("notify-admin-on-item-use.enable", false);
		preconfig.put("notify-admin-on-item-use.message", "&2%PLAYER% &7has tried to use &2%ITEM% &7at &2%LOCATION%");
		String[] items = {Material.FLINT_AND_STEEL.name(), Material.FIREBALL.name(), Material.FIRE.name()};
		preconfig.put("notify-admin-on-item-use.items", Arrays.asList(items).toArray());
		preconfig.put("block.blacklist.enable", false);
		preconfig.put("block.blacklist.blocks", blocks);
		preconfig.put("item.blacklist.enable", false);
		preconfig.put("item.blacklist.items", items);
	}
	
	/**
	 * returns true if bedrock is not allowed to be placed otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isBedrockPlaceDisabled() {
		return con.getBoolean("disable-bedrock-place");
	}
	
	/**
	 * returns true if breaking bedrock is not allowed otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isBedrockBreakDisabled() {
		return con.getBoolean("disable-bedrock-break");
	}
	
	/**
	 * returns true if notifications for block breaks are enabled otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isNotifyOnBreakEnabled() {
		return con.getBoolean("notify-admin-on-block-break.enable");
	}
	
	/**
	 * returns the formatted notify of a block break message whereas %PLAYER% is the player, %BLOCK% the block and %LOCATION% the location
	 * 
	 * @author xize
	 * @return String
	 */
	public String getNotifyOnBreakMessage() {
		return ChatColor.translateAlternateColorCodes('&', con.getString("notify-admin-on-block-break.message"));
	}
	
	/**
	 * returns a serialized list of blocks with the following serialized format: Material:data
	 * 
	 * @author xize
	 * @return List<String>
	 */
	public List<String> getBlocksFromNotify() {
		return con.getStringList("notify-admin-on-block-break.blocks");
	}
	
	/**
	 * returns true if notifications for item consume is enabled otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isNotifyOnConsumeEnabled() {
		return con.getBoolean("notify-admin-on-item-use.enable");
	}
	
	/**
	 * returns the formated notification for consumed items
	 * 
	 * @author xize
	 * @return String
	 */
	public String getNotifyOnConsumeMessage() {
		return ChatColor.translateAlternateColorCodes('&', con.getString("notify-admin-on-item-use.message"));
	}
	
	/**
	 * returns the blacklist of possible consumed items
	 * 
	 * @author xize
	 * @return List<String>
	 */
	public List<String> getConsumedItemsFromNotify() {
		return con.getStringList("notify-admin-on-item-use.items");
	}
	
	/**
	 * returns true if the block blocklist is enabled otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isBlockBlacklistEnabled() {
		return con.getBoolean("block.blacklist.enable");
	}
	
	/**
	 * returns a list of blacklisted blocks
	 * 
	 * @author xize
	 * @return List<String>
	 */
	public List<String> getBlockBlackList() {
		return con.getStringList("block.blacklist.blocks");
	}
	
	/**
	 * returns the item black list
	 * 
	 * @author xize 
	 * @return boolean
	 */
	public boolean isItemBlacklistEnabled() { 
		return con.getBoolean("item.blacklist.enable");
	}
	
	/**
	 * returns a list of items being blacklisted
	 * 
	 * @author xize
	 * @return List<String>
	 */
	public List<String> getItemBlackList() {
		return con.getStringList("item.blacklist.items");
	}

	@Override
	public String getName() {
		return getType().name();
	}

	@Override
	public ConfigType getType() {
		return ConfigType.BLOCKS;
	}

}
