package tv.mineinthebox.essentials.managers;

import java.io.File;
import java.util.HashMap;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;
import tv.mineinthebox.essentials.instances.Bridge;

public class BridgeManager {
	
	private final xEssentials pl;

	private final HashMap<String, Bridge> bridges = new HashMap<String, Bridge>();
	
	public BridgeManager(xEssentials pl) {
		this.pl = pl;
	}
	
	public boolean contains(Bridge bridge) {
		return bridges.containsValue(bridge);
	}
	
	public boolean isBridgeSign(Block block) {
		for(Bridge bridge : bridges.values()) {
			if(bridge.getSigns()[0].equals(block) || bridge.getSigns()[1].equals(block)) {
				return true;
			}
		}
		return false;
	}
	
	public Bridge getBridgeBySign(Block block) {
		for(Bridge bridge : bridges.values()) {
			if(bridge.getSigns()[0].equals(block) || bridge.getSigns()[1].equals(block)) {
				return bridge;
			}
		}
		return null;
	}
	
	public Bridge[] getBridges() {
		Bridge[] args = new Bridge[bridges.size()];
		int i = 0;
		for(Bridge bridge : bridges.values()) {
			args[i] = bridge;
			i++;
		}
		return args;
	}
	
	public void addBridge(Bridge bridge) {
		bridges.put(bridge.getId(), bridge);
	}
	
	public void removeBridge(Bridge bridge) {
		bridges.remove(bridge.getId());
	}
	
	public boolean isBridgeBlock(Block block) {
		for(Bridge bridge : getBridges()) {
			for(Block blocka : bridge.getBridgeBlocks()) {
				if(blocka.equals(block)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public Bridge getBridgeFromBlock(Block block) {
		for(Bridge bridge : getBridges()) {
			for(Block blocka : bridge.getBridgeBlocks()) {
				if(blocka.equals(block)) {
					return bridge;
				}
			}
		}
		return null;
	}
	
	public void reloadBridges() {
		bridges.clear();
		File dir = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "misc" + File.separator + "bridges");
		if(dir.isDirectory()) {
			File[] files = dir.listFiles();
			for(File f : files) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				Bridge bridge = new Bridge(f, con, pl);
				if(bridge.getWorld() instanceof World) {
					if(pl.getConfiguration().getDebugConfig().isEnabled()) {
						xEssentials.log("bridge " + bridge.getId() + " has been registered at: (" + bridge.getSigns()[0].getWorld().getName() + " x:" + bridge.getSigns()[0].getX()+" y:" + bridge.getSigns()[0].getY() + " z:" + bridge.getSigns()[0].getZ()+")", LogType.DEBUG);	
					}
					addBridge(bridge);
				} else {
					if(pl.getConfiguration().getDebugConfig().isEnabled()) {
						xEssentials.log("unregistering gate " + bridge.getId() + " world is not active.", LogType.DEBUG);	
					}
					bridge.remove();
				}
			}
		}
	}

}
