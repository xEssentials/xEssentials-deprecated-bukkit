package tv.mineinthebox.essentials;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import tv.mineinthebox.essentials.commands.CommandList;
import tv.mineinthebox.essentials.commands.command;
import tv.mineinthebox.essentials.enums.LogType;
import tv.mineinthebox.essentials.events.CustomEventHandler;
import tv.mineinthebox.essentials.events.Handler;
import tv.mineinthebox.essentials.hook.Hooks;
import tv.mineinthebox.essentials.managers.Manager;


public class xEssentials extends JavaPlugin {

	private static xEssentials pl;

	private final Configuration conf = new Configuration();
	private final Handler Handler = new Handler();
	private final CustomEventHandler customhandler = new CustomEventHandler();
	private final CommandList cmdlist = new CommandList();

	private static Manager manager;

	public void onEnable() {
		pl = this;
		log("has been enabled", LogType.INFO);
		conf.createConfigs();
		manager = new Manager();
		conf.loadMiniGames();
		Handler.start();
		customhandler.startCustomEvents();
		if(xEssentials.getOnlinePlayers().length > 0) {
			xEssentials.getManagers().getPlayerManager().reloadPlayerBase();
		}
		for(String cmd : cmdlist.getAllCommands) {
			getCommand(cmd).setExecutor(new command());	
		}
		if(Configuration.getPlayerConfig().isRealisticGlassEnabled()) {
			xEssentials.getManagers().getRealisticGlassManager().loadGlassBlocks();
		}
		xEssentials.getManagers().getTPSManager().startTps();
		Configuration.HandleCommandManager();
		if(Configuration.getGrayListConfig().isEnabled()) {
			xEssentials.getManagers().getGreylistManager().start();
		}

		if(Hooks.isVaultEnabled()) {
			if(Configuration.getEconomyConfig().isEconomyEnabled()) {
				xEssentials.getManagers().getVaultManager().hookEconomyInVault();
			}
		}

		if(Configuration.getEntityConfig().isExplosionRegenEnabled()) {
			xEssentials.getManagers().getExplosionRegenManager().loadRegenObjects();
		}
		
		if(Configuration.getMiscConfig().isGatesEnabled()) {
			xEssentials.getManagers().getGateManager().reloadGates();
		}
		if(Configuration.getMiscConfig().isBridgesEnabled()) {
			xEssentials.getManagers().getBridgeManager().reloadBridges();
		}
		if(Configuration.getEntityConfig().isRealisticWaterEnabled()) {
			xEssentials.getManagers().getRealisticWaterManager().start();
		}
	}

	public void onDisable() {
		xEssentials.getManagers().getPlayerManager().clear();
		if(Configuration.getPlayerConfig().isRealisticGlassEnabled()) {
			xEssentials.getManagers().getRealisticGlassManager().saveGlassBlocks();
		}
		log("has been disabled!", LogType.INFO);

		if(Configuration.getChatConfig().isRssBroadcastEnabled()) {
			xEssentials.getManagers().getRssManager().saveLastFeed();
		}

		if(xEssentials.getManagers().getGreylistManager().isRunning()) {
			xEssentials.getManagers().getGreylistManager().stop();
		}

		if(xEssentials.getManagers().getBroadcastManager().isRunning()) {
			xEssentials.getManagers().getBroadcastManager().stop();
		}

		if(Configuration.getEntityConfig().isExplosionRegenEnabled()) {
			for(World w : Bukkit.getWorlds()) {
				for(Entity entity : w.getEntities()) {
					if(entity instanceof FallingBlock) {
						FallingBlock fb = (FallingBlock)entity;
						if(fb.getMaterial() != Material.GRAVEL || fb.getMaterial() != Material.SAND || fb.getMaterial() != Material.TNT) {
							fb.remove();
						}
					}
				}
			}
			xEssentials.getManagers().getExplosionRegenManager().saveRegenObjects();
			if(Configuration.getMiscConfig().isChairsEnabled()) {
				xEssentials.getManagers().getChairManager().killAll();	
			}
		}
		if(Configuration.getEntityConfig().isRealisticWaterEnabled()) {
			xEssentials.getManagers().getRealisticWaterManager().stop();
		}
	}

	/**
	 * 
	 * @author xize
	 * @param String, logType
	 * @param uses to log things through the console
	 * 
	 */
	public void log(String message, LogType type) {
		String prefix = "&2[&8xEssentials&2]&f";
		if(type == LogType.INFO) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+":"+message));
		} else if(type == LogType.SEVERE) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c[Error]"+prefix+":"+message));
		} else if(type == LogType.DEBUG) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[Debug]"+prefix+":"+message));
		}
	}

	/**
	 * 
	 * @author xize
	 * @param returns a static instance back for the plugin
	 * @return xEssentials (JavaPlugin)
	 * 
	 */
	public static xEssentials getPlugin() {
		return pl;
	}

	/**
	 * @author xize
	 * @param returns the managers
	 * @return Manager
	 */
	public static Manager getManagers() {
		return manager;
	}

	/**
	 * @author xize, shadypotato
	 * @param this is a modificated version from https://forums.bukkit.org/threads/code-snippet-workaround-for-the-new-bukkit-getonlineplayers-method.285072/ this version is wroted by myself to learn a bit about reflection, fall pits are Bukkit.getServer().getClass() ive learned to not use that.
	 * @param this will return a safe type compatibility array which could either be returned from a Collection, List, or the array it self.
	 * @return Player[]
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@SuppressWarnings("unchecked")
	public static Player[] getOnlinePlayers() {
		try {
			Method check = Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]);
			if(check.getReturnType() == Player[].class) {
				return (Player[])check.invoke(null, new Object[0]);	
			} else if(check.getReturnType() == List.class || check.getReturnType() == Collection.class) {
				Collection<Player> players = (Collection<Player>) check.invoke(null, new Object[0]);
				Player[] ps = new Player[(players.size())];
				int i = 0;
				for(Player p : players) {
					ps[i] = p;
					i++;
				}
				return ps;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		throw new NullPointerException("a fatal error has been occuried, please restart your server.");
	}
}
