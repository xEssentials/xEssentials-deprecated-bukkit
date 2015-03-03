package tv.mineinthebox.essentials.events;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

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
import tv.mineinthebox.essentials.events.motd.NormalMotdEvent;
import tv.mineinthebox.essentials.events.motd.RandomMotdEvent;
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
import tv.mineinthebox.essentials.events.players.OpKitEvent;
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
import tv.mineinthebox.essentials.events.players.WorldGuardMonsterEvent;
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

public class Handler {

	private final xEssentials pl;

	public Handler(xEssentials pl) {
		this.pl = pl;
	}

	/**
	 * @author xize
	 * @param starts all events!
	 * @return void
	 */

	public void start() {
		//memory system
		setListener(new LoadMemoryEvent(pl));
		if(Hooks.isWorldGuardEnabled()) {setListener(new PlayerZoneEvent(pl));}
		setListener(new VanishEvent(pl));
		setListener(new VanishArchievementEvent(pl));
		if(pl.getConfiguration().getPlayerConfig().isSeperatedInventorysEnabled()) {
			setListener(new GameModeInvChangeEvent(pl));
		}
		if(pl.getConfiguration().getPlayerConfig().isSaveInventoryEnabled()) {
			setListener(new SaveLastInventoryEvent(pl));
		} else if(pl.getConfiguration().getPvpConfig().isReplaceNpcEnabled()) {
			setListener(new SaveLastInventoryEvent(pl));
		}
		if(pl.getConfiguration().getPlayerConfig().isWorldBorderEnabled()) {
			setListener(new PlayerBorderEvent(pl));
		}
		if(pl.getConfiguration().getChatConfig().isRssBroadcastEnabled()) {
			setListener(new BroadcastSiteNewsEvent());
		}
		setListener(new SaveLastLocationEvent(pl));
		setListener(new TorchEvent(pl));
		setListener(new FireflyEvent(pl));
		setListener(new FlyEvent(pl));
		setListener(new PlayerJoinMessageEvent(pl));
		setListener(new PlayerQuitMessageEvent(pl));
		setListener(new ModreqJoinEvent(pl));
		if(pl.getConfiguration().getMotdConfig().isRandomMotdEnabled()) {
			setListener(new RandomMotdEvent(pl));
		} else if(pl.getConfiguration().getMotdConfig().isNormalMotdEnabled()) {
			setListener(new NormalMotdEvent(pl));
		}
		setListener(new MotdVanishEvent(pl));
		//entity yml
		setListener(new EntitySpawnEventManagerEvent(pl));
		if(pl.getConfiguration().getEntityConfig().isRealisticWaterEnabled()) {setListener(new RealisticWaterEvent());}
		if(pl.getConfiguration().getEntityConfig().isStonePressurePlatesDisabledForMobs()) {setListener(new EntityPressurePlateInteractEvent());}
		if(pl.getConfiguration().getEntityConfig().isCleanUpOnChunkUnloadEnabled()) {setListener(new CleanupUnloadedChunkEvent());}
		if(pl.getConfiguration().getEntityConfig().isProjectileRemovalEnabled()) {setListener(new ChunkProtectionEvent(pl));}
		if(pl.getConfiguration().getEntityConfig().isWeatherDisabled()) {setListener(new DisableWeatherEvent());}
		if(pl.getConfiguration().getEntityConfig().isFireSpreadDisabled()) {setListener(new DisableFireSpreadEvent());}
		if(pl.getConfiguration().getEntityConfig().isExplosionsDisabled()) {setListener(new DisableExplosionEvent());}
		if(pl.getConfiguration().getEntityConfig().isFireworksDisabled()) {setListener(new DisableFireworkEvent());}
		if(pl.getConfiguration().getEntityConfig().isWitherGriefDisabled()) {setListener(new DisableWitherGriefEvent());}
		if(pl.getConfiguration().getEntityConfig().isEnderManGriefDisabled()) {setListener(new DisableEndermanGriefEvent());}
		if(pl.getConfiguration().getEntityConfig().isEnderDragonGriefDisabled()) {setListener(new DisableEndDragonGriefEvent());}
		if(pl.getConfiguration().getEntityConfig().isCustomZombieAggroRangeEnabled()) {setListener(new CustomZombieAggroRangeEvent(pl));}
		if(pl.getConfiguration().getEntityConfig().isLoggingSpawnEggsEnabled()) {setListener(new SpawnEggLogEvent(pl));}
		if(pl.getConfiguration().getEntityConfig().isExplosionRegenEnabled()) {setListener(new ExplosionRegenEvent(pl));}
		if(pl.getConfiguration().getEntityConfig().isBloodEnabled()) {setListener(new EntityBleedEvent(pl));}
		//chat.yml
		if(pl.getConfiguration().getChatConfig().isSwearFilterEnabled()) {
			setListener(new AntiSwearEvent(pl));
		}
		setListener(new DrunkChatEvent(pl));
		setListener(new SilenceChatEvent(pl));
		setListener(new PlayerIgnorePlayerChatEvent(pl));
		if(pl.getConfiguration().getChatConfig().isChatHighLightEnabled()) {setListener(new ChatHighLightEvent(pl));}
		if(pl.getConfiguration().getChatConfig().isSmilleysEnabled()) {setListener(new ChatSmilleyEvent());}
		if(pl.getConfiguration().getChatConfig().isAntiAdvertiseEnabled()) {setListener(new AntiAddvertiseEvent(pl));}
		setListener(new MuteEvent(pl));
		//player.yml
		if(Hooks.isWorldGuardEnabled()) {
			setListener(new WorldGuardMonsterEvent(pl));
		}
		setListener(new OpKitEvent());
		setListener(new PlayerShootbowSoundEvent());
		setListener(new SignEditEvent(pl));
		if(pl.getConfiguration().getPlayerConfig().isAutoRespawnEnabled()) {setListener(new PlayerForceRespawnEvent(pl));}
		setListener(new AntiKnockBackEvent(pl));
		setListener(new FirstJoinTeleportEvent(pl));
		setListener(new MobProcEvent(pl));
		setListener(new CommandRestrictEvent(pl));
		setListener(new TrollModeEvent(pl));
		setListener(new FreezePlayerEvent(pl));
		setListener(new InventoryMenuEvent(pl));
		setListener(new CompassEvent(pl));
		setListener(new FakeNukeEvent());
		setListener(new PowerToolEvent(pl));
		setListener(new PlayerTaskLoginEvent(pl));
		setListener(new StaffSafeTeleportEvent(pl));
		setListener(new PlayerDeathBackEvent(pl));
		setListener(new TeleportBackEvent(pl));
		setListener(new PlayerRespawnTeleportEvent(pl));
		setListener(new PotatoMoveEvent(pl));
		setListener(new AfkCheckEvent(pl));
		setListener(new PlayerCheckNameEvent());
		setListener(new PlayerFloorEvent(pl));
		setListener(new PlayerWallEvent(pl));
		setListener(new DoubleJumpEvent(pl));
		if(pl.getConfiguration().getPlayerConfig().isCustomPortalSizeDisabled()) {setListener(new PortalSizeEvent());}
		if(pl.getConfiguration().getPlayerConfig().isPortalsDisabled()) {setListener(new DisablePortalCreationEvent());}
		if(pl.getConfiguration().getPlayerConfig().isHungerCancelled()) {setListener(new HungerEvent());}
		if(pl.getConfiguration().getPlayerConfig().isKeepInventoryOnDeathEnabled()) {setListener(new PlayerHoldItemsEvent(pl));}
		if(pl.getConfiguration().getPlayerConfig().isCanEntityStealHatOnPlayersDeath()) {setListener(new EntityUseHeadOnPlayerDeathEvent());}
		if(!pl.getConfiguration().getPlayerConfig().isBroadcastAchievementsEnabled()) {setListener(new AchievementEvent());}
		if(pl.getConfiguration().getEntityConfig().isLeaveDecayDisabled()) {setListener(new StopLeavesDecayEvent());}
		if(pl.getConfiguration().getEntityConfig().isRealisticGlassEnabled()) {
			RealisticGlassEvent glass = new RealisticGlassEvent(pl);
			setListener(glass);
			glass.startRegen();
		}
		if(pl.getConfiguration().getPlayerConfig().isAutoAnvilEnabled()) {setListener(new AnvilResetEvent());}
		if(pl.getConfiguration().getEntityConfig().isRealisticTreesEnabled()) {setListener(new RealisticTreeEvent(pl));}
		//pvp.yml
		if(pl.getConfiguration().getPvpConfig().isFakePvpEnabled()) {setListener(new FakePvpEvent(pl));}
		if(pl.getConfiguration().getPvpConfig().isPvpDisabled()) {setListener(new PvpEvent(pl));}
		if(pl.getConfiguration().getPvpConfig().isClientGravesEnabled()) {
			setListener(new ClientSideGraveYardEvent(pl));
		}
		if(pl.getConfiguration().getPvpConfig().isKillBountyEnabled()) {setListener(new KillBountyEvent(pl));}
		if(pl.getConfiguration().getPvpConfig().isReplaceNpcEnabled()) { setListener(new NpcReplacePlayerEvent(pl)); }
		//ban.yml
		if(pl.getConfiguration().getBanConfig().isPwnAgeEnabled()) {setListener(new PwnAgeProtectionEvent(pl));}
		if(pl.getConfiguration().getBanConfig().isFloodSpamEnabled()) {setListener(new FloodSpamEvent(pl));}
		if(pl.getConfiguration().getBanConfig().isHumanSpamEnabled()) {
			setListener(new HumanSpamEvent(pl));
			setListener(new HumanSpamCommandEvent(pl));
		}
		if(pl.getConfiguration().getBanConfig().isAlternateAccountsEnabled()) {setListener(new ShowAlternateAccountsEvent(pl));}
		//signs
		if(pl.getConfiguration().getSignConfig().isColorSignEnabled()) {
			setListener(new ColorSignEvent());	
		}
		if(pl.getConfiguration().getSignConfig().isFreeSignEnabled()) {
			setListener(new FreeSignEvent());	
		}
		if(pl.getConfiguration().getSignConfig().isFireworkSignEnabled()) {
			setListener(new FireworkSignEvent(pl));	
		}
		if(pl.getConfiguration().getSignConfig().isBoomSignEnabled()) {
			setListener(new SignBoomEvent(pl));	
		}
		if(pl.getConfiguration().getSignConfig().isGetYourHeadSignEnabled()) {
			setListener(new GetYourHeadSignEvent());	
		}
		if(pl.getConfiguration().getSignConfig().isWarpSignEnabled()) {
			setListener(new WarpSignEvent(pl));	
		}
		if(pl.getConfiguration().getSignConfig().isWildSignEnabled()) {
			setListener(new WildSignEvent());	
		}
		if(pl.getConfiguration().getSignConfig().isDispenserEnabled()) {
			setListener(new DispenseSignEvent());
		}
		if(pl.getConfiguration().getShopConfig().isShopEnabled()) {
			//TO-DO:recreate shops.
			setListener(new CreateShopEvent(pl));
			setListener(new ShopInteractEvent(pl));
			setListener(new RemoveShopEvent(pl));
			setListener(new AdminShopInventoryEvent(pl));
		}

		//block events
		if(pl.getConfiguration().getBlockConfig().isNotifyOnBreakEnabled()) {setListener(new NotifyAdminOnBlockBreakEvent(pl));}
		if(pl.getConfiguration().getBlockConfig().isBedrockBreakDisabled()) {setListener(new BedrockBreakEvent());}
		if(pl.getConfiguration().getBlockConfig().isBedrockPlaceDisabled()) {setListener(new BedrockPlaceEvent());}
		if(pl.getConfiguration().getBlockConfig().isNotifyOnConsumeEnabled()) {setListener(new NotifyItemUseEvent(pl));}
		if(pl.getConfiguration().getBlockConfig().isBlockBlacklistEnabled()) {setListener(new BlockBlackListEvent(pl));}
		if(pl.getConfiguration().getBlockConfig().isItemBlacklistEnabled()) {setListener(new ItemBlackListEvent(pl));}

		//protection events
		if(pl.getConfiguration().getProtectionConfig().isProtectionEnabled()) {
			setListener(new UnregisterProtectionEvent(pl));
			setListener(new RegisterProtectionEvent(pl));
			setListener(new ModifyProtectionEvent(pl));
			//setListener(new HopperEvent());
			setListener(new BlockProtectedEvent(pl));
			if(pl.getConfiguration().getProtectionConfig().isSignProtectionEnabled()) {setListener(new SignProtectedEvent(pl));}
			if(pl.getConfiguration().getProtectionConfig().isChestProtectionEnabled()) {setListener(new ChestProtectedEvent(pl));}
			if(pl.getConfiguration().getProtectionConfig().isFurnaceProtectionEnabled()) {setListener(new FurnaceProtectedEvent(pl));}
			if(pl.getConfiguration().getProtectionConfig().isJukeboxProtectionEnabled()) {setListener(new JukeboxProtectedEvent(pl));}
			if(pl.getConfiguration().getProtectionConfig().isDispenserEnabled()) {setListener(new DispenserProtectionEvent(pl));}
		}

		//portal events
		if(pl.getConfiguration().getPortalConfig().isPortalEnabled()) {
			setListener(new PortalSelectedCreateEvent(pl));
			setListener(new PortalEvent(pl));
			setListener(new PortalActivateEvent(pl));
		}

		//backpack events
		setListener(new BackpackDespawningEvent(pl));
		setListener(new OpenBackPackEvent(pl));

		//gate events
		if(pl.getConfiguration().getMiscConfig().isGatesEnabled()) {
			setListener(new GateCreateEvent(pl));
			setListener(new GateInteractEvent(pl));
			setListener(new GateBreakEvent(pl));
			setListener(new RemoveGateEvent(pl));
			setListener(new GateGriefPreventionEvent(pl));
			if(pl.getConfiguration().getMiscConfig().isGateRedstoneEnabled()) {
				setListener(new GateRedstoneEvent(pl));
			}
		}

		//bridge events
		if(pl.getConfiguration().getMiscConfig().isBridgesEnabled()) {
			setListener(new BridgeCreateEvent(pl));
			setListener(new BridgeInteractEvent(pl));
			setListener(new BridgeBreakEvent(pl));
			setListener(new BridgeGriefPreventionEvent(pl));
			setListener(new RemoveBridgeEvent(pl));
		}

		//elevator events
		if(pl.getConfiguration().getMiscConfig().isElevatorsEnabled()) {
			setListener(new ElevatorCreateEvent());
			setListener(new ElevatorInteractEvent(pl));
		}

		//chair events
		if(pl.getConfiguration().getMiscConfig().isChairsEnabled()) {
			setListener(new PlayerSitOnChairEvent(pl));
			if(pl.getConfiguration().getMiscConfig().isChairMonsterOff()) {
				setListener(new ChairDisableMonsterEvent(pl));
			}
		}

		if(pl.getConfiguration().getMiscConfig().isBooksEnabled()) {
			setListener(new BookInteractEvent(pl));
		}

		//vote
		if(pl.getConfiguration().getVoteConfig().isVoteEnabled() && Hooks.isVotifierEnabled()) {
			if(pl.getConfiguration().getVoteConfig().isMoneyRewardEnabled() && Hooks.isVaultEcoEnabled()) {setListener(new VoteMoneyEvent(pl));}
			if(pl.getConfiguration().getVoteConfig().isRewardCrateEnabled() && Hooks.isManCoEnabled()) {setListener(new VoteCrateEvent(pl));}
		}

		setListener(new RemoveMemoryEvent(pl));
	}

	/**
	 * @author xize
	 * @param automatic stops all events
	 * @return void
	 */
	public void stop() {
		HandlerList.unregisterAll(pl);
	}

	private void setListener(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, pl);
	}

}