package tv.mineinthebox.essentials.events.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class PlayerTaskLoginEvent implements Listener {
	
	private final xEssentials pl;
	
	public PlayerTaskLoginEvent(xEssentials pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onLogin(PlayerJoinEvent e) {
		if(pl.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.hasLoginTask()) {
				xp.performLoginTask();
			}
		}
	}

}
