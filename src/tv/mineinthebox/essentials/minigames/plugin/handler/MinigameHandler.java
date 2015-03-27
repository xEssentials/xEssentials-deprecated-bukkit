package tv.mineinthebox.essentials.minigames.plugin.handler;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import tv.mineinthebox.essentials.xEssentials;

public class MinigameHandler {
	
	private final xEssentials pl;
	private final Set<Listener> listeners = new HashSet<Listener>();
	
	public MinigameHandler(xEssentials pl) {
		this.pl = pl;
	}
	
	public void startListeners() {
		for(Listener listener : listeners) {
			Bukkit.getPluginManager().registerEvents(listener, pl);
		}
	}
	
	public void stopListeners() {
		Iterator<Listener> it = listeners.iterator();
		while(it.hasNext()) {
			unregisterEvent(it.next());
			it.remove();
		}
	}
	
	
	public void registerEvent(Listener listener) {
		listeners.add(listener);
		Bukkit.getPluginManager().registerEvents(listener, pl);
	}
	
	public void unregisterEvent(Listener listener) {
		HandlerList.unregisterAll(listener);
	}

}
