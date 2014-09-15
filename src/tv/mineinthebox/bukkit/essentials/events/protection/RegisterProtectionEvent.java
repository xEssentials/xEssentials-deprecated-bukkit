package tv.mineinthebox.bukkit.essentials.events.protection;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tv.mineinthebox.bukkit.essentials.xEssentials;

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

	public static HashSet<String> players = new HashSet<String>();

	@EventHandler(ignoreCancelled = true)
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(players.contains(e.getPlayer().getName())) {
				if(!xEssentials.getManagers().getProtectionDBManager().isRegistered(e.getClickedBlock())) {
					if(materials().contains(e.getClickedBlock().getType())) {
						xEssentials.getManagers().getProtectionDBManager().register(e.getPlayer().getName(), e.getClickedBlock());
						e.getPlayer().sendMessage(ChatColor.GREEN + "successfully registered permissions for this " + e.getClickedBlock().getType().name() + " block");
						players.remove(e.getPlayer().getName());
						e.setCancelled(true);
					} else {
						e.getPlayer().sendMessage(ChatColor.RED + "could not register permissions on a block which isn't a permissionable block.");
						players.remove(e.getPlayer().getName());
						e.setCancelled(true);
					}
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
