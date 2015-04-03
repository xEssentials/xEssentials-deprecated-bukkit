package tv.mineinthebox.essentials.instances;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import tv.mineinthebox.essentials.xEssentials;

public class SlowUpdateBlock {
	
	private final Location loc;
	private final long time;
	private final xEssentials pl;
	
	public SlowUpdateBlock(Location loc, long time, xEssentials pl) {
		this.loc = loc;
		this.time = time;
		this.pl = pl;
	}
	
	public void startUpdate() {
		new BukkitRunnable() {

			@Override
			public void run() {
				Block block = loc.add(0, -1, 0).getBlock();
				block.getState().update(true);
			}
			
		}.runTaskLater(pl, time);
	}

}
