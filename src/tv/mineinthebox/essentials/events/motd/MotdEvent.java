package tv.mineinthebox.essentials.events.motd;

import java.util.ListIterator;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import tv.mineinthebox.essentials.xEssentials;

public class MotdEvent implements Listener {
	
	private final xEssentials pl;
	private final ListIterator<String> it;
	
	public MotdEvent(xEssentials pl) {
		this.pl = pl;
		this.it = pl.getConfiguration().getMotdConfig().getMotdMessages();
	}

	@EventHandler
	public void onMotdEvent(ServerListPingEvent e) {
		if(pl.getConfiguration().getMotdConfig().isNormalMotdEnabled()) {
			e.setMotd(ChatColor.translateAlternateColorCodes('&', pl.getConfiguration().getMotdConfig().getMotdMessage()));
		} else if(pl.getConfiguration().getMotdConfig().isRandomMotdEnabled()) {
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
