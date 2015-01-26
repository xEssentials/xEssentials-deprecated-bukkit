package tv.mineinthebox.essentials.events.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class FlyEvent implements Listener {
	
	private final xEssentials pl;
	
	public FlyEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onFly(PlayerJoinEvent e) {
		if(pl.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isFlying()) {
				e.getPlayer().setAllowFlight(true);
				e.getPlayer().setFlying(true);
			}
		}
	}

}
