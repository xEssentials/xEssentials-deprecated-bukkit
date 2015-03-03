package tv.mineinthebox.essentials.events.motd;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import tv.mineinthebox.essentials.xEssentials;

public class NormalMotdEvent implements Listener {
	
	private final xEssentials pl;
	
	public NormalMotdEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onPing(ServerListPingEvent e) {
		e.setMotd(ChatColor.translateAlternateColorCodes('&', pl.getConfiguration().getMotdConfig().getMotdMessage()));
	}

}
