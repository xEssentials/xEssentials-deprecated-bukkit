package tv.mineinthebox.essentials.events.customevents;

import java.util.ListIterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import tv.mineinthebox.essentials.xEssentials;

public class CallEssentialsBroadcastEvent {
	
	private ListIterator<String> it;
	private BukkitTask task;
	
	private final xEssentials pl;
	
	public CallEssentialsBroadcastEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	/**
	 * @author xize
	 * @param starts the broadcast system
	 */
	public void start() {
		this.it = pl.getConfiguration().getBroadcastConfig().getMessages().listIterator();
		startBroadcast();
	}
	
	/**
	 * @author xize
	 * @param stops the broadcast system
	 */
	public void stop() {
		task.cancel();
		task = null;
	}
	
	/**
	 * @author xize
	 * @param returns true if the broadcast system is still running else false.
	 * @return Boolean
	 */
	public boolean isRunning() {
		if(task instanceof BukkitTask) {
			return true;
		}
		return false;
	}
	
	public String getPrefix() {
		return pl.getConfiguration().getBroadcastConfig().getPrefix();
	}
	
	public String getSuffix() {
		return pl.getConfiguration().getBroadcastConfig().getSuffix();
	}
	
	private void startBroadcast() {
		task = Bukkit.getScheduler().runTaskTimer(pl, new Runnable() {

			@Override
			public void run() {
				if(it.hasNext()) {
					String message = it.next();
					for(Player p : pl.getOnlinePlayers()) {
						if(message.contains("%p")) {
							p.sendMessage(getPrefix() + getSuffix() + message.replaceAll("%p",ChatColor.GREEN + pl.getConfiguration().getChatConfig().getHashTag() + p.getName() + getSuffix()));
							p.getWorld().playSound(p.getPlayer().getLocation(), Sound.NOTE_PIANO, 10, 100);
							p.getWorld().playSound(p.getPlayer().getLocation(), Sound.NOTE_BASS_DRUM, 10, 100);
							p.getWorld().playSound(p.getPlayer().getLocation(), Sound.NOTE_SNARE_DRUM, 10, 100);
							Bukkit.getPluginManager().callEvent(new EssentialsBroadcastEvent(message, p.getName()));
						} else {
							p.sendMessage(getPrefix() + getSuffix() + message);
							Bukkit.getPluginManager().callEvent(new EssentialsBroadcastEvent(message, null));
						}
					}
				} else {
					while(it.hasPrevious()) {it.previous();}
					if(it.hasNext()) {
						String message = it.next();
						for(Player p : pl.getOnlinePlayers()) {
							if(message.contains("%p")) {
								p.sendMessage(getPrefix() + getSuffix() + message.replaceAll("%p",ChatColor.GREEN + p.getName() + getSuffix()));
								p.getWorld().playSound(p.getPlayer().getLocation(), Sound.NOTE_PIANO, 10, 100);
								p.getWorld().playSound(p.getPlayer().getLocation(), Sound.NOTE_BASS_DRUM, 10, 100);
								p.getWorld().playSound(p.getPlayer().getLocation(), Sound.NOTE_SNARE_DRUM, 10, 100);
								Bukkit.getPluginManager().callEvent(new EssentialsBroadcastEvent(message, p.getName()));
							} else {
								p.sendMessage(getPrefix() + getSuffix() + message);
								Bukkit.getPluginManager().callEvent(new EssentialsBroadcastEvent(message, null));
							}
						}
					}
				}
			}
			
		}, 0L, 800L);
	}

}