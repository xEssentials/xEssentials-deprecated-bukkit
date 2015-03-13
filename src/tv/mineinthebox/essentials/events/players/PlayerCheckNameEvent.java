package tv.mineinthebox.essentials.events.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.events.customevents.PlayerNameChangeEvent;
import tv.mineinthebox.essentials.interfaces.EventTemplate;

public class PlayerCheckNameEvent extends EventTemplate implements Listener {
	
	public PlayerCheckNameEvent(xEssentials pl) {
		super(pl, "NameChangeAlert");
	}
	
	@EventHandler
	public void onPlayerNameCheck(PlayerNameChangeEvent e) {
		broadcast("player " + e.getNameBeforeUpdate() + " is now known as " + e.getNameUpdated());
	}

}
