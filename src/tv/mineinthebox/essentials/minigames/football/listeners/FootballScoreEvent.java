package tv.mineinthebox.essentials.minigames.football.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import tv.mineinthebox.essentials.minigames.football.events.FootballPassEvent;

public class FootballScoreEvent implements Listener {
	
	@EventHandler
	public void onSlimeScore(FootballPassEvent e) {
		if(e.hasWon()) {
			
		} else if(e.hasScored()) {
			
		} else {
			e.getPlayer().getPlayer().sendMessage(ChatColor.GRAY + "you kicked the ball!");
			//e.getPlayer().getPlayer().getWorld().playSound(e.getPlayer().getPlayer().getLocation(), Sound., volume, pitch);
		}
	}

}
