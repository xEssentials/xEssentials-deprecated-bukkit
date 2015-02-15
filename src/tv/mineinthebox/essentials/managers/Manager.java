package tv.mineinthebox.essentials.managers;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.events.customevents.CallEssentialsBroadcastEvent;
import tv.mineinthebox.essentials.events.customevents.CallMojangStatus;
import tv.mineinthebox.essentials.events.customevents.CallRssFeedEvent;
import tv.mineinthebox.essentials.events.entity.RealisticGlassEvent;
import tv.mineinthebox.essentials.hook.VaultHook;
import tv.mineinthebox.simpleserver.SimpleServer;

public class Manager {
	
	private BackPackManager backpacks;
	private GateManager gate;
	private HomeInviteManager homeInvite;
	private ProtectionDBManager protectiondb;
	private RegenManager regen;
	private TpaManager tpa;
	private TPSManager tps;
	private xEssentialsPlayerManager players;
	private WarpManager warp;
	private EconomyManager econ;
	private RealisticGlassEvent glass;
	private SimpleServer greylist;
	private VaultHook vault;
	private CallRssFeedEvent rss;
	private CallMojangStatus moj;
	private CallEssentialsBroadcastEvent broadcast;
	private BridgeManager bridge;
	private ChairManager chair;
	private ManCoManager manco;
	private RealisticWaterManager water;
	private TeleportManager teleport;
	private WorldGuardManager worldguard;
	private CommandManager commandmanager;
	
	private final xEssentials pl;
	
	public Manager(xEssentials pl) {
		this.pl = pl;
	}
	
	/**
	 * returns the tick count of the server
	 * 
	 * @author xize
	 * @return TPSManager
	 */
	public TPSManager getTPSManager() {
		if(!(tps instanceof TPSManager)) {
			this.tps = new TPSManager(pl);
		}
		return tps;
	}
	
	/**
	 * returns the tpa manager
	 * 
	 * @author xize
	 * @return TpaManager
	 */
	public TpaManager getTpaManager() {
		if(!(tpa instanceof TpaManager)) {
			this.tpa = new TpaManager();
		}
		return tpa;
	}
	
	/**
	 * returns the explosion regen manager
	 * 
	 * @author xize
	 * @return RegenManager
	 */
	public RegenManager getExplosionRegenManager() {
		if(!(regen instanceof RegenManager)) {
			this.regen = new RegenManager(pl);
		}
		return regen;
	}
	
	/**
	 * returns the protection manager
	 * 
	 * @author xize
	 * @return ProtectionDBManager
	 */
	public ProtectionDBManager getProtectionDBManager() {
		if(!(protectiondb instanceof ProtectionDBManager)) {
			this.protectiondb = new ProtectionDBManager(pl);
		}
		return protectiondb;
	}
	
	/**
	 * returns the home invites manager
	 * 
	 * @author xize
	 * @return HomeInviteManager
	 */
	public HomeInviteManager getHomeInviteManager() {
		if(!(homeInvite instanceof HomeInviteManager)) {
			this.homeInvite = new HomeInviteManager();
		}
		return homeInvite;
	}
	
	/**
	 * returns the Gate manager
	 * 
	 * @author xize
	 * @return GateManager
	 */
	public GateManager getGateManager() {
		if(!(gate instanceof GateManager)) {
			this.gate = new GateManager(pl);
		}
		return gate;
	}
	
	/**
	 * returns the backpack manager
	 * 
	 * @author xize
	 * @return BackPackManager
	 */
	public BackPackManager getBackPackManager() {
		if(!(backpacks instanceof BackPackManager)) {
			this.backpacks = new BackPackManager(pl);
		}
		return backpacks;
	}
	
	/**
	 * returns the player manager
	 * 
	 * @author xize
	 * @return xEssentialsPlayerManager
	 */
	public xEssentialsPlayerManager getPlayerManager() {
		if(!(players instanceof xEssentialsPlayerManager)) {
			this.players = new xEssentialsPlayerManager(pl);
		}
		return players;
	}
	
	/**
	 * returns the warp manager
	 * 
	 * @author xize
	 * @return WarpManager
	 */
	public WarpManager getWarpManager() {
		if(!(warp instanceof WarpManager)) {
			this.warp = new WarpManager(pl);
		}
		return warp;
	}
	
