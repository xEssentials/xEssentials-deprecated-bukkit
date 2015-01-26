package tv.mineinthebox.essentials.configurations;

import java.util.List;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.ConfigType;

public class MiscConfig {
	
	private final xEssentials pl;
	
	public  MiscConfig(xEssentials pl) {
		this.pl = pl;
	}
	
	public boolean isGatesEnabled() {
		return (Boolean)pl.getConfiguration().getConfigValue(ConfigType.MISC, "gatesEnable");
	}
	
	public boolean isGateRedstoneEnabled() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.MISC, "gatesRedstone");
	}
	
	public int getMaxGateSize() {
		return (Integer) pl.getConfiguration().getConfigValue(ConfigType.MISC, "gatesSize");
	}
	
	public boolean isBridgesEnabled() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.MISC, "bridgesEnable");
	}
	
	public int getMaxBridgeSize() {
		return (Integer) pl.getConfiguration().getConfigValue(ConfigType.MISC, "bridgesSize");
	}
	
	public boolean isElevatorsEnabled() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.MISC, "elevatorEnable");
	}
	
	public int getMaxElevatorObstructionSize() {
		return (Integer) pl.getConfiguration().getConfigValue(ConfigType.MISC, "elevatorSize");
	}
	
	public boolean isChairsEnabled() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.MISC, "chairsEnable");
	}
	
	public boolean isChairMonsterOff() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.MISC, "chairsMonsters");
	}
	
	public boolean isBooksEnabled() {
		return (Boolean) pl.getConfiguration().getConfigValue(ConfigType.MISC, "booksEnable");
	}
	
	@SuppressWarnings("unchecked")
	public String[] getBookMessages() {
		List<String> list = (List<String>)pl.getConfiguration().getConfigValue(ConfigType.MISC, "bookTexts");
		String[] messages = new String[list.size()];
		for(int i = 0; i < list.size(); i++) {
			messages[i] = list.get(i);
		}
		return messages;
	}
}
