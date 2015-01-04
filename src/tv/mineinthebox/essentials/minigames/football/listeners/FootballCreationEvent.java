package tv.mineinthebox.essentials.minigames.football.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.minigames.football.FootballSession;

public class FootballCreationEvent implements Listener {

	@EventHandler
	public void onBuild(BlockPlaceEvent e) {
		if(e.isCancelled()) {
			return;
		}

		Player p = e.getPlayer();
		FootballSession session = (FootballSession) xEssentials.getManagers().getMinigameManager().getMinigameSessions().getFootballSessions();

		if(session.hasSession(p.getName())) {
			if(!session.containsSessionData(p.getName(), "slimespawn")) {
				session.addSessionData(p.getName(), "slimespawn", e.getBlock().getLocation());
				p.sendMessage(ChatColor.GRAY + "football spawn registered, now place a block where redteam should spawn!");
				e.setCancelled(true);
			} else if(!session.containsSessionData(p.getName(), "redteamspawn")) {
				Location loc = e.getBlock().getLocation();
				loc.setYaw(p.getLocation().getYaw());
				loc.setPitch(p.getLocation().getPitch());
				session.addSessionData(p.getName(), "redteamspawn", loc);
				p.sendMessage(ChatColor.GRAY + "red teams spawn set, now place a block where blue team should spawn!");
				e.setCancelled(true);
			} else if(!session.containsSessionData(p.getName(), "blueteamspawn")) {
				Location loc = e.getBlock().getLocation();
				loc.setYaw(p.getLocation().getYaw());
				loc.setPitch(p.getLocation().getPitch());
				session.addSessionData(p.getName(), "blueteamspawn", loc);
				p.sendMessage(ChatColor.GRAY + "blue teams spawn set, now place blocks to represent the goal of the red team, type then /save red");
				e.setCancelled(true);
			} else if(!session.isRedGoalFinished(p.getName())) {
				session.addGoalBlock(p.getName(), e.getBlock().getLocation());
				e.getPlayer().sendMessage(ChatColor.GRAY + "registered block for red teams goal at x: " + e.getBlock().getX() + " y: " + e.getBlock().getY() + " z: " + e.getBlock().getZ());
				e.setCancelled(true);
			} else if(!session.isBlueGoalFinished(p.getName())) {
				session.addGoalBlock(p.getName(), e.getBlock().getLocation());
				e.getPlayer().sendMessage(ChatColor.GRAY + "registered block for blue teams goal at x: " + e.getBlock().getX() + " y: " + e.getBlock().getY() + " z: " + e.getBlock().getZ());
				e.setCancelled(true);
			}
		}
	}

}
