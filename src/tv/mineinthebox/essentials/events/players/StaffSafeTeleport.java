package tv.mineinthebox.essentials.events.players;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

public class StaffSafeTeleport implements Listener {

	private HashMap<String, Integer> staff = new HashMap<String, Integer>();

	@EventHandler
	public void onStaffTeleport(PlayerTeleportEvent e) {
		if(e.getCause() == TeleportCause.PLUGIN) {
			xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isStaff()) {
				if(xp.isVanished()) {
					return;
				} else if(xp.getPlayer().getGameMode() == GameMode.CREATIVE) {
					return;
				} else {
					if(staff.containsKey(e.getPlayer().getName())) {
						Bukkit.getScheduler().cancelTask(staff.get(e.getPlayer().getName()));
						staff.remove(e.getPlayer().getName());
						staff.put(e.getPlayer().getName(), staffScheduler(e.getPlayer()));
						e.getPlayer().sendMessage(ChatColor.GRAY + "teleportation safety against damage has been " + ChatColor.GREEN + "enabled!");
					} else {
						staff.put(e.getPlayer().getName(), staffScheduler(e.getPlayer()));
						e.getPlayer().sendMessage(ChatColor.GRAY + "teleportation safety against damage has been " + ChatColor.GREEN + "enabled!");
					}
				}
			}
		}
	}

	@EventHandler
	public void onStaffDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(staff.containsKey(p.getName())) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onGameMode(PlayerGameModeChangeEvent e) {
		if(e.getNewGameMode() == GameMode.CREATIVE) {
			if(staff.containsKey(e.getPlayer().getName())) {
				Bukkit.getScheduler().cancelTask(staff.get(e.getPlayer().getName()));
				staff.remove(e.getPlayer().getName());
				e.getPlayer().sendMessage(ChatColor.GRAY + "teleportation safety against damage has been " + ChatColor.RED + "worn off");
			}
		}
	}

	@EventHandler
	public void onStaffQuit(PlayerQuitEvent e) {
		if(staff.containsKey(e.getPlayer().getName())) {
			Bukkit.getScheduler().cancelTask(staff.get(e.getPlayer().getName()));
			staff.remove(e.getPlayer().getName());
		}
	}

	@EventHandler
	public void onStaffQuit(PlayerKickEvent e) {
		if(staff.containsKey(e.getPlayer().getName())) {
			Bukkit.getScheduler().cancelTask(staff.get(e.getPlayer().getName()));
			staff.remove(e.getPlayer().getName());
		}
	}

	private int staffScheduler(final Player p) {
		int a = Bukkit.getScheduler().scheduleSyncDelayedTask(xEssentials.getPlugin(), new Runnable() {

			@Override
			public void run() {
				if(p instanceof Player) {
					if(staff.containsKey(p.getName())) {
						p.sendMessage(ChatColor.GRAY + "teleportation safety against damage has been " + ChatColor.RED + "worn off");
						staff.remove(p.getName());
					}
				}
			}			
		}, 400);
		return a;
	}

}
