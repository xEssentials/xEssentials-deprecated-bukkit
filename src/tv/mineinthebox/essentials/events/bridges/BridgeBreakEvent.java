package tv.mineinthebox.essentials.events.bridges;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import tv.mineinthebox.essentials.xEssentials;

public class BridgeBreakEvent implements Listener {
	
	private final xEssentials pl;
	
	public BridgeBreakEvent(xEssentials pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(e.isCancelled()) {
			return;
		}
		
		if(pl.getManagers().getBridgeManager().isBridgeBlock(e.getBlock())) {
			e.getPlayer().sendMessage(ChatColor.RED + "you are not allowed to break bridges!");
			e.setCancelled(true);
		}
	}
	
}
