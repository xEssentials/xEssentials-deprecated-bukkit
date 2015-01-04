package tv.mineinthebox.essentials.minigames.memory;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class MinigameTeleportEvent implements Listener {

	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		if(e.getCause() == TeleportCause.COMMAND) {
			if(e.getPlayer().hasMetadata("gameType") && e.getPlayer().hasMetadata("game")) {
				e.getPlayer().sendMessage(ChatColor.RED + "you cannot teleport while being in a game!");
				e.setCancelled(true);
			}
		}
	}

}
