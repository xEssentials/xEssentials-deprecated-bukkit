package tv.mineinthebox.essentials.events.customevents;

import org.bukkit.entity.Chicken;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class ChairExitEvent extends PlayerEvent {

	private static final HandlerList handlers = new HandlerList();
	
	private final Chicken chicken;
	
	public ChairExitEvent(Player who, Chicken chicken) {
		super(who);
		this.chicken = chicken;
	}
	
	public Chicken getChairPiece() {
		return chicken;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}


}
