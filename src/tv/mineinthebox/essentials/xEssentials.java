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
import tv.mineinthebox.essentials.interfaces.xEssentialsAPI;
import tv.mineinthebox.essentials.managers.Manager;
import tv.mineinthebox.simpleserver.SimpleServer;


public class xEssentials extends JavaPlugin implements xEssentialsAPI {

	private GlobalConfiguration conf;
	private Handler handler;
	private CustomEventHandler customhandler;
	private CommandList cmdlist;
	private MojangUUID uuid;
	private Manager manager;

	public void onEnable() {

		this.conf = new GlobalConfiguration(this);
		
		this.handler = new Handler(this);
		this.customhandler = new CustomEventHandler(this);
		this.cmdlist = new CommandList();
		this.uuid = new MojangUUID(this);
		this.manager = new Manager(this);

		handler.start();
		customhandler.startCustomEvents();

		if(getOnlinePlayers().length > 0) {
			getManagers().getPlayerManager().reloadPlayerBase();
		}
		for(String cmd : cmdlist.getAllCommands) {
			getCommand(cmd).setExecutor(new SimpleCommand(this));	
		}
		if(conf.getEntityConfig().isRealisticGlassEnabled()) {
			getManagers().getRealisticGlassManager().loadGlassBlocks();
		}

		getManagers().getTPSManager().startTps();

		if(conf.getGreyListConfig().isEnabled()) {
			SimpleServer server = getManagers().getGreylistManager();
			server.addListener(new GreyListServer(this));
			try {
				server.startServer();
				log(server.getName() + " has been started on port " + server.getPort(), LogType.INFO);
			} catch (Exception e) {
				log("failed to create server " + server.getName() + " on port " + server.getPort(), LogType.SEVERE);
				e.printStackTrace();
			}
		}

		if(Hooks.isVaultEnabled()) {
			if(conf.getEconomyConfig().isEconomyEnabled()) {
				getManagers().getVaultManager().hookEconomyInVault();
			}
		}

		if(conf.getEntityConfig().isExplosionRegenEnabled()) {
			getManagers().getExplosionRegenManager().loadRegenObjects();
		}

		if(conf.getMiscConfig().isGatesEnabled()) {
			getManagers().getGateManager().reloadGates();
		}
		if(conf.getMiscConfig().isBridgesEnabled()) {
			getManagers().getBridgeManager().reloadBridges();
		}
		if(conf.getEntityConfig().isRealisticWaterEnabled()) {
			getManagers().getRealisticWaterManager().start();
		}
		getManagers().getBackPackManager().loadBackpacks();
		
		//getManagers().getMinigamesManager().enablePlugins();
		
		log("has been enabled", LogType.INFO);
	}

	public void onDisable() {

		for(XPlayer xp : getManagers().getPlayerManager().getPlayers()) {
			xp.save();
		}

		if(getUUIDManager().isExecutorServiceRunning()) {
			getUUIDManager().shutdownExecutorService();
		}
		getManagers().getPlayerManager().clear();
		if(conf.getEntityConfig().isRealisticGlassEnabled()) {
			getManagers().getRealisticGlassManager().saveGlassBlocks();
		}
		if(conf.getChatConfig().isRssBroadcastEnabled()) {
			getManagers().getRssManager().saveLastFeed();
		}

		if(getManagers().getGreylistManager().isRunning()) {
			SimpleServer server = getManagers().getGreylistManager();
			log("stopping server " + server.getName() + " at port " + server.getPort(), LogType.INFO);
			try {
				server.stopServer();
				log("server successfully stopped!", LogType.INFO);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if(getManagers().getBroadcastManager().isRunning()) {
			getManagers().getBroadcastManager().stop();
		}

		if(conf.getEntityConfig().isExplosionRegenEnabled()) {
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
			getManagers().getExplosionRegenManager().saveRegenObjects();
			if(conf.getMiscConfig().isChairsEnabled()) {
				getManagers().getChairManager().killAll();	
			}
		}
		if(conf.getEntityConfig().isRealisticWaterEnabled()) {
			getManagers().getRealisticWaterManager().stop();
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
	public static void log(String message, LogType type) {
		Bukkit.getConsoleSender().sendMessage(type.getPrefix() + message);
	}

	/**
	 * @author xize
	 * @param returns the managers
	 * @return Manager
	 */
	@Override
	public Manager getManagers() {
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
	public Player[] getOnlinePlayers() {
		return OnlinePlayersHelper.getOnlinePlayers();
	}

	@Override
	public GlobalConfiguration getConfiguration() {
		return conf;
	}

	/**
	 * @author xize
	 * @param p - returns the uuid manager
	 * @return MojangUUID
	 */
	public MojangUUID getUUIDManager() {
		return uuid;
	}
}
