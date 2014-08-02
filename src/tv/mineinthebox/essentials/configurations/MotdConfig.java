package tv.mineinthebox.essentials.configurations;

import java.util.ArrayList;
import java.util.ListIterator;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;

public class MotdConfig {
	
	/**
	 * @author xize
	 * @param returns true whenever the normal motd system is enabled
	 * @return boolean
	 */
	public boolean isNormalMotdEnabled() {
		Boolean bol = (Boolean) Configuration.getConfigValue(ConfigType.MOTD, "NormalEnable");
		return bol;
	}
	
	/**
	 * @author xize
	 * @param returns true whenever the random motd system is enabled
	 * @return boolean
	 */
	public boolean isRandomMotdEnabled() {
		Boolean bol = (Boolean) Configuration.getConfigValue(ConfigType.MOTD, "RandomEnable");
		return bol;
	}
	
	/**
	 * @author xize
	 * @param returns a List of all motd messages for the random motd system
	 * @return List<String>()
	 */
	@SuppressWarnings("unchecked")
	public ListIterator<String> getMotdMessages() {
		ArrayList<String> list = (ArrayList<String>) Configuration.getConfigValue(ConfigType.MOTD, "messages");
		return list.listIterator();
	}
	
	/**
	 * @author xize
	 * @param returns a single motd message for the normal motd system
	 * @return String
	 */
	public String getMotdMessage() {
		String s = (String) Configuration.getConfigValue(ConfigType.MOTD, "message");
		return s;
	}

}
