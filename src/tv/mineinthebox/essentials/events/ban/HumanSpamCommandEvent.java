package tv.mineinthebox.essentials.events.ban;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;

public class HumanSpamCommandEvent implements Listener {
	
	private HashMap<String, String> cmdmsg1 = new HashMap<String, String>();
	private HashMap<String, String> cmdmsg2 = new HashMap<String, String>();
	
	@EventHandler
	public void slowcmdSpamCheck(PlayerCommandPreprocessEvent e) {
		final Player p = (Player) e.getPlayer();
		if(e.getMessage().startsWith("/") && !e.getPlayer().hasPermission(PermissionKey.IGNORE_HUMAN_SPAM.getPermission())) {
			if(cmdmsg2.containsKey(e.getPlayer().getName())) {
				if(cmdmsg2.get(e.getPlayer().getName()).equalsIgnoreCase(e.getMessage())) {
					e.getPlayer().kickPlayer(Configuration.getBanConfig().getHumanSpamBanMessage());
					cmdmsg2.remove(e.getPlayer().getName());
				} else {
					cmdmsg2.remove(e.getPlayer().getName());
				}
			} else if(cmdmsg1.containsKey(e.getPlayer().getName())) {
				if(cmdmsg1.get(e.getPlayer().getName()).equalsIgnoreCase(e.getMessage())) {
					cmdmsg1.remove(e.getPlayer().getName());
					cmdmsg2.put(e.getPlayer().getName(), e.getMessage());
					e.getPlayer().sendMessage(ChatColor.RED + "Warning if you post one more time the same message you will be kicked!");
				} else {
					cmdmsg1.put(e.getPlayer().getName(), e.getMessage());
				}
			} else {
				cmdmsg1.put(e.getPlayer().getName(), e.getMessage());
			}
			Bukkit.getScheduler().scheduleSyncDelayedTask(xEssentials.getPlugin(), new Runnable() {

				@Override
				public void run() {
					if(cmdmsg1.containsKey(p.getPlayer().getName())) {
						cmdmsg1.remove(p.getPlayer().getName());
					} else if(cmdmsg2.containsKey(p.getPlayer().getName())) {
						cmdmsg2.remove(p.getPlayer().getName());
					}
				}
			}, 300);
		}
	}
}
