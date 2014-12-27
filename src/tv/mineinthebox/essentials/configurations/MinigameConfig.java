package tv.mineinthebox.essentials.configurations;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class MinigameConfig {
	
	/**
	 * @author xize
	 * @param returns true if minigames otherwise false
	 * @return boolean
	 */
	public boolean isMinigamesEnabled() {
		return (Boolean) Configuration.getConfigValue(ConfigType.MINIGAMES, "minigamesenabled");
	}

}
