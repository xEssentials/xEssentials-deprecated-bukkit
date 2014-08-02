package tv.mineinthebox.essentials.events.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

public class TeleportEvent implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		if(e.getCause() == TeleportCause.COMMAND || e.getCause() == TeleportCause.PLUGIN || e.getCause() == TeleportCause.UNKNOWN) {
			xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isVanished()) {
				return;
			} else {
				//refresh player on teleport
				xp.refreshPlayer();
			}
		}
	}

}
