package tv.mineinthebox.bukkit.essentials.instances;

import java.util.concurrent.TimeUnit;

import org.bukkit.Location;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WitherSkull;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import tv.mineinthebox.bukkit.essentials.xEssentials;

public class TimeClock {

	private final Location loc;
	private Long time;
	private final WitherSkull skull;
	private final EnderDragon ender;
	private final String text;


	public TimeClock(String text, Location loc, Long timeEnds) {
		this.loc = loc;
		this.time = timeEnds;
		this.text = text;
		this.ender = (EnderDragon)loc.getWorld().spawnEntity(loc, EntityType.ENDER_DRAGON);
		this.ender.setNoDamageTicks(200000);
		this.skull = (WitherSkull)loc.getWorld().spawnEntity(loc, EntityType.WITHER_SKULL);
		this.skull.setVelocity(new Vector(0,0,0).normalize());
		this.skull.setPassenger(this.ender);
		this.ender.setCustomName(text);
		this.ender.setCustomNameVisible(true);
	}

	public Location getLocation() {
		return loc;
	}

	public void setNewEndTime(Long time) {
		this.time = time;
	}

	public void setName(String name) {
		this.ender.setCustomName(name);
	}
	
	public String getName() {
		return this.ender.getCustomName();
	}

	public void start() {
		new BukkitRunnable() {

			@Override
			public void run() {
				if(System.currentTimeMillis() > time) {
					skull.remove();
					ender.remove();
					cancel();
				} else {
					setName(text + " ends over " + TimeUnit.MILLISECONDS.toMinutes((System.currentTimeMillis()-time)) + " minutes");
					ender.damage(100);
				}
			}

		}.runTaskTimer(xEssentials.getPlugin(), 0L, 1L);
	}

}
