package tv.mineinthebox.bukkit.essentials.events.players;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.hook.Hooks;
import tv.mineinthebox.bukkit.essentials.hook.WorldGuardHook;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsPlayer;

public class PlayerJoinMessageEvent implements Listener {

	@EventHandler
	public void onjoin(PlayerJoinEvent e) {
		if(xEssentials.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			
			xp.setCustomName(xp.getCustomName());
			
			if(Hooks.isWorldGuardEnabled()) {
				if(xp.isVanished()) {
					e.setJoinMessage("");
				} else {
					e.setJoinMessage(WorldGuardHook.sendJoinMessage(e.getPlayer()));	
				}
			} else {
				if(xp.isStaff()) {
					if(xp.isVanished()) {
						e.setJoinMessage("");
					} else {
						if(e.getPlayer().getName().equalsIgnoreCase("Xeph0re")) {
							e.setJoinMessage(ChatColor.GRAY + "Developer of xEssentials " + ChatColor.GREEN + e.getPlayer().getDisplayName() + ChatColor.GRAY + " has joined the game!");
						} else {
							e.setJoinMessage(ChatColor.GRAY + "Staff member " + ChatColor.GREEN + e.getPlayer().getDisplayName() + ChatColor.GRAY + " has joined the game!");	
						}	
					}
				} else {
					e.setJoinMessage(ChatColor.GREEN + "player " + ChatColor.GRAY + e.getPlayer().getDisplayName() + ChatColor.GREEN + " has joined the game!");
				}

			}
		}
	}

}
