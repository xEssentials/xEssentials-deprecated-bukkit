package tv.mineinthebox.essentials.events.bridges;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.Bridge;

public class BridgeInteractEvent implements Listener {
	
	@EventHandler
	public void onBridge(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.SIGN_POST) {
				Sign sign = (Sign) e.getClickedBlock().getState();
				if(sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_PURPLE + "[Bridge]")) {
					Bridge bridge = xEssentials.getManagers().getBridgeManager().getBridgeBySign(sign.getBlock());
					if(bridge instanceof Bridge) {
						if(bridge.isToggled()) {
							bridge.toggleBridge();
							e.getPlayer().sendMessage(ChatColor.GREEN + "bridge has been untoggled!");
						} else {
							bridge.toggleBridge();
							e.getPlayer().sendMessage(ChatColor.GREEN + "bridge has been toggled!");
						}	
					} else {
						e.getPlayer().sendMessage(ChatColor.RED + "invalid bridge, perhaps you need to reassign the bridge?");
					}
				}
			}
		}
	}

}
