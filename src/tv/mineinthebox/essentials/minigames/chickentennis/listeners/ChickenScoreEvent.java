package tv.mineinthebox.essentials.minigames.chickentennis.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;
import tv.mineinthebox.essentials.minigames.chickentennis.events.ChickenTennisBallEvent;

public class ChickenScoreEvent implements Listener {
	
	@EventHandler
	public void onScore(ChickenTennisBallEvent e) {
		XPlayer xp = e.getWhoScore();
		int score = xp.getPlayer().getMetadata("gameScore").get(0).asInt()+1;
		if(score == e.getArena().getWinScore()) {
			xp.getPlayer().getWorld().playSound(xp.getPlayer().getLocation(), Sound.FIREWORK_LAUNCH, 1F, 1F);
			Bukkit.broadcastMessage(ChatColor.GREEN + xp.getUser() + " has won the tennis match against " + e.getLoser().getUser());
			e.getArena().reset();
		} else {
			xp.getPlayer().getWorld().playSound(xp.getPlayer().getLocation(), Sound.LEVEL_UP, 1F, 1F);
			xp.getPlayer().sendMessage(ChatColor.GRAY + "you scored!");
			xp.getPlayer().setMetadata("gameScore", new FixedMetadataValue(xEssentials.getPlugin(), score));
		}
	}

}
