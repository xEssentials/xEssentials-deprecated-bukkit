package tv.mineinthebox.bukkit.essentials.events.customevents;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerChunkMoveEvent extends PlayerEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private Location from;
	private Location to;
	private PlayerMoveEvent e;
	
	public PlayerChunkMoveEvent(Player who, Location to, Location from, PlayerMoveEvent e) {
		super(who);
		this.from = from;
		this.to = to;
		this.e = e;
	}

	/**
	 * @author xize
	 * @param returns the location where the player came from one chunk back
	 * @return Location
	 */
	public Location getFrom() {
		return from;
	}
	
	/**
	 * @author xize
	 * @param returns the location where the player gets to one chunk futher
	 * @return Location
	 */
	public Location getTo() {
		return to;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return e.isCancelled();
	}

	@Override
	public void setCancelled(boolean arg0) {
		if(arg0) {
			e.getPlayer().teleport(e.getFrom());
		}
	}

}
