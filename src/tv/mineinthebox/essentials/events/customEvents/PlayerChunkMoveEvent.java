package tv.mineinthebox.essentials.events.customEvents;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerChunkMoveEvent extends PlayerEvent {

	private static final HandlerList handlers = new HandlerList();
	private Location From;
	private Location To;
	
	public PlayerChunkMoveEvent(Player who, Location to, Location from) {
		super(who);
		this.From = from;
		this.To = to;
	}

	/**
	 * @author xize
	 * @param returns the location where the player came from one chunk back
	 * @return Location
	 */
	public Location getFrom() {
		return From;
	}
	
	/**
	 * @author xize
	 * @param returns the location where the player gets to one chunk futher
	 * @return Location
	 */
	public Location getTo() {
		return To;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
