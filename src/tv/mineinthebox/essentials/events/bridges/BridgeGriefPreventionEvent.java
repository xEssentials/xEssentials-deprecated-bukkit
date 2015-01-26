package tv.mineinthebox.essentials.events.bridges;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import tv.mineinthebox.essentials.xEssentials;

public class BridgeGriefPreventionEvent implements Listener {
	
	private final xEssentials pl;
	
	public BridgeGriefPreventionEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onExplosive(EntityExplodeEvent e) {
		for(Block block : e.blockList()) {
			if(pl.getManagers().getBridgeManager().isBridgeBlock(block) || pl.getManagers().getBridgeManager().isBridgeSign(block)) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPiston(BlockPistonExtendEvent e) {
		for(Block block : e.getBlocks()) {
			if(pl.getManagers().getBridgeManager().isBridgeBlock(block) || pl.getManagers().getBridgeManager().isBridgeSign(block)) {
				e.setCancelled(true);
				break;
			}
		}
	}

	@EventHandler
	public void onPiston(BlockPistonRetractEvent e) {
		if(pl.getManagers().getBridgeManager().isBridgeBlock(e.getBlock()) || pl.getManagers().getBridgeManager().isBridgeSign(e.getBlock())) {
			e.setCancelled(true);
		}
	}

}
