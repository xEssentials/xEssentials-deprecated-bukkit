package tv.mineinthebox.essentials.events.players;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

import tv.mineinthebox.essentials.enums.PermissionKey;

public class DisablePortalCreation implements Listener {
	
	@EventHandler
	public void onCreatePortal(PortalCreateEvent e) {
		Block block = e.getBlocks().get(0);
		Player p = ExtractPlayerFromLocation(block.getLocation());
		if(p instanceof Player) {
			if(!p.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
				e.setCancelled(true);
				p.sendMessage(ChatColor.RED + "portal creation has been disabled on the server!");
			}
		}
	}
	
	private Player ExtractPlayerFromLocation(Location loc) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(p.getWorld().equals(loc.getWorld())) {
				Location ploc = p.getLocation();
				if(ploc.distance(loc) < 8) {
					return p;
				}	
			}
		}
		return null;
	}

}
