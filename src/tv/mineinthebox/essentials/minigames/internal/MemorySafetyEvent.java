package tv.mineinthebox.essentials.minigames.internal;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class MemorySafetyEvent implements Listener {
	
	private final xEssentials pl;
	
	public MemorySafetyEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(pl.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			final XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isInArena()) {
				new BukkitRunnable() {

					@Override
					public void run() {
						if(!xp.getBukkitPlayer().isOnline()) {
							xp.getArena().leaveArena(xp);
							xp.setArena(null);
						}
					}
					
				}.runTaskLater(pl, 15L);
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerKickEvent e) {
		if(pl.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			final XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isInArena()) {
				new BukkitRunnable() {

					@Override
					public void run() {
						if(!xp.getBukkitPlayer().isOnline()) {
							xp.getArena().leaveArena(xp);
							xp.setArena(null);
						}
					}
					
				}.runTaskLater(pl, 15L);
			}
		}
	}
	
	@EventHandler
	public void onQuitSess(PlayerQuitEvent e) {
		if(pl.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.hasSession()) {
				xp.getSession().stopSession(e.getPlayer());
				xp.setSession(null);
			}
		}
	}
	
	@EventHandler
	public void onQuitSess(PlayerKickEvent e) {
		if(pl.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.hasSession()) {
				xp.getSession().stopSession(e.getPlayer());
				xp.setSession(null);
			}
		}
	}

}
