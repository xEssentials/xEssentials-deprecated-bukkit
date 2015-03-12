package tv.mineinthebox.essentials.events.protection;

import java.util.UUID;

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
import tv.mineinthebox.essentials.instances.ProtectedBlock;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.managers.ProtectionManager;

public class ModifyProtectionEvent implements Listener {
	
	private final ProtectionManager manager;
	private final xEssentials pl;
	
	public ModifyProtectionEvent(xEssentials pl) {
		this.manager = pl.getManagers().getProtectionDBManager();
		this.pl = pl;
	}

	@EventHandler(ignoreCancelled = true)
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			if(manager.hasSession(e.getPlayer().getName())) {
				Object[] obj = manager.getSessionData(e.getPlayer().getName());
				ProtectionType type = (ProtectionType)obj[0];
				if(type == ProtectionType.MODIFY) {
					XOfflinePlayer player = (XOfflinePlayer)obj[1];
					ProtectedBlock pblock = new ProtectedBlock(pl, e.getClickedBlock());
					if(pblock.isProtected()) {
						if(pblock.isMember(e.getPlayer().getUniqueId()) || e.getPlayer().hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							pblock.addProtection(UUID.fromString(player.getUniqueId()));
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
