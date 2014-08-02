package tv.mineinthebox.essentials.events.players;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import tv.mineinthebox.essentials.events.customEvents.PlayerNameChangeEvent;

public class PlayerCheckNameEvent implements Listener {
	
	@EventHandler
	public void onPlayerNameCheck(PlayerNameChangeEvent e) {
		Bukkit.broadcastMessage("player " + e.getNameBeforeUpdate() + " is now known as " + e.getNameUpdated());
	}

}
