package tv.mineinthebox.bukkit.essentials.events.players;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.LogType;

public class RemoveMemoryEvent implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled=true)
	public void onQuitMemory(PlayerQuitEvent e) {
		if(Configuration.getDebugConfig().isEnabled()) {
			xEssentials.getPlugin().log("preparing to remove player on normal quit: " + e.getPlayer().getName(), LogType.DEBUG);
		}
		if(xEssentials.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			xEssentials.getManagers().getPlayerManager().removePlayer(e.getPlayer().getName());
			if(Configuration.getDebugConfig().isEnabled()) {
				xEssentials.getPlugin().log("player found and removed!, online check returns: " + xEssentials.getManagers().getPlayerManager().isOnline(e.getPlayer().getName()), LogType.DEBUG);
			}
		} else {
			if(Configuration.getDebugConfig().isEnabled()) {
				xEssentials.getPlugin().log("player " + e.getPlayer().getName() + " was already removed due a kick event however this event shouldn't be fired see:", LogType.DEBUG);
				xEssentials.getPlugin().log("found player in xEssentialsManager?: " + (xEssentials.getManagers().getPlayerManager().isOnline(e.getPlayer().getName()) ? ChatColor.GREEN + "yes" : ChatColor.RED + "no"), LogType.DEBUG);
				xEssentials.getPlugin().log("player is still online by this event?: "+ (e.getPlayer() instanceof Player && e.getPlayer().isOnline() ? ChatColor.GREEN + "yes" : ChatColor.RED + "no"), LogType.DEBUG);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onQuitMemory(PlayerKickEvent e) {
		if(Configuration.getDebugConfig().isEnabled()) {
			xEssentials.getPlugin().log("preparing to remove player on normal kick: " + e.getPlayer().getName(), LogType.DEBUG);
		}
		if(xEssentials.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			xEssentials.getManagers().getPlayerManager().removePlayer(e.getPlayer().getName());
			if(Configuration.getDebugConfig().isEnabled()) {
				xEssentials.getPlugin().log("player found and removed!, online check returns: " + (xEssentials.getManagers().getPlayerManager().isOnline(e.getPlayer().getName()) ? ChatColor.RED + "false" : ChatColor.GREEN + "true"), LogType.DEBUG);
			}
		}
	}
}
