package tv.mineinthebox.essentials.minigames.football.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import tv.mineinthebox.essentials.hook.Hooks;
import tv.mineinthebox.essentials.interfaces.XPlayer;
import tv.mineinthebox.essentials.minigames.football.TeamType;
import tv.mineinthebox.essentials.minigames.football.events.FootballPassEvent;

public class FootballScoreEvent implements Listener {
	
	@EventHandler
	public void onSlimeScore(FootballPassEvent e) {
		
		if(e.hasWon()) {
			Bukkit.broadcastMessage(ChatColor.GREEN + e.getTeam().name() + " has won the football match against " + e.getLosingTeam().name() + (Hooks.isVaultEnabled() ? " and has won " + e.getArena().getReward() : "") + " at arena " + e.getArena().getName());
			if(e.getTeam() == TeamType.RED_TEAM) {
				for(XPlayer xp : e.getArena().getRedTeam()) {
					e.getArena().sentReward(xp);
				}
			} else if(e.getTeam() == TeamType.BLUE_TEAM) {
				for(XPlayer xp : e.getArena().getBlueTeam()) {
					e.getArena().sentReward(xp);
				}
			}
			e.getArena().reset();
		} else if(e.hasScored()) {
			if(e.getTeam() == TeamType.BLUE_TEAM) {
				e.getArena().setBlueScore(e.getArena().getBlueScore()+1);
				e.getArena().broadcastMessage(ChatColor.GRAY + "blue team has scored!");
				e.getPlayer().getPlayer().getWorld().playSound(e.getPlayer().getPlayer().getLocation(), Sound.LEVEL_UP, 1F, 1F);
			} else if(e.getTeam() == TeamType.RED_TEAM) {
				e.getArena().setRedScore(e.getArena().getRedScore()+1);
				e.getArena().broadcastMessage(ChatColor.GRAY + "red team has scored!");
				e.getPlayer().getPlayer().getWorld().playSound(e.getPlayer().getPlayer().getLocation(), Sound.LEVEL_UP, 1F, 1F);
			}
		} else {
			e.getPlayer().getPlayer().sendMessage(ChatColor.GRAY + "you kicked the ball!");
			e.getPlayer().getPlayer().getWorld().playSound(e.getPlayer().getPlayer().getLocation(), Sound.COW_WALK, 1F, 3F);
		}
	}

}
