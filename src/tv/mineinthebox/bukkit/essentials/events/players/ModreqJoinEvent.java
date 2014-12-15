package tv.mineinthebox.bukkit.essentials.events.players;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;
import tv.mineinthebox.bukkit.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.bukkit.essentials.interfaces.XPlayer;

public class ModreqJoinEvent implements Listener {
	
	@EventHandler
	public void modreqOnJoin(PlayerJoinEvent e) {
		if(xEssentials.getManagers().getPlayerManager().isOnline(e.getPlayer().getName())) {
			XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.hasModreqDoneMessage()) {
				String[] messages = xp.getModreqDoneMessage().split(",");
				e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',messages[0]));
				e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',messages[1]));
				e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',messages[2]));
				e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',messages[3]));
				xp.removeGetModregDoneMessage();
			}
		}
	}
	
	@EventHandler
	public void modreqForAdmins(PlayerJoinEvent e) {
		if(e.getPlayer().hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
			Boolean areModreqsOpen = false;
			int i = 0;
			for(XOfflinePlayer off : xEssentials.getManagers().getPlayerManager().getOfflinePlayers()) {
				if(off.hasModreqsOpen()) {
					areModreqsOpen = true;
					i = (i+off.getModreqs().length);
				}
			}
			if(areModreqsOpen) {
				e.getPlayer().sendMessage(ChatColor.GREEN + "[Modreq]" + ChatColor.GRAY + "there are " + ChatColor.RED + i + ChatColor.GRAY + " modreq(s) open!");
			} else {
				e.getPlayer().sendMessage(ChatColor.GREEN + "[Modreq]" + ChatColor.GRAY + "no open modreqs found!");
			}
		}
	}

}
