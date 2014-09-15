package tv.mineinthebox.bukkit.essentials.events.bridges;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import tv.mineinthebox.bukkit.essentials.xEssentials;

public class BridgeGriefPrevention implements Listener {
	
	@EventHandler
	public void onExplosive(EntityExplodeEvent e) {
		for(Block block : e.blockList()) {
			if(xEssentials.getManagers().getBridgeManager().isBridgeBlock(block) || xEssentials.getManagers().getBridgeManager().isBridgeSign(block)) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPiston(BlockPistonExtendEvent e) {
		for(Block block : e.getBlocks()) {
			if(xEssentials.getManagers().getBridgeManager().isBridgeBlock(block) || xEssentials.getManagers().getBridgeManager().isBridgeSign(block)) {
				e.setCancelled(true);
				break;
			}
		}
	}

	@EventHandler
	public void onPiston(BlockPistonRetractEvent e) {
		if(xEssentials.getManagers().getBridgeManager().isBridgeBlock(e.getBlock()) || xEssentials.getManagers().getBridgeManager().isBridgeSign(e.getBlock())) {
			e.setCancelled(true);
		}
	}

}
