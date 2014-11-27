package tv.mineinthebox.bukkit.essentials.events.chat;

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
		
		String[] split = e.getMessage().split(" ");
		
		String message = e.getMessage();
		
		for(String partialmsg : split) {
			for(String badword : Configuration.getChatConfig().getSwearWords()) {
				if(partialmsg.startsWith(badword) || partialmsg.equalsIgnoreCase(badword)) {
					message = message.replace(badword, getCensor(badword));
				}
			}
		}
		
	}
	
	public String getCensor(String badword) {
		String censor = "";
		for(int i = 0; i < badword.length(); i++) {
			censor += "*";
		}
		return censor;
	}

}
