package tv.mineinthebox.essentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.PluginCommand;

import tv.mineinthebox.essentials.commands.CommandList;
import tv.mineinthebox.essentials.configurations.BanConfig;
import tv.mineinthebox.essentials.configurations.BlockConfig;
import tv.mineinthebox.essentials.configurations.BroadcastConfig;
import tv.mineinthebox.essentials.configurations.ChatConfig;
import tv.mineinthebox.essentials.configurations.CommandConfig;
import tv.mineinthebox.essentials.configurations.DebugConfig;
import tv.mineinthebox.essentials.configurations.EconomyConfig;
import tv.mineinthebox.essentials.configurations.EntityConfig;
import tv.mineinthebox.essentials.configurations.GreylistConfig;
import tv.mineinthebox.essentials.configurations.KitConfig;
import tv.mineinthebox.essentials.configurations.MiscConfig;
import tv.mineinthebox.essentials.configurations.MotdConfig;
import tv.mineinthebox.essentials.configurations.PlayerConfig;
import tv.mineinthebox.essentials.configurations.PortalConfig;
import tv.mineinthebox.essentials.configurations.ProtectionConfig;
import tv.mineinthebox.essentials.configurations.PvpConfig;
import tv.mineinthebox.essentials.configurations.RulesConfig;
import tv.mineinthebox.essentials.configurations.ShopConfig;
import tv.mineinthebox.essentials.configurations.SignConfig;
import tv.mineinthebox.essentials.configurations.VoteConfig;
import tv.mineinthebox.essentials.enums.ConfigType;
import tv.mineinthebox.essentials.enums.LogType;
import tv.mineinthebox.essentials.events.CustomEventHandler;
import tv.mineinthebox.essentials.events.Handler;
import tv.mineinthebox.essentials.greylist.GreyListServer;
import tv.mineinthebox.simpleserver.SimpleServer;

public class GlobalConfiguration {

	private final EnumMap<ConfigType, Configuration> configurations = new EnumMap<ConfigType, Configuration>(ConfigType.class);
	private final List<String> materials = new ArrayList<String>() { private static final long serialVersionUID = 1L; {
		for(Material mat : Material.values()) {
			add(mat.name());
		}
	}};
	private final xEssentials pl;
	
	private boolean isSilenceToggled = false;

	public GlobalConfiguration(xEssentials pl) {
		this.pl = pl;
		createConfigs();
	}

	/**
	 * returns the protection configuration
	 * 
	 * @author xize
	 * @return ProtectionConfig
	 */
	public ProtectionConfig getProtectionConfig() {
		return (ProtectionConfig)configurations.get(ConfigType.PROTECTION);
	}

	/**
	 * returns the misc configuration
	 * 
	 * @author xize
	 * @return MiscConfig
	 */
	public MiscConfig getMiscConfig() {
		return (MiscConfig)configurations.get(ConfigType.MISC);
	}

	/**
	 * returns the ban configuration
	 * 
	 * @author xize
	 * @return BanConfig
	 */
	public BanConfig getBanConfig() {
		return (BanConfig)configurations.get(ConfigType.BAN);
	}

	/**
	 * returns the greylist configuration
	 * 
	 * @author xize
	 * @return GreylistConfig
	 */
	public GreylistConfig getGreyListConfig() {
		return (GreylistConfig)configurations.get(ConfigType.GREYLIST);
	}

	/**
	 * returns the economy configuration
	 * 
	 * @author xize
	 * @return EconomyConfig
	 */
	public EconomyConfig getEconomyConfig() {
		return (EconomyConfig)configurations.get(ConfigType.ECONOMY);
	}

	/**
	 * returns the block configuration
	 * 
	 * @author xize
	 * @return BlockConfig
	 */
	public BlockConfig getBlockConfig() {
		return (BlockConfig) configurations.get(ConfigType.BLOCKS);
	}

	/**
	 * returns the broadcast configuration
	 * 
	 * @author xize
	 * @return BroadcastConfig
	 */
	public BroadcastConfig getBroadcastConfig() {
		return (BroadcastConfig) configurations.get(ConfigType.BROADCAST);
	}

	/**
	 * returns the command configuration
	 * 
	 * @author xize
	 * @return CommandConfig
	 */
	public CommandConfig getCommandConfig() {
		return (CommandConfig) configurations.get(ConfigType.COMMAND);
	}

	/**
	 * returns the configuration for kits
	 * 
	 * @author xize
	 * @return KitConfig
	 */
	public KitConfig getKitConfig() {
		return (KitConfig) configurations.get(ConfigType.KITS);
	}

	/**
	 * returns the chat configuration
	 * 
	 * @author xize
	 * @return ChatConfig
	 */
	public ChatConfig getChatConfig() {
		return (ChatConfig) configurations.get(ConfigType.CHAT);
	}

	/**
	 * returns the shop configuration
	 * 
	 * @author xize
	 * @return ShopConfig
	 */
	public ShopConfig getShopConfig() {
		return (ShopConfig) configurations.get(ConfigType.SHOP);
	}

	/**
	 * returns the entity configuration
	 * 
	 * @author xize
	 * @return EntityConfig
	 */
	public EntityConfig getEntityConfig() {
		return (EntityConfig) configurations.get(ConfigType.ENTITY);
	}

	/**
	 * returns the motd configuration
	 * 
	 * @author xize
	 * @return MotdConfig
	 */
	public MotdConfig getMotdConfig() {
		return (MotdConfig) configurations.get(ConfigType.MOTD);
	}

