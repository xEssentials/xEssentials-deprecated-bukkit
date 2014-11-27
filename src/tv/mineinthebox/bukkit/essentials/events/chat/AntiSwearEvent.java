package tv.mineinthebox.bukkit.essentials.events.chat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import tv.mineinthebox.bukkit.essentials.Configuration;

public class AntiSwearEvent implements Listener {
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		if(e.isCancelled()) {
			return;
		}
		
		Pattern pat = Pattern.compile("[A-Za-z]");
		Pattern p = Pattern.compile(Configuration.getChatConfig().getSwearWords(), Pattern.CASE_INSENSITIVE);
		
		StringBuilder build = new StringBuilder(e.getMessage());
		
		Matcher match = p.matcher(e.getMessage());
		
		while(match.find()) {
			build.replace(match.start(), match.end(), pat.matcher(match.group()).replaceAll("*"));
		}
		
		e.setMessage(build.toString());
		
	}

}
