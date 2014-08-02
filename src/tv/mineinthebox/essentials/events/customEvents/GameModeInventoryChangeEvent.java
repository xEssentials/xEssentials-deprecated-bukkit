package tv.mineinthebox.essentials.events.customEvents;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class GameModeInventoryChangeEvent extends PlayerEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private ItemStack[] oldInventoryContents;
	private ItemStack[] newInventoryContents;
	private GameMode gmBefore;
	private GameMode gmNow;
	private boolean cancelled = false;
	
	public GameModeInventoryChangeEvent(Player who, ItemStack[] oldInventory, ItemStack[] newInventory, GameMode gmBefore, GameMode gmNow) {
		super(who);
		this.oldInventoryContents = oldInventory;
		this.newInventoryContents = newInventory;
		this.gmBefore = gmBefore;
		this.gmNow = gmNow;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	/**
	 * @author xize
	 * @param gets the old inventory
	 * @return ItemStack[]
	 */
	public ItemStack[] getOldInventoryState() {
		return oldInventoryContents;
	}
	
	/**
	 * @author xize
	 * @param gets the new inventory
	 * @return ItemStack[]
	 */
	public ItemStack[] getNewInventoryState() {
		return newInventoryContents;
	}

	/**
	 * @author xize
	 * @param while cancelled we return the old inventory back
	 * @return void
	 */
	@Override
	public void setCancelled(boolean bol) {
		if(bol) {
			getPlayer().getInventory().setContents(oldInventoryContents);
			cancelled = true;
		}
		cancelled = false;
	}
	
	/**
	 * @author xize
	 * @param returns the older state from the gamemode
	 * @return GameMode
	 */
	public GameMode getGameModeBeforeState() {
		return gmBefore;
	}
	
	/**
	 * @author xize
	 * @param gets the newest gamemode state
	 * @return GameMOde
	 */
	public GameMode getGameModeNewState() {
		return gmNow;
	}

	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
