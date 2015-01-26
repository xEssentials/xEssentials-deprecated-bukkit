package tv.mineinthebox.essentials.events.gates;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import tv.mineinthebox.essentials.xEssentials;

public class GateBreakEvent implements Listener {
	
	private final xEssentials pl;
	
	public GateBreakEvent(xEssentials pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(e.isCancelled()) {
			return;
		}
		
		if(pl.getManagers().getGateManager().isGateFenceBlock(e.getBlock())) {
			e.getPlayer().sendMessage(ChatColor.RED + "you cannot break this block!");
			e.setCancelled(true);
		} else if(pl.getManagers().getGateManager().isGateFrameBlock(e.getBlock())) {
			e.getPlayer().sendMessage(ChatColor.RED + "you cannot break this block!");
			e.setCancelled(true);
		}
	}

}
