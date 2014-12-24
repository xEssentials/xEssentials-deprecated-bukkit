package tv.mineinthebox.essentials.events.motd;

import java.util.ListIterator;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import tv.mineinthebox.essentials.Configuration;

public class MotdEvent implements Listener {

	private ListIterator<String> it = Configuration.getMotdConfig().getMotdMessages();

	@EventHandler
	public void onMotdEvent(ServerListPingEvent e) {
		//new feature from bukkit!, this will hide players from being vanished
		/*
		if(e.getNumPlayers() > 0) {
			if(e.iterator().hasNext()) {
				e.iterator().remove();
			}
		}
		*/
		if(Configuration.getMotdConfig().isNormalMotdEnabled()) {
			e.setMotd(ChatColor.translateAlternateColorCodes('&', Configuration.getMotdConfig().getMotdMessage()));
		} else if(Configuration.getMotdConfig().isRandomMotdEnabled()) {
			if(it.hasNext()) {
				e.setMotd(ChatColor.translateAlternateColorCodes('&', it.next()));
			} else {
				while(it.hasPrevious()) {
					it.previous();
				}
				if(it.hasNext()) {
					e.setMotd(ChatColor.translateAlternateColorCodes('&', it.next()));
				}
			}
		}
	}

}
