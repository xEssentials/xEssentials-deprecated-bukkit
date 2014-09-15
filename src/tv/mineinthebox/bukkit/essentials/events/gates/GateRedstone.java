package tv.mineinthebox.bukkit.essentials.events.gates;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.instances.Gate;

public class GateRedstone implements Listener {

	@EventHandler
	public void onRedstone(BlockRedstoneEvent e) {
		if(e.getNewCurrent() > 0) {
			if(e.getBlock().getType() == Material.REDSTONE_WIRE || e.getBlock().getType() == Material.DIODE_BLOCK_OFF || e.getBlock().getType() == Material.DIODE_BLOCK_ON) {
				return;
			}
			
			for(Gate gate : xEssentials.getManagers().getGateManager().getGates()) {
				if(doesMatch(e.getBlock(), gate)) {
					if(gate.isToggled()) {
						gate.toggleGate();
					} else {
						gate.toggleGate();
					}
					break;
				}
			}
			
			if(xEssentials.getManagers().getGateManager().isGateFrameBlock(e.getBlock())) {
				Gate gate = xEssentials.getManagers().getGateManager().getGateFromFrameBlock(e.getBlock());
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
