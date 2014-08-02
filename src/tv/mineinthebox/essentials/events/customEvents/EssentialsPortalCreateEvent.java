package tv.mineinthebox.essentials.events.customEvents;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.instances.Portal;

public class EssentialsPortalCreateEvent extends PlayerEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private boolean cancel = false;
	private final Portal portal;
	
	public EssentialsPortalCreateEvent(Player who, Portal portal) {
		super(who);
		this.portal = portal;
	}
	
	/**
	 * @author xize
	 * @param name - the portal name
	 * @return Portal
	 * @throws NullPointerException - when the name does not exist
	 */
	public Portal getPortalByName(String name) throws Exception {
		return Configuration.getPortalConfig().getPortal(name);
	}
	
	/**
	 * @author xize
	 * @param returns all the portals
	 * @return Portal[]
	 */
	public Portal[] getPortals() {
		return Configuration.getPortalConfig().getPortals().values().toArray(new Portal[Configuration.getPortalConfig().getPortals().size()]);
	}
	
	/**
	 * @author xize
	 * @param returns the portal name which is created inside this event
	 * @return String
	 */
	public String getPortalName() {
		return this.portal.getPortalName();
	}
	
	/**
	 * @author xize
	 * @param returns all the frame blocks of this portal
	 * @return Block[]
	 */
	public Block[] getPortalFrameBlocks() {
		return this.portal.getBlocks();
	}
	
	/**
	 * @author xize
	 * @param get all inner blocks inside the portal frame
	 * @return Block[]
	 */
	public Block[] getInnerBlocks() {
		return this.portal.getInnerBlocks();
	}
	
	/**
	 * @author xize
	 * @param returns true if the portal is linked to a other, else false
	 * @return Boolean
	 */
	public boolean isLinked() {
		return this.portal.isLinked();
	}
	
	/**
	 * @author xize
	 * @param returns the linked portal
	 * @return Portal
	 */
	public Portal getLinkedPortal() {
		return this.portal.getLinkedPortal();
	}
	
	/**
	 * @author xize
	 * @param name - the portal where this portal needs to be linked to.
	 * @throws NullPointerException - when the portal does not exist.
	 */
	public void setLinkedPortal(String name) throws Exception {
		this.portal.linkPortal(name, true);
	}
	
	@Override
	public boolean isCancelled() {
		return cancel;
	}

	/**
	 * @author xize
	 * @param when cancelled the portal will be deleted, this means new instances or method calls will not be usable.
	 */
	@Override
	public void setCancelled(boolean arg0) {
		if(arg0) {
			portal.remove();
		}
		this.cancel = arg0;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
