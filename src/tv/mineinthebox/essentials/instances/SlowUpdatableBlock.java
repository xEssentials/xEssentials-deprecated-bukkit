package tv.mineinthebox.essentials.instances;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import tv.mineinthebox.essentials.xEssentials;

public class SlowUpdatableBlock {
	
	private final Location loc;
	private final long time;
	
	public SlowUpdatableBlock(Location loc, long time) {
		this.loc = loc;
		this.time = time;
	}
	
	public void startUpdate() {
		new BukkitRunnable() {

			@Override
			public void run() {
				Block block = loc.add(0, -1, 0).getBlock();
				block.getState().update(true);
			}
			
		}.runTaskLater(xEssentials.getPlugin(), time);
	}

}
