package tv.mineinthebox.essentials.events.chat;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import tv.mineinthebox.essentials.events.customEvents.RssFeedEvent;

public class BroadcastSiteNewsEvent implements Listener {
	
	@EventHandler
	public void onBroadCastRss(RssFeedEvent e) {
		if(e.getRssFeed().getTitle().contains("Maintenance") || e.getRssFeed().getTitle().contains("maintenance") || e.getRssFeed().getTitle().contains("Maintance") || e.getRssFeed().getTitle().contains("maintance")) {
			e.getPlayer().sendMessage(ChatColor.GREEN + "[news-important]" + ChatColor.GRAY + ChatColor.UNDERLINE + e.getRssFeed().getAuthor() + ChatColor.GRAY + " has posted a new thread!");
			e.getPlayer().sendMessage(ChatColor.GREEN + "[news-important]" + ChatColor.GRAY + "title: " + e.getRssFeed().getTitle());
			e.getPlayer().sendMessage(ChatColor.GREEN + "[news-important]" + ChatColor.GRAY + "url: " + e.getRssFeed().getLink());
			e.getPlayer().sendMessage(ChatColor.GREEN + "[news-important]" + ChatColor.GRAY + "feel free to join on this thread :)!");		
		} else {
			e.getPlayer().sendMessage(ChatColor.GREEN + "[news]" + ChatColor.GRAY + ChatColor.UNDERLINE + e.getRssFeed().getAuthor() + ChatColor.GRAY + " has posted a new thread!");
			e.getPlayer().sendMessage(ChatColor.GREEN + "[news]" + ChatColor.GRAY + "title: " + e.getRssFeed().getTitle());
			e.getPlayer().sendMessage(ChatColor.GREEN + "[news]" + ChatColor.GRAY + "url: " + e.getRssFeed().getLink());
			e.getPlayer().sendMessage(ChatColor.GREEN + "[news]" + ChatColor.GRAY + "feel free to join on this thread :)!");
		}
	}

}
