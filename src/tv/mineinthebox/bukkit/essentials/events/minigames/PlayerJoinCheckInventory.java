package tv.mineinthebox.bukkit.essentials.events.minigames;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsPlayer;

public class PlayerJoinCheckInventory implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
		
		if(xp.hasSavedInventory() && !xEssentials.getManagers().getMinigameManager().isPlayerInArea(e.getPlayer())) {
			e.getPlayer().sendMessage(ChatColor.GREEN + "since it seems you have quited before a minigame ended we have backuped your inventory!");
			xp.loadInventory();
		}
	}

}
