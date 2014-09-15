package tv.mineinthebox.bukkit.essentials.events.signs;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;

public class WarpSign implements Listener {
	
	@EventHandler
	public void onCreateSign(SignChangeEvent e) {
		if(e.getLine(0).equalsIgnoreCase("[warp]")) {
			if(e.getPlayer().hasPermission(PermissionKey.SIGN_WARP_SIGN.getPermission())) {
				if(xEssentials.getManagers().getWarpManager().doesWarpExist(e.getLine(1))) {
					e.setLine(0, ChatColor.BLUE + "[warp]");
					e.getPlayer().sendMessage(ChatColor.GREEN + "you have successfully created a warp sign!");
				} else {
					e.getBlock().breakNaturally();
					e.getPlayer().sendMessage(ChatColor.RED + "invalid warp!");
				}
			} else {
				e.getBlock().breakNaturally();
				e.getPlayer().sendMessage(ChatColor.RED + "you are not allowed to create this kind of signs!");
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getState() instanceof Sign) {
				Sign sign = (Sign) e.getClickedBlock().getState();
				if(sign.getLine(0).equalsIgnoreCase(ChatColor.BLUE + "[warp]")) {
					if(xEssentials.getManagers().getWarpManager().doesWarpExist(sign.getLine(1))) {
						e.getPlayer().performCommand("warp " + sign.getLine(1));
					}
				}
			}
		}
	}

}
