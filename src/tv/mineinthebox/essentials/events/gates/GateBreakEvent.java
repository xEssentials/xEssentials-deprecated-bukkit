package tv.mineinthebox.essentials.events.gates;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.EventTemplate;

public class GateBreakEvent extends EventTemplate implements Listener {
	
	public GateBreakEvent(xEssentials pl) {
		super(pl, "Gate");
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(e.isCancelled()) {
			return;
		}
		
		if(pl.getManagers().getGateManager().isGateFenceBlock(e.getBlock())) {
			sendMessage(e.getPlayer(), ChatColor.RED + "you cannot break this block!");
			e.setCancelled(true);
		} else if(pl.getManagers().getGateManager().isGateFrameBlock(e.getBlock())) {
			sendMessage(e.getPlayer(), ChatColor.RED + "you cannot break this block!");
			e.setCancelled(true);
		}
	}

}
