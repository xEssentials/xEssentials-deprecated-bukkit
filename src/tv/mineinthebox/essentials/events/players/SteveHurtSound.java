package tv.mineinthebox.essentials.events.players;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class SteveHurtSound implements Listener {
	
	@EventHandler
	public void onDamagePlayer(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			/*
			Player p = (Player) e.getEntity();
			if(e.getCause() == DamageCause.BLOCK_EXPLOSION) {
				p.getWorld().playSound(p.getLocation(), Sound.HURT, 1, 1);
				return;
			} else if(e.getCause() == DamageCause.DROWNING) {
				p.getWorld().playSound(p.getLocation(), Sound.HURT, 1, 1);
				return;
			} else if(e.getCause() == DamageCause.ENTITY_ATTACK) {
				p.getWorld().playSound(p.getLocation(), Sound.HURT, 1, 1);
				return;
			} else if(e.getCause() == DamageCause.ENTITY_EXPLOSION) {
				p.getWorld().playSound(p.getLocation(), Sound.HURT, 1, 1);
				return;
			} else if(e.getCause() == DamageCause.FALL) {
				p.getWorld().playSound(p.getLocation(), Sound.HURT, 1, 1);
				return;
			} else if(e.getCause() == DamageCause.FALLING_BLOCK) {
				p.getWorld().playSound(p.getLocation(), Sound.HURT, 1, 1);
				return;
			} else if(e.getCause() == DamageCause.FIRE_TICK) {
				p.getWorld().playSound(p.getLocation(), Sound.HURT, 1, 1);
				return;
			} else if(e.getCause() == DamageCause.LIGHTNING) {
				p.getWorld().playSound(p.getLocation(), Sound.HURT, 1, 1);
				return;
			} else if(e.getCause() == DamageCause.MELTING) {
				p.getWorld().playSound(p.getLocation(), Sound.HURT, 1, 1);
				return;
			} else if(e.getCause() == DamageCause.POISON) {
				p.getWorld().playSound(p.getLocation(), Sound.HURT, 1, 1);
				return;
			} else if(e.getCause() == DamageCause.PROJECTILE) {
				p.getWorld().playSound(p.getLocation(), Sound.HURT, 1, 1);
				return;
			} else if(e.getCause() == DamageCause.STARVATION) {
				p.getWorld().playSound(p.getLocation(), Sound.HURT, 1, 1);
				return;
			} else if(e.getCause() == DamageCause.SUFFOCATION) {
				p.getWorld().playSound(p.getLocation(), Sound.HURT, 1, 1);
				return;
			} else if(e.getCause() == DamageCause.SUICIDE) {
				p.getWorld().playSound(p.getLocation(), Sound.HURT, 1, 1);
				return;
			} else if(e.getCause() == DamageCause.THORNS) {
				p.getWorld().playSound(p.getLocation(), Sound.HURT, 1, 1);
				return;
			} else if(e.getCause() == DamageCause.VOID) {
				p.getWorld().playSound(p.getLocation(), Sound.HURT, 1, 1);
				return;
			} else if(e.getCause() == DamageCause.WITHER) {
				p.getWorld().playSound(p.getLocation(), Sound.HURT, 1, 1);
				return;
			}
			*/
		}
	}

}
