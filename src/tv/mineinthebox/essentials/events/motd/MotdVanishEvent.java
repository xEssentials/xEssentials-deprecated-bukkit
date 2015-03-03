package tv.mineinthebox.essentials.events.motd;

import java.util.Iterator;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;


public class MotdVanishEvent implements Listener {
	
	private final xEssentials pl;
	
	public MotdVanishEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onPing(ServerListPingEvent e) {
		Iterator<Player> it = e.iterator();
		while(it.hasNext()) {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(it.next().getName());
			if(xp.isVanished()) {
				it.remove();
			}
		}
	}
	
}
