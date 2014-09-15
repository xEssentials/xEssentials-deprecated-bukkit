package tv.mineinthebox.bukkit.essentials.events.players;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.instances.MojangUUID;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsPlayer;

public class LoadMemory implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoinSetMemory(PlayerJoinEvent e) {
		if(Bukkit.getOnlineMode()) {
			try {
				String uuid = new MojangUUID(e.getPlayer()).getUniqueId();
				xEssentialsPlayer xp = new xEssentialsPlayer(e.getPlayer(), uuid);
				xEssentials.getManagers().getPlayerManager().addPlayer(e.getPlayer().getName(), xp);
			} catch(Exception r) {
				e.setJoinMessage("");
				e.getPlayer().kickPlayer("failed to join, cannot retrieve UUID of this player");
			}	
		} else {
			xEssentialsPlayer xp = new xEssentialsPlayer(e.getPlayer(), e.getPlayer().getName().toLowerCase());
			xEssentials.getManagers().getPlayerManager().addPlayer(e.getPlayer().getName(), xp);
		}
	}

}
