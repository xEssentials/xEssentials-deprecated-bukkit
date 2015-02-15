package tv.mineinthebox.essentials.configurations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.PluginCommand;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.ConfigType;

public class CommandConfig {
	
	private final xEssentials pl;
	
	public CommandConfig(xEssentials pl) {
		this.pl = pl;
	}
	
	/**
	 * @author xize
	 * @param returns the configuration with all commands
	 * @return HashMap<String, Boolean>()
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Boolean> getCommandList() {
		return (HashMap<String, Boolean>) pl.getConfiguration().getConfigValue(ConfigType.COMMAND, "commands");
	}

	/**
	 * @author xize
	 * @param get the list with all unregistered commands!
	 * @return List<String>()
	 */
	public List<String> getUnregisteredCommands() {
		HashMap<String, Boolean> hash = new HashMap<String, Boolean>(getCommandList());
		List<String> list = new ArrayList<String>();
		for(String key : hash.keySet().toArray(new String[0])) {
			if(!hash.get(key)) {
				list.add(key);
			}
		}
		return list;
	}

	/**
	 * @author zeeveener
	 * @param unregister a command, credits to zeeveener for his awesome code to unregister commands!
	 */
	public void unRegisterBukkitCommand(PluginCommand cmd) {
		pl.getManagers().getCommandManager().unRegisterBukkitCommand(cmd);
	}
	
	public boolean isRegistered(String cmd) {
		return pl.getManagers().getCommandManager().isRegistered(cmd);
	}
	
	public void registerBukkitCommand(PluginCommand cmd) {
		pl.getManagers().getCommandManager().registerBukkitCommand(cmd);
	}
}
