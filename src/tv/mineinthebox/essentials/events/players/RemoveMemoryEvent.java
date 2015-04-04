package tv.mineinthebox.essentials.events.players;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class RemoveMemoryEvent implements Listener {
	
	private final xEssentials pl;
	
	public RemoveMemoryEvent(xEssentials pl) {
		this.pl = pl;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onQuitMemory(PlayerQuitEvent e) {
		if(pl.getConfiguration().getDebugConfig().isEnabled()) {
			xEssentials.log("preparing to remove player on normal quit: " + e.getPlayer().getName(), LogType.DEBUG);
		}
		if(pl.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			xp.save();
			pl.getManagers().getPlayerManager().removePlayer(xp.getName());
			if(pl.getConfiguration().getDebugConfig().isEnabled()) {
				xEssentials.log("player found and removed!, online check returns: " + pl.getManagers().getPlayerManager().isOnline(e.getPlayer().getName()), LogType.DEBUG);
			}
		} else {
			if(pl.getConfiguration().getDebugConfig().isEnabled()) {
				xEssentials.log("player " + e.getPlayer().getName() + " was already removed due a kick event however this event shouldn't be fired see:", LogType.DEBUG);
				xEssentials.log("found player in xEssentialsManager?: " + (pl.getManagers().getPlayerManager().isOnline(e.getPlayer().getName()) ? ChatColor.GREEN + "yes" : ChatColor.RED + "no"), LogType.DEBUG);
				xEssentials.log("player is still online by this event?: "+ (e.getPlayer() instanceof Player && e.getPlayer().isOnline() ? ChatColor.GREEN + "yes" : ChatColor.RED + "no"), LogType.DEBUG);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onQuitMemory(PlayerKickEvent e) {
		if(pl.getConfiguration().getDebugConfig().isEnabled()) {
			xEssentials.log("preparing to remove player on normal kick: " + e.getPlayer().getName(), LogType.DEBUG);
		}
		if(pl.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			xp.save();
			pl.getManagers().getPlayerManager().removePlayer(xp.getName());
			if(pl.getConfiguration().getDebugConfig().isEnabled()) {
				xEssentials.log("player found and removed!, online check returns: " + (pl.getManagers().getPlayerManager().isOnline(e.getPlayer().getName()) ? ChatColor.RED + "false" : ChatColor.GREEN + "true"), LogType.DEBUG);
			}
		}
	}
}
