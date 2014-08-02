package tv.mineinthebox.essentials.events.ban;

import java.sql.Date;
import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

@SuppressWarnings("deprecation")
public class FloodSpamEvent implements Listener {
	
	private HashMap<String, Long> chatTime = new HashMap<String, Long>();
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void playerChatTime(AsyncPlayerChatEvent e) {
		if(chatTime.containsKey(e.getPlayer().getName())) {
			if(System.currentTimeMillis() - chatTime.get(e.getPlayer().getName()) < 300) {
				e.setCancelled(true);
				xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
				Date date = new Date(System.currentTimeMillis());
				date.setDate(date.getDate() + 1);
				xp.setTempbanned(date.getTime(), Configuration.getBanConfig().getFloodSpamBanMessage(), "CONSOLE");
			} else {
				chatTime.put(e.getPlayer().getName(), System.currentTimeMillis());
			}
		} else {
			chatTime.put(e.getPlayer().getName(), System.currentTimeMillis());
		}
	}

	@EventHandler
	public void pQuit(PlayerQuitEvent e) {
		if(chatTime.containsKey(e.getPlayer().getName())) {
			chatTime.remove(e.getPlayer().getName());
		}
	}

}
