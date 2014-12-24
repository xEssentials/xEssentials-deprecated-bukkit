package tv.mineinthebox.essentials.managers;

import java.lang.management.ManagementFactory;

import org.bukkit.Bukkit;

import tv.mineinthebox.essentials.xEssentials;

public class TPSManager {

	private int tps = 0;
	private long second = 0;
	
	public float getServerTicks() {
		return tps;
	}
	
	public Long getServerUpTime() {
		return ManagementFactory.getRuntimeMXBean().getStartTime();
	}
	
	public long garbageCollectorMax() {
		Long time = ((Runtime.getRuntime().maxMemory() / 1024) / 1024);
		return time;
	}
	
	public long getMemoryMax() {
		Long time = ((Runtime.getRuntime().totalMemory() / 1024) / 1024);
		return time;
	}
	
	public long getFreeMemory() {
		Long time = ((Runtime.getRuntime().freeMemory() / 1024) / 1024);
		return time;
	}
	
	public void startTps() {
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(xEssentials.getPlugin(), new Runnable() {
			long sec;
			int ticks;
			@Override
			public void run() {
				sec = System.currentTimeMillis() / 1000;
				if (second == sec) {
					ticks++; 
				} else {
					second = sec;
					tps = tps == 0 ? ticks : (tps + ticks) / 2;
					ticks = 0;
				}
			}
		}, 21, 1);
	}

}
