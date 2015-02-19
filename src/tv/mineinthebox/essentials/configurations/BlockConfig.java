package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class BlockConfig implements Configuration {
	
	private final File f;
	private final FileConfiguration con;
	
	public BlockConfig(File f, FileConfiguration con) {
		this.f = f;
		this.con = con;
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
			con.set("disable-bedrock-place", false);
			con.set("disable-bedrock-break", false);
			con.set("notify-admin-on-block-break.enable", false);
			con.set("notify-admin-on-block-break.message", "&2%PLAYER% &7has tried to break &2%BLOCK%&7 at &2%LOCATION%");
			String[] blocks = {Material.GLASS.name(), Material.DIAMOND_ORE.name(), "2:0", Material.COBBLESTONE.name()+":2"};
			con.set("notify-admin-on-block-break.blocks", Arrays.asList(blocks).toArray());
			con.set("notify-admin-on-item-use.enable", false);
			con.set("notify-admin-on-item-use.message", "&2%PLAYER% &7has tried to use &2%ITEM% &7at &2%LOCATION%");
			String[] items = {Material.FLINT_AND_STEEL.name(), Material.FIREBALL.name(), Material.FIRE.name()};
			con.set("notify-admin-on-item-use.items", Arrays.asList(items).toArray());
			con.set("block.blacklist.enable", false);
			con.set("block.blacklist.blocks", blocks);
			con.set("item.blacklist.enable", false);
			con.set("item.blacklist.items", items);
			try {
				con.save(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
