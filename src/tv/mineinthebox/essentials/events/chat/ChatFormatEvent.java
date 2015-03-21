package tv.mineinthebox.essentials.events.chat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import tv.mineinthebox.essentials.xEssentials;

@SuppressWarnings("deprecation")
public class ChatFormatEvent implements Listener {
	
	private final xEssentials pl;
	
	public ChatFormatEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(PlayerChatEvent e) {
		if(e.isCancelled()) {
			return;
		}
		
		String prefix = pl.getConfiguration().getChatConfig().getPrefixByPlayer(e.getPlayer());
		String suffix = pl.getConfiguration().getChatConfig().getSuffixByPlayer(e.getPlayer());
		Player p = e.getPlayer();
		e.setFormat(prefix +  p.getName() + ": " + suffix + e.getMessage());
	}

}
