package tv.mineinthebox.essentials.events.customEvents;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.hook.Hooks;
import tv.mineinthebox.essentials.instances.xEssentialsOfflinePlayer;

public class PlayerChatHighLightEvent extends PlayerEvent implements Cancellable {

	private AsyncPlayerChatEvent e;
	private String[] CalledPlayerNames;

	private static final HandlerList handlers = new HandlerList();
	public PlayerChatHighLightEvent(AsyncPlayerChatEvent e, String[] CalledPlayerNames) {
		super(e.getPlayer());
		this.e = e;
		this.CalledPlayerNames = CalledPlayerNames;
	}

	/**
	 * @author xize
	 * @param get all players called within this chat, this also count for players being offline.
	 * @return xEssentialsOfflinePlayer[]
	 */
	public xEssentialsOfflinePlayer[] getCalledPlayers() {
		List<xEssentialsOfflinePlayer> offPlayers = new ArrayList<xEssentialsOfflinePlayer>();
		for(int i = 0; i < CalledPlayerNames.length; i++) {
			if(!xEssentials.getManagers().getPlayerManager().getOfflinePlayer(CalledPlayerNames[i]).equals(null) || xEssentials.getManagers().getPlayerManager().getOfflinePlayer(CalledPlayerNames[i]) != null) {
				offPlayers.add(xEssentials.getManagers().getPlayerManager().getOfflinePlayer(CalledPlayerNames[i]));
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
		return Configuration.getChatConfig().getHashTag();
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
		if(Hooks.isVaultEnabled()) {
			return ChatColor.translateAlternateColorCodes('&', xEssentials.getManagers().getVaultManager().getSuffix(getPlayer().getName(), xEssentials.getManagers().getVaultManager().getGroup(getPlayer())));
		} else {
			return ChatColor.WHITE+"";
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
