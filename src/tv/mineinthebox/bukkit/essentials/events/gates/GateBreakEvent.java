package tv.mineinthebox.bukkit.essentials.events.gates;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import tv.mineinthebox.bukkit.essentials.xEssentials;

public class GateBreakEvent implements Listener {

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(e.isCancelled()) {
			return;
		}
		
		if(xEssentials.getManagers().getGateManager().isGateFenceBlock(e.getBlock())) {
			e.getPlayer().sendMessage(ChatColor.RED + "you cannot break this block!");
			e.setCancelled(true);
		} else if(xEssentials.getManagers().getGateManager().isGateFrameBlock(e.getBlock())) {
			e.getPlayer().sendMessage(ChatColor.RED + "you cannot break this block!");
			e.setCancelled(true);
		}
	}

}