	/**
	 * returns the economy manager
	 * 
	 * @author xize
	 * @return EconomyManager
	 */
	public EconomyManager getEcoManager() {
		if(!(econ instanceof EconomyManager)) {
			this.econ = new EconomyManager(pl);
		}
		return econ;
	}
	
	/**
	 * returns the glass regen manager
	 * 
	 * @author xize
	 * @return RealisticGlassEvent
	 */
	public RealisticGlassEvent getRealisticGlassManager() {
		if(!(glass instanceof RealisticGlassEvent)) {
			this.glass = new RealisticGlassEvent(pl);
		}
		return glass;
	}
	
	/**
	 * returns the greylist server
	 * 
	 * @author xize
	 * @return SimpleServer
	 */
	public SimpleServer getGreylistManager() {
		if(!(greylist instanceof SimpleServer)) {
			this.greylist = new SimpleServer(pl.getConfiguration().getGrayListConfig().getPort(), "greylist");
		}
		return greylist;
	}
	
	/**
	 * returns the vault manager
	 * 
	 * @author xize
	 * @return VaultHook
	 */
	public VaultHook getVaultManager() {
		if(!(vault instanceof VaultHook)) {
			this.vault = new VaultHook(pl);
		}
		return vault;
	}
	
	/**
	 * returns the rss manager
	 * 
	 * @author xize
	 * @return CallRssFeedEvent
	 */
	public CallRssFeedEvent getRssManager() {
		if(!(rss instanceof CallRssFeedEvent)) {
			this.rss = new CallRssFeedEvent(pl);
		}
		return rss;
	}
	
	/**
	 * returns the mojang status service manager
	 * 
	 * @author xize
	 * @return CallMojangStatus
	 */
	public CallMojangStatus getMojangStatusManager() {
		if(!(moj instanceof CallMojangStatus)) {
			this.moj = new CallMojangStatus(pl);
		}
		return moj;
	}
	
	/**
	 * returns the broadcast manager
	 * 
	 * @author xize
	 * @return CallEssentialsBroadcastEvent
	 */
	public CallEssentialsBroadcastEvent getBroadcastManager() {
		if(!(broadcast instanceof CallEssentialsBroadcastEvent)) {
			this.broadcast = new CallEssentialsBroadcastEvent(pl);
		}
		return broadcast;
	}
	
	/**
	 * returns the bridge manager
	 * 
	 * @author xize
	 * @return BridgeManager
	 */
	public BridgeManager getBridgeManager() {
		if(!(bridge instanceof BridgeManager)) {
			this.bridge = new BridgeManager(pl);
		}
		return bridge;
	}
	
	/**
	 * returns the chair manager
	 * 
	 * @author xize
	 * @return ChairManager
	 */
	public ChairManager getChairManager() {
		if(!(chair instanceof ChairManager)) {
			this.chair = new ChairManager(pl);
		}
		return chair;
	}
	
	/**
	 * returns ManCo manager
	 * 
	 * @author xize
	 * @return ManCoManager
	 */
	public ManCoManager getManCoManager() {
		if(!(manco instanceof ManCoManager)) {
			this.manco = new ManCoManager(pl);
		}
		return manco;
	}
	
	/**
	 * returns the water manager
	 * 
	 * @author xize
	 * @return RealisticWaterManager
	 */
	public RealisticWaterManager getRealisticWaterManager() {
		if(!(water instanceof RealisticWaterManager)) {
			this.water = new RealisticWaterManager(pl);
		}
		return water;
	}
	
	/**
	 * returns the teleport manager
	 * 
	 * @author xize
	 * @return TeleportManager
	 */
	public TeleportManager getTeleportManager() {
		if(!(teleport instanceof TeleportManager)) {
			this.teleport = new TeleportManager();
		}
		return teleport;
	}
	
	/**
	 * returns the Worldguard manager
	 * 
	 * @author xize
	 * @return WorldGuardManager
	 */
	public WorldGuardManager getWorldGuardManager() {
		if(!(worldguard instanceof WorldGuardManager)) {
			this.worldguard = new WorldGuardManager(pl);
			this.worldguard.registerMonsterFlag();
			this.worldguard.reloadWG();
		}
		return worldguard;
	}
	
	/**
	 * returns the command manager
	 * 
	 * @author xize, zeeveener
	 * @return CommandManager
	 */
	public CommandManager getCommandManager() {
		if(!(commandmanager instanceof CommandManager)) {
			this.commandmanager = new CommandManager(pl);
		}
		return commandmanager;
	}
}
