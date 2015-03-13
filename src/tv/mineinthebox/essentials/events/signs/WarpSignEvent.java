package tv.mineinthebox.essentials.events.signs;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.EventTemplate;

public class WarpSignEvent extends EventTemplate implements Listener {
	
	public WarpSignEvent(xEssentials pl) {
		super(pl, "WarpSign");
	}
	
	@EventHandler
	public void onCreateSign(SignChangeEvent e) {
		if(e.getLine(0).equalsIgnoreCase("[warp]")) {
			if(e.getPlayer().hasPermission(PermissionKey.SIGN_WARP_SIGN.getPermission())) {
				if(pl.getManagers().getWarpManager().isWarp(e.getLine(1))) {
					e.setLine(0, ChatColor.BLUE + "[warp]");
					sendMessage(e.getPlayer(), ChatColor.GREEN + "you have successfully created a warp sign!");
				} else {
					e.getBlock().breakNaturally();
					sendMessage(e.getPlayer(), ChatColor.RED + "invalid warp!");
				}
			} else {
				e.getBlock().breakNaturally();
				sendMessage(e.getPlayer(), ChatColor.RED + "you are not allowed to create this kind of signs!");
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getState() instanceof Sign) {
				Sign sign = (Sign) e.getClickedBlock().getState();
				if(sign.getLine(0).equalsIgnoreCase(ChatColor.BLUE + "[warp]")) {
					if(pl.getManagers().getWarpManager().isWarp(sign.getLine(1))) {
						e.getPlayer().performCommand("warp " + sign.getLine(1));
					}
					e.setCancelled(true);
				}
			}
		}
	}

}
