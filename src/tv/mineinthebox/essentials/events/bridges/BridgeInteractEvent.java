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
import tv.mineinthebox.essentials.interfaces.EventTemplate;

public class BridgeInteractEvent extends EventTemplate implements Listener {
	
	public BridgeInteractEvent(xEssentials pl) {
		super(pl, "Bridge");
	}
	
	@EventHandler
	public void onBridge(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.SIGN_POST) {
				Sign sign = (Sign) e.getClickedBlock().getState();
				if(sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_PURPLE + "[Bridge]")) {
					Bridge bridge = pl.getManagers().getBridgeManager().getBridgeBySign(sign.getBlock());
					if(bridge instanceof Bridge) {
						if(bridge.isToggled()) {
							bridge.toggleBridge();
							sendMessage(e.getPlayer(), ChatColor.GREEN + "bridge has been untoggled!");
						} else {
							bridge.toggleBridge();
							sendMessage(e.getPlayer(), ChatColor.GREEN + "bridge has been toggled!");
						}	
					} else {
						sendMessage(e.getPlayer(), ChatColor.RED + "invalid bridge, perhaps you need to reassign the bridge?");
					}
				}
			}
		}
	}

}
