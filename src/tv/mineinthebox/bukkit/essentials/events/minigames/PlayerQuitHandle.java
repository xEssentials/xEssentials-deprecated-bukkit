package tv.mineinthebox.bukkit.essentials.events.minigames;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.instances.SpleefArena;

public class PlayerQuitHandle implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(xEssentials.getManagers().getMinigameManager().isPlayerInArea(e.getPlayer())) {
			Object obj = xEssentials.getManagers().getMinigameManager().getArenaFromPlayer(e.getPlayer());
			if(obj instanceof SpleefArena) {
				SpleefArena arena = (SpleefArena) obj;
				arena.removePlayer(e.getPlayer().getName());
			} else {
				//TO-DO hungergames arena.
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerKickEvent e) {
		if(xEssentials.getManagers().getMinigameManager().isPlayerInArea(e.getPlayer())) {
			Object obj = xEssentials.getManagers().getMinigameManager().getArenaFromPlayer(e.getPlayer());
			if(obj instanceof SpleefArena) {
				SpleefArena arena = (SpleefArena) obj;
				arena.removePlayer(e.getPlayer().getName());
			} else {
				//TO-DO hungergames arena.
			}
		}
	}

}
