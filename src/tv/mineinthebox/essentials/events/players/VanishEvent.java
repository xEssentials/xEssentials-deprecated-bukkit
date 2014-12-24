package tv.mineinthebox.essentials.events.players;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class VanishEvent implements Listener {

	@EventHandler(priority = EventPriority.LOW)
	public void onJoin(PlayerJoinEvent e) {
		//obtaining all global intances and whenever isVanished() triggers from the config we vanish them again
		for(XPlayer xp : xEssentials.getManagers().getPlayerManager().getPlayers()) {
			if(xp.isVanished()) {
				xp.vanish();
			}
		}
	}

	@EventHandler
	public void onTarget(EntityTargetEvent e) {
		if(e.getTarget() instanceof Player) {
			Player p = (Player) e.getTarget();
			if(xEssentials.getManagers().getPlayerManager().isOnline(p.getName())) {
				XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(p.getName());
				if(xp.isVanished()) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onTarget(EntityTargetLivingEntityEvent e) {
		if(e.getTarget() instanceof Player) {
			Player p = (Player) e.getTarget();
			if(xEssentials.getManagers().getPlayerManager().isOnline(p.getName())) {
				XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(p.getName());
				if(xp.isVanished()) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void vanishItems(PlayerPickupItemEvent e) {
		if(xEssentials.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isVanished()) {
				if(xp.isNoPickUpEnabled()) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void vanishInteract(PlayerInteractEvent e) {	
		if(e.isCancelled()) {
			return;
		}
		if(xEssentials.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if(xp.isPotato()) {
					e.setCancelled(true);
					return;
				}
				if(xp.isVanished()) {
					if(xp.isNoPickUpEnabled()) {
						if(e.getClickedBlock().getType() == Material.CHEST) {
							Chest chest = (Chest) e.getClickedBlock().getState();
							if(chest.getInventory().getSize() == 27) {
								Inventory inv = Bukkit.createInventory(chest, chest.getInventory().getType());
								inv.setContents(chest.getInventory().getContents());
								e.getPlayer().openInventory(inv);
								e.setCancelled(true);
								e.getPlayer().sendMessage(ChatColor.GRAY + "you are vanished so you silence accessed the chest");
							} else if(chest.getInventory().getSize() == 54) {
								DoubleChestInventory inv = (DoubleChestInventory) chest.getInventory();
								Inventory inv2 = Bukkit.createInventory(inv.getHolder(), 54);
								inv2.setContents(inv.getContents());
								e.getPlayer().openInventory(inv2);
								e.setCancelled(true);
								e.getPlayer().sendMessage(ChatColor.GRAY + "you are vanished so you silence accessed the chest");
							}
						} else if(e.getClickedBlock().getType() == Material.TRAPPED_CHEST) {
							Chest chest = (Chest) e.getClickedBlock().getState();
							if(chest.getInventory().getSize() == 27) {
								Inventory inv = Bukkit.createInventory(chest, chest.getInventory().getType());
								inv.setContents(chest.getInventory().getContents());
								e.getPlayer().openInventory(inv);
								e.setCancelled(true);
								e.getPlayer().sendMessage(ChatColor.GRAY + "you are vanished so you silence accessed the chest");
							} else if(chest.getInventory().getSize() == 54) {
								DoubleChestInventory inv = (DoubleChestInventory) chest.getInventory();
								Inventory inv2 = Bukkit.createInventory(inv.getHolder(), 54);
								inv2.setContents(inv.getContents());
								e.getPlayer().openInventory(inv2);
								e.setCancelled(true);
								e.getPlayer().sendMessage(ChatColor.GRAY + "you are vanished so you silence accessed the chest");
							}
						} else if(e.getClickedBlock().getType() == Material.TRAP_DOOR) {
							Location loc = e.getClickedBlock().getRelative(e.getBlockFace().getOppositeFace()).getLocation();
							loc.setYaw(e.getPlayer().getLocation().getYaw());
							loc.setPitch(e.getPlayer().getLocation().getPitch());
							e.getPlayer().sendMessage(ChatColor.GRAY + "you are vanished so we sillenced teleported you " + e.getBlockFace().getOppositeFace() + " face of the trap door");
							e.getPlayer().teleport(loc);
							e.setCancelled(true);

						} else if(e.getClickedBlock().getType() == Material.WOODEN_DOOR) {
							Location loc = e.getClickedBlock().getRelative(e.getBlockFace().getOppositeFace()).getLocation();
							loc.setYaw(e.getPlayer().getLocation().getYaw());
							loc.setPitch(e.getPlayer().getLocation().getPitch());
							e.getPlayer().sendMessage(ChatColor.GRAY + "you are vanished so we sillenced teleported you " + e.getBlockFace().getOppositeFace() + " face of the wooden door");
							e.getPlayer().teleport(loc);
							e.setCancelled(true);
						} else if(e.getClickedBlock().getType() == Material.IRON_DOOR_BLOCK) {
							Location loc = e.getClickedBlock().getRelative(e.getBlockFace().getOppositeFace()).getLocation();
							loc.setYaw(e.getPlayer().getLocation().getYaw());
							loc.setPitch(e.getPlayer().getLocation().getPitch());
							e.getPlayer().sendMessage(ChatColor.GRAY + "you are vanished so we sillenced teleported you " + e.getBlockFace().getOppositeFace() + " face of the iron door");
							e.getPlayer().teleport(loc);
							e.setCancelled(true);
						}
					}
				}
			} else {
				if(e.getAction() == Action.PHYSICAL) {
					if(xp.isVanished() && xp.isNoPickUpEnabled()) {
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	public void vanishMessageOnJoin(PlayerJoinEvent e) {
		if(xEssentials.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isVanished()) {
				for(XPlayer a : xEssentials.getManagers().getPlayerManager().getPlayers()) {
					if(a.isStaff()) {
						a.getPlayer().sendMessage(ChatColor.GREEN + "[vanish]" + ChatColor.GRAY + e.getPlayer().getName() + " has joined silenced!");
					}
				}
			}
		}
	}

	@EventHandler
	public void vanishMessageOnQuit(PlayerQuitEvent e) {
		if(xEssentials.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isVanished()) {
				for(XPlayer a : xEssentials.getManagers().getPlayerManager().getPlayers()) {
					if(a.isStaff()) {
						a.getPlayer().sendMessage(ChatColor.GREEN + "[vanish]" + ChatColor.GRAY + e.getPlayer().getName() + " has left silenced!");
					}
				}
			}
		}
	}

	@EventHandler
	public void hideAchievement(PlayerAchievementAwardedEvent e) {
		XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
		if(xp.isVanished()) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void VanishGodMode(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(((Player) e.getEntity()).getName());
			if(xp.isVanished()) {
				e.setCancelled(true);
			}
		}
	}
}
