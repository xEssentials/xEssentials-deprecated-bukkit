package tv.mineinthebox.bukkit.essentials.events.ban;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.interfaces.XPlayer;

public class PwnAgeProtectionEvent implements Listener {
	
	@EventHandler
	public void playerJoinCheckSpamBot(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		final XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(p.getName());
		
		if(xp.hasLastLoginTime()) {
			if((System.currentTimeMillis()-xp.getLastLoginTime()) > 280) {
				xp.setLastLoginTime(System.currentTimeMillis());
			} else {
				if(xp.hasPwnageLevel()) {
					if(xp.getPwnageLevel() > 2) {
						xp.setPermBanned(Configuration.getBanConfig().getPwnAgeSpamBanMessage(), p.getName());
					} else {
						xp.setPwnageLevel(xp.getPwnageLevel()+1);
						new BukkitRunnable() {

							@Override
							public void run() {
								if(!xp.getPlayer().isBanned()) {
									xp.setPwnageLevel(xp.getPwnageLevel()-1);
								}
							}
							
						}.runTaskLater(xEssentials.getPlugin(), 100);
						xp.setLastLoginTime(System.currentTimeMillis());
					}
				} else {
					xp.setPwnageLevel(xp.getPwnageLevel()+1);
					new BukkitRunnable() {

						@Override
						public void run() {
							if(!xp.getPlayer().isBanned()) {
								xp.setPwnageLevel(xp.getPwnageLevel()-1);
							}
						}
						
					}.runTaskLater(xEssentials.getPlugin(), 100);
					xp.setLastLoginTime(System.currentTimeMillis());
				}
			}
		} else {
			xp.setLastLoginTime(System.currentTimeMillis());
		}
		
		
	}
}
