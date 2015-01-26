package tv.mineinthebox.essentials.events.customevents;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.Smilley;
import tv.mineinthebox.essentials.hook.Hooks;

@SuppressWarnings("deprecation")
public class PlayerChatSmilleyEvent extends PlayerEvent implements Cancellable {

	private PlayerChatEvent e;
	private String[] CalledSmilleys;

	private static final HandlerList handlers = new HandlerList();
	
	private final xEssentials pl;
	
	public PlayerChatSmilleyEvent(PlayerChatEvent e, String[] CalledSmilleys, xEssentials pl) {
		super(e.getPlayer());
		this.e = e;
		this.CalledSmilleys = CalledSmilleys;
		this.pl = pl;
	}

	/**
	 * @author xize
	 * @param gets all smilleys in this chat!
	 * @return String[]
	 */
	public String[] getCalledSmilleys() {
		return CalledSmilleys;
	}

	/**
	 * @author xize
	 * @param gets all the Recipients receiving the chat.
	 * @return Set<Player>
	 */
	public Set<Player> getRecipients() {
		return e.getRecipients();
	}

	/**
	 * @author xize
	 * @param gets the message of the chat
	 * @return String
	 */
	public String getMessage() {
		return e.getMessage();
	}

	/**
	 * @author xize
	 * @param gets the smilley enum
	 * @return Smilley
	 */
	public Smilley getSmilley(String smilley) {
		if(smilley.length() == 2) {
			if(smilley.equalsIgnoreCase(":)")) {
				return Smilley.HAPPY;
			}else if(smilley.equalsIgnoreCase(":D")) {
				return Smilley.HAPPY;
			} else if(smilley.equalsIgnoreCase(":@")) {
				return Smilley.ANGRY;
			} else if(smilley.equalsIgnoreCase("<3")) {
				return Smilley.LOVE;
			}
		}
		throw new NullPointerException("cannot use smilley on a longer string than 2 chars");
	}

	/**
	 * @author xize
	 * @param replace the chat message.
	 * @return void
	 */
	public void setMessage(String message) {
		e.setMessage(message);
	}

	/**
	 * @author xize
	 * @param gets the suffix of the begin of the chat, works with bPermissions, Groupmanager, 
	 * @return
	 */
	public String getSuffix() {
		if(Hooks.isVaultChatEnabled()) {
			return ChatColor.translateAlternateColorCodes('&', pl.getManagers().getVaultManager().getSuffix(getPlayer().getName(), pl.getManagers().getVaultManager().getGroup(getPlayer())));
		} else {
			return ChatColor.RESET+"";
		}
	}

	@Override
	public boolean isCancelled() {
		if(e.isCancelled()) {
			return true;
		}
		return false;
	}

	@Override
	public void setCancelled(boolean bol) {
		e.setCancelled(bol);
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
