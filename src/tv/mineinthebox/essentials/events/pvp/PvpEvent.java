package tv.mineinthebox.essentials.events.pvp;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PvpEvent implements Listener {

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled=true)
	public void onDamage(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player) {
			if(e.getEntity() instanceof Player) {
				Player damager = (Player) e.getDamager();
				damager.sendMessage(ChatColor.RED + "you are not allowed to pvp on this server!");
				e.setCancelled(true);	
			}
		} else if(e.getDamager() instanceof Arrow) {
			Arrow arrow = (Arrow) e.getDamager();
			if(arrow.getShooter() instanceof Player) {
				if(e.getEntity() instanceof Player) {
					Player damager = (Player) arrow.getShooter();
					damager.sendMessage(ChatColor.RED + "you are not allowed to pvp on this server!");
					arrow.remove();
					e.setCancelled(true);
				}
			}
		} else if(e.getDamager() instanceof ThrownPotion) {
			ThrownPotion pot = (ThrownPotion) e.getDamager();
			if(pot.getShooter() instanceof Player) {
				if(e.getEntity() instanceof Player) {
					Player damager = (Player) pot.getShooter();
					damager.sendMessage(ChatColor.RED + "you are not allowed to pvp on this server!");
					pot.remove();
					e.setCancelled(true);
				}
			}
		}
	}

}
