package tv.mineinthebox.essentials.events.customEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

public class VanishChangeEvent extends PlayerEvent implements Cancellable{

	private static final HandlerList handlers = new HandlerList();
	private boolean canceled = false;
	private xEssentialsPlayer p;
	
	public VanishChangeEvent(Player who, xEssentialsPlayer p) {
		super(who);
		this.p = p;
	}

	@Override
	public boolean isCancelled() {
		return canceled;
	}

	/**
	 * @author xize
	 * @param returns true if the player whas vanished before
	 * @return boolean
	 */
	public boolean getStateIsVanishedBefore() {
		if(p.isVanished()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * @author xize
	 * @param returns true when the player is vanished now
	 * @return boolean
	 */
	public boolean getStateIsVanishedNow() {
		if(p.isVanished()) {
			return true;
		}
		return false;
	}
	
	/**
	 * @author xize
	 * @param returns the xEssentialsPlayer interface associated in this event
	 * @return
	 */
	public xEssentialsPlayer getxEssentialsPlayer() {
		return p;
	}
	
	/**
	 * @author xize
	 * @param when set to cancel, we revert the players vanish mode to the old state
	 * @return void
	 */
	@Override
	public void setCancelled(boolean cancel) {
		if(cancel) {
			if(p.isVanished()) {
				p.unvanish();
				p.setNoPickUp(false);
			} else {
				p.vanish();
				p.setNoPickUp(true);
			}
			canceled = true;
		} else {
			canceled = false;
		}
	}

	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
