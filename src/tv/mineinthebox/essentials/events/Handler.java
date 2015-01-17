package tv.mineinthebox.essentials.events;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.events.backpack.BackpackDespawningEvent;
import tv.mineinthebox.essentials.events.backpack.OpenBackPackEvent;
import tv.mineinthebox.essentials.events.ban.FloodSpamEvent;
import tv.mineinthebox.essentials.events.ban.HumanSpamCommandEvent;
import tv.mineinthebox.essentials.events.ban.HumanSpamEvent;
import tv.mineinthebox.essentials.events.ban.PwnAgeProtectionEvent;
import tv.mineinthebox.essentials.events.ban.ShowAlternateAccountsEvent;
import tv.mineinthebox.essentials.events.blocks.BedrockBreakEvent;
import tv.mineinthebox.essentials.events.blocks.BedrockPlaceEvent;
import tv.mineinthebox.essentials.events.blocks.BlockBlackListEvent;
import tv.mineinthebox.essentials.events.blocks.ItemBlackListEvent;
import tv.mineinthebox.essentials.events.blocks.NotifyAdminOnBlockBreakEvent;
import tv.mineinthebox.essentials.events.blocks.NotifyItemUseEvent;
import tv.mineinthebox.essentials.events.books.BookInteractEvent;
import tv.mineinthebox.essentials.events.bridges.BridgeBreakEvent;
import tv.mineinthebox.essentials.events.bridges.BridgeCreateEvent;
import tv.mineinthebox.essentials.events.bridges.BridgeGriefPreventionEvent;
import tv.mineinthebox.essentials.events.bridges.BridgeInteractEvent;
import tv.mineinthebox.essentials.events.bridges.RemoveBridgeEvent;
import tv.mineinthebox.essentials.events.chairs.ChairDisableMonsterEvent;
import tv.mineinthebox.essentials.events.chairs.PlayerSitOnChairEvent;
import tv.mineinthebox.essentials.events.chat.AntiAddvertiseEvent;
import tv.mineinthebox.essentials.events.chat.AntiSwearEvent;
import tv.mineinthebox.essentials.events.chat.BroadcastSiteNewsEvent;
import tv.mineinthebox.essentials.events.chat.ChatHighLightEvent;
import tv.mineinthebox.essentials.events.chat.ChatSmilleyEvent;
import tv.mineinthebox.essentials.events.chat.DrunkChatEvent;
import tv.mineinthebox.essentials.events.chat.MuteEvent;
import tv.mineinthebox.essentials.events.chat.PlayerIgnorePlayerChatEvent;
import tv.mineinthebox.essentials.events.chat.PublishMojangStatusEvent;
import tv.mineinthebox.essentials.events.chat.SilenceChatEvent;
import tv.mineinthebox.essentials.events.elevators.ElevatorCreateEvent;
import tv.mineinthebox.essentials.events.elevators.ElevatorInteractEvent;
import tv.mineinthebox.essentials.events.entity.ChunkProtectionEvent;
import tv.mineinthebox.essentials.events.entity.CleanupUnloadedChunkEvent;
import tv.mineinthebox.essentials.events.entity.CustomZombieAggroRangeEvent;
import tv.mineinthebox.essentials.events.entity.DisableEndDragonGriefEvent;
import tv.mineinthebox.essentials.events.entity.DisableEndermanGriefEvent;
import tv.mineinthebox.essentials.events.entity.DisableExplosionEvent;
import tv.mineinthebox.essentials.events.entity.DisableFireSpreadEvent;
import tv.mineinthebox.essentials.events.entity.DisableFireworkEvent;
import tv.mineinthebox.essentials.events.entity.DisableWeatherEvent;
import tv.mineinthebox.essentials.events.entity.DisableWitherGriefEvent;
import tv.mineinthebox.essentials.events.entity.EntityBleedEvent;
import tv.mineinthebox.essentials.events.entity.EntityPressurePlateInteractEvent;
import tv.mineinthebox.essentials.events.entity.EntitySpawnEventManagerEvent;
import tv.mineinthebox.essentials.events.entity.ExplosionRegenEvent;
import tv.mineinthebox.essentials.events.entity.RealisticGlassEvent;
import tv.mineinthebox.essentials.events.entity.RealisticTreeEvent;
import tv.mineinthebox.essentials.events.entity.RealisticWaterEvent;
import tv.mineinthebox.essentials.events.entity.SpawnEggLogEvent;
import tv.mineinthebox.essentials.events.entity.StopLeavesDecayEvent;
import tv.mineinthebox.essentials.events.gates.GateBreakEvent;
import tv.mineinthebox.essentials.events.gates.GateCreateEvent;
import tv.mineinthebox.essentials.events.gates.GateGriefPreventionEvent;
import tv.mineinthebox.essentials.events.gates.GateInteractEvent;
import tv.mineinthebox.essentials.events.gates.GateRedstoneEvent;
import tv.mineinthebox.essentials.events.gates.RemoveGateEvent;
import tv.mineinthebox.essentials.events.motd.MotdEvent;
import tv.mineinthebox.essentials.events.motd.MotdVanishEvent;
import tv.mineinthebox.essentials.events.players.AchievementEvent;
import tv.mineinthebox.essentials.events.players.AfkCheckEvent;
import tv.mineinthebox.essentials.events.players.AntiKnockBackEvent;
import tv.mineinthebox.essentials.events.players.AnvilResetEvent;
import tv.mineinthebox.essentials.events.players.CommandRestrictEvent;
import tv.mineinthebox.essentials.events.players.CompassEvent;
import tv.mineinthebox.essentials.events.players.DisablePortalCreationEvent;
import tv.mineinthebox.essentials.events.players.DoubleJumpEvent;
import tv.mineinthebox.essentials.events.players.EntityUseHeadOnPlayerDeathEvent;
import tv.mineinthebox.essentials.events.players.FakeNukeEvent;
import tv.mineinthebox.essentials.events.players.FireflyEvent;
import tv.mineinthebox.essentials.events.players.FirstJoinTeleportEvent;
import tv.mineinthebox.essentials.events.players.FlyEvent;
import tv.mineinthebox.essentials.events.players.FreezePlayerEvent;
import tv.mineinthebox.essentials.events.players.GameModeInvChangeEvent;
import tv.mineinthebox.essentials.events.players.HungerEvent;
import tv.mineinthebox.essentials.events.players.InventoryMenuEvent;
import tv.mineinthebox.essentials.events.players.LoadMemoryEvent;
import tv.mineinthebox.essentials.events.players.MobProcEvent;
import tv.mineinthebox.essentials.events.players.ModreqJoinEvent;
import tv.mineinthebox.essentials.events.players.PlayerBorderEvent;
import tv.mineinthebox.essentials.events.players.PlayerCheckNameEvent;
import tv.mineinthebox.essentials.events.players.PlayerDeathBackEvent;
import tv.mineinthebox.essentials.events.players.PlayerFloorEvent;
import tv.mineinthebox.essentials.events.players.PlayerForceRespawnEvent;
import tv.mineinthebox.essentials.events.players.PlayerHoldItemsEvent;
import tv.mineinthebox.essentials.events.players.PlayerJoinMessageEvent;
import tv.mineinthebox.essentials.events.players.PlayerQuitMessageEvent;
import tv.mineinthebox.essentials.events.players.PlayerRespawnTeleportEvent;
import tv.mineinthebox.essentials.events.players.PlayerShootbowSoundEvent;
import tv.mineinthebox.essentials.events.players.PlayerTaskLoginEvent;
import tv.mineinthebox.essentials.events.players.PlayerVanillaBorderEvent;
import tv.mineinthebox.essentials.events.players.PlayerWallEvent;
import tv.mineinthebox.essentials.events.players.PlayerZoneEvent;
import tv.mineinthebox.essentials.events.players.PortalSizeEvent;
import tv.mineinthebox.essentials.events.players.PotatoMoveEvent;
import tv.mineinthebox.essentials.events.players.PowerToolEvent;
import tv.mineinthebox.essentials.events.players.RemoveMemoryEvent;
import tv.mineinthebox.essentials.events.players.SaveLastInventoryEvent;
import tv.mineinthebox.essentials.events.players.SaveLastLocationEvent;
import tv.mineinthebox.essentials.events.players.SignEditEvent;
import tv.mineinthebox.essentials.events.players.StaffSafeTeleportEvent;
import tv.mineinthebox.essentials.events.players.TeleportBackEvent;
import tv.mineinthebox.essentials.events.players.TorchEvent;
import tv.mineinthebox.essentials.events.players.TrollModeEvent;
import tv.mineinthebox.essentials.events.players.VanishArchievementEvent;
import tv.mineinthebox.essentials.events.players.VanishEvent;
import tv.mineinthebox.essentials.events.portals.PortalActivateEvent;
import tv.mineinthebox.essentials.events.portals.PortalEvent;
import tv.mineinthebox.essentials.events.portals.PortalSelectedCreateEvent;
import tv.mineinthebox.essentials.events.protection.BlockProtectedEvent;
import tv.mineinthebox.essentials.events.protection.ChestProtectedEvent;
import tv.mineinthebox.essentials.events.protection.DispenserProtectionEvent;
import tv.mineinthebox.essentials.events.protection.FurnaceProtectedEvent;
import tv.mineinthebox.essentials.events.protection.JukeboxProtectedEvent;
import tv.mineinthebox.essentials.events.protection.ModifyProtectionEvent;
import tv.mineinthebox.essentials.events.protection.RegisterProtectionEvent;
import tv.mineinthebox.essentials.events.protection.SignProtectedEvent;
import tv.mineinthebox.essentials.events.protection.UnregisterProtectionEvent;
import tv.mineinthebox.essentials.events.pvp.ClientSideGraveYardEvent;
import tv.mineinthebox.essentials.events.pvp.ClientSideGraveYard_ProtocolLibEvent;
import tv.mineinthebox.essentials.events.pvp.FakePvpEvent;
import tv.mineinthebox.essentials.events.pvp.KillBountyEvent;
import tv.mineinthebox.essentials.events.pvp.NpcReplacePlayerEvent;
import tv.mineinthebox.essentials.events.pvp.PvpEvent;
import tv.mineinthebox.essentials.events.shops.AdminShopInventoryEvent;
import tv.mineinthebox.essentials.events.shops.CreateShopEvent;
import tv.mineinthebox.essentials.events.shops.RemoveShopEvent;
import tv.mineinthebox.essentials.events.shops.ShopInteractEvent;
import tv.mineinthebox.essentials.events.signs.ColorSignEvent;
import tv.mineinthebox.essentials.events.signs.DispenseSignEvent;
import tv.mineinthebox.essentials.events.signs.FireworkSignEvent;
import tv.mineinthebox.essentials.events.signs.FreeSignEvent;
import tv.mineinthebox.essentials.events.signs.GetYourHeadSignEvent;
import tv.mineinthebox.essentials.events.signs.SignBoomEvent;
import tv.mineinthebox.essentials.events.signs.WarpSignEvent;
import tv.mineinthebox.essentials.events.signs.WildSignEvent;
import tv.mineinthebox.essentials.events.vote.VoteCrateEvent;
import tv.mineinthebox.essentials.events.vote.VoteMoneyEvent;
import tv.mineinthebox.essentials.hook.Hooks;
import tv.mineinthebox.essentials.minigames.football.listeners.FootballCreationEvent;
import tv.mineinthebox.essentials.minigames.football.listeners.FootballNoDamageEvent;
import tv.mineinthebox.essentials.minigames.football.listeners.FootballScoreEvent;
import tv.mineinthebox.essentials.minigames.memory.MinigameQuitEvent;
import tv.mineinthebox.essentials.minigames.memory.MinigameSessionRemoveEvent;
import tv.mineinthebox.essentials.minigames.memory.MinigameTeleportEvent;
import tv.mineinthebox.essentials.minigames.tennis.listeners.TennisBallVelocityEvent;
import tv.mineinthebox.essentials.minigames.tennis.listeners.TennisCreationEvent;
import tv.mineinthebox.essentials.minigames.tennis.listeners.TennisScoreEvent;

