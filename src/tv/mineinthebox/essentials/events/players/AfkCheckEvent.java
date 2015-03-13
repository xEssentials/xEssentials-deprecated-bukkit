package tv.mineinthebox.essentials.events.players;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.events.customevents.PlayerAfkEvent;
import tv.mineinthebox.essentials.interfaces.EventTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

@SuppressWarnings("deprecation")
public class AfkCheckEvent extends EventTemplate implements Listener {
	
	public AfkCheckEvent(xEssentials pl) {
		super(pl, "Afk");
	}

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
				XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
				if(xp.isAfk()) {
					if(xp.isVanished()) {
						return;
					}
					xp.removeAfk();
					broadcast(ChatColor.GREEN + e.getPlayer().getName() + " is no longer afk");
					Bukkit.getPluginManager().callEvent(new PlayerAfkEvent(xp.getPlayer(), false, true, pl));
				}
			} else if(destz > 0.0) {
				XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
				if(xp.isAfk()) {
					if(xp.isVanished()) {
						return;
					}
					xp.removeAfk();
					broadcast(ChatColor.GREEN + e.getPlayer().getName() + " is no longer afk");
					Bukkit.getPluginManager().callEvent(new PlayerAfkEvent(xp.getPlayer(), false, true, pl));
				}
			}
		}
	}

	@EventHandler
	public void isGodModeAfk(EntityDamageEvent e) {
		if(e.isCancelled()) {
			return;
		}
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(pl.getConfiguration().getPlayerConfig().isGodModeInAfkEnabled()) {
				XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(p.getName());
				if(xp.isAfk()) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void isGodModeAfk(EntityDamageByEntityEvent e) {
		if(e.isCancelled()) {
			return;
		}
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(pl.getConfiguration().getPlayerConfig().isGodModeInAfkEnabled()) {
				XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(p.getName());
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
			if(pl.getConfiguration().getPlayerConfig().isGodModeInAfkEnabled()) {
				XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(p.getName());
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
			if(pl.getConfiguration().getPlayerConfig().isGodModeInAfkEnabled()) {
				XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(p.getName());
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
			if(pl.getConfiguration().getPlayerConfig().isGodModeInAfkEnabled()) {
				XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(p.getName());
				if(xp.isAfk()) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void chatAfk(PlayerChatEvent e) {
		for(XPlayer xp : pl.getManagers().getPlayerManager().getPlayers()) {
			if(e.getMessage().contains(xp.getPlayer().getName())) {
				if(xp.isAfk()) {
					sendMessage(e.getPlayer(), ChatColor.GREEN + xp.getPlayer().getName() + " has been afk [ " + xp.getAfkReason() + " ]");
				}
			}
		}
	}
}
