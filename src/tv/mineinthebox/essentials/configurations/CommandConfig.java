package tv.mineinthebox.essentials.configurations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.commands.CommandList;
import tv.mineinthebox.essentials.enums.ConfigType;
import tv.mineinthebox.essentials.enums.LogType;

public class CommandConfig extends Configuration {

	private final HashMap<String, Boolean> commands = new HashMap<String, Boolean>();

	public CommandConfig(xEssentials pl, File f, FileConfiguration con) {
		super(pl, f, con);
	}


	/**
	 * returns a HashMap with the commands
	 * 
	 * @author xize
	 * @return HashMap<String, Boolean>
	 */
	public HashMap<String, Boolean> getCommandList() {
		return commands;
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

	@Override
	public boolean isGenerated() {
		return f.exists();
	}

	@Override
	public boolean isGeneratedOnce() {
		return false;
	}

	@Override
	public void generateConfig() {
		if(!isGenerated()) {
			FileConfiguration con = YamlConfiguration.loadConfiguration(f);
			FileConfigurationOptions opt = con.options();
			opt.header("here you can specify whenever a command should be unregistered or not\nfor example you got a other plugin which has the /home command this would basicly conflict with xEssentials\nhereby you can change the behaviour by unregistering xEssentials commands here.");
			CommandList list = new CommandList();
			List<String> commands = new ArrayList<String>(Arrays.asList(list.getAllCommands));

			con.set("global-command-display.prefix", "&2[%s]:  ");
			con.set("global-command-display.suffix", "&7");
			
			//blacklist, this will be handle by the configuration it self.
			commands.remove("money");
			commands.remove("cprivate");
			commands.remove("cmodify");
			commands.remove("cremove");
			commands.remove("portals");

			for(String command : commands) {
				con.set("command."+command+".enable", true);
			}

			try {
				con.save(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			reload();
		} else {
			CommandList commandlist = new CommandList();
			List<String> commands = new ArrayList<String>(Arrays.asList(commandlist.getAllCommands));

			//blacklist, this will be handle by the configuration it self.
			commands.remove("money");
			commands.remove("cprivate");
			commands.remove("cmodify");
			commands.remove("cremove");
			commands.remove("portals");

			List<String> orginal = Arrays.asList(con.getConfigurationSection("command").getKeys(false).toArray(new String[0]));

			if(commands.size() != orginal.size()) {
				xEssentials.log("new commands detected!, adding them right now inside the command config!", LogType.INFO);
				for(String cmd : commands) {
					if(!orginal.contains(cmd)) {
						xEssentials.log("registering new command: " + cmd + " in commands.yml", LogType.INFO);
						con.set("command."+cmd+".enable", true);
					}
				}
				try {
					con.save(f);
				} catch (IOException e) {
					e.printStackTrace();
				}
				reload();
			} else {
				xEssentials.log("there where no newer commands found to be added in commands.yml", LogType.INFO);
			}
		}
	}

	@Override
	public void reload() {
		commands.clear();
		try {
			con.load(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
		generateConfig();
		
	}
}
