package tv.mineinthebox.bukkit.essentials.events.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

public class StopLeavesDecayEvent implements Listener {
	
	@EventHandler(priority = EventPriority.LOW)
	public void onDecay(LeavesDecayEvent e) {
		e.setCancelled(true);
	}

}
