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
	
	private final xEssentials pl;
	
	public Manager(xEssentials pl) {
		this.pl = pl;
	}
	
	/**
	 * @author xize
	 * @param returns the tps speedness of the server.
	 * @return TPS
	 */
	public TPSManager getTPSManager() {
		if(!(tps instanceof TPSManager)) {
			this.tps = new TPSManager(pl);
		}
		return tps;
	}
	
	/**
	 * @author xize
	 * @param returns the Tpa manager
	 * @return TpaManager
	 */
	public TpaManager getTpaManager() {
		if(!(tpa instanceof TpaManager)) {
			this.tpa = new TpaManager();
		}
		return tpa;
	}
	
	/**
	 * @author xize
	 * @param returns the regen list
	 * @return RegenList
	 */
	public RegenManager getExplosionRegenManager() {
		if(!(regen instanceof RegenManager)) {
			this.regen = new RegenManager(pl);
		}
		return regen;
	}
	
	/**
	 * @author xize
	 * @param returns the protection database
	 * @return ProtectionDB
	 */
	public ProtectionDBManager getProtectionDBManager() {
		if(!(protectiondb instanceof ProtectionDBManager)) {
			this.protectiondb = new ProtectionDBManager(pl);
		}
		return protectiondb;
	}
	
	/**
	 * @author xize
	 * @param returns the HomeInviteManager
	 * @return HomeInviteManager
	 */
	public HomeInviteManager getHomeInviteManager() {
		if(!(homeInvite instanceof HomeInviteManager)) {
			this.homeInvite = new HomeInviteManager();
		}
		return homeInvite;
	}
	
	/**
	 * @author xize
	 * @param returns the Gate manager
	 * @return GateManager
	 */
	public GateManager getGateManager() {
		if(!(gate instanceof GateManager)) {
			this.gate = new GateManager(pl);
		}
		return gate;
	}
	
	/**
	 * @author xize
	 * @param returns the backpack manager
	 * @return BackPackData
	 */
	public BackPackManager getBackPackManager() {
		if(!(backpacks instanceof BackPackManager)) {
			this.backpacks = new BackPackManager(pl);
		}
		return backpacks;
	}
	
	/**
	 * @author xize
	 * @param returns the player manager.
	 * @return xEssentialsPlayerManager
	 */
	public xEssentialsPlayerManager getPlayerManager() {
		if(!(players instanceof xEssentialsPlayerManager)) {
			this.players = new xEssentialsPlayerManager(pl);
		}
		return players;
	}
	
	/**
	 * @author xize
	 * @param returns the warp manager
	 * @return WarpManager
	 */
	public WarpManager getWarpManager() {
		if(!(warp instanceof WarpManager)) {
			this.warp = new WarpManager(pl);
		}
		return warp;
	}
	
	/**
	 * @author xize
	 * @param returns the economy manager
	 * @return EconomyManager
	 */
	public EconomyManager getEcoManager() {
		if(!(econ instanceof EconomyManager)) {
			this.econ = new EconomyManager(pl);
		}
		return econ;
	}
	
	/**
	 * @author xize
	 * @param returns the RealisticGlass manager.
	 * @return RealisticGlass
	 */
	public RealisticGlassEvent getRealisticGlassManager() {
		if(!(glass instanceof RealisticGlassEvent)) {
			this.glass = new RealisticGlassEvent(pl);
		}
		return glass;
	}
	
	/**
	 * @author xize
	 * @param returns the greylist manager
	 * @return GreyListServer
	 */
	public SimpleServer getGreylistManager() {
		if(!(greylist instanceof SimpleServer)) {
			this.greylist = new SimpleServer(pl.getConfiguration().getGrayListConfig().getPort(), "greylist");
		}
		return greylist;
	}
	
	/**
	 * @author xize
	 * @param returns the vault manager
	 * @return VaultHook
	 */
	public VaultHook getVaultManager() {
		if(!(vault instanceof VaultHook)) {
			this.vault = new VaultHook();
		}
		return vault;
	}
	
	/**
	 * @author xize
	 * @param returns the rss manager
	 * @return CallRssFeedEvent
	 */
	public CallRssFeedEvent getRssManager() {
		if(!(rss instanceof CallRssFeedEvent)) {
			this.rss = new CallRssFeedEvent(pl);
		}
		return rss;
	}
	
	/**
	 * @author xize
	 * @param returns the mojang status service manager
	 * @return CallMojangStatus
	 */
	public CallMojangStatus getMojangStatusManager() {
		if(!(moj instanceof CallMojangStatus)) {
			this.moj = new CallMojangStatus(pl);
		}
		return moj;
	}
	
	/**
	 * @author xize
	 * @param returns the broadcast manager
	 * @return CallEssentialsBroadcastEvent
	 */
	public CallEssentialsBroadcastEvent getBroadcastManager() {
		if(!(broadcast instanceof CallEssentialsBroadcastEvent)) {
			this.broadcast = new CallEssentialsBroadcastEvent(pl);
		}
		return broadcast;
	}
	
	/**
	 * @author xize
	 * @param returns the  bridge manager
	 * @return BridgeManager
	 */
	public BridgeManager getBridgeManager() {
		if(!(bridge instanceof BridgeManager)) {
			this.bridge = new BridgeManager(pl);
		}
		return bridge;
	}
	
	/**
	 * @author xize
	 * @param returns the chair manager
	 * @return ChairManager
	 */
	public ChairManager getChairManager() {
		if(!(chair instanceof ChairManager)) {
			this.chair = new ChairManager(pl);
		}
		return chair;
	}
	
	/**
	 * @author xize
	 * @param returns the ManCo manager
	 * @return ManCoManager
	 */
	public ManCoManager getManCoManager() {
		if(!(manco instanceof ManCoManager)) {
			this.manco = new ManCoManager(pl);
		}
		return manco;
	}
	
	/**
	 * @author xize
	 * @param returns the water manager
	 * @return RealisticWaterManager
	 */
	public RealisticWaterManager getRealisticWaterManager() {
		if(!(water instanceof RealisticWaterManager)) {
			this.water = new RealisticWaterManager(pl);
		}
		return water;
	}
	
	public TeleportManager getTeleportManager() {
		if(!(teleport instanceof TeleportManager)) {
			this.teleport = new TeleportManager();
		}
		return teleport;
	}
}
