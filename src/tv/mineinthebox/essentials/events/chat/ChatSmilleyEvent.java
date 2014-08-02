package tv.mineinthebox.essentials.events.chat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import tv.mineinthebox.essentials.events.customEvents.PlayerChatSmilleyEvent;

public class ChatSmilleyEvent implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
	public void onChatSmilley(PlayerChatSmilleyEvent e) {
		if(e.getMessage().contains(":)")) {
			e.setMessage(e.getMessage().replaceAll(":\\)", e.getSmilley(":)").getChar() + e.getSuffix()));
		}
		if(e.getMessage().toLowerCase().contains(":d")) {
			e.setMessage(e.getMessage().replaceAll(":d", e.getSmilley(":D").getChar() + e.getSuffix()));
			e.setMessage(e.getMessage().replaceAll(":D", e.getSmilley(":D").getChar() + e.getSuffix()));
		}
		if(e.getMessage().toLowerCase().contains(":@")) {
			e.setMessage(e.getMessage().replaceAll(":@", e.getSmilley(":@").getChar() + e.getSuffix()));
		}
		if(e.getMessage().toLowerCase().contains("<3")) {
			e.setMessage(e.getMessage().replaceAll("<3", e.getSmilley("<3").getChar() + e.getSuffix()));
		}
	}

}
