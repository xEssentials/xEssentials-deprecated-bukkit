package tv.mineinthebox.essentials.events.customEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import tv.mineinthebox.essentials.instances.RssFeed;

public class RssFeedEvent extends PlayerEvent {

	private static final HandlerList handlers = new HandlerList();
	private RssFeed feed;
	
	public RssFeedEvent(Player who, RssFeed feed) {
		super(who);
		this.feed = feed;
	}
	
	public RssFeed getRssFeed() {
		return feed;
	}

	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
}
