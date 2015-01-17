package tv.mineinthebox.essentials;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import tv.mineinthebox.essentials.commands.CommandList;
import tv.mineinthebox.essentials.commands.SimpleCommand;
import tv.mineinthebox.essentials.enums.LogType;
import tv.mineinthebox.essentials.events.CustomEventHandler;
import tv.mineinthebox.essentials.events.Handler;
import tv.mineinthebox.essentials.greylist.GreyListServer;
import tv.mineinthebox.essentials.helpers.MojangUUID;
import tv.mineinthebox.essentials.helpers.OnlinePlayersHelper;
import tv.mineinthebox.essentials.hook.Hooks;
import tv.mineinthebox.essentials.interfaces.XPlayer;
import tv.mineinthebox.essentials.managers.Manager;
import tv.mineinthebox.simpleserver.SimpleServer;


public class xEssentials extends JavaPlugin {

	private static xEssentials pl;

	private final Configuration conf = new Configuration();
	private final Handler Handler = new Handler();
	private final CustomEventHandler customhandler = new CustomEventHandler();
	private final CommandList cmdlist = new CommandList();
	private final MojangUUID uuid = new MojangUUID();

	private static Manager manager;

	public void onEnable() {
		pl = this;
		log("has been enabled", LogType.INFO);
		conf.createConfigs();
		manager = new Manager();
		Handler.start();
		customhandler.startCustomEvents();
		if(xEssentials.getOnlinePlayers().length > 0) {
			xEssentials.getManagers().getPlayerManager().reloadPlayerBase();
		}
		for(String cmd : cmdlist.getAllCommands) {
			getCommand(cmd).setExecutor(new SimpleCommand());	
		}
		if(Configuration.getEntityConfig().isRealisticGlassEnabled()) {
			xEssentials.getManagers().getRealisticGlassManager().loadGlassBlocks();
		}
		xEssentials.getManagers().getTPSManager().startTps();
		Configuration.HandleCommandManager();
		if(Configuration.getGrayListConfig().isEnabled()) {
			SimpleServer server = xEssentials.getManagers().getGreylistManager();
			server.addListener(new GreyListServer());
			try {
				server.startServer();
				log(server.getName() + " has been started on port " + server.getPort(), LogType.INFO);
			} catch (Exception e) {
				log("failed to create server " + server.getName() + " on port " + server.getPort(), LogType.SEVERE);
				e.printStackTrace();
			}
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
		xEssentials.getManagers().getBackPackManager().loadBackpacks();
	}

	public void onDisable() {
		
		for(XPlayer xp : xEssentials.getManagers().getPlayerManager().getPlayers()) {
			xp.save();
		}
		
		if(getUUIDManager().isExecutorServiceRunning()) {
			getUUIDManager().shutdownExecutorService();
		}
		if(Configuration.getMinigameConfig().isMinigamesEnabled()) {
			getManagers().getMinigameManager().onDisable();
		}
		xEssentials.getManagers().getPlayerManager().clear();
		if(Configuration.getEntityConfig().isRealisticGlassEnabled()) {
			xEssentials.getManagers().getRealisticGlassManager().saveGlassBlocks();
		}
		if(Configuration.getChatConfig().isRssBroadcastEnabled()) {
			xEssentials.getManagers().getRssManager().saveLastFeed();
		}

		if(xEssentials.getManagers().getGreylistManager().isRunning()) {
			SimpleServer server = xEssentials.getManagers().getGreylistManager();
			log("stopping server " + server.getName() + " at port " + server.getPort(), LogType.INFO);
			try {
				server.stopServer();
				log("server successfully stopped!", LogType.INFO);
			} catch (IOException e) {
				e.printStackTrace();
			}
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

		try {
			conf.finalize();
			finalize();
		} catch(Throwable e) {
			e.printStackTrace();
		}
		log("has been disabled!", LogType.INFO);
	}

	/**
	 * 
	 * @author xize
	 * @param String, logType
	 * @param uses to log things through the console
	 * 
	 */
	public void log(String message, LogType type) {
		Bukkit.getConsoleSender().sendMessage(type.getPrefix() + message);
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
	public static Player[] getOnlinePlayers() {
		return OnlinePlayersHelper.getOnlinePlayers();
	}

	/**
	 * @author xize
	 * @param p - returns the uuid manager
	 * @return MojangUUID
	 */
	public static MojangUUID getUUIDManager() {
		return xEssentials.getPlugin().uuid;
	}

	@Override
	public void finalize() throws Throwable {
		super.finalize();
	}
}
