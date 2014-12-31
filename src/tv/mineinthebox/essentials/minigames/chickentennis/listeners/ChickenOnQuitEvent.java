package tv.mineinthebox.essentials.minigames.chickentennis.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.minigames.MinigameArena;
import tv.mineinthebox.essentials.minigames.MinigameType;

public class ChickenOnQuitEvent implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(e.getPlayer().hasMetadata("game")) {
			MinigameType type = (MinigameType) e.getPlayer().getMetadata("gameType").get(0).value();
			if(type == MinigameType.CHICKEN_TENNIS) {
				MinigameArena arena = xEssentials.getManagers().getMinigameManager().getArenaByName(type, e.getPlayer().getMetadata("game").get(0).asString());
				arena.reset();	
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerKickEvent e) {
		if(e.getPlayer().hasMetadata("game")) {
			MinigameType type = (MinigameType) e.getPlayer().getMetadata("gameType").get(0).value();
			if(type == MinigameType.CHICKEN_TENNIS) {
				MinigameArena arena = xEssentials.getManagers().getMinigameManager().getArenaByName(type, e.getPlayer().getMetadata("game").get(0).asString());
				arena.reset();	
			}
		}
	}

}
