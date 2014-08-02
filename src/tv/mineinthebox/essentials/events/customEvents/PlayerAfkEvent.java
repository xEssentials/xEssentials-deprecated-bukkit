package tv.mineinthebox.essentials.events.customEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

public class PlayerAfkEvent extends PlayerEvent {

	private static final HandlerList handlers = new HandlerList();
	private boolean isAfk;
	private boolean whasAfk;
	private xEssentialsPlayer xp;
	
	public PlayerAfkEvent(Player who, Boolean isAfk, Boolean whasAfk) {
		super(who);
		this.isAfk = isAfk;
		this.whasAfk = whasAfk;
		this.xp = xEssentials.getManagers().getPlayerManager().getPlayer(who.getName());
	}
	
	/**
	 * @author xize
	 * @param returns true whenever the new state is set to afk.
	 * @return boolean
	 */
	public boolean isAfk() {
		return isAfk;
	}
	
	/**
	 * @author xize
	 * @param returns true whenever the new state is set to non afk
	 * @return boolean
	 */
	public boolean whasAfk() {
		return whasAfk;
	}
	
	/**
	 * @author xize
	 * @param get the afk message
	 * @return String
	 */
	public String getAfkMessage() {
		return xp.getAfkReason();
	}
	
	/**
	 * @author xize
	 * @param gets the xEssentialsPlayer
	 * @return xEssentialsPlayer
	 */
	public xEssentialsPlayer getxEssentialsPlayer() {
		return xp;
	}

	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
