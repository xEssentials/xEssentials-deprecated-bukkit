package tv.mineinthebox.essentials.minigames.tennis.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.minigames.MinigameSession;

public class TennisCreationEvent implements Listener {

	@EventHandler
	public void onPlaceSpawn(BlockPlaceEvent e) {
		if(e.isCancelled()) {
			return;
		}
		
		MinigameSession session = xEssentials.getManagers().getMinigameManager().getMinigameSessions().getTennisSessions();
		
		if(session.hasSession(e.getPlayer().getName())) {
			if(!session.containsSessionData(e.getPlayer().getName(), "spawn1")) {
				Location loc = e.getBlock().getLocation();
				loc.setYaw(e.getPlayer().getLocation().getYaw());
				loc.setPitch(e.getPlayer().getLocation().getPitch());
				session.addSessionData(e.getPlayer().getName(), "spawn1", loc);
				e.getPlayer().sendMessage(ChatColor.GRAY + "saved spawn1 location, now set spawn2 by placing a block!");
				e.setCancelled(true);
			} else if(!session.containsSessionData(e.getPlayer().getName(), "spawn2")) {
				Location loc = e.getBlock().getLocation();
				loc.setYaw(e.getPlayer().getLocation().getYaw());
				loc.setPitch(e.getPlayer().getLocation().getPitch());
				session.addSessionData(e.getPlayer().getName(), "spawn2", loc);
				e.getPlayer().sendMessage(ChatColor.GRAY + "saved spawn2 location, now set the spawn of the chicken ball.");
				e.setCancelled(true);
			} else if(!session.containsSessionData(e.getPlayer().getName(), "chickenloc")) {
				session.addSessionData(e.getPlayer().getName(), "chickenloc", e.getBlock().getLocation());
				e.getPlayer().sendMessage(ChatColor.GRAY + "chicken ball location saved, now define the border of the x-axis");
				e.setCancelled(true);
			} else if(!session.containsSessionData(e.getPlayer().getName(), "boundsx")) {
				session.addSessionData(e.getPlayer().getName(), "boundsx", e.getBlock().getRelative(BlockFace.DOWN).getX());
				e.getPlayer().sendMessage(ChatColor.GRAY + "bounds x is defined, now define the border of the z-axis");
				e.setCancelled(true);
			} else if(!session.containsSessionData(e.getPlayer().getName(), "boundsz")) {
				session.addSessionData(e.getPlayer().getName(), "boundsz", e.getBlock().getRelative(BlockFace.DOWN).getZ());
				e.getPlayer().sendMessage(ChatColor.GRAY + "now type /chickentennis save to save the arena and make it into a workable arena");
				e.setCancelled(true);
			}
		}
	}
	
}
