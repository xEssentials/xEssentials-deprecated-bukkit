package tv.mineinthebox.bukkit.essentials.events.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.LogType;

public class RemoveMemory implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onQuitMemory(PlayerQuitEvent e) {
		if(Configuration.getDebugConfig().isEnabled()) {
			xEssentials.getPlugin().log("preparing to remove player on normal quit." + e.getPlayer().getName(), LogType.DEBUG);
		}
		if(xEssentials.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			if(Configuration.getDebugConfig().isEnabled()) {
				xEssentials.getPlugin().log("player found and removed!, check returns: " + xEssentials.getManagers().getPlayerManager().isOnline(e.getPlayer().getName()), LogType.DEBUG);
			}
			xEssentials.getManagers().getPlayerManager().removePlayer(e.getPlayer().getName());
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onQuitMemory(PlayerKickEvent e) {
		if(Configuration.getDebugConfig().isEnabled()) {
			xEssentials.getPlugin().log("preparing to remove player on normal kick." + e.getPlayer().getName(), LogType.DEBUG);
		}
		if(xEssentials.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			if(Configuration.getDebugConfig().isEnabled()) {
				xEssentials.getPlugin().log("player found and removed!, check returns: " + xEssentials.getManagers().getPlayerManager().isOnline(e.getPlayer().getName()), LogType.DEBUG);
			}
			xEssentials.getManagers().getPlayerManager().removePlayer(e.getPlayer().getName());
		}
	}
}
