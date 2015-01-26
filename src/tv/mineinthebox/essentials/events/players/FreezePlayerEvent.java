package tv.mineinthebox.essentials.events.players;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class FreezePlayerEvent implements Listener {
	
	private final xEssentials pl;
	
	public FreezePlayerEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
		if(xp.isFreezed()) {
			Location from = e.getFrom();
			from.setPitch(e.getTo().getPitch());
			from.setYaw(e.getTo().getYaw());
			e.setTo(from);
		}
	}

}
