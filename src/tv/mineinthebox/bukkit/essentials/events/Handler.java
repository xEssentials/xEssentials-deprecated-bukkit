package tv.mineinthebox.bukkit.essentials.events;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.events.backpack.BackpackDespawningEvent;
import tv.mineinthebox.bukkit.essentials.events.backpack.OpenBackPackEvent;
import tv.mineinthebox.bukkit.essentials.events.ban.FloodSpamEvent;
import tv.mineinthebox.bukkit.essentials.events.ban.HumanSpamCommandEvent;
import tv.mineinthebox.bukkit.essentials.events.ban.HumanSpamEvent;
import tv.mineinthebox.bukkit.essentials.events.ban.PwnAgeProtectionEvent;
import tv.mineinthebox.bukkit.essentials.events.ban.ShowAlternateAccounts;
import tv.mineinthebox.bukkit.essentials.events.blocks.BedrockBreakEvent;
import tv.mineinthebox.bukkit.essentials.events.blocks.BedrockPlaceEvent;
import tv.mineinthebox.bukkit.essentials.events.blocks.BlockBlackListEvent;
import tv.mineinthebox.bukkit.essentials.events.blocks.ItemBlackListEvent;
import tv.mineinthebox.bukkit.essentials.events.blocks.NotifyAdminOnBlockBreak;
import tv.mineinthebox.bukkit.essentials.events.blocks.NotifyItemUseEvent;
import tv.mineinthebox.bukkit.essentials.events.books.BookInteractEvent;
import tv.mineinthebox.bukkit.essentials.events.bridges.BridgeBreakEvent;
import tv.mineinthebox.bukkit.essentials.events.bridges.BridgeCreateEvent;
import tv.mineinthebox.bukkit.essentials.events.bridges.BridgeGriefPrevention;
import tv.mineinthebox.bukkit.essentials.events.bridges.BridgeInteractEvent;
import tv.mineinthebox.bukkit.essentials.events.bridges.RemoveBridgeEvent;
import tv.mineinthebox.bukkit.essentials.events.chairs.ChairDisableMonster;
import tv.mineinthebox.bukkit.essentials.events.chairs.PlayerSitOnChair;
import tv.mineinthebox.bukkit.essentials.events.chat.AntiAddvertiseEvent;
import tv.mineinthebox.bukkit.essentials.events.chat.BroadcastSiteNewsEvent;
import tv.mineinthebox.bukkit.essentials.events.chat.ChatHighLightEvent;
import tv.mineinthebox.bukkit.essentials.events.chat.ChatSmilleyEvent;
import tv.mineinthebox.bukkit.essentials.events.chat.DrunkChatEvent;
import tv.mineinthebox.bukkit.essentials.events.chat.MuteEvent;
import tv.mineinthebox.bukkit.essentials.events.chat.PlayerIgnorePlayerChatEvent;
import tv.mineinthebox.bukkit.essentials.events.chat.PublishMojangStatus;
import tv.mineinthebox.bukkit.essentials.events.chat.SilenceChatEvent;
import tv.mineinthebox.bukkit.essentials.events.elevators.ElevatorCreateEvent;
import tv.mineinthebox.bukkit.essentials.events.elevators.ElevatorInteract;
import tv.mineinthebox.bukkit.essentials.events.entity.ChunkProtection;
import tv.mineinthebox.bukkit.essentials.events.entity.CleanupUnloadedChunkEvent;
import tv.mineinthebox.bukkit.essentials.events.entity.CustomZombieAggroRange;
import tv.mineinthebox.bukkit.essentials.events.entity.DisableEndDragonGrief;
import tv.mineinthebox.bukkit.essentials.events.entity.DisableEndermanGrief;
import tv.mineinthebox.bukkit.essentials.events.entity.DisableExplosionEvent;
import tv.mineinthebox.bukkit.essentials.events.entity.DisableFireSpreadEvent;
import tv.mineinthebox.bukkit.essentials.events.entity.DisableFireworkEvent;
import tv.mineinthebox.bukkit.essentials.events.entity.DisableWeatherEvent;
import tv.mineinthebox.bukkit.essentials.events.entity.DisableWitherGriefEvent;
import tv.mineinthebox.bukkit.essentials.events.entity.EntityBleedEvent;
import tv.mineinthebox.bukkit.essentials.events.entity.EntityPressurePlateInteractEvent;
import tv.mineinthebox.bukkit.essentials.events.entity.EntitySpawnEventManager;
import tv.mineinthebox.bukkit.essentials.events.entity.ExplosionRegen;
import tv.mineinthebox.bukkit.essentials.events.entity.RealisticTreeEvent;
import tv.mineinthebox.bukkit.essentials.events.entity.RealisticWaterEvent;
import tv.mineinthebox.bukkit.essentials.events.entity.SpawnEggLogEvent;
import tv.mineinthebox.bukkit.essentials.events.gates.GateBreakEvent;
import tv.mineinthebox.bukkit.essentials.events.gates.GateCreateEvent;
import tv.mineinthebox.bukkit.essentials.events.gates.GateGriefPrevention;
import tv.mineinthebox.bukkit.essentials.events.gates.GateInteractEvent;
import tv.mineinthebox.bukkit.essentials.events.gates.GateRedstone;
import tv.mineinthebox.bukkit.essentials.events.gates.RemoveGateEvent;
import tv.mineinthebox.bukkit.essentials.events.minigames.PlayerJoinCheckInventory;
import tv.mineinthebox.bukkit.essentials.events.minigames.PlayerQuitHandle;
import tv.mineinthebox.bukkit.essentials.events.minigames.PlayerTeleportCheck;
import tv.mineinthebox.bukkit.essentials.events.motd.MotdEvent;
import tv.mineinthebox.bukkit.essentials.events.motd.MotdVanishEvent;
import tv.mineinthebox.bukkit.essentials.events.players.AchievementEvent;
import tv.mineinthebox.bukkit.essentials.events.players.AfkChecks;
import tv.mineinthebox.bukkit.essentials.events.players.AntiKnockBackEvent;
import tv.mineinthebox.bukkit.essentials.events.players.AnvilResetEvent;
import tv.mineinthebox.bukkit.essentials.events.players.CommandRestrictEvent;
import tv.mineinthebox.bukkit.essentials.events.players.CompassEvent;
import tv.mineinthebox.bukkit.essentials.events.players.DisablePortalCreation;
import tv.mineinthebox.bukkit.essentials.events.players.DoubleJumpEvent;
import tv.mineinthebox.bukkit.essentials.events.players.EntityUseHeadOnPlayerDeath;
import tv.mineinthebox.bukkit.essentials.events.players.FakeNukeEvent;
import tv.mineinthebox.bukkit.essentials.events.players.Firefly;
import tv.mineinthebox.bukkit.essentials.events.players.FirstJoinTeleportEvent;
import tv.mineinthebox.bukkit.essentials.events.players.FlyEvent;
import tv.mineinthebox.bukkit.essentials.events.players.FreezePlayerEvent;
import tv.mineinthebox.bukkit.essentials.events.players.GameModeInvChange;
import tv.mineinthebox.bukkit.essentials.events.players.HungerEvent;
import tv.mineinthebox.bukkit.essentials.events.players.InventoryMenu;
import tv.mineinthebox.bukkit.essentials.events.players.LoadMemory;
import tv.mineinthebox.bukkit.essentials.events.players.MobProcEvent;
import tv.mineinthebox.bukkit.essentials.events.players.ModreqJoinEvent;
import tv.mineinthebox.bukkit.essentials.events.players.PlayerCheckNameEvent;
import tv.mineinthebox.bukkit.essentials.events.players.PlayerDeathBackEvent;
import tv.mineinthebox.bukkit.essentials.events.players.PlayerForceRespawnEvent;
import tv.mineinthebox.bukkit.essentials.events.players.PlayerHoldItemsEvent;
import tv.mineinthebox.bukkit.essentials.events.players.PlayerJoinMessage;
import tv.mineinthebox.bukkit.essentials.events.players.PlayerQuitMessage;
import tv.mineinthebox.bukkit.essentials.events.players.PlayerRespawnTeleport;
import tv.mineinthebox.bukkit.essentials.events.players.PlayerTaskLogin;
import tv.mineinthebox.bukkit.essentials.events.players.PlayerZone;
import tv.mineinthebox.bukkit.essentials.events.players.PortalSizeEvent;
import tv.mineinthebox.bukkit.essentials.events.players.PotatoMoveEvent;
import tv.mineinthebox.bukkit.essentials.events.players.PowerToolEvent;
import tv.mineinthebox.bukkit.essentials.events.players.RealisticGlass;
import tv.mineinthebox.bukkit.essentials.events.players.RemoveMemory;
import tv.mineinthebox.bukkit.essentials.events.players.SaveLastInventory;
import tv.mineinthebox.bukkit.essentials.events.players.SaveLastLocation;
import tv.mineinthebox.bukkit.essentials.events.players.SignEditEvent;
import tv.mineinthebox.bukkit.essentials.events.players.StaffSafeTeleport;
import tv.mineinthebox.bukkit.essentials.events.players.SteveHurtSound;
import tv.mineinthebox.bukkit.essentials.events.players.TeleportBackEvent;
import tv.mineinthebox.bukkit.essentials.events.players.TeleportEvent;
import tv.mineinthebox.bukkit.essentials.events.players.TorchEvent;
import tv.mineinthebox.bukkit.essentials.events.players.TrollModeEvent;
import tv.mineinthebox.bukkit.essentials.events.players.VanishEvent;
import tv.mineinthebox.bukkit.essentials.events.portals.PortalActivateEvent;
import tv.mineinthebox.bukkit.essentials.events.portals.PortalEvent;
import tv.mineinthebox.bukkit.essentials.events.portals.PortalSelectedCreateEvent;
import tv.mineinthebox.bukkit.essentials.events.protection.BlockProtectedEvent;
import tv.mineinthebox.bukkit.essentials.events.protection.ChestProtectedEvent;
import tv.mineinthebox.bukkit.essentials.events.protection.DispenserProtectionEvent;
import tv.mineinthebox.bukkit.essentials.events.protection.FurnaceProtectedEvent;
import tv.mineinthebox.bukkit.essentials.events.protection.JukeboxProtectedEvent;
import tv.mineinthebox.bukkit.essentials.events.protection.ModifyProtectionEvent;
import tv.mineinthebox.bukkit.essentials.events.protection.RegisterProtectionEvent;
import tv.mineinthebox.bukkit.essentials.events.protection.SignProtectedEvent;
import tv.mineinthebox.bukkit.essentials.events.protection.UnregisterProtectionEvent;
import tv.mineinthebox.bukkit.essentials.events.pvp.ClientSideGraveYard;
import tv.mineinthebox.bukkit.essentials.events.pvp.ClientSideGraveYard_ProtocolLib;
import tv.mineinthebox.bukkit.essentials.events.pvp.KillBountys;
import tv.mineinthebox.bukkit.essentials.events.pvp.NpcReplacePlayer;
import tv.mineinthebox.bukkit.essentials.events.pvp.PvpEvent;
import tv.mineinthebox.bukkit.essentials.events.shops.SignAdminShopCreateEvent;
import tv.mineinthebox.bukkit.essentials.events.shops.SignAdminShopOpenEvent;
import tv.mineinthebox.bukkit.essentials.events.shops.SignNormalShopCreateEvent;
import tv.mineinthebox.bukkit.essentials.events.shops.SignNormalShopOpenEvent;
import tv.mineinthebox.bukkit.essentials.events.shops.SignShopBreakEvent;
import tv.mineinthebox.bukkit.essentials.events.signs.ColorSign;
import tv.mineinthebox.bukkit.essentials.events.signs.FireworkSign;
import tv.mineinthebox.bukkit.essentials.events.signs.FreeSign;
import tv.mineinthebox.bukkit.essentials.events.signs.GetYourHeadSign;
import tv.mineinthebox.bukkit.essentials.events.signs.SignBoom;
import tv.mineinthebox.bukkit.essentials.events.signs.WarpSign;
import tv.mineinthebox.bukkit.essentials.events.signs.WildSign;
import tv.mineinthebox.bukkit.essentials.events.spleef.CreateSpleefArenaEvent;
import tv.mineinthebox.bukkit.essentials.events.vote.VoteCrateListener;
import tv.mineinthebox.bukkit.essentials.events.vote.VoteMoneyListener;
import tv.mineinthebox.bukkit.essentials.hook.Hooks;

