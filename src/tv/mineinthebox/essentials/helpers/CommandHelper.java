package tv.mineinthebox.essentials.helpers;

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

public class CommandHelper {

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
	private static Object getPrivateField(Object object, String field)throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
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
	public static void unRegisterBukkitCommand(PluginCommand cmd) {
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

	public static boolean isRegistered(String cmd) {
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
	public static void registerBukkitCommand(PluginCommand cmd) {
		try {
			Object result = getPrivateField(Bukkit.getServer().getPluginManager(), "commandMap");
			SimpleCommandMap commandMap = (SimpleCommandMap) result;
			Object map = getPrivateField(commandMap, "knownCommands");
			HashMap<String, Command> knownCommands = (HashMap<String, Command>) map;
			knownCommands.put("xessentials:"+cmd.getName(), cmd);
			knownCommands.put(cmd.getName(), cmd);
			List<String> aliases = (List<String>)xEssentials.getPlugin().getDescription().getCommands().get(cmd.getName()).get("aliases");
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

	@SuppressWarnings("unchecked")
	public static PluginCommand createPluginCommand(String cmd) {
		try {
			//forcibly make a new PluginCommand object
			Class<?> clazz = Class.forName("org.bukkit.command.PluginCommand");
			Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, Plugin.class);
			constructor.setAccessible(true);
			Field mf = Constructor.class.getDeclaredField("modifiers");
			mf.setAccessible(true);
			mf.setInt(constructor, constructor.getModifiers() &~Modifier.PROTECTED);

			PluginCommand command = (PluginCommand) constructor.newInstance(cmd, xEssentials.getPlugin());
			command.setExecutor(new SimpleCommand());
			List<String> aliases = (List<String>) xEssentials.getPlugin().getDescription().getCommands().get(command.getName()).get("aliases");
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
