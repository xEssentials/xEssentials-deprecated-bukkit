package tv.mineinthebox.essentials.events.gates;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.Gate;

public class GateRedstoneEvent implements Listener {
	
	private final xEssentials pl;
	
	public GateRedstoneEvent(xEssentials pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onRedstone(BlockRedstoneEvent e) {
		if(e.getNewCurrent() > 0) {
			if(e.getBlock().getType() == Material.REDSTONE_WIRE || e.getBlock().getType() == Material.DIODE_BLOCK_OFF || e.getBlock().getType() == Material.DIODE_BLOCK_ON) {
				return;
			}
			
			for(Gate gate : pl.getManagers().getGateManager().getGates()) {
				if(doesMatch(e.getBlock(), gate)) {
					if(gate.isToggled()) {
						gate.toggleGate();
					} else {
						gate.toggleGate();
					}
					break;
				}
			}
			
			if(pl.getManagers().getGateManager().isGateFrameBlock(e.getBlock())) {
				Gate gate = pl.getManagers().getGateManager().getGateFromFrameBlock(e.getBlock());
				if(gate instanceof Gate) {
					gate.toggleGate();	
				}
			}
		}
	}
	
	private boolean doesMatch(Block block, Gate gate) {
		BlockFace[] faces = {BlockFace.SELF, BlockFace.UP, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
		for(BlockFace face : faces) {
			Block newblock = block.getRelative(face);
			List<Block> blocks = Arrays.asList(gate.getFrameBlocks());
			if(blocks.contains(newblock)) {
				return true;
			}
		}
		return false;
	}
	
}
