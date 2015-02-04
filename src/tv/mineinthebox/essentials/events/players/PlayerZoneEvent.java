package tv.mineinthebox.essentials.events.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.events.customevents.PlayerChunkMoveEvent;

public class PlayerZoneEvent implements Listener {
	
	private final xEssentials pl;
	
	public PlayerZoneEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onChunk(final PlayerChunkMoveEvent e) {
		pl.getManagers().getWorldGuardManager().sendRegionMessage(e.getPlayer(), e.getFrom().getChunk(), e.getTo().getChunk());
	}

}
 