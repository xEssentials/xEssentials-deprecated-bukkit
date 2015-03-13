package tv.mineinthebox.essentials.events.bridges;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.EventTemplate;

public class BridgeBreakEvent extends EventTemplate implements Listener {
	
	public BridgeBreakEvent(xEssentials pl) {
		super(pl, "Bridge");
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(e.isCancelled()) {
			return;
		}
		
		if(pl.getManagers().getBridgeManager().isBridgeBlock(e.getBlock())) {
			sendMessage(e.getPlayer(), ChatColor.RED + "you are not allowed to break bridges!");
			e.setCancelled(true);
		}
	}
	
}
