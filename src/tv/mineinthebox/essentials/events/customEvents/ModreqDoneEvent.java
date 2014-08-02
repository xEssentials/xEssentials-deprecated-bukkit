package tv.mineinthebox.essentials.events.customEvents;

import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class ModreqDoneEvent extends PlayerEvent {

	private static final HandlerList handlers = new HandlerList();
	private String description;
	private String comment;
	private String author;
	private int id;
	private Date date;
	private Player ModreqManager;
	
	public ModreqDoneEvent(Player who, String description, String comment, String author, int id, Date date, Player ModreqManager) {
		super(who);
		this.description = description;
		this.comment = comment;
		this.author = author;
		this.id = id;
		this.date = date;
		this.ModreqManager = ModreqManager;
	}
	
	/**
	 * @author xize
	 * @param gets the comment of this modreq
	 * @return String
	 */
	public String getComment() {
		return comment;
	}
	
	/**
	 * @author xize
	 * @param gets the author of this modreq which is completed
	 * @return String
	 */
	public String getAuthor() {
		return author;
	}
	
	/**
	 * @author xize
	 * @param gets the old id of this modreq
	 * @return int
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @author xize
	 * @param gets the orginal date when the modreq whas posted
	 * @return Date
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * @author xize
	 * @param returns the admin which is responsible for completing the modreq
	 * @return Player
	 */
	public Player getWhoManaged() {
		return ModreqManager;
	}
	
	/**
	 * @author xize
	 * @param get the modreqs Description
	 * @return String
	 */
	public String getModreqDescription() {
		return description;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
