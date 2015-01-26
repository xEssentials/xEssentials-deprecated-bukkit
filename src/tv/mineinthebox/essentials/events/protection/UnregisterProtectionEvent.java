package tv.mineinthebox.essentials.events.protection;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.ProtectionType;
import tv.mineinthebox.essentials.managers.ProtectionDBManager;

public class UnregisterProtectionEvent implements Listener {
	
	private final ProtectionDBManager manager;
	
	public UnregisterProtectionEvent(xEssentials pl) {
		this.manager = pl.getManagers().getProtectionDBManager();
	}

	@EventHandler(ignoreCancelled = true)
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(manager.hasSession(e.getPlayer().getName())) {
				ProtectionType type = (ProtectionType)manager.getSessionData(e.getPlayer().getName())[0];
				if(type == ProtectionType.REMOVE) {
					if(manager.isRegistered(e.getClickedBlock())) {
						if(manager.isOwner(e.getPlayer().getName(), e.getClickedBlock())) {
							manager.unregister(e.getPlayer().getName(), e.getClickedBlock());
							e.getPlayer().sendMessage(ChatColor.GRAY + "you have successfully unregistered " + e.getClickedBlock().getType().name());
							manager.removeSession(e.getPlayer().getName());
							e.setCancelled(true);
						} else {
							e.getPlayer().sendMessage(ChatColor.RED + "you are not the owner of this block!");
							manager.removeSession(e.getPlayer().getName());
							e.setCancelled(true);
						}
					} else {
						e.getPlayer().sendMessage(ChatColor.RED + "this block was already unregistered.");
						manager.removeSession(e.getPlayer().getName());
						e.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(manager.hasSession(e.getPlayer().getName())){
			manager.removeSession(e.getPlayer().getName());
		}
	}
	
	@EventHandler
	public void onQuit(PlayerKickEvent e) {
		if(manager.hasSession(e.getPlayer().getName())){
			manager.removeSession(e.getPlayer().getName());
		}
	}

}
