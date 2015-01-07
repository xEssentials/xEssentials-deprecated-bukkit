package tv.mineinthebox.essentials.minigames.parkour.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;
import tv.mineinthebox.essentials.minigames.MinigameType;
import tv.mineinthebox.essentials.minigames.parkour.ParkourArena;

public class PlayerTeleportSpawnPointEvent implements Listener {

	@EventHandler
	public void onTeleport(EntityDamageEvent e) {
		if(e.getCause() == DamageCause.FALL) {
			if(e.getEntity() instanceof Player) {
				Player p = (Player) e.getEntity();
				if(p.hasMetadata("gameType") && p.hasMetadata("game")) {
					MinigameType type = (MinigameType) p.getMetadata("gameType").get(0).value();
					if(type == MinigameType.PARKOUR) {
						String name = p.getMetadata("game").get(0).asString();
						ParkourArena arena = (ParkourArena) xEssentials.getManagers().getMinigameManager().getArenaByName(type, name);
						XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(p.getName());
						p.teleport(arena.getLastSpawnPoint(xp));
						p.sendMessage(ChatColor.GRAY + "oh oh, you felt down teleporting to last checkpoint!");
						e.setCancelled(true);
					}
				}
			}
		}
	}

}
