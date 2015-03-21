package tv.mineinthebox.essentials.events.customevents;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.xEssentialsOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;

@SuppressWarnings("deprecation")
public class PlayerChatHighLightEvent extends PlayerEvent implements Cancellable {

	private PlayerChatEvent e;
	private String[] CalledPlayerNames;

	private static final HandlerList handlers = new HandlerList();
	
	private final xEssentials pl;
	
	public PlayerChatHighLightEvent(PlayerChatEvent e, String[] CalledPlayerNames, xEssentials pl) {
		super(e.getPlayer());
		this.e = e;
		this.CalledPlayerNames = CalledPlayerNames;
		this.pl = pl;
	}

	/**
	 * @author xize
	 * @param get all players called within this chat, this also count for players being offline.
	 * @return xEssentialsOfflinePlayer[]
	 */
	public xEssentialsOfflinePlayer[] getCalledPlayers() {
		List<XOfflinePlayer> offPlayers = new ArrayList<XOfflinePlayer>();
		for(int i = 0; i < CalledPlayerNames.length; i++) {
			if(!pl.getManagers().getPlayerManager().getOfflinePlayer(CalledPlayerNames[i]).equals(null) || pl.getManagers().getPlayerManager().getOfflinePlayer(CalledPlayerNames[i]) != null) {
				offPlayers.add(pl.getManagers().getPlayerManager().getOfflinePlayer(CalledPlayerNames[i]));
			}
		}
		xEssentialsOfflinePlayer[] offs = offPlayers.toArray(new xEssentialsOfflinePlayer[offPlayers.size()]);
		return offs;
	}

	/**
	 * @author xize
	 * @param get a unmodified array of player names
	 * @return String[]
	 */
	public String[] getCalledPlayersAsStringArray() {
		return CalledPlayerNames;
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
	 * @param gets the HashTag
	 * @return String
	 */
	public String getHashTag() {
		return pl.getConfiguration().getChatConfig().getHashTag();
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
		return pl.getConfiguration().getChatConfig().getSuffixByPlayer(player);
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
