package tv.mineinthebox.essentials.events.protection;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.ProtectionType;
import tv.mineinthebox.essentials.managers.ProtectionDBManager;

public class RegisterProtectionEvent implements Listener {

	private final List<Material> materials() {
		Material[] materials = {
				Material.CHEST, 
				Material.TRAPPED_CHEST,
				Material.IRON_DOOR_BLOCK,
				Material.WOODEN_DOOR,
				Material.SIGN_POST,
				Material.WALL_SIGN,
				Material.FURNACE,
				Material.JUKEBOX,
				Material.TRAP_DOOR,
				Material.DISPENSER
		};
		return Arrays.asList(materials);
	}

	private final ProtectionDBManager manager;
	
	public RegisterProtectionEvent(xEssentials pl) {
		this.manager = pl.getManagers().getProtectionDBManager();
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(manager.hasSession(e.getPlayer().getName())) {
				ProtectionType type = (ProtectionType) manager.getSessionData(e.getPlayer().getName())[0];
				if(type == ProtectionType.CREATE) {
					if(!manager.isRegistered(e.getClickedBlock())) {
						if(materials().contains(e.getClickedBlock())) {
							manager.register(e.getPlayer().getName(), e.getClickedBlock());
							e.getPlayer().sendMessage(ChatColor.GRAY + "successfully registered permissions for this " + e.getClickedBlock().getType().name() + " block");
							manager.removeSession(e.getPlayer().getName());
							e.setCancelled(true);
						} else {
							e.getPlayer().sendMessage(ChatColor.RED + "could not register permissions on a block which isnt a permissionable block");
							manager.removeSession(e.getPlayer().getName());
							e.setCancelled(true);
						}
					} else {
						e.getPlayer().sendMessage(ChatColor.RED + "this block is already private!");
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
