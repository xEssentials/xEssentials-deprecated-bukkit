package tv.mineinthebox.essentials.managers;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.events.customEvents.CallEssentialsBroadcastEvent;
import tv.mineinthebox.essentials.events.customEvents.CallMojangStatus;
import tv.mineinthebox.essentials.events.customEvents.CallRssFeedEvent;
import tv.mineinthebox.essentials.events.players.RealisticGlass;
import tv.mineinthebox.essentials.greylist.GreyListServer;
import tv.mineinthebox.essentials.hook.VaultHook;

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
	private RealisticGlass glass;
	private GreyListServer greylist;
	private VaultHook vault;
	private CallRssFeedEvent rss;
	private CallMojangStatus moj;
	private CallEssentialsBroadcastEvent broadcast;
	private MinigameManager minigame;
	private BridgeManager bridge;
	private ChairManager chair;
	private ManCoManager manco;
	private RealisticWaterManager water;
	
	/**
	 * @author xize
	 * @param returns the tps speedness of the server.
	 * @return TPS
	 */
	public TPSManager getTPSManager() {
		if(!(tps instanceof TPSManager)) {
			this.tps = new TPSManager();
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
			this.regen = new RegenManager();
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
			this.protectiondb = new ProtectionDBManager();
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
			this.gate = new GateManager();
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
			this.backpacks = new BackPackManager();
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
			this.players = new xEssentialsPlayerManager();
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
			this.warp = new WarpManager();
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
			this.econ = new EconomyManager();
		}
		return econ;
	}
	
	/**
	 * @author xize
	 * @param returns the RealisticGlass manager.
	 * @return RealisticGlass
	 */
	public RealisticGlass getRealisticGlassManager() {
		if(!(glass instanceof RealisticGlass)) {
			this.glass = new RealisticGlass();
		}
		return glass;
	}
	
	/**
	 * @author xize
	 * @param returns the greylist manager
	 * @return GreyListServer
	 */
	public GreyListServer getGreylistManager() {
		if(!(greylist instanceof GreyListServer)) {
			this.greylist = new GreyListServer(Configuration.getGrayListConfig().getPort());
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
			this.rss = new CallRssFeedEvent();
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
			this.moj = new CallMojangStatus();
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
			this.broadcast = new CallEssentialsBroadcastEvent();
		}
		return broadcast;
	}
	
	/**
	 * @author xize
	 * @param returns the  minigame manager
	 * @return MinigameManager
	 */
	public MinigameManager getMinigameManager() {
		if(!(minigame instanceof MinigameManager)) {
			this.minigame = new MinigameManager();
		}
		return minigame;
	}
	
	/**
	 * @author xize
	 * @param returns the  bridge manager
	 * @return BridgeManager
	 */
	public BridgeManager getBridgeManager() {
		if(!(bridge instanceof BridgeManager)) {
			this.bridge = new BridgeManager();
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
			this.chair = new ChairManager();
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
			this.manco = new ManCoManager();
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
			this.water = new RealisticWaterManager();
		}
		return water;
	}

}
