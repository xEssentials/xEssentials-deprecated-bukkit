package tv.mineinthebox.essentials.events.players;

import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.scheduler.BukkitRunnable;

import tv.mineinthebox.essentials.xEssentials;

public class PlayerFixPortalSoundEvent implements Listener {
	
	private final xEssentials pl;
	
	public PlayerFixPortalSoundEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@Deprecated
	@EventHandler
	public void onSound(final PlayerPortalEvent e) {
		if(e.isCancelled()) {
			return;
		}
		
		new BukkitRunnable() {

			@Override
			public void run() {
				e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_PORTAL_TRAVEL, 1F, 1F);
			}
			
		}.runTaskLater(pl, 5L);
	}

}
