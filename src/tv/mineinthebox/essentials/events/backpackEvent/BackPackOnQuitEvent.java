package tv.mineinthebox.essentials.events.backpackEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.BackPack;

public class BackPackOnQuitEvent implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(xEssentials.getManagers().getBackPackManager().users.containsKey(e.getPlayer().getName())) {
			BackPack backpack;
			try {
				backpack = new BackPack(xEssentials.getManagers().getBackPackManager().users.get(e.getPlayer().getName()), e.getPlayer());
				backpack.saveBackPack(e.getPlayer().getInventory());
				xEssentials.getManagers().getBackPackManager().users.remove(e.getPlayer().getName());
			} catch (Exception e1) {}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerKickEvent e) {
		if(xEssentials.getManagers().getBackPackManager().users.containsKey(e.getPlayer().getName())) {
			BackPack backpack;
			try {
				backpack = new BackPack(xEssentials.getManagers().getBackPackManager().users.get(e.getPlayer().getName()), e.getPlayer());
				backpack.saveBackPack(e.getPlayer().getInventory());
				xEssentials.getManagers().getBackPackManager().users.remove(e.getPlayer().getName());
			} catch (Exception e1) {}
		}
	}
}
