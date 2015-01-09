package tv.mineinthebox.essentials.minigames.tennis.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.hook.Hooks;
import tv.mineinthebox.essentials.interfaces.XPlayer;
import tv.mineinthebox.essentials.minigames.tennis.events.TennisBallMoveEvent;

public class TennisScoreEvent implements Listener {
	
	@EventHandler
	public void onScore(TennisBallMoveEvent e) {
		XPlayer xp = e.getWhoScore();
		int score = xp.getPlayer().getMetadata("gameScore").get(0).asInt()+1;
		if(score == e.getArena().getWinScore()) {
			xp.getPlayer().getWorld().playSound(xp.getPlayer().getLocation(), Sound.FIREWORK_LAUNCH, 1F, 1F);
			Bukkit.broadcastMessage(ChatColor.GREEN + xp.getUser() + " has won the tennis match against " + e.getLoser().getUser() + (Hooks.isVaultEcoEnabled() ? " and has won " + e.getArena().getReward() : ""));
			e.getArena().sentReward(e.getWhoScore());
			e.getArena().reset();
		} else {
			xp.getPlayer().getWorld().playSound(xp.getPlayer().getLocation(), Sound.LEVEL_UP, 1F, 1F);
			xp.getPlayer().sendMessage(ChatColor.GRAY + "you scored!");
			xp.getPlayer().setMetadata("gameScore", new FixedMetadataValue(xEssentials.getPlugin(), score));
		}
	}

}
