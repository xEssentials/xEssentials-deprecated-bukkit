package tv.mineinthebox.essentials.configurations;

import java.util.List;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class MiscConfig {
	
	public boolean isGatesEnabled() {
		return (Boolean)Configuration.getConfigValue(ConfigType.MISC, "gatesEnable");
	}
	
	public boolean isGateRedstoneEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.MISC, "gatesRedstone");
	}
	
	public int getMaxGateSize() {
		return (Integer) Configuration.getConfigValue(ConfigType.MISC, "gatesSize");
	}
	
	public boolean isBridgesEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.MISC, "bridgesEnable");
	}
	
	public int getMaxBridgeSize() {
		return (Integer) Configuration.getConfigValue(ConfigType.MISC, "bridgesSize");
	}
	
	public boolean isElevatorsEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.MISC, "elevatorEnable");
	}
	
	public int getMaxElevatorObstructionSize() {
		return (Integer) Configuration.getConfigValue(ConfigType.MISC, "elevatorSize");
	}
	
	public boolean isChairsEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.MISC, "chairsEnable");
	}
	
	public boolean isChairMonsterOff() {
		return (Boolean) Configuration.getConfigValue(ConfigType.MISC, "chairsMonsters");
	}
	
	public boolean isBooksEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.MISC, "booksEnable");
	}
	
	@SuppressWarnings("unchecked")
	public String[] getBookMessages() {
		List<String> list = (List<String>)Configuration.getConfigValue(ConfigType.MISC, "bookTexts");
		String[] messages = new String[list.size()];
		for(int i = 0; i < list.size(); i++) {
			messages[i] = list.get(i);
		}
		return messages;
	}
}
