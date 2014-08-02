package tv.mineinthebox.essentials.commands;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;

public class CmdTest {
	
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
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Command> getMap() throws Exception {
		Object result = getPrivateField(Bukkit.getServer().getPluginManager(), "commandMap");
		SimpleCommandMap commandMap = (SimpleCommandMap) result;
		Object map = getPrivateField(commandMap, "knownCommands");
		return (HashMap<String, Command>) map;
	}
	
	public boolean __execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("test")) {
			try {
				HashMap<String, Command> commands = new HashMap<String, Command>(getMap());
				
				Iterator<Entry<String, Command>> it = commands.entrySet().iterator();
				while(it.hasNext()) {
					Entry<String, Command> entry = it.next();
					if(entry.getKey().contains("money") || entry.getKey().contains("bal")) {
						sender.sendMessage("key: " + entry.getKey());
						sender.sendMessage("value: " + entry.getValue());
					}
				} 
			} catch(Exception e) {
				
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("test")) {
		
		}
		return false;
	}

}
