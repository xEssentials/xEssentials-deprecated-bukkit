package tv.mineinthebox.essentials.events.customevents;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.GreyListCause;
import tv.mineinthebox.essentials.interfaces.XPlayer;
public class PlayerGreyListedEvent extends PlayerEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	private String group;
	private String OldGroup;
	private boolean cancel;
	private GreyListCause cause;

	public PlayerGreyListedEvent(Player who, String group, String OldGroup, GreyListCause cause) {
		super(who);
		this.group = group;
		this.OldGroup = OldGroup;
		this.cause = cause;
	}

	/**
	 * @author xize
	 * @param get the new group the player is in
	 * @return String
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @author xize
	 * @param returns the type of cause why this event has been triggered
	 * @return
	 */
	public GreyListCause getCause() {
		return cause;
	}

	/**
	 * @author xize
	 * @param get the old group the player whas in
	 * @return String
	 */
	public String getOldGroup() {
		return OldGroup;
	}

	/**
	 * @author xize
	 * @param returns the xEssentials player!
	 * @return xEssentialsPlayer
	 */
	public XPlayer getEssentialsPlayer() {
		return xEssentials.getManagers().getPlayerManager().getPlayer(player.getName());
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public boolean isCancelled() {
		return cancel;
	}

	public void setCancelled(boolean bol) {
		if(bol) {
			cancel = true;
			xEssentials.getManagers().getVaultManager().setGroup(Bukkit.getWorlds().get(0), player.getName(), OldGroup);
		}
	}

}
