package tv.mineinthebox.essentials.events.players;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.hook.Hooks;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class PlayerJoinMessageEvent implements Listener {
	
	private final xEssentials pl;
	
	public PlayerJoinMessageEvent(xEssentials pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onjoin(PlayerJoinEvent e) {
		if(pl.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			
			xp.setCustomName(xp.getCustomName());
			
			if(Hooks.isWorldGuardEnabled()) {
				if(xp.isVanished()) {
					e.setJoinMessage("");
				} else {
					e.setJoinMessage(pl.getManagers().getWorldGuardManager().sendJoinMessage(e.getPlayer()));	
				}
			} else {
				if(xp.isStaff()) {
					if(xp.isVanished()) {
						e.setJoinMessage("");
					} else {
						if(e.getPlayer().getName().equalsIgnoreCase("xize")) {
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