public class Handler {

	/**
	 * @author xize
	 * @param starts all events!
	 * @return void
	 */

	public void start() {
		//memory system
		setListener(new LoadMemoryEvent());
		if(Hooks.isWorldGuardEnabled()) {setListener(new PlayerZoneEvent());}
		setListener(new VanishEvent());
		setListener(new VanishArchievementEvent());
		if(Configuration.getPlayerConfig().isSeperatedInventorysEnabled()) {
			setListener(new GameModeInvChangeEvent());
		}
		if(Configuration.getPlayerConfig().isSaveInventoryEnabled()) {
			setListener(new SaveLastInventoryEvent());
		} else if(Configuration.getPvpConfig().isReplaceNpcEnabled()) {
			setListener(new SaveLastInventoryEvent());
		}
		if(Configuration.getPlayerConfig().isWorldBorderEnabled()) {
			if(Configuration.getPlayerConfig().isVanillaBorderSupported()) {
				setListener(new PlayerVanillaBorderEvent());	
			} else {
				setListener(new PlayerBorderEvent());
			}
		}
		if(Configuration.getChatConfig().isRssBroadcastEnabled()) {
			setListener(new BroadcastSiteNewsEvent());
		}
		setListener(new SaveLastLocationEvent());
		setListener(new TorchEvent());
		setListener(new FireflyEvent());
		setListener(new FlyEvent());
		setListener(new PlayerJoinMessageEvent());
		setListener(new PlayerQuitMessageEvent());
		setListener(new ModreqJoinEvent());
		if(Hooks.isProtocolLibEnabled()) {
			MotdVanishEvent.initPacketListener();
		} else {
			setListener(new MotdEvent());
		}
		//entity yml
		setListener(new EntitySpawnEventManagerEvent());
		if(Configuration.getEntityConfig().isRealisticWaterEnabled()) {setListener(new RealisticWaterEvent());}
		if(Configuration.getEntityConfig().isStonePressurePlate()) {setListener(new EntityPressurePlateInteractEvent());}
		if(Configuration.getEntityConfig().isCleanUpOnChunkUnloadEnabled()) {setListener(new CleanupUnloadedChunkEvent());}
		if(Configuration.getEntityConfig().isChunkProtectionEnabled()) {setListener(new ChunkProtectionEvent());}
		if(Configuration.getEntityConfig().isWeatherDisabled()) {setListener(new DisableWeatherEvent());}
		if(Configuration.getEntityConfig().isFireSpreadDisabled()) {setListener(new DisableFireSpreadEvent());}
		if(Configuration.getEntityConfig().isExplosionsDisabled()) {setListener(new DisableExplosionEvent());}
		if(Configuration.getEntityConfig().isFireworksDisabled()) {setListener(new DisableFireworkEvent());}
		if(Configuration.getEntityConfig().isWitherGriefDisabled()) {setListener(new DisableWitherGriefEvent());}
		if(Configuration.getEntityConfig().isEnderManGriefDisabled()) {setListener(new DisableEndermanGriefEvent());}
		if(Configuration.getEntityConfig().isEnderDragonGriefDisabled()) {setListener(new DisableEndDragonGriefEvent());}
		if(Configuration.getEntityConfig().isCustomZombieAggroRangeEnabled()) {setListener(new CustomZombieAggroRangeEvent());}
		if(Configuration.getEntityConfig().isLoggingSpawnEggsEnabled()) {setListener(new SpawnEggLogEvent());}
		if(Configuration.getEntityConfig().isExplosionRegenEnabled()) {setListener(new ExplosionRegenEvent());}
		if(Configuration.getEntityConfig().isBloodEnabled()) {setListener(new EntityBleedEvent());}
		//chat.yml
		if(Configuration.getChatConfig().isSwearFilterEnabled()) {
			setListener(new AntiSwearEvent());
		}
		setListener(new DrunkChatEvent());
		setListener(new SilenceChatEvent());
		setListener(new PlayerIgnorePlayerChatEvent());
		if(Configuration.getChatConfig().isMojangStatusEnabled()) {setListener(new PublishMojangStatusEvent());}
		if(Configuration.getChatConfig().isChatHighLightEnabled()) {setListener(new ChatHighLightEvent());}
		if(Configuration.getChatConfig().isSmilleysEnabled()) {setListener(new ChatSmilleyEvent());}
		if(Configuration.getChatConfig().isAntiAdvertiseEnabled()) {setListener(new AntiAddvertiseEvent());}
		setListener(new MuteEvent());
		//player.yml
		setListener(new PlayerShootbowSoundEvent());
		setListener(new SignEditEvent());
		if(Configuration.getPlayerConfig().isAutoRespawnEnabled()) {setListener(new PlayerForceRespawnEvent());}
		setListener(new AntiKnockBackEvent());
		setListener(new FirstJoinTeleportEvent());
		setListener(new MobProcEvent());
		setListener(new CommandRestrictEvent());
		setListener(new TrollModeEvent());
		setListener(new FreezePlayerEvent());
		setListener(new InventoryMenuEvent());
		setListener(new CompassEvent());
		setListener(new FakeNukeEvent());
		setListener(new PowerToolEvent());
		setListener(new PlayerTaskLoginEvent());
		setListener(new StaffSafeTeleportEvent());
		setListener(new PlayerDeathBackEvent());
		setListener(new TeleportBackEvent());
		setListener(new PlayerRespawnTeleportEvent());
		setListener(new PotatoMoveEvent());
		setListener(new AfkCheckEvent());
		setListener(new PlayerCheckNameEvent());
		setListener(new PlayerFloorEvent());
		setListener(new PlayerWallEvent());
		setListener(new DoubleJumpEvent());
		if(Configuration.getPlayerConfig().isCustomPortalSizeDisabled()) {setListener(new PortalSizeEvent());}
		if(Configuration.getPlayerConfig().isPortalsDisabled()) {setListener(new DisablePortalCreationEvent());}
		if(Configuration.getPlayerConfig().isHungerCancelled()) {setListener(new HungerEvent());}
		if(Configuration.getPlayerConfig().isKeepInventoryOnDeathEnabled()) {setListener(new PlayerHoldItemsEvent());}
		if(Configuration.getPlayerConfig().isCanEntityStealHatOnPlayersDeath()) {setListener(new EntityUseHeadOnPlayerDeathEvent());}
		if(!Configuration.getPlayerConfig().isBroadcastAchievementsEnabled()) {setListener(new AchievementEvent());}
		if(Configuration.getEntityConfig().isLeaveDecayDisabled()) {setListener(new StopLeavesDecayEvent());}
		if(Configuration.getEntityConfig().isRealisticGlassEnabled()) {
			RealisticGlassEvent glass = new RealisticGlassEvent();
			setListener(glass);
			glass.startRegen();
		}
		if(Configuration.getPlayerConfig().isAutoAnvilEnabled()) {setListener(new AnvilResetEvent());}
		if(Configuration.getEntityConfig().isRealisticTreesEnabled()) {setListener(new RealisticTreeEvent());}
		//pvp.yml
		if(Configuration.getPvpConfig().isFakePvpEnabled()) {setListener(new FakePvpEvent());}
		if(Configuration.getPvpConfig().isPvpDisabled()) {setListener(new PvpEvent());}
		if(Configuration.getPvpConfig().isClientGravesEnabled()) {
			if(Hooks.isProtocolLibEnabled()) {
				setListener(new ClientSideGraveYard_ProtocolLibEvent());
			} else {
				setListener(new ClientSideGraveYardEvent());
			}
		}
		if(Configuration.getPvpConfig().isKillBountyEnabled()) {setListener(new KillBountyEvent());}
		if(Configuration.getPvpConfig().isReplaceNpcEnabled()) { setListener(new NpcReplacePlayerEvent()); }
		//ban.yml
		if(Configuration.getBanConfig().isPwnAgeEnabled()) {setListener(new PwnAgeProtectionEvent());}
		if(Configuration.getBanConfig().isFloodSpamEnabled()) {setListener(new FloodSpamEvent());}
		if(Configuration.getBanConfig().isHumanSpamEnabled()) {
			setListener(new HumanSpamEvent());
			setListener(new HumanSpamCommandEvent());
		}
		if(Configuration.getBanConfig().isAlternateAccountsEnabled()) {setListener(new ShowAlternateAccountsEvent());}
		//signs
		if(Configuration.getSignConfig().isColorSignEnabled()) {
			setListener(new ColorSignEvent());	
		}
		if(Configuration.getSignConfig().isFreeSignEnabled()) {
			setListener(new FreeSignEvent());	
		}
		if(Configuration.getSignConfig().isFireworkSignEnabled()) {
			setListener(new FireworkSignEvent());	
		}
		if(Configuration.getSignConfig().isBoomSignEnabled()) {
			setListener(new SignBoomEvent());	
		}
		if(Configuration.getSignConfig().isGetYourHeadSignEnabled()) {
			setListener(new GetYourHeadSignEvent());	
		}
		if(Configuration.getSignConfig().isWarpSignEnabled()) {
			setListener(new WarpSignEvent());	
		}
		if(Configuration.getSignConfig().isWildSignEnabled()) {
			setListener(new WildSignEvent());	
		}
		if(Configuration.getSignConfig().isDispenserEnabled()) {
			setListener(new DispenseSignEvent());
		}
		if(Configuration.getShopConfig().isShopEnabled()) {
			//TO-DO:recreate shops.
			setListener(new CreateShopEvent());
			setListener(new ShopInteractEvent());
			setListener(new RemoveShopEvent());
			setListener(new AdminShopInventoryEvent());
		}
		
		//block events
		if(Configuration.getBlockConfig().isNotifyOnBreakEnabled()) {setListener(new NotifyAdminOnBlockBreakEvent());}
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
			setListener(new GateGriefPreventionEvent());
			if(Configuration.getMiscConfig().isGateRedstoneEnabled()) {
				setListener(new GateRedstoneEvent());
			}
		}
		
