package tv.mineinthebox.bukkit.essentials.instances;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitTask;

import tv.mineinthebox.bukkit.essentials.xEssentials;

public class RegenObject implements Runnable {

	private final LinkedHashMap<Location, MaterialData> blocks;
	
	private BukkitTask task;

	public RegenObject(LinkedHashMap<Location, MaterialData> blocks) {
		this.blocks = blocks;
		startRegen();
	}
	
	public RegenObject getValue() {
		return this;
	}

	/**
	 * @author xize
	 * @param starts the block regen task.
	 */
	public void startRegen() {
		if(!(task instanceof BukkitTask)) {
			this.task = Bukkit.getScheduler().runTaskTimer(xEssentials.getPlugin(), this, 0L, 4L);
		}
	}
	
	/**
	 * @author xize
	 * @param stops the regen task.
	 */
	public void stopRegen() {
		if(task instanceof BukkitTask) {
			this.task.cancel();
			this.task = null;
		}
		if(xEssentials.getManagers().getExplosionRegenManager().getList.contains(this)) {
			xEssentials.getManagers().getExplosionRegenManager().getList.remove(this);
		}
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
			Iterator<Entry<Location, MaterialData>> it = blocks.entrySet().iterator();
		if(it.hasNext()) {
			Entry<Location, MaterialData> entry = it.next();
			entry.getKey().getBlock().setTypeIdAndData(entry.getValue().getItemTypeId(), entry.getValue().getData(), true);
			entry.getKey().getWorld().playEffect(entry.getKey(), Effect.STEP_SOUND, entry.getValue().getItemTypeId());
			it.remove();
			blocks.remove(entry.getKey());
		} else {
			stopRegen();	
		}
	}
	
	/**
	 * @author xize
	 * @param returns the id.
	 * @return Integer
	 */
	public int getId() {
		return this.task.getTaskId();
	}
	
	/**
	 * @author xize
	 * @param returns the data.
	 * @return HashMap<Location, MaterialData>()
	 */
	public HashMap<Location, MaterialData> getData() {
		return this.blocks;
	}

	@Override
	public String toString() {
		return "RegenObject [blocks=" + blocks + ", task=" + task
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
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
		RegenObject other = (RegenObject) obj;
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
