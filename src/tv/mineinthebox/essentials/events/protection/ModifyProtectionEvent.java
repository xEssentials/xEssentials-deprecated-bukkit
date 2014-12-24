package tv.mineinthebox.essentials.events.protection;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;

public class ModifyProtectionEvent implements Listener {

	public static HashMap<String, String> players = new HashMap<String, String>();

	@EventHandler(ignoreCancelled = true)
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(players.containsKey(e.getPlayer().getName())) {
				if(xEssentials.getManagers().getProtectionDBManager().isRegistered(e.getClickedBlock())) {
					if(xEssentials.getManagers().getProtectionDBManager().isOwner(e.getPlayer().getName(), e.getClickedBlock()) || e.getPlayer().hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
						xEssentials.getManagers().getProtectionDBManager().register(players.get(e.getPlayer().getName()), e.getClickedBlock());
						e.getPlayer().sendMessage(ChatColor.GREEN + "successfully registered block permissions for player " + players.get(e.getPlayer().getName()));
						players.remove(e.getPlayer().getName());
						e.setCancelled(true);
					}
				} else {
					players.remove(e.getPlayer().getName());
					e.getPlayer().sendMessage(ChatColor.RED + "could not modify permissions on a unregistered block");
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(players.containsKey(e.getPlayer().getName())) {
			players.remove(e.getPlayer().getName());
		}
	}
	
	@EventHandler
	public void onQuit(PlayerKickEvent e) {
		if(players.containsKey(e.getPlayer().getName())) {
			players.remove(e.getPlayer().getName());
		}
	}

}
