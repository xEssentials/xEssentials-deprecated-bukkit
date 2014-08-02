package tv.mineinthebox.essentials.events.customEvents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EssentialsBroadcastEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	private String message;
	private String highlighted;
	
	public EssentialsBroadcastEvent(String message, String highlighted) {
		this.highlighted = highlighted;
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getHighLighted() {
		return highlighted;
	}
	
	public boolean hasHighlightedPlayerName() {
		if(highlighted != null || !highlighted.equalsIgnoreCase("null")) {
			return true;
		}
		return false;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
