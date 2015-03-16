package tv.mineinthebox.essentials.events.chat;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.events.customevents.RssFeedEvent;
import tv.mineinthebox.essentials.interfaces.EventTemplate;

public class BroadcastSiteNewsEvent extends EventTemplate implements Listener {
	
	public BroadcastSiteNewsEvent(xEssentials pl) {
		super(pl, "News");
	}

	@EventHandler
	public void onBroadCastRss(RssFeedEvent e) {
			sendMessage(e.getPlayer(), e.getRssFeed().getAuthor() + ChatColor.GRAY + " has posted a new thread!");
			sendMessage(e.getPlayer(), "title: " + e.getRssFeed().getTitle());
			sendMessage(e.getPlayer(), "url: " + e.getRssFeed().getLink());
			sendMessage(e.getPlayer(), "feel free to join on this thread :)!");
	}

}
