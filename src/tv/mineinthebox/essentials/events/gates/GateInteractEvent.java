package tv.mineinthebox.essentials.events.gates;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.Gate;

public class GateInteractEvent implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.WALL_SIGN) {
				Sign sign = (Sign) e.getClickedBlock().getState();
				if(sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_PURPLE + "[Gate]")) {
					Gate gate = xEssentials.getManagers().getGateManager().getGateBySign(e.getClickedBlock());
					if(gate instanceof Gate) {
						if(gate.isToggled()) {
							gate.toggleGate();
							e.getPlayer().sendMessage(ChatColor.GREEN + "untoggled gate!");
						} else {
							gate.toggleGate();
							e.getPlayer().sendMessage(ChatColor.GREEN + "gate toggled!");
						}
					}
				}
			}
		}
	}

}
