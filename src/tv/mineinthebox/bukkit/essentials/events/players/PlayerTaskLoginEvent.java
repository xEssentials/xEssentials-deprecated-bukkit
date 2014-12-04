package tv.mineinthebox.bukkit.essentials.events.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsPlayer;

public class PlayerTaskLoginEvent implements Listener {
	
	@EventHandler
	public void onLogin(PlayerJoinEvent e) {
		xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
		if(xp.hasLoginTask()) {
			xp.performLoginTask();
		}
	}

}
