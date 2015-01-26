package tv.mineinthebox.essentials.events.players;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class AntiKnockBackEvent implements Listener {

	private final xEssentials pl;
	
	public AntiKnockBackEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onAttack(EntityDamageEvent e) {
		if(e.isCancelled()) {
			return;
		}
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(p.getName());
			if(xp.isKnock()) {
				double damage = e.getDamage();
				e.setCancelled(true);
				p.damage(damage);
			}
		}
	}

}
