package tv.mineinthebox.essentials.events.chat;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.events.customEvents.PlayerChatHighLightEvent;
import tv.mineinthebox.essentials.instances.xEssentialsOfflinePlayer;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

public class ChatHighLightEvent implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
	public void OnHighLight(PlayerChatHighLightEvent e) {
		for(String name : e.getCalledPlayersAsStringArray()) {
			if(!e.getMessage().contains(Configuration.getChatConfig().getHashTag()+name)) {
				xEssentials.getPlugin();
				if(xEssentials.getManagers().getPlayerManager().isOnline(name)) {
					xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(name);
					if(xp.isVanished()) {
						e.setMessage(e.getMessage().replaceAll(name, ChatColor.GRAY+"[offline]"+e.getHashTag()+name+e.getSuffix()));
					} else {
						xp.getPlayer().getWorld().playEffect(xp.getPlayer().getLocation(), Effect.MOBSPAWNER_FLAMES, 100);
						xp.getPlayer().playSound(xp.getPlayer().getLocation(), Sound.NOTE_PIANO, 10, 100);
						xp.getPlayer().playSound(xp.getPlayer().getLocation(), Sound.NOTE_BASS_DRUM, 10, 100);
						xp.getPlayer().playSound(xp.getPlayer().getLocation(), Sound.NOTE_SNARE_DRUM, 10, 100);
						e.setMessage(e.getMessage().replaceAll(name, e.getHashTag()+name+e.getSuffix()));	
					}
				} else {
					if(name.startsWith("town-")) {
						e.setMessage(e.getMessage().replaceAll(name, ChatColor.DARK_GREEN+"[Town]"+e.getHashTag()+name+e.getSuffix()));
					} else {
						e.setMessage(e.getMessage().replaceAll(name, ChatColor.GRAY+"[offline]"+e.getHashTag()+name+e.getSuffix()));	
					}
				}
			}
		}
	}

	@EventHandler
	public void onTabComplete(PlayerChatTabCompleteEvent e) {
		e.getTabCompletions().clear();
		for(xEssentialsOfflinePlayer off : xEssentials.getManagers().getPlayerManager().getOfflinePlayers()) {
				if(off.getUser().toUpperCase().startsWith(e.getLastToken().toUpperCase())) {
					e.getTabCompletions().add(off.getUser());
				}
		}
	}
}
