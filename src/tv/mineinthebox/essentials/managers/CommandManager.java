package tv.mineinthebox.essentials.managers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.commands.SimpleCommand;

public class CommandManager {

	private final xEssentials pl;

	public CommandManager(xEssentials pl) {
		this.pl = pl;
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
	private Object getPrivateField(Object object, String field)throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = object.getClass();
		Field objectField = clazz.getDeclaredField(field);
		objectField.setAccessible(true);
		Object result = objectField.get(object);
		objectField.setAccessible(false);
		return result;

	}

	/**
	 * unregister a command, credits to zeeveener for his awesome code to unregister commands!
	 * 
	 * @author zeeveener, xize
	 * @param cmd - the command to be unregistered
	 */
	public void unRegisterBukkitCommand(PluginCommand cmd) {
		try {
			Object result = getPrivateField(Bukkit.getServer().getPluginManager(), "commandMap");
			SimpleCommandMap commandMap = (SimpleCommandMap) result;
			Object map = getPrivateField(commandMap, "knownCommands");
			@SuppressWarnings("unchecked")
			HashMap<String, Command> knownCommands = (HashMap<String, Command>) map;
			knownCommands.remove("xessentials"+":"+cmd.getName());
			if(knownCommands.containsKey(cmd.getName()) && knownCommands.get(cmd.getName().toLowerCase()).toString().contains(pl.getName())) {
				knownCommands.remove(cmd.getName());
			}
			for (String alias : cmd.getAliases()){
				if(knownCommands.containsKey("xessentials:"+alias) && knownCommands.get("xessentials:"+alias).toString().contains(pl.getName())){
					knownCommands.remove("xessentials:"+alias);
				}
				if(knownCommands.containsKey(alias) && knownCommands.get(alias).toString().contains(pl.getName())){
					knownCommands.remove(alias);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * returns true if the command is registered inside xEssentials otherwise false
	 * 
	 * @author xize
	 * @param cmd - the command
	 * @return boolean
	 */
	public boolean isRegistered(String cmd) {
		try {
			Object result = getPrivateField(Bukkit.getServer().getPluginManager(), "commandMap");
			SimpleCommandMap commandMap = (SimpleCommandMap) result;
			Object map = getPrivateField(commandMap, "knownCommands");
			@SuppressWarnings("unchecked")
			HashMap<String, Command> knownCommands = (HashMap<String, Command>) map;
			if(knownCommands.containsKey("xessentials"+":"+cmd) || (knownCommands.containsKey(cmd) && knownCommands.get(cmd).toString().contains(pl.getName()))) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * re-registers the command in the plugin
	 * 
	 * @author zeeveener, xize
	 * @param cmd - the command
	 */
	@SuppressWarnings("unchecked")
	public void registerBukkitCommand(PluginCommand cmd) {
		try {
			Object result = getPrivateField(Bukkit.getServer().getPluginManager(), "commandMap");
			SimpleCommandMap commandMap = (SimpleCommandMap) result;
			Object map = getPrivateField(commandMap, "knownCommands");
			HashMap<String, Command> knownCommands = (HashMap<String, Command>) map;
			knownCommands.put("xessentials:"+cmd.getName(), cmd);
			knownCommands.put(cmd.getName(), cmd);
			List<String> aliases = (List<String>)pl.getDescription().getCommands().get(cmd.getName()).get("aliases");
			for(String alias : aliases){
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

	/**
	 * forces to create a PluginCommand
	 * 
	 * @author xize
	 * @param cmd - the command to be created as instance
	 * @return PluginCommand
	 */
	@SuppressWarnings("unchecked")
	public PluginCommand createPluginCommand(String cmd) {
		try {
			//forcibly make a new PluginCommand object
			Class<?> clazz = Class.forName("org.bukkit.command.PluginCommand");
			Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, Plugin.class);
			constructor.setAccessible(true);
			Field mf = Constructor.class.getDeclaredField("modifiers");
			mf.setAccessible(true);
			mf.setInt(constructor, constructor.getModifiers() &~Modifier.PROTECTED);

			PluginCommand command = (PluginCommand) constructor.newInstance(cmd, pl);
			command.setExecutor(new SimpleCommand(pl));
			List<String> aliases = (List<String>) pl.getDescription().getCommands().get(command.getName()).get("aliases");
			command.setAliases(aliases);

			constructor.setAccessible(false);
			mf.setAccessible(false);

			return command;  
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
