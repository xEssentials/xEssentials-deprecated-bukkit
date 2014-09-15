package tv.mineinthebox.bukkit.essentials.events.protection;

import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;

public class UnregisterProtectionEvent implements Listener {

	public static HashSet<String> players = new HashSet<String>();

	@EventHandler(ignoreCancelled = true)
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(players.contains(e.getPlayer().getName())) {
				if(xEssentials.getManagers().getProtectionDBManager().isRegistered(e.getClickedBlock())) {
					if(xEssentials.getManagers().getProtectionDBManager().isOwner(e.getPlayer().getName(), e.getClickedBlock()) || e.getPlayer().hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
						xEssentials.getManagers().getProtectionDBManager().unregister(e.getPlayer().getName(), e.getClickedBlock());
						e.getPlayer().sendMessage(ChatColor.GREEN + "you have successfully unregistered " + e.getClickedBlock().getType().name());
						players.remove(e.getPlayer().getName());
						e.setCancelled(true);
					}
				} else {
					e.getPlayer().sendMessage(ChatColor.RED + "this block was already unregistered.");
					players.remove(e.getPlayer().getName());
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(players.contains(e.getPlayer().getName())) {
			players.remove(e.getPlayer().getName());
		}
	}
	
	@EventHandler
	public void onQuit(PlayerKickEvent e) {
		if(players.contains(e.getPlayer().getName())) {
			players.remove(e.getPlayer().getName());
		}
	}

}
