package tv.mineinthebox.essentials.events.players;

import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerShootbowSoundEvent implements Listener {
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled=true)
	public void onshoot(EntityDamageByEntityEvent e) {
		if(e.isCancelled()) {
			return;
		}
		if(e.getEntity() instanceof LivingEntity) {
			if(e.getDamager() instanceof Arrow) {
				Arrow arrow = (Arrow)e.getDamager();
				if(arrow.getShooter() instanceof Player) {
					Player p = (Player) arrow.getShooter();
					p.playSound(p.getLocation(), Sound.NOTE_PLING, 2F, 1F);
				}
			}
		}
	}

}
