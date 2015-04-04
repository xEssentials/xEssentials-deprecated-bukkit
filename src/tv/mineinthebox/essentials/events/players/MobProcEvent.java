package tv.mineinthebox.essentials.events.players;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class MobProcEvent implements Listener, Runnable {

	private final static HashMap<String, Long> player = new HashMap<String, Long>();

	private final int proctime = 5;

	private static BukkitTask task; 
	
	private final xEssentials pl;
	
	public MobProcEvent(xEssentials pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onProc(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Monster) {
			Monster monster = (Monster) e.getEntity();
			if(e.getDamager() instanceof Player) {
				Player p = (Player) e.getDamager();
				XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(p.getName());
				if(xp.hasProc()) {
					if(player.containsKey(xp.getName())) {
						monster.setHealth(0);
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onProc(EntityDeathEvent e) {
		if(e.getEntity() instanceof Monster) {
			if(e.getEntity().getKiller() instanceof Player) {
				Player p = (Player) e.getEntity().getKiller();
				if(p.getItemInHand() != null) {
					if(p.getItemInHand().getType() == Material.BOW) {
						return;
					}
				}
				XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(p.getName());
				if(xp.hasProc()) {
					Random rand = new Random();
					p.getWorld().playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 1F, 1F);
					for(int i =0; i < 5; i++) {
						p.getWorld().playEffect(new Location(p.getWorld(), rand.nextInt(10), rand.nextInt(4), rand.nextInt(10)), Effect.MOBSPAWNER_FLAMES, 100, 10);
						//ParticleEffect.HAPPY_VILLAGER.display(p.getLocation(), rand.nextInt(10), rand.nextInt(4), rand.nextInt(10), 100, 10);	
					}
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3));
					Date date = new Date(System.currentTimeMillis());
					date.setSeconds(date.getSeconds() + proctime);
					player.put(p.getName(), date.getTime());
					if(!(task instanceof BukkitTask)) {
						task = Bukkit.getScheduler().runTaskTimer(pl, this, 0L, 1L);
					}	
				}
			}
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(player.containsKey(e.getPlayer().getName())) {
			player.remove(e.getPlayer().getName());
		}
	}

	@EventHandler
	public void onQuit(PlayerKickEvent e) {
		if(player.containsKey(e.getPlayer().getName())) {
			player.remove(e.getPlayer().getName());
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		if(player.isEmpty()) {
			task.cancel();
			task = null;
		} else {
			Iterator<String> it = player.keySet().iterator();
			while(it.hasNext()) {
				String playename = it.next();
				long time = player.get(playename);
				if(System.currentTimeMillis() > time) {
					Player p = Bukkit.getPlayer(playename);
					if(p.hasPotionEffect(PotionEffectType.SPEED)) {
						p.removePotionEffect(PotionEffectType.SPEED);
					}
					player.remove(playename);
				}
			}
		}
	}

}
