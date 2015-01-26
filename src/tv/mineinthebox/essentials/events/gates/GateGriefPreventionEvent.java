package tv.mineinthebox.essentials.events.gates;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import tv.mineinthebox.essentials.xEssentials;

public class GateGriefPreventionEvent implements Listener {
	
	private final xEssentials pl;
	
	public GateGriefPreventionEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	
	@EventHandler
	public void onExplosive(EntityExplodeEvent e) {
		for(Block block : e.blockList()) {
			if(pl.getManagers().getGateManager().isGateFenceBlock(block) || pl.getManagers().getGateManager().isGateFrameBlock(block)) {
				e.setCancelled(true);
				break;
			}
		}
	}
	
	@EventHandler
	public void onPiston(BlockPistonExtendEvent e) {
		for(Block block : e.getBlocks()) {
			if(pl.getManagers().getGateManager().isGateFenceBlock(block) || pl.getManagers().getGateManager().isGateFrameBlock(block)) {
				e.setCancelled(true);
				break;
			}
		}
	}

	@EventHandler
	public void onPiston(BlockPistonRetractEvent e) {
		if(pl.getManagers().getGateManager().isGateFenceBlock(e.getBlock()) || pl.getManagers().getGateManager().isGateFrameBlock(e.getBlock())) {
			e.setCancelled(true);
		}
	}

}
