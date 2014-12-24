package tv.mineinthebox.essentials.instances;

import java.util.Iterator;
import java.util.LinkedList;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitTask;

import tv.mineinthebox.essentials.xEssentials;

public class LavaPilar implements Runnable {
	
	private final LinkedList<Block> blocks;
	private BukkitTask task;
	
	public LavaPilar(LinkedList<Block> blocks) {
		this.blocks = blocks;
	}
	
	public boolean isRunning() {
		if(task instanceof BukkitTask) {
			return (task != null);
		}
		return false;
	}
	
	public void startTask() {
		if(!isRunning()) {
			this.task = Bukkit.getScheduler().runTaskTimer(xEssentials.getPlugin(), this, 0L, 20L);
		}
	}
	
	public void stopTask() {
		this.task.cancel();
		this.task = null;
	}

	@Override
	public void run() {
		Iterator<Block> it = blocks.descendingIterator();
		if(it.hasNext()) {
			Block block = it.next();
			block.getState().update();
			block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, Material.LAVA);
			it.remove();
			blocks.remove(block);
		} else {
			this.stopTask();
		}
	}

	@Override
	public String toString() {
		return "VanishEffect [blocks=" + blocks + ", task=" + task
				+ ", isRunning()=" + isRunning() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((blocks == null) ? 0 : blocks.hashCode());
		result = prime * result + ((task == null) ? 0 : task.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LavaPilar other = (LavaPilar) obj;
		if (blocks == null) {
			if (other.blocks != null)
				return false;
		} else if (!blocks.equals(other.blocks))
			return false;
		if (task == null) {
			if (other.task != null)
				return false;
		} else if (!task.equals(other.task))
			return false;
		return true;
	}
}
