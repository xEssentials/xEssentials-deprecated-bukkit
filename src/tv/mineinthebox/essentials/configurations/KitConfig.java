package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.inventory.ItemStack;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;
import tv.mineinthebox.essentials.instances.Kit;

public class KitConfig extends Configuration {

	private final HashMap<String, Kit> kitss = new HashMap<String, Kit>();

	public KitConfig(File f, FileConfiguration con) {
		super(f, con);

		if(isGenerated()) {
			Kit[] kits = parseKits(con);
			for(Kit kit : kits) {
				kitss.put(kit.getKitName(), kit);
			}
		}
	}

	/**
	 * returns true if the kit cooldown is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isCooldownEnabled() {
		return con.getBoolean("cooldown.isEnabled");
	}

	/**
	 * returns te kit cooldown
	 * 
	 * @author xize
	 * @return Integer
	 */
	public int getCoolDown() {
		return con.getInt("cooldown.time");
	}

	/**
	 * returns a map where the key is the kit name and where the value is the kits content
	 * 
	 * @author xize
	 * @return HashMap<String, Kit>
	 */
	public Map<String, Kit> getConfigKits() {
		return Collections.unmodifiableMap(kitss);
	}

	@SuppressWarnings("deprecation")
	private Kit[] parseKits(FileConfiguration con) {
		List<Kit> kits = new ArrayList<Kit>();
		for(String path : con.getConfigurationSection("kit").getKeys(true)) {
			String kitname = path;
			List<ItemStack> stacks = new ArrayList<ItemStack>();
			List<String> items = new ArrayList<String>(con.getStringList("kit."+kitname));
			for(String item : items) {
				String[] split = item.split(":");
				if(isNumberic(split[0])) {
					Material mat = Material.getMaterial(Integer.parseInt(split[0]));
					Short subdata = Short.parseShort(split[1]);
					int amount = Integer.parseInt(split[2]);
					ItemStack stack = new ItemStack(mat, amount);
					stack.setDurability(subdata);
					stacks.add(stack);
				} else {
					Material mat = Material.getMaterial(split[0].toUpperCase());
					Short subdata = Short.parseShort(split[1]);
					int amount = Integer.parseInt(split[2]);
					ItemStack stack = new ItemStack(mat, amount);
					stack.setDurability(subdata);
					stacks.add(stack);
				}
			}
			Kit kit = new Kit(kitname, stacks.toArray(new ItemStack[stacks.size()]));
			kits.add(kit);
		}
		return kits.toArray(new Kit[kits.size()]);
	}

	private boolean isNumberic(String arg) {
		try {
			Integer i = Integer.parseInt(arg);
			if(i != null) {
				return true;
			}
		} catch(NumberFormatException e) {
			return false;
		}
		return false;
	}

	@Override
	public String getName() {
		return getType().name();
	}

	@Override
	public ConfigType getType() {
		return ConfigType.KITS;
	}

	@Override
	public boolean isGenerated() {
		return f.exists();
	}

	@Override
	public boolean isGeneratedOnce() {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void generateConfig() {
		if(!isGenerated()) {
			FileConfigurationOptions opt = con.options();
			opt.header("this kit has customizeable permissions!\nfor each kit name its like xEssentials.kit.<nameofkit>\nhowever this is a little scheme how to use the data value system:\nid:subdata:amount");
			String[] DiamondKit = {Material.DIAMOND_PICKAXE.getId()+":0:1", Material.DIAMOND_SPADE.name()+":0:1", Material.DIAMOND_AXE.name()+":0:1", Material.DIAMOND_SWORD+":0:1", Material.MELON+":0:30"};
			String[] IronKit = {Material.IRON_PICKAXE.getId()+":0:1", Material.IRON_SPADE.name()+":0:1", Material.IRON_AXE.name()+":0:1", Material.IRON_SWORD+":0:1", Material.MELON+":0:30"};
			String[] WoodKit = {Material.WOOD_PICKAXE.getId()+":0:1", Material.WOOD_SPADE.name()+":0:1", Material.WOOD_AXE.name()+":0:1", Material.WOOD_SWORD+":0:1", Material.MELON+":0:30"};
			con.set("cooldown.isEnabled", true);
			con.set("cooldown.time", 100);
			con.set("kit.diamondkit", DiamondKit);
			con.set("kit.ironkit", IronKit);
			con.set("kit.woodkit", WoodKit);
			try {
				con.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void reload() {
		kitss.clear();
		try {
			con.load(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		Kit[] kits = parseKits(con);
		for(Kit kit : kits) {
			kitss.put(kit.getKitName(), kit);
		}
	}

}
