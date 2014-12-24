package tv.mineinthebox.essentials.configurations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.PluginCommand;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.ConfigType;
import tv.mineinthebox.essentials.helpers.CommandHelper;

public class CommandConfig {
	
	/**
	 * @author xize
	 * @param returns the configuration with all commands
	 * @return HashMap<String, Boolean>()
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Boolean> getCommandList() {
		return (HashMap<String, Boolean>) Configuration.getConfigValue(ConfigType.COMMAND, "commands");
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
		CommandHelper.unRegisterBukkitCommand(cmd);
	}
	
	public boolean isRegistered(String cmd) {
		return CommandHelper.isRegistered(cmd);
	}
	
	public void registerBukkitCommand(PluginCommand cmd) {
		CommandHelper.registerBukkitCommand(cmd);
	}
}
