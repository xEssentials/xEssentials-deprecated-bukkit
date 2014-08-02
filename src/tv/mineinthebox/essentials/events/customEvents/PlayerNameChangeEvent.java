package tv.mineinthebox.essentials.events.customEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

public class PlayerNameChangeEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private String oldName;
	private String newName;
	private Player p;
	private xEssentialsPlayer xp;
	
	public PlayerNameChangeEvent(String oldName, String newName, Player p, xEssentialsPlayer xp) {
		this.oldName = oldName;
		this.newName = newName;
		this.p = p;
		this.xp = xp;
	}
	
	/**
	 * @author xize
	 * @param gets the old name before the name whas updated!
	 * @return String
	 */
	public String getNameBeforeUpdate() {
		return oldName;
	}
	
	/**
	 * @author xize
	 * @param gets the new updated name of this player
	 * @return String
	 */
	public String getNameUpdated() {
		return newName;
	}
	
	/**
	 * @author xize
	 * @param get the player within this event
	 * @return Player
	 */
	public Player getPlayer() {
		return p;
	}
	
	/**
	 * @author xize
	 * @param gets the xEssentialsPlayer interface
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
