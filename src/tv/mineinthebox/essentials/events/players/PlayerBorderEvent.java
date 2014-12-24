package tv.mineinthebox.essentials.events.players;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.events.customevents.PlayerChunkMoveEvent;

public class PlayerBorderEvent implements Listener {
	
	@EventHandler
	public void onBorder(PlayerChunkMoveEvent e) {
		int range = Configuration.getPlayerConfig().getWorldBorderSize();
		
		int xspawn = Configuration.getPlayerConfig().getSpawnLocation().getBlockX();
		int zspawn = Configuration.getPlayerConfig().getSpawnLocation().getBlockZ();
		
		int minx = ~((range+xspawn) - 1);
		int maxx = (range+xspawn);
		
		int minz = ~((range+zspawn) - 1);
		int maxz = (range+zspawn);
		
		int x = e.getPlayer().getLocation().getBlockX();
		int z = e.getPlayer().getLocation().getBlockZ();
		
		if(!(x < maxx && x > minx && z < maxz && z > minz)) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(ChatColor.RED + "you are at the worldborder!, please return.");
		}
	}

}