		//bridge events
		if(Configuration.getMiscConfig().isBridgesEnabled()) {
			setListener(new BridgeCreateEvent());
			setListener(new BridgeInteractEvent());
			setListener(new BridgeBreakEvent());
			setListener(new BridgeGriefPreventionEvent());
			setListener(new RemoveBridgeEvent());
		}
		
		//elevator events
		if(Configuration.getMiscConfig().isElevatorsEnabled()) {
			setListener(new ElevatorCreateEvent());
			setListener(new ElevatorInteractEvent());
		}
		
		//chair events
		if(Configuration.getMiscConfig().isChairsEnabled()) {
			setListener(new PlayerSitOnChairEvent());
			if(Configuration.getMiscConfig().isChairMonsterOff()) {
				setListener(new ChairDisableMonsterEvent());
			}
		}
		
		if(Configuration.getMiscConfig().isBooksEnabled()) {
			setListener(new BookInteractEvent());
		}
		
		//vote
		if(Configuration.getVoteConfig().isVoteEnabled() && Hooks.isVotifierEnabled()) {
			if(Configuration.getVoteConfig().isMoneyRewardEnabled() && Hooks.isVaultEcoEnabled()) {setListener(new VoteMoneyEvent());}
			if(Configuration.getVoteConfig().isRewardCrateEnabled() && Hooks.isManCoEnabled()) {setListener(new VoteCrateEvent());}
		}
		
		//minigames
		if(Configuration.getMinigameConfig().isMinigamesEnabled()) {
			setListener(new MinigameSessionRemoveEvent());
			setListener(new MinigameQuitEvent());
			setListener(new MinigameTeleportEvent());
			
			setListener(new TennisCreationEvent());
			setListener(new TennisBallVelocityEvent());
			setListener(new TennisScoreEvent());
			
			setListener(new FootballCreationEvent());
			setListener(new FootballScoreEvent());
			setListener(new FootballNoDamageEvent());
		}
			
		setListener(new RemoveMemoryEvent());
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