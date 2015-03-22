package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class MiscConfig extends Configuration {
	
	public  MiscConfig(File f, FileConfiguration con) {
		super(f, con);
		preconfig.put("gates.enable", true);
		preconfig.put("gates.redstone-powered", true);
		preconfig.put("gates.max-size", 100);
		preconfig.put("bridges.enable", true);
		preconfig.put("bridges.max-size", 100);
		preconfig.put("elevator.enable", true);
		preconfig.put("elevator.obstruction-space", 10);
		preconfig.put("chairs.enable", true);
		preconfig.put("chairs.ignore-monsters", true);
		String[] bookData = {
				"in dundrom village we've found new special gear, but herobrine had killed us...",
				"herobrine had losed from the army of villagers and was not able to speak to the preconfigference...",
				"notch had a nightmare about guardians snabling his feets...",
				"herobrine died by listening Knife party...",
				"Knife party ancidentaly found Herobrine on the toilet and started to party..."
		};
		preconfig.put("bookcases.enable", true);
		preconfig.put("bookcases.texts", bookData);
	}
	
	/**
	 * returns true if the gate system is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isGatesEnabled() {
		return con.getBoolean("gates.enable");
	}
	
	/**
	 * returns true if the gates can be redstone activated, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isGateRedstoneEnabled() {
		return con.getBoolean("gates.redstone-powered");
	}
	
	/**
	 * returns the max size how big a gate can be
	 * 
	 * @author xize
	 * @return Integer
	 */
	public int getMaxGateSize() {
		return con.getInt("gates.max-size");
	}
	
	/**
	 * returns true if the bridge system is enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isBridgesEnabled() {
		return con.getBoolean("bridges.enable");
	}
	
	/**
	 * returns the max size how big a bridge can be
	 * 
	 * @author xize
	 * @return Integer
	 */
	public int getMaxBridgeSize() {
		return con.getInt("bridges.max-size");
	}
	
	/**
	 * returns true if elevators are enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isElevatorsEnabled() {
		return con.getBoolean("elevator.enable");
	}
	
	/**
	 * returns the max obstruction size of a elevator
	 * 
	 * @author xize
	 * @return Integer
	 */
	public int getMaxElevatorObstructionSize() {
		return con.getInt("elevator.obstruction-space");
	}
	
	/**
	 * returns true if chairs are enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isChairsEnabled() {
		return con.getBoolean("chairs.enable");
	}
	
	/**
	 * returns true if monsters shall not target a player whilst sitting on a chair
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isChairMonsterOff() {
		return con.getBoolean("chairs.ignore-monsters");
	}
	
	/**
	 * returns true if books are enabled, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isBooksEnabled() {
		return con.getBoolean("bookcases.enable");
	}
	
	/**
	 * returns all book messages
	 * 
	 * @author xize
	 * @return String[]
	 */
	public String[] getBookMessages() {
		List<String> list = con.getStringList("bookcases.texts");
		String[] messages = new String[list.size()];
		for(int i = 0; i < list.size(); i++) {
			messages[i] = list.get(i);
		}
		return messages;
	}

	@Override
	public String getName() {
		return getType().name();
	}

	@Override
	public ConfigType getType() {
		return ConfigType.MISC;
	}
}
