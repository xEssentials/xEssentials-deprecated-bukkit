package tv.mineinthebox.essentials.events.chat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import tv.mineinthebox.essentials.events.customevents.PlayerChatSmilleyEvent;

public class ChatSmilleyEvent implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
	public void onChatSmilley(PlayerChatSmilleyEvent e) {
		if(e.getMessage().contains(":)")) {
			e.setMessage(e.getMessage().replace(":\\)", e.getSmilley(":)").getChar() + e.getSuffix()));
		}
		if(e.getMessage().toLowerCase().contains(":d")) {
			e.setMessage(e.getMessage().replace(":d", e.getSmilley(":D").getChar() + e.getSuffix()));
			e.setMessage(e.getMessage().replace(":D", e.getSmilley(":D").getChar() + e.getSuffix()));
		}
		if(e.getMessage().toLowerCase().contains(":@")) {
			e.setMessage(e.getMessage().replace(":@", e.getSmilley(":@").getChar() + e.getSuffix()));
		}
		if(e.getMessage().toLowerCase().contains("<3")) {
			e.setMessage(e.getMessage().replace("<3", e.getSmilley("<3").getChar() + e.getSuffix()));
		}
	}

}