public class Handler {

	/**
	 * @author xize
	 * @param starts all events!
	 * @return void
	 */

	@SuppressWarnings("deprecation")
	public void start() {
		//memory system
		setListener(new LoadMemory());
		if(Hooks.isWorldGuardEnabled()) {setListener(new PlayerZone());}
		setListener(new VanishEvent());
		if(Configuration.getPlayerConfig().isSeperatedInventorysEnabled()) {setListener(new GameModeInvChange());}
		if(Configuration.getPlayerConfig().isSaveInventoryEnabled()) {
			setListener(new SaveLastInventory());
		} else if(Configuration.getPvpConfig().isReplaceNpcEnabled()) {
			setListener(new SaveLastInventory());
		}
		if(Configuration.getChatConfig().isRssBroadcastEnabled()) {setListener(new BroadcastSiteNewsEvent());}
		setListener(new SaveLastLocation());
		setListener(new TorchEvent());
		setListener(new Firefly());
		setListener(new FlyEvent());
		setListener(new PlayerJoinMessage());
		setListener(new PlayerQuitMessage());
		setListener(new ModreqJoinEvent());
		if(Hooks.isProtocolLibEnabled()) {
			MotdVanishEvent.initPacketListener();
		} else {
			setListener(new MotdEvent());
		}
		//entity yml
		setListener(new EntitySpawnEventManager());
		if(Configuration.getEntityConfig().isRealisticWaterEnabled()) {setListener(new RealisticWaterEvent());}
		if(Configuration.getEntityConfig().isStonePressurePlate()) {setListener(new EntityPressurePlateInteractEvent());}
		if(Configuration.getEntityConfig().isCleanUpOnChunkUnloadEnabled()) {setListener(new CleanupUnloadedChunkEvent());}
		if(Configuration.getEntityConfig().isChunkProtectionEnabled()) {setListener(new ChunkProtection());}
		if(Configuration.getEntityConfig().isWeatherDisabled()) {setListener(new DisableWeatherEvent());}
		if(Configuration.getEntityConfig().isFireSpreadDisabled()) {setListener(new DisableFireSpreadEvent());}
		if(Configuration.getEntityConfig().isExplosionsDisabled()) {setListener(new DisableExplosionEvent());}
		if(Configuration.getEntityConfig().isFireworksDisabled()) {setListener(new DisableFireworkEvent());}
		if(Configuration.getEntityConfig().isWitherGriefDisabled()) {setListener(new DisableWitherGriefEvent());}
		if(Configuration.getEntityConfig().isEnderManGriefDisabled()) {setListener(new DisableEndermanGrief());}
		if(Configuration.getEntityConfig().isEnderDragonGriefDisabled()) {setListener(new DisableEndDragonGrief());}
		if(Configuration.getEntityConfig().isCustomZombieAggroRangeEnabled()) {setListener(new CustomZombieAggroRange());}
		if(Configuration.getEntityConfig().isLoggingSpawnEggsEnabled()) {setListener(new SpawnEggLogEvent());}
		if(Configuration.getEntityConfig().isExplosionRegenEnabled()) {setListener(new ExplosionRegen());}
		if(Configuration.getEntityConfig().isBloodEnabled()) {setListener(new EntityBleedEvent());}
		//chat.yml
		setListener(new DrunkChatEvent());
		setListener(new SilenceChatEvent());
		setListener(new PlayerIgnorePlayerChatEvent());
		if(Configuration.getChatConfig().isMojangStatusEnabled()) {setListener(new PublishMojangStatus());}
		if(Configuration.getChatConfig().isChatHighLightEnabled()) {setListener(new ChatHighLightEvent());}
		if(Configuration.getChatConfig().isSmilleysEnabled()) {setListener(new ChatSmilleyEvent());}
		if(Configuration.getChatConfig().isAntiAdvertiseEnabled()) {setListener(new AntiAddvertiseEvent());}
		setListener(new MuteEvent());
		//player.yml
		setListener(new SignEditEvent());
		if(Configuration.getPlayerConfig().isAutoRespawnEnabled()) {setListener(new PlayerForceRespawnEvent());}
		setListener(new AntiKnockBackEvent());
		setListener(new FirstJoinTeleportEvent());
		setListener(new MobProcEvent());
		setListener(new CommandRestrictEvent());
		setListener(new TrollModeEvent());
		setListener(new FreezePlayerEvent());
		setListener(new InventoryMenu());
		setListener(new CompassEvent());
		setListener(new FakeNukeEvent());
		setListener(new PowerToolEvent());
		setListener(new PlayerTaskLogin());
		setListener(new StaffSafeTeleport());
		setListener(new PlayerDeathBackEvent());
		setListener(new TeleportBackEvent());
		setListener(new PlayerRespawnTeleport());
		setListener(new PotatoMoveEvent());
		setListener(new TeleportEvent());
		setListener(new AfkChecks());
		setListener(new PlayerCheckNameEvent());
		setListener(new DoubleJumpEvent());
		if(Configuration.getPlayerConfig().isCustomPortalSizeDisabled()) {setListener(new PortalSizeEvent());}
		if(Configuration.getPlayerConfig().isPortalsDisabled()) {setListener(new DisablePortalCreation());}
		if(Configuration.getPlayerConfig().isHungerCancelled()) {setListener(new HungerEvent());}
		if(Configuration.getPlayerConfig().isKeepInventoryOnDeathEnabled()) {setListener(new PlayerHoldItemsEvent());}
		if(Configuration.getPlayerConfig().isSteveHurtSoundEnabled()) {setListener(new SteveHurtSound());}
		if(Configuration.getPlayerConfig().isCanEntityStealHatOnPlayersDeath()) {setListener(new EntityUseHeadOnPlayerDeath());}
		if(!Configuration.getPlayerConfig().isBroadcastAchievementsEnabled()) {setListener(new AchievementEvent());}
		if(Configuration.getPlayerConfig().isRealisticGlassEnabled()) {
			RealisticGlass glass = new RealisticGlass();
			setListener(glass);
			glass.startRegen();
		}
		if(Configuration.getPlayerConfig().isAutoAnvilEnabled()) {setListener(new AnvilResetEvent());}
		if(Configuration.getEntityConfig().isRealisticTreesEnabled()) {setListener(new RealisticTreeEvent());}
		//pvp.yml
		if(Configuration.getPvpConfig().isPvpDisabled()) {setListener(new PvpEvent());}
		if(Configuration.getPvpConfig().isClientGravesEnabled()) {
			if(Hooks.isProtocolLibEnabled()) {
				setListener(new ClientSideGraveYard_ProtocolLib());
			} else {
				setListener(new ClientSideGraveYard());
			}
		}
		if(Configuration.getPvpConfig().isKillBountyEnabled()) {setListener(new KillBountys());}
		if(Configuration.getPvpConfig().isReplaceNpcEnabled()) { setListener(new NpcReplacePlayer()); }
		//ban.yml
		if(Configuration.getBanConfig().isPwnAgeEnabled()) {setListener(new PwnAgeProtectionEvent());}
		if(Configuration.getBanConfig().isFloodSpamEnabled()) {setListener(new FloodSpamEvent());}
		if(Configuration.getBanConfig().isHumanSpamEnabled()) {
			setListener(new HumanSpamEvent());
			setListener(new HumanSpamCommandEvent());
		}
		if(Configuration.getBanConfig().isAlternateAccountsEnabled()) {setListener(new ShowAlternateAccounts());}
		//signs
		if(Configuration.getSignConfig().isColorSignEnabled()) {
			setListener(new ColorSign());	
		}
		if(Configuration.getSignConfig().isFreeSignEnabled()) {
			setListener(new FreeSign());	
		}
		if(Configuration.getSignConfig().isFireworkSignEnabled()) {
			setListener(new FireworkSign());	
		}
		if(Configuration.getSignConfig().isBoomSignEnabled()) {
			setListener(new SignBoom());	
		}
		if(Configuration.getSignConfig().isGetYourHeadSignEnabled()) {
			setListener(new GetYourHeadSign());	
		}
		if(Configuration.getSignConfig().isWarpSignEnabled()) {
			setListener(new WarpSign());	
		}
		if(Configuration.getSignConfig().isWildSignEnabled()) {
			setListener(new WildSign());	
		}
		if(Configuration.getShopConfig().isShopsEnabled()) {
			setListener(new SignAdminShopCreateEvent());
			setListener(new SignAdminShopOpenEvent());
			setListener(new SignNormalShopCreateEvent());
			setListener(new SignNormalShopOpenEvent());
			setListener(new SignShopBreakEvent());
		}
		
		//block events
		if(Configuration.getBlockConfig().isNotifyOnBreakEnabled()) {setListener(new NotifyAdminOnBlockBreak());}
		if(Configuration.getBlockConfig().isBedrockBreakDisabled()) {setListener(new BedrockBreakEvent());}
		if(Configuration.getBlockConfig().isBedrockPlaceDisabled()) {setListener(new BedrockPlaceEvent());}
		if(Configuration.getBlockConfig().isNotifyOnConsumeEnabled()) {setListener(new NotifyItemUseEvent());}
		if(Configuration.getBlockConfig().isBlockBlacklistEnabled()) {setListener(new BlockBlackListEvent());}
		if(Configuration.getBlockConfig().isItemBlacklistEnabled()) {setListener(new ItemBlackListEvent());}
		
		//protection events
		if(Configuration.getProtectionConfig().isProtectionEnabled()) {
			setListener(new UnregisterProtectionEvent());
			setListener(new RegisterProtectionEvent());
			setListener(new ModifyProtectionEvent());
			//setListener(new HopperEvent());
			setListener(new BlockProtectedEvent());
			if(Configuration.getProtectionConfig().isSignProtectionEnabled()) {setListener(new SignProtectedEvent());}
			if(Configuration.getProtectionConfig().isChestProtectionEnabled()) {setListener(new ChestProtectedEvent());}
			if(Configuration.getProtectionConfig().isFurnaceProtectionEnabled()) {setListener(new FurnaceProtectedEvent());}
			if(Configuration.getProtectionConfig().isJukeboxProtectionEnabled()) {setListener(new JukeboxProtectedEvent());}
			if(Configuration.getProtectionConfig().isDispenserEnabled()) {setListener(new DispenserProtectionEvent());}
		}
		
		//portal events
		if(Configuration.getPortalConfig().isPortalEnabled()) {
			setListener(new PortalSelectedCreateEvent());
			setListener(new PortalEvent());
			setListener(new PortalActivateEvent());
		}
		
		//backpack events
		setListener(new BackpackDespawningEvent());
		setListener(new OpenBackPackEvent());
		
		//gate events
		if(Configuration.getMiscConfig().isGatesEnabled()) {
			setListener(new GateCreateEvent());
			setListener(new GateInteractEvent());
			setListener(new GateBreakEvent());
			setListener(new RemoveGateEvent());
			setListener(new GateGriefPrevention());
			if(Configuration.getMiscConfig().isGateRedstoneEnabled()) {
				setListener(new GateRedstone());
			}
		}
		
		//bridge events
		if(Configuration.getMiscConfig().isBridgesEnabled()) {
			setListener(new BridgeCreateEvent());
			setListener(new BridgeInteractEvent());
			setListener(new BridgeBreakEvent());
			setListener(new BridgeGriefPrevention());
			setListener(new RemoveBridgeEvent());
		}
		
		//elevator events
		if(Configuration.getMiscConfig().isElevatorsEnabled()) {
			setListener(new ElevatorCreateEvent());
			setListener(new ElevatorInteract());
		}
		
		//chair events
		if(Configuration.getMiscConfig().isChairsEnabled()) {
			setListener(new PlayerSitOnChair());
			if(Configuration.getMiscConfig().isChairMonsterOff()) {
				setListener(new ChairDisableMonster());
			}
		}
		
		if(Configuration.getMiscConfig().isBooksEnabled()) {
			setListener(new BookInteractEvent());
		}
		
		//arena events
		setListener(new PlayerJoinCheckInventory());
		setListener(new PlayerQuitHandle());
		setListener(new PlayerTeleportCheck());
		
		//vote
		if(Configuration.getVoteConfig().isVoteEnabled()) {
			if(Configuration.getVoteConfig().isMoneyRewardEnabled() && Hooks.isVaultEnabled()) {setListener(new VoteMoneyListener());}
			if(Configuration.getVoteConfig().isRewardCrateEnabled() && Hooks.isManCoEnabled()) {setListener(new VoteCrateListener());}
		}
		
		//spleef    
		setListener(new CreateSpleefArenaEvent());
			
		setListener(new RemoveMemory());
	}

	/**
	 * @author xize
	 * @param automatic stops all events
	 * @return void
	 */
	public void stop() {
		HandlerList.unregisterAll(xEssentials.getPlugin());
	}

	private void setListener(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, xEssentials.getPlugin());
	}

}