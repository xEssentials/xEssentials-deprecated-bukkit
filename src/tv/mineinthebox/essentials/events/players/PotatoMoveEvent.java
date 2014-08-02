package tv.mineinthebox.essentials.events.players;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

public class PotatoMoveEvent implements Listener {

	@EventHandler
	public void onPotatoMove(PlayerMoveEvent e) {
		if(xEssentials.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isPotato()) {
				if(e.getFrom().distanceSquared(e.getTo()) > 0) {
					Item item = xp.getPotato();
					Vector direction = e.getTo().toVector().subtract(item.getLocation().toVector()).normalize();
					item.setVelocity(direction.multiply(0.5));
				}
			}
		}
	}

	@EventHandler
	public void onPickup(PlayerPickupItemEvent e) {
		if(e.getItem().getItemStack().getType() == Material.POTATO_ITEM) {
			for(Entity entity : e.getItem().getNearbyEntities(5, 5, 5)) {
				if(entity instanceof Player) {
					xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(((Player) entity).getName());
					if(xp.isPotato()) {
						if(e.getItem().equals(xp.getPotato())) {
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if(xEssentials.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isPotato()) {
				e.getPlayer().sendMessage(ChatColor.GREEN + "potatos cannot pickup items!");
				e.setCancelled(true);
			}	
		}
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(e.isCancelled()) {
			return;
		}

		if(xEssentials.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isPotato()) {
				e.getPlayer().sendMessage(ChatColor.GREEN + "potatos cannot place blocks!");
				e.setCancelled(true);
			}	
		}
	}

	@EventHandler
	public void onPlace(BlockBreakEvent e) {
		if(xEssentials.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isPotato()) {
				e.getPlayer().sendMessage(ChatColor.GREEN + "potatos cannot break blocks!");
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
		if(xp instanceof xEssentialsPlayer) {	
			if(xp.isPotato()) {
				xp.unvanish();
				xp.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
				xp.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
				xp.getPlayer().removePotionEffect(PotionEffectType.SPEED);
				Item potato = xp.getPotato();
				potato.remove();
				xp.removePotato();
			}	
		}
	}

	@EventHandler
	public void onQuit(PlayerKickEvent e) {
		xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
		if(xp instanceof xEssentialsPlayer) {	
			if(xp.isPotato()) {
				xp.unvanish();
				xp.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
				xp.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
				xp.getPlayer().removePotionEffect(PotionEffectType.SPEED);
				Item potato = xp.getPotato();
				potato.remove();
				xp.removePotato();
			}
		}	
	}

}
