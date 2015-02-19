package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class MiscConfig implements Configuration {
	
	private final File f;
	private final FileConfiguration con;
	
	public  MiscConfig(File f, FileConfiguration con) {
		this.f = f;
		this.con = con;
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
			con.set("gates.enable", true);
			con.set("gates.redstone-powered", true);
			con.set("gates.max-size", 100);
			con.set("bridges.enable", true);
			con.set("bridges.max-size", 100);
			con.set("elevator.enable", true);
			con.set("elevator.obstruction-space", 10);
			con.set("chairs.enable", true);
			con.set("chairs.ignore-monsters", true);
			String[] bookData = {
					"in dundrom village we've found new special gear, but herobrine had killed us...",
					"herobrine had losed from the army of villagers and was not able to speak to the conference...",
					"notch had a nightmare about guardians snabling his feets...",
					"herobrine died by listening Knife party...",
					"Knife party ancidentaly found Herobrine on the toilet and started to party..."
			};
			con.set("bookcases.enable", true);
			con.set("bookcases.texts", bookData);
			try {
				con.save(f);
			} catch (IOException e) {
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
