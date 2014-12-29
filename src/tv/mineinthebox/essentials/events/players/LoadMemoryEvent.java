package tv.mineinthebox.essentials.events.players;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class LoadMemoryEvent implements Listener {

	private String uuid;

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoinSetMemory(PlayerJoinEvent e) {
		if(Bukkit.getOnlineMode()) {
			if(Configuration.getDebugConfig().isEnabled()) {
				xEssentials.getPlugin().log("player " + e.getPlayer().getName() + " has joined, checking uuid in dir /xEssentials/players/", LogType.DEBUG);
			}
			
			try {
				this.uuid = xEssentials.getUUIDManager().getUniqueId(e.getPlayer());
				if(uuid != null) {
					XPlayer xp = new xEssentialsPlayer(e.getPlayer(), uuid);
					xEssentials.getManagers().getPlayerManager().addPlayer(e.getPlayer().getName(), xp);	
				} else {
					e.getPlayer().kickPlayer("failed to verify username's uuid for xEssentials, your uuid is null");
				}
			} catch(Exception r) {
				e.getPlayer().kickPlayer("failed to verify username's uuid for xEssentials");
				if(Configuration.getDebugConfig().isEnabled()) {
					r.printStackTrace();	
				}
			}
		} else {
			xEssentialsPlayer xp = new xEssentialsPlayer(e.getPlayer(), e.getPlayer().getName().toLowerCase());
			xEssentials.getManagers().getPlayerManager().addPlayer(e.getPlayer().getName(), xp);
		}
	}

}
