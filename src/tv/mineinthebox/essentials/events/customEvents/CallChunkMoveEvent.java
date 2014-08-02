package tv.mineinthebox.essentials.events.customEvents;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class CallChunkMoveEvent implements Listener {

	@EventHandler
	public void move(PlayerMoveEvent e) {
		if(e.getFrom().getBlockX() == e.getTo().getBlockX() && e.getFrom().getBlockZ() == e.getTo().getBlockZ()) {
			return;
		} else if(e.getFrom().getChunk().getX() != e.getTo().getChunk().getX() && e.getFrom().getX() > e.getTo().getX()) {
			Location getToCenteredChunk = new Location(e.getPlayer().getWorld(), (e.getTo().getX()-8), e.getTo().getY(), e.getTo().getZ());
			Location getFromCenteredChunk = new Location(e.getPlayer().getWorld(), (e.getFrom().getX()+8), e.getFrom().getY(), e.getFrom().getZ());
			Bukkit.getPluginManager().callEvent(new PlayerChunkMoveEvent(e.getPlayer(), getToCenteredChunk, getFromCenteredChunk));
		} else if(e.getFrom().getChunk().getZ() != e.getTo().getChunk().getZ() && e.getFrom().getZ() > e.getTo().getZ()) {
			Location getToCenteredChunk = new Location(e.getPlayer().getWorld(), e.getTo().getX(), e.getTo().getY(), (e.getTo().getZ()-8));
			Location getFromCenteredChunk = new Location(e.getPlayer().getWorld(), e.getFrom().getX(), e.getFrom().getY(), (e.getFrom().getZ()+8));
			Bukkit.getPluginManager().callEvent(new PlayerChunkMoveEvent(e.getPlayer(), getToCenteredChunk, getFromCenteredChunk));
		} else if(e.getFrom().getChunk().getX() != e.getTo().getChunk().getX() && e.getFrom().getX() < e.getTo().getX()) {
			Location getToCenteredChunk = new Location(e.getPlayer().getWorld(), (e.getTo().getX()+8), e.getTo().getY(), e.getTo().getZ());
			Location getFromCenteredChunk = new Location(e.getPlayer().getWorld(), (e.getFrom().getX()-8), e.getFrom().getY(), e.getFrom().getZ());
			Bukkit.getPluginManager().callEvent(new PlayerChunkMoveEvent(e.getPlayer(), getToCenteredChunk, getFromCenteredChunk));
		} else if(e.getFrom().getChunk().getZ() != e.getTo().getChunk().getZ() && e.getFrom().getZ() < e.getTo().getZ()) {
			Location getToCenteredChunk = new Location(e.getPlayer().getWorld(), e.getTo().getX(), e.getTo().getY(), (e.getTo().getZ()+8));
			Location getFromCenteredChunk = new Location(e.getPlayer().getWorld(), e.getFrom().getX(), e.getFrom().getY(), (e.getFrom().getZ()-8));
			Bukkit.getPluginManager().callEvent(new PlayerChunkMoveEvent(e.getPlayer(), getToCenteredChunk, getFromCenteredChunk));
		}
	}
}
