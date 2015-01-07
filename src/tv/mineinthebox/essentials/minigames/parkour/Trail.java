package tv.mineinthebox.essentials.minigames.parkour;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import tv.mineinthebox.essentials.xEssentials;

public class Trail {
	
	private BukkitTask task;
	private final TreeMap<Location, MaterialData> data = new TreeMap<Location, MaterialData>();
	
	@SuppressWarnings("deprecation")
	public Trail(ConfigurationSection con) {
		for(String s : con.getStringList("data")) {
			String[] split = s.split(":");
			World w = Bukkit.getWorld(split[0]);
			int x = Integer.parseInt(split[1]);
			int y = Integer.parseInt(split[2]);
			int z = Integer.parseInt(split[3]);
			int datav = Integer.parseInt(split[4]);
			byte subdata = Byte.parseByte(split[5]);
			Location loc = new Location(w, x, y, z);
			MaterialData material = new MaterialData(datav, subdata);
			data.put(loc, material);
		}
	}
	
	public void startTrail() {
		this.task = new BukkitRunnable()  {

			private Iterator<Entry<Location, MaterialData>> it;
			private Location lastLocation;
			
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				if(it == null) {
					this.it = getIterator();
				} else {
					if(it.hasNext()) {
						Entry<Location, MaterialData> data = it.next();
						if(lastLocation == null) {
							this.lastLocation = data.getKey();
							data.getKey().getBlock().setTypeIdAndData(data.getValue().getItemTypeId(), data.getValue().getData(), false);
						} else {
							this.lastLocation.getBlock().setType(Material.AIR);
							data.getKey().getBlock().setTypeIdAndData(data.getValue().getItemTypeId(), data.getValue().getData(), false);
							this.lastLocation = data.getKey();
						}
					} else {
						this.it = getIterator();
					}
				}
			}
			
		}.runTaskTimer(xEssentials.getPlugin(), 5L, 10L);
	}
	
	public boolean isRunning() {
		return (task != null);
	}
	
	public void stopTrail() {
		if(isRunning()) {
			this.task.cancel();
			this.task = null;
		}
	}
	
	private Iterator<Entry<Location, MaterialData>> getIterator() {
		return data.entrySet().iterator();
	}

}
