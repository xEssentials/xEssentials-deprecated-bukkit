package tv.mineinthebox.essentials.minigames.parkour.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.hook.Hooks;
import tv.mineinthebox.essentials.interfaces.XPlayer;
import tv.mineinthebox.essentials.minigames.MinigameType;
import tv.mineinthebox.essentials.minigames.parkour.ParkourArena;

public class PlayerCheckSpawnpointEvent implements Listener {
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if(e.getPlayer().hasMetadata("gameType") && e.getPlayer().hasMetadata("game")) {
			MinigameType type = (MinigameType) e.getPlayer().getMetadata("gameType").get(0).value();
			if(type == MinigameType.PARKOUR) {
				String arenaname = e.getPlayer().getMetadata("game").get(0).asString();
				ParkourArena arena = (ParkourArena) xEssentials.getManagers().getMinigameManager().getArenaByName(type, arenaname);
				Location loc = e.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation();
				XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
				if(arena.isFinishPoint(loc)) {
					e.getPlayer().sendMessage(ChatColor.GRAY + "you have walked the parkour!" + (Hooks.isVaultEcoEnabled() ? ", you earned " + arena.getReward() : ""));
					arena.sentReward(xp);
					arena.removePlayer(xp);
				} else if(arena.isSpawnPoint(loc)) {
					xp.getPlayer().playSound(xp.getPlayer().getLocation(), Sound.LEVEL_UP, 1F, 1F);
					xp.getPlayer().sendMessage(ChatColor.GRAY + "checkpoint set!");
					arena.setSpawnPoint(xp, loc);
				}
			}
		}
	}

}
