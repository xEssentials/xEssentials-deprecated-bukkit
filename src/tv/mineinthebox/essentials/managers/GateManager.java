package tv.mineinthebox.essentials.managers;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;
import tv.mineinthebox.essentials.instances.Gate;

public class GateManager {

	private final HashMap<String, Gate> gates = new HashMap<String, Gate>();
	
	public boolean contains(Gate gate) {
		return gates.containsValue(gate);
	}
	
	public boolean isGateSign(Block block) {
		return gates.containsKey(getSerialized(block));
	}
	
	public Gate getGateBySign(Block block) {
		return gates.get(getSerialized(block));
	}
	
	public Gate[] getGates() {
		Gate[] args = new Gate[gates.size()];
		int i = 0;
		for(Gate gate : gates.values()) {
			args[i] = gate;
			i++;
		}
		return args;
	}
	
	public void addGate(Gate gate) {
		gates.put(gate.getId(), gate);
	}
	
	public void removeGate(Gate gate) {
		gates.remove(gate.getId());
	}
	
	public boolean isGateFrameBlock(Block block) {
		for(Gate gate : getGates()) {
			for(Block blocka : gate.getFrameBlocks()) {
				if(blocka.equals(block)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isGateFenceBlock(Block block) {
		for(Gate gate : getGates()) {
			for(Block blocka : gate.getInnerBlocks()) {
				if(blocka.equals(block)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public Gate getGateFromFrameBlock(Block block) {
		for(Gate gate : getGates()) {
			for(Block blocka : gate.getFrameBlocks()) {
				if(blocka.equals(block)) {
					return gate;
				}
			}
		}
		return null;
	}
	
	private String getSerialized(Block block) {
		UUID id = UUID.nameUUIDFromBytes((block.getWorld().getName()+":"+block.getX()+":"+block.getY()+":"+block.getZ()).getBytes());
		return id.toString();
	}
	
	public void reloadGates() {
		gates.clear();
		File dir = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "misc" + File.separator + "gates");
		if(dir.isDirectory()) {
			File[] files = dir.listFiles();
			for(File f : files) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				Gate gate = new Gate(f, con);
				if(gate.getWorld() instanceof World) {
					if(Configuration.getDebugConfig().isEnabled()) {
						xEssentials.getPlugin().log("gate " + gate.getId() + " has been registered at: (" + gate.getSignBlock().getWorld().getName() + " x:" + gate.getSignBlock().getX()+" y:" + gate.getSignBlock().getY() + " z:" + gate.getSignBlock().getZ()+")", LogType.DEBUG);	
					}
					addGate(gate);
				} else {
					if(Configuration.getDebugConfig().isEnabled()) {
						xEssentials.getPlugin().log("unregistering gate " + gate.getId() + " world is not active.", LogType.DEBUG);	
					}
					gate.remove();
				}
			}
		}
	}

}
