package tv.mineinthebox.essentials.events.players;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.RestrictedCommand;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CommandRestrictEvent implements Listener {
	
	private final xEssentials pl;
	
	public CommandRestrictEvent(xEssentials pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		if(e.getMessage().startsWith("/")) {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.hasCommandRestrictions()) {
				for(RestrictedCommand restrict : xp.getCommandRestrictions()) {
					if(e.getMessage().equalsIgnoreCase("/"+restrict.getCommand())) {
						e.getPlayer().sendMessage(restrict.getReason());
						if(restrict.hasTask()) {
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), restrict.getTaskCommand());
						}
						e.setCancelled(true);
						break;
					}
				}
			}
		}
	}

}
