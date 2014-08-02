package tv.mineinthebox.essentials.events.customEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerTransactionEvent extends PlayerEvent {

	private static final HandlerList handlers = new HandlerList();
	
	private Double amount;
	private String paidPlayer;
	
	public PlayerTransactionEvent(Player who, Double amount, String paidPlayer) {
		super(who);
		this.amount = amount;
		this.paidPlayer = paidPlayer;
	}
	
	/**
	 * @author xize
	 * @param gets the player who launched the /money pay command
	 * @return Player
	 */
	public Player getPayer() {
		return player;
	}
	
	/**
	 * @author xize
	 * @param returns the amount of payed currency
	 * @return Double
	 */
	public Double getPayedAmount() {
		return amount;
	}
	
	/**
	 * @author xize
	 * @param get the receipt to who this money is paid
	 * @return String
	 */
	public String getPaidReceipment() {
		return paidPlayer;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
