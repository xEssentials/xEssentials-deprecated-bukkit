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
import tv.mineinthebox.essentials.interfaces.EventTemplate;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.managers.ProtectionManager;

public class ModifyProtectionEvent extends EventTemplate implements Listener {
	
	private final ProtectionManager manager;
	
	public ModifyProtectionEvent(xEssentials pl) {
		super(pl, "Protection");
		this.manager = pl.getManagers().getProtectionDBManager();
	}

	@EventHandler
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
							sendMessage(e.getPlayer(), ChatColor.GRAY + "successfully registered block permissions for player " + player);
							manager.removeSession(e.getPlayer().getName());
							e.setCancelled(true);
						}
					} else {
						manager.removeSession(e.getPlayer().getName());
						sendMessage(e.getPlayer(), ChatColor.RED + "could not modify permissions on a unregistered block");
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
