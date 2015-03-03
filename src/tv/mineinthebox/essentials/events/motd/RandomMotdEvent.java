package tv.mineinthebox.essentials.events.motd;

import java.util.ListIterator;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import tv.mineinthebox.essentials.xEssentials;

public class RandomMotdEvent implements Listener {

	private final ListIterator<String> it;

	public RandomMotdEvent(xEssentials pl) {
		this.it = pl.getConfiguration().getMotdConfig().getMotdMessages();
	}

	@EventHandler
	public void onMotdEvent(ServerListPingEvent e) {
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
