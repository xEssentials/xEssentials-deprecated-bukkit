package tv.mineinthebox.essentials.events.chat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;

@SuppressWarnings("deprecation")
public class PlayerIgnorePlayerChatEvent implements Listener {
	
	private final xEssentials pl;
	
	public PlayerIgnorePlayerChatEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onIgnoreChat(PlayerChatEvent e) {
		for(XPlayer xp : pl.getManagers().getPlayerManager().getPlayers()) {
			if(xp.hasIgnoredPlayers()) {
				if(xp.getIgnoredPlayers().contains(e.getPlayer().getName())) {
					e.getRecipients().remove(xp.getBukkitPlayer());
				}
			}
		}
	}

}
