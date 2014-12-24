package tv.mineinthebox.essentials.events.ban;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class PwnAgeProtectionEvent implements Listener {
	
	@EventHandler
	public void playerJoinCheckSpamBot(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		final XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(p.getName());
		
		if((System.currentTimeMillis()-xp.getLastLoginTime()) < 200) {
			if(xp.hasPwnageLevel()) {
				if(xp.getPwnageLevel() > 2) {
					xp.setPermBanned(Configuration.getBanConfig().getPwnAgeSpamBanMessage(), p.getName());
				} else {
					xp.setPwnageLevel(xp.getPwnageLevel()+1);
					BukkitTask task = new BukkitRunnable() {

						@Override
						public void run() {
							if(xp.getPwnageLevel() == 0) {
								cancel();
							} else {
								xp.setPwnageLevel(xp.getPwnageLevel()-1);
							}
						}
						
					}.runTaskTimer(xEssentials.getPlugin(), 100L, 3);
					xp.setPwnageScheduler(task);
				}
			} else {
				xp.setPwnageLevel(1);
				BukkitTask task = new BukkitRunnable() {

					@Override
					public void run() {
						if(xp.getPwnageLevel() == 0) {
							cancel();
						} else {
							xp.setPwnageLevel(xp.getPwnageLevel()-1);
						}
					}
					
				}.runTaskTimer(xEssentials.getPlugin(), 100L, 3);
				xp.setPwnageScheduler(task);
			}
		}
	}
}
