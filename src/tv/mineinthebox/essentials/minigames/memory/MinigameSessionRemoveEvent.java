package tv.mineinthebox.essentials.minigames.memory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.minigames.MinigameSession;

public class MinigameSessionRemoveEvent implements Listener {
	
	@EventHandler
	public void onRemove(PlayerQuitEvent e) {
		for(MinigameSession session : xEssentials.getManagers().getMinigameManager().getMinigameSessions().getAllSessions()) {
			if(session.hasSession(e.getPlayer().getName())) {
				session.removeSession(e.getPlayer().getName());
			}
		}
	}
	
	@EventHandler
	public void onRemove(PlayerKickEvent e) {
		for(MinigameSession session : xEssentials.getManagers().getMinigameManager().getMinigameSessions().getAllSessions()) {
			if(session.hasSession(e.getPlayer().getName())) {
				session.removeSession(e.getPlayer().getName());
			}
		}
	}

}
