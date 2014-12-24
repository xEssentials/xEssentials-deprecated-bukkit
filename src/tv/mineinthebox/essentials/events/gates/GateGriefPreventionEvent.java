package tv.mineinthebox.essentials.events.gates;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import tv.mineinthebox.essentials.xEssentials;

public class GateGriefPreventionEvent implements Listener {
	
	
	@EventHandler
	public void onExplosive(EntityExplodeEvent e) {
		for(Block block : e.blockList()) {
			if(xEssentials.getManagers().getGateManager().isGateFenceBlock(block) || xEssentials.getManagers().getGateManager().isGateFrameBlock(block)) {
				e.setCancelled(true);
				break;
			}
		}
	}
	
	@EventHandler
	public void onPiston(BlockPistonExtendEvent e) {
		for(Block block : e.getBlocks()) {
			if(xEssentials.getManagers().getGateManager().isGateFenceBlock(block) || xEssentials.getManagers().getGateManager().isGateFrameBlock(block)) {
				e.setCancelled(true);
				break;
			}
		}
	}

	@EventHandler
	public void onPiston(BlockPistonRetractEvent e) {
		if(xEssentials.getManagers().getGateManager().isGateFenceBlock(e.getBlock()) || xEssentials.getManagers().getGateManager().isGateFrameBlock(e.getBlock())) {
			e.setCancelled(true);
		}
	}

}
