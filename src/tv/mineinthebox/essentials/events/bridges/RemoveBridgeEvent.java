package tv.mineinthebox.essentials.events.bridges;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.Bridge;

public class RemoveBridgeEvent implements Listener {

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(e.isCancelled()) {
			return;
		}

		if(e.getBlock().getType() == Material.SIGN_POST) {
			Sign sign = (Sign)e.getBlock().getState();
			if(sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_PURPLE + "[Bridge]")) {
				if(xEssentials.getManagers().getBridgeManager().isBridgeSign(e.getBlock())) {
					Bridge bridge = xEssentials.getManagers().getBridgeManager().getBridgeBySign(e.getBlock());
					if(bridge instanceof Bridge) {
						e.getPlayer().sendMessage(ChatColor.GREEN + "you have successfully removed the bridge!");
						bridge.remove();
					}
				}
			}
		}
	}

}
