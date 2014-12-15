package tv.mineinthebox.bukkit.essentials.events.customevents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.MojangStatusResponse;
import tv.mineinthebox.bukkit.essentials.instances.MojangStatus;
import tv.mineinthebox.bukkit.essentials.interfaces.XPlayer;

public class MojangStatusEvent extends Event {

	private MojangStatusResponse resp;
	private MojangStatus status;
	
	public MojangStatusEvent(MojangStatus status, MojangStatusResponse resp) {
		this.resp = resp;
		this.status = status;
	}
	
	/**
	 * @author xize
	 * @param get the response from the mojang servers
	 * @return MojangResponse
	 */
	public MojangStatusResponse getChangedStatus() {
		return resp;
	}
	
	/**
	 * @author xize
	 * @param get the current status object containing all type statuses.
	 * @return MojangStatus
	 */
	public MojangStatus getCurrentStatus() {
		return status;
	}
	
	/**
	 * @author xize
	 * @param get all xEssentials players
	 * @return xEssentialsPlayer[]
	 */
	public XPlayer[] getPlayers() {
		return xEssentials.getManagers().getPlayerManager().getPlayers();
	}
	
	private static final HandlerList handlers = new HandlerList();
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
