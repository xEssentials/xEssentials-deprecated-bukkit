package tv.mineinthebox.essentials.events.players;

import java.util.Iterator;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class PlayerSendPooledMessageEvent implements Listener {

	private final xEssentials pl;

	public PlayerSendPooledMessageEvent(xEssentials pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onLogin(PlayerJoinEvent e) {
		if(pl.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.hasMessages()) {
				Iterator<String> it = xp.getMessages().iterator();
				while(it.hasNext()) {
					e.getPlayer().sendMessage(it.next());
					it.remove();
				}
			}
		}
	}

}
