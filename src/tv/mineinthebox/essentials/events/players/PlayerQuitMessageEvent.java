package tv.mineinthebox.essentials.events.players;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.hook.Hooks;
import tv.mineinthebox.essentials.hook.WorldGuardHook;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class PlayerQuitMessageEvent implements Listener {

	@EventHandler
	public void onjoin(PlayerQuitEvent e) {
		if(xEssentials.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(Hooks.isWorldGuardEnabled()) {
				if(xp.isVanished()) {
					e.setQuitMessage("");
				} else {
					e.setQuitMessage(WorldGuardHook.sendQuitMessage(e.getPlayer()));	
				}
			} else {
				if(xp.isStaff()) {
					if(xp.isVanished()) {
						e.setQuitMessage("");
					} else {
						if(e.getPlayer().getName().equalsIgnoreCase("Xeph0re")) {
							e.setQuitMessage(ChatColor.GRAY + "Developer of xEssentials " + ChatColor.GREEN + e.getPlayer().getDisplayName() + ChatColor.GRAY + " has left the game!");
						} else {
							e.setQuitMessage(ChatColor.GRAY + "Staff member " + ChatColor.GREEN + e.getPlayer().getDisplayName() + ChatColor.GRAY + " has left the game!");	
						}	
					}
				} else {
					e.setQuitMessage(ChatColor.GREEN + "player " + ChatColor.GRAY + e.getPlayer().getDisplayName() + ChatColor.GREEN + " has left the game!");
				}

			}
		}
	}

}
