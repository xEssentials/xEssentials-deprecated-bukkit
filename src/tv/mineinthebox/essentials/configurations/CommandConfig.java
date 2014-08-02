package tv.mineinthebox.essentials.configurations;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.ConfigType;

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
	 * @param object
	 * @param field
	 * @return Object
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private Object getPrivateField(Object object, String field)throws SecurityException,

	NoSuchFieldException, IllegalArgumentException, IllegalAccessException {

		Class<?> clazz = object.getClass();

		Field objectField = clazz.getDeclaredField(field);

		objectField.setAccessible(true);

		Object result = objectField.get(object);

		objectField.setAccessible(false);

		return result;

	}

	/**
	 * @author zeeveener
	 * @param unregister a command, credits to zeeveener for his awesome code to unregister commands!
	 */
	public void unRegisterBukkitCommand(PluginCommand cmd) {
		try {
			Object result = getPrivateField(Bukkit.getServer().getPluginManager(), "commandMap");
			SimpleCommandMap commandMap = (SimpleCommandMap) result;
			Object map = getPrivateField(commandMap, "knownCommands");
			@SuppressWarnings("unchecked")
			HashMap<String, Command> knownCommands = (HashMap<String, Command>) map;
			knownCommands.remove("xessentials"+":"+cmd.getName());
			if(knownCommands.containsKey(cmd.getName()) && knownCommands.get(cmd.getName().toLowerCase()).toString().contains(xEssentials.getPlugin().getName())) {
				knownCommands.remove(cmd.getName());
			}
			for (String alias : cmd.getAliases()){
				if(knownCommands.containsKey("xessentials:"+alias) && knownCommands.get("xessentials:"+alias).toString().contains(xEssentials.getPlugin().getName())){
					knownCommands.remove("xessentials:"+alias);
				}
				if(knownCommands.containsKey(alias) && knownCommands.get(alias).toString().contains(xEssentials.getPlugin().getName())){
					knownCommands.remove(alias);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isRegistered(String cmd) {
		try {
			Object result = getPrivateField(Bukkit.getServer().getPluginManager(), "commandMap");
			SimpleCommandMap commandMap = (SimpleCommandMap) result;
			Object map = getPrivateField(commandMap, "knownCommands");
			@SuppressWarnings("unchecked")
			HashMap<String, Command> knownCommands = (HashMap<String, Command>) map;
			if(knownCommands.containsKey("xessentials"+":"+cmd) || (knownCommands.containsKey(cmd) && knownCommands.get(cmd).toString().contains(xEssentials.getPlugin().getName()))) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public void registerBukkitCommand(PluginCommand cmd) {
		try {
			Object result = getPrivateField(Bukkit.getServer().getPluginManager(), "commandMap");
			SimpleCommandMap commandMap = (SimpleCommandMap) result;
			Object map = getPrivateField(commandMap, "knownCommands");
			HashMap<String, Command> knownCommands = (HashMap<String, Command>) map;
			knownCommands.put("xessentials:"+cmd.getName(), cmd);
			knownCommands.put(cmd.getName(), cmd);
			List<String> aliases = (List<String>)xEssentials.getPlugin().getDescription().getCommands().get(cmd.getName()).get("aliases");
			for(String alias :aliases){
				if(!knownCommands.containsKey("xessentials:"+alias)){
					knownCommands.put("xessentials:"+alias, cmd);
				}
				if(!knownCommands.containsKey(alias)){
					knownCommands.put(alias, cmd);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
