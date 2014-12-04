package tv.mineinthebox.bukkit.essentials.events.players;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.LogType;
import tv.mineinthebox.bukkit.essentials.helpers.MojangUUID;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsPlayer;

public class LoadMemoryEvent implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoinSetMemory(PlayerJoinEvent e) {
		if(Configuration.getDebugConfig().isEnabled()) {
			xEssentials.getPlugin().log("player " + e.getPlayer().getName() + " has joined, checking uuid in dir /xEssentials/players/", LogType.DEBUG);
		}
		if(Bukkit.getOnlineMode()) {
			try {
				String uuid = new MojangUUID(e.getPlayer()).getUniqueId();
				xEssentialsPlayer xp = new xEssentialsPlayer(e.getPlayer(), uuid);
				xEssentials.getManagers().getPlayerManager().addPlayer(e.getPlayer().getName(), xp);
			} catch(Exception r) {
				if(Configuration.getDebugConfig().isEnabled()) {
					xEssentials.getPlugin().log("failed to fetch uuid of this player perhaps the url is changed?, player is kicked!", LogType.DEBUG);
				}
				e.setJoinMessage("");
				e.getPlayer().kickPlayer("failed to join, cannot retrieve UUID of this player");
			}	
		} else {
			xEssentialsPlayer xp = new xEssentialsPlayer(e.getPlayer(), e.getPlayer().getName().toLowerCase());
			xEssentials.getManagers().getPlayerManager().addPlayer(e.getPlayer().getName(), xp);
		}
	}

}
