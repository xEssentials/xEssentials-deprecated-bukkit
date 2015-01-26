package tv.mineinthebox.essentials.events.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class VanishArchievementEvent implements Listener {
	
	private final xEssentials pl;
	
	public VanishArchievementEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void hideAchievement(PlayerAchievementAwardedEvent e) {
		XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
		if(xp.isVanished()) {
			e.setCancelled(true);
		}
	}

}
