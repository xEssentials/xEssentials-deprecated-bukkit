package tv.mineinthebox.essentials.events.protection;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.enums.ProtectionType;
import tv.mineinthebox.essentials.managers.ProtectionDBManager;

public class ModifyProtectionEvent implements Listener {
	
	private final ProtectionDBManager manager;
	
	public ModifyProtectionEvent(xEssentials pl) {
		this.manager = pl.getManagers().getProtectionDBManager();
	}

	@EventHandler(ignoreCancelled = true)
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			if(manager.hasSession(e.getPlayer().getName())) {
				Object[] obj = manager.getSessionData(e.getPlayer().getName());
				ProtectionType type = (ProtectionType)obj[0];
				if(type == ProtectionType.MODIFY) {
					String player = (String)obj[1];
					if(manager.isRegistered(e.getClickedBlock())) {
						if(manager.isOwner(e.getPlayer().getName(), e.getClickedBlock()) || e.getPlayer().hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							manager.register(player, e.getClickedBlock());
							e.getPlayer().sendMessage(ChatColor.GRAY + "successfully registered block permissions for player " + player);
							manager.removeSession(e.getPlayer().getName());
							e.setCancelled(true);
						}
					} else {
						manager.removeSession(e.getPlayer().getName());
						e.getPlayer().sendMessage(ChatColor.RED + "could not modify permissions on a unregistered block");
						e.setCancelled(true);
					}
				}
			}
			
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(manager.hasSession(e.getPlayer().getName())) {
			manager.removeSession(e.getPlayer().getName());
		}
	}
	
	@EventHandler
	public void onQuit(PlayerKickEvent e) {
		if(manager.hasSession(e.getPlayer().getName())) {
			manager.removeSession(e.getPlayer().getName());
		}
	}

}
