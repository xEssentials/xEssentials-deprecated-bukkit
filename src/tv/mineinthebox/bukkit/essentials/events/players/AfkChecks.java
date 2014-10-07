package tv.mineinthebox.bukkit.essentials.events.players;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.events.customevents.PlayerAfkEvent;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsPlayer;

public class AfkChecks implements Listener {

	@EventHandler
	public void removeAfk(PlayerMoveEvent e) {

		if(e.getFrom().distanceSquared(e.getTo()) > 0.04) {
			double distanceXmin = Math.min(e.getFrom().getX(), e.getTo().getX());
			double distanceXmax = Math.max(e.getFrom().getX(), e.getTo().getX());

			double distanceZmin = Math.min(e.getFrom().getZ(), e.getTo().getZ());
			double distanceZmax = Math.max(e.getFrom().getZ(), e.getTo().getZ());

			double destx = (distanceXmax - distanceXmin);
			double destz = (distanceZmax - distanceZmin);

			if(destx > 0.0) {
				xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
				if(xp.isAfk()) {
					xp.removeAfk();
					Bukkit.broadcastMessage(ChatColor.GREEN + e.getPlayer().getName() + " is no longer afk");
					Bukkit.getPluginManager().callEvent(new PlayerAfkEvent(xp.getPlayer(), false, true));
				}
			} else if(destz > 0.0) {
				xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
				if(xp.isAfk()) {
					xp.removeAfk();
					Bukkit.broadcastMessage(ChatColor.GREEN + e.getPlayer().getName() + " is no longer afk");
					Bukkit.getPluginManager().callEvent(new PlayerAfkEvent(xp.getPlayer(), false, true));
				}
			}
		}
	}

	@EventHandler
	public void isGodModeAfk(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(Configuration.getPlayerConfig().isGodModeInAfkEnabled()) {
				xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(p.getName());
				if(xp.isAfk()) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void isGodModeAfk(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(Configuration.getPlayerConfig().isGodModeInAfkEnabled()) {
				xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(p.getName());
				if(xp.isAfk()) {
					e.setDamage(0.0F);
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void isGodModeAfk(EntityTargetEvent e) {
		if(e.getTarget() instanceof Player) {
			Player p = (Player) e.getTarget();
			if(Configuration.getPlayerConfig().isGodModeInAfkEnabled()) {
				xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(p.getName());
				if(xp.isAfk()) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void isGodModeAfk(EntityTargetLivingEntityEvent e) {
		if(e.getTarget() instanceof Player) {
			Player p = (Player) e.getTarget();
			if(Configuration.getPlayerConfig().isGodModeInAfkEnabled()) {
				xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(p.getName());
				if(xp.isAfk()) {
					e.setCancelled(true);
				}
			}
		}
	}


	@EventHandler
	public void isGodModeAfk(FoodLevelChangeEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(Configuration.getPlayerConfig().isGodModeInAfkEnabled()) {
				xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(p.getName());
				if(xp.isAfk()) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void chatAfk(AsyncPlayerChatEvent e) {
		for(xEssentialsPlayer xp : xEssentials.getManagers().getPlayerManager().getPlayers()) {
			if(e.getMessage().contains(xp.getPlayer().getName())) {
				if(xp.isAfk()) {
					e.getPlayer().sendMessage(ChatColor.GREEN + xp.getPlayer().getName() + " has been afk [ " + xp.getAfkReason() + " ]");
				}
			}
		}
	}
}
