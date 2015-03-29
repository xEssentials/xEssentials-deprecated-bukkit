package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.commands.CommandList;
import tv.mineinthebox.essentials.enums.ConfigType;

public class CommandConfig extends Configuration {
	
	private final List<String> cmdlist;
	
	public CommandConfig(xEssentials pl, File f, FileConfiguration con) {
		super(pl, f, con);
		preconfig.put("global-command-display.prefix", "&2[%s]:  ");
		preconfig.put("global-command-display.suffix", "&7");
		preconfig.put("global-command-display.player-highlight", "&e@");
		CommandList list = new CommandList();
		List<String> commands = new ArrayList<String>(Arrays.asList(list.getAllCommands));
		this.cmdlist = commands;
		//blacklist, this will be handle by the configuration it self.
		commands.remove("money");
		commands.remove("cprivate");
		commands.remove("cmodify");
		commands.remove("cremove");
		commands.remove("portals");
		for(String command : commands) {
			preconfig.put("command."+command+".enable", true);
		}
	}
	
	/**
	 * returns the player highlight style
	 * 
	 * @author xize
	 * @return String
	 */
	public String getPlayerHighLight() {
		return ChatColor.translateAlternateColorCodes('&', con.getString("global-command-display.player-highlight"));
	}

	/**
	 * returns a HashMap with the commands
	 * 
	 * @author xize
	 * @return Map<String, Boolean>
	 */
	public Map<String, Boolean> getCommandList() {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		for(String command : cmdlist) {
			map.put(command, con.getBoolean("command."+command+".enable"));
		}
		return map;
	}
	
	/**
	 * returns the prefix for the global command template
	 * 
	 * @author xize
	 * @return String
	 */
	public String getPrefix() {
		return ChatColor.translateAlternateColorCodes('&', con.getString("global-command-display.prefix"));
	}
	
	/**
	 * returns the suffix for the global command template
	 * 
	 * @author xize
	 * @return String
	 */
	public String getSuffix() {
		return ChatColor.translateAlternateColorCodes('&', con.getString("global-command-display.suffix"));
	}

	/**
	 * returns all unregistered commands
	 * 
	 * @author xize
	 * @return List<String>
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
	 * unregisters a bukkit command
	 * 
	 * @author xize, zeeveener
	 * @param cmd - the command
	 */
	public void unRegisterBukkitCommand(PluginCommand cmd) {
		pl.getManagers().getCommandManager().unRegisterBukkitCommand(cmd);
	}

	/**
	 * checks if a command was already registered, true when registered otherwise false
	 * 
	 * @author xize, zeeveener
	 * @param cmd - the command
	 * @return boolean
	 */
	public boolean isRegistered(String cmd) {
		return pl.getManagers().getCommandManager().isRegistered(cmd);
	}

	/**
	 * registers a bukkit command
	 * 
	 * @author xize, zeeveener
	 * @param cmd - the command
	 */
	public void registerBukkitCommand(PluginCommand cmd) {
		pl.getManagers().getCommandManager().registerBukkitCommand(cmd);
	}


	@Override
	public String getName() {
		return getType().name();
	}


	@Override
	public ConfigType getType() {
		return ConfigType.COMMAND;
	}
}
