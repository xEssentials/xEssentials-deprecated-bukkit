package tv.mineinthebox.essentials.instances;

import java.util.Date;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import tv.mineinthebox.essentials.xEssentials;

public class SpawnerBlock {
	
	private final Block block;
	private final EntityType type;
	private final xEssentials pl;
	
	private BukkitTask task;
	private long end;
	
	@SuppressWarnings("deprecation")
	public SpawnerBlock(Block block, EntityType type, xEssentials pl) {
		this.block = block;
		this.type = type;
		this.pl = pl;
		Date date = new Date(System.currentTimeMillis());
		date.setSeconds(date.getSeconds()+5);
		this.end = date.getTime();
	}
	
	public void start() {
		if(!(this.task instanceof BukkitTask)) {
			this.task = new BukkitRunnable() {

				@Override
				public void run() {
					if(System.currentTimeMillis() >= SpawnerBlock.this.end) {
						cancel();
						SpawnerBlock.this.task = null;
					}
					SpawnerBlock.this.block.getWorld().spawnEntity(SpawnerBlock.this.block.getRelative(BlockFace.UP).getLocation(), SpawnerBlock.this.type);
				}
				
			}.runTaskTimer(pl, 0L, 1L);
		}
	}
	
	public void stop() {
		if(this.task instanceof BukkitTask) {
			this.task.cancel();
			this.task = null;
		}
	}
}
