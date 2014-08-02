package tv.mineinthebox.essentials.events.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import tv.mineinthebox.essentials.events.customEvents.PlayerChunkMoveEvent;
import tv.mineinthebox.essentials.hook.WorldGuardHook;

public class PlayerZone implements Listener {
	
	@EventHandler
	public void onChunk(final PlayerChunkMoveEvent e) {
		WorldGuardHook.sendRegionMessage(e.getPlayer(), e.getFrom().getChunk(), e.getTo().getChunk());
	}

}
 