	/**
	 * returns the player configuration
	 * 
	 * @author xize
	 * @return PlayerConfig
	 */
	public PlayerConfig getPlayerConfig() {
		return (PlayerConfig) configurations.get(ConfigType.PLAYER);
	}

	/**
	 * returns the pvp configuration
	 * 
	 * @author xize
	 * @return PvpConfig
	 */
	public PvpConfig getPvpConfig() {
		return (PvpConfig) configurations.get(ConfigType.PVP);
	}

	/**
	 * returns the portal configuration
	 * 
	 * @author xize
	 * @return PortalConfig
	 */
	public PortalConfig getPortalConfig() {
		return (PortalConfig) configurations.get(ConfigType.PORTAL);
	}

	/**
	 * returns the rules configuration
	 * 
	 * @author xize
	 * @return RulesConfig
	 */
	public RulesConfig getRulesConfig() {
		return (RulesConfig) configurations.get(ConfigType.RULES);
	}

	/**
	 * returns the sign configuration
	 * 
	 * @author xize
	 * @return SignConfig
	 */
	public SignConfig getSignConfig() {
		return (SignConfig) configurations.get(ConfigType.SIGN);
	}

	/**
	 * returns the vote configuration
	 * 
	 * @author xize
	 * @return VoteConfig
	 */
	public VoteConfig getVoteConfig() {
		return (VoteConfig) configurations.get(ConfigType.VOTE);
	}

	/**
	 * returns the debug configuration
	 * 
	 * @author xize
	 * @return DebugConfig
	 */
	public DebugConfig getDebugConfig() {
		return (DebugConfig) configurations.get(ConfigType.DEBUG);
	}

	public boolean reload() {
		Handler handler = new Handler(pl);
		handler.stop();
		if(pl.getManagers().getBroadcastManager().isRunning()) {
			pl.getManagers().getBroadcastManager().stop();
		}
		if(pl.getManagers().getGreylistManager() != null) {
			if(pl.getManagers().getGreylistManager().isRunning()) {
				SimpleServer server = pl.getManagers().getGreylistManager();
				xEssentials.log("stopping server " + server.getName() + " at port " + server.getPort(), LogType.INFO);
				try {
					server.stopServer();
					xEssentials.log("server successfully stopped!", LogType.INFO);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if(pl.getManagers().getRealisticWaterManager().isRunning()) {
			pl.getManagers().getRealisticWaterManager().stop();
		}
		configurations.clear();
		createConfigs();
		HandleCommandManager();
		if(getGreyListConfig().isEnabled()) {
			SimpleServer server = pl.getManagers().getGreylistManager();
			server.addListener(new GreyListServer(pl));
			try {
				server.startServer();
				xEssentials.log(server.getName() + " has been started on port " + server.getPort(), LogType.INFO);
			} catch (Exception e) {
				xEssentials.log("failed to create server " + server.getName() + " on port " + server.getPort(), LogType.SEVERE);
				e.printStackTrace();
			}
		}
		if(getMiscConfig().isGatesEnabled()) {
			pl.getManagers().getGateManager().reloadGates();
		}
		if(getMiscConfig().isBridgesEnabled()) {
			pl.getManagers().getBridgeManager().reloadBridges();
		}
		if(getEntityConfig().isRealisticWaterEnabled()) {
			pl.getManagers().getRealisticWaterManager().start();
		}
		pl.getManagers().getPlayerManager().reloadPlayerBase(); 
		handler.start();
		CustomEventHandler customhandler = new CustomEventHandler(pl);
		customhandler.startCustomEvents();
		return true;
	}
	
	/**
	 * handles all the internal commands of xEssentials
	 * 
	 * @author xize
	 * 
	 */
	public void HandleCommandManager() {
		CommandList cmdlist = new CommandList();
		List<String> unregCommands = new ArrayList<String>(getCommandConfig().getUnregisteredCommands());

		if(!getEconomyConfig().isEconomyEnabled()) {
			unregCommands.add("money");
		}
		if(!getProtectionConfig().isProtectionEnabled()) {
			unregCommands.add("cprivate");
			unregCommands.add("cmodify");
			unregCommands.add("cremove");
		}
		if(!getPortalConfig().isPortalEnabled()) {
			unregCommands.add("portals");
		}

		for(String cmd : cmdlist.getAllCommands) {			
			if(!unregCommands.contains(cmd) && !getCommandConfig().isRegistered(cmd)) {
				PluginCommand command = pl.getManagers().getCommandManager().createPluginCommand(cmd);
				getCommandConfig().registerBukkitCommand(command);   
			} else if(unregCommands.contains(cmd) && getCommandConfig().isRegistered(cmd)) {
				PluginCommand command = pl.getCommand(cmd);
				getCommandConfig().unRegisterBukkitCommand(command);
			}
		}
	}
	
	/**
	 * toggles silence chat
	 * 
	 * @author xize
	 */
	public void toggleSillenceChat() {
		this.isSilenceToggled = !this.isSilenceToggled;
	}
	
	/**
	 * returns true if chat is silenced, otherwise false
	 * 
	 * @author xize
	 * @return boolean
	 */
	public boolean isChatSillenced() {
		return this.isSilenceToggled;
	}
	
	/**
	 * returns all the material names
	 * 
	 * @author xize
	 * @return List<String>
	 */
	public List<String> getMaterials() {
		return materials;
	}

	private void createConfigs() {
		for(ConfigType type : ConfigType.values()) {
			Configuration conf = type.getNewConfiguration(pl);
			if(conf.isGeneratedOnce()) {
				if(!conf.isGenerated()) {
					conf.generateConfig();
				}
			} else {
				conf.generateConfig();
			}
			configurations.put(type, conf);
		}
	}

}
