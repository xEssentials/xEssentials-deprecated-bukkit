package tv.mineinthebox.essentials.minigames.memory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.minigames.MinigameArena;
import tv.mineinthebox.essentials.minigames.MinigameType;

public class MinigameQuitEvent implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(e.getPlayer().hasMetadata("gameType") && e.getPlayer().hasMetadata("game")) {
			MinigameType type = (MinigameType)e.getPlayer().getMetadata("gameType").get(0).value();
			String arenaname = e.getPlayer().getMetadata("game").get(0).asString();
			MinigameArena arena = xEssentials.getManagers().getMinigameManager().getArenaByName(type, arenaname);
			arena.reset();
			e.getPlayer().removeMetadata("gameType", xEssentials.getPlugin());
			e.getPlayer().removeMetadata("game", xEssentials.getPlugin());
			if(e.getPlayer().hasMetadata("gameScore")) {
				e.getPlayer().removeMetadata("gameScore", xEssentials.getPlugin());
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerKickEvent e) {
		if(e.getPlayer().hasMetadata("gameType") && e.getPlayer().hasMetadata("game")) {
			MinigameType type = (MinigameType)e.getPlayer().getMetadata("gameType").get(0).value();
			String arenaname = e.getPlayer().getMetadata("game").get(0).asString();
			MinigameArena arena = xEssentials.getManagers().getMinigameManager().getArenaByName(type, arenaname);
			arena.reset();
			e.getPlayer().removeMetadata("gameType", xEssentials.getPlugin());
			e.getPlayer().removeMetadata("game", xEssentials.getPlugin());
			if(e.getPlayer().hasMetadata("gameScore")) {
				e.getPlayer().removeMetadata("gameScore", xEssentials.getPlugin());
			}
		}
	}

}
