package tv.mineinthebox.essentials.events.gates;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.Gate;

public class RemoveGateEvent implements Listener {
	
	private final xEssentials pl;
	
	public RemoveGateEvent(xEssentials pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onRemove(BlockBreakEvent e) {
		if(e.isCancelled()) {
			return;
		}
		
		if(e.getBlock().getType() == Material.WALL_SIGN) {
			Sign sign = (Sign) e.getBlock().getState();
			if(sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_PURPLE + "[Gate]")) {
				Gate gate = pl.getManagers().getGateManager().getGateBySign(e.getBlock());
				if(gate instanceof Gate) {
					gate.remove();
				}
			}
		}
	}
	
}
