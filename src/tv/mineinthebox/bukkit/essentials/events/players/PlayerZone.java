package tv.mineinthebox.bukkit.essentials.events.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import tv.mineinthebox.bukkit.essentials.events.customevents.PlayerChunkMoveEvent;
import tv.mineinthebox.bukkit.essentials.hook.WorldGuardHook;

public class PlayerZone implements Listener {
	
	@EventHandler
	public void onChunk(final PlayerChunkMoveEvent e) {
		WorldGuardHook.sendRegionMessage(e.getPlayer(), e.getFrom().getChunk(), e.getTo().getChunk());
	}

}
 