package tv.mineinthebox.essentials.events.customevents;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class PlayerOpenBookEvent extends PlayerEvent implements Cancellable {
	
	private static final HandlerList handlers = new HandlerList();
	private boolean cancel;
	private BookMeta meta;
	private ItemStack book;
	
	public PlayerOpenBookEvent(Player p, ItemStack book, BookMeta meta) {
		super(p);
		this.book = book;
		this.meta = meta;	
	}
	
	/**
	 * @author xize
	 * @param returns true whenever the book has contents
	 * @return Boolean
	 */
	public boolean hasBookContents() {
		if(meta != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * @author xize
	 * @param returns the book contents, however make sure to use hasBookContents() first.
	 * @return BookMeta
	 * @throws NullPointerException - when there is no ItemMeta/book contents found.
	 */
	public BookMeta getBookContents() {
		return meta;
	}
	
	/**
	 * @author xize
	 * @param returns the ItemStack which get used in this event
	 * @return ItemStack
	 */
	public ItemStack getBookItem() {
		return book;
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
		this.cancel = bol;
		if(cancel) {
			player.closeInventory();
		}
	}
}
