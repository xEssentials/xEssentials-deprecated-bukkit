package tv.mineinthebox.essentials.events.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

public class PlayerTaskLogin implements Listener {
	
	@EventHandler
	public void onLogin(PlayerJoinEvent e) {
		xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
		if(xp.hasLoginTask()) {
			xp.performLoginTask();
		}
	}

}
