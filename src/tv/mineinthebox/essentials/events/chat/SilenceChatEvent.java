package tv.mineinthebox.essentials.events.chat;

import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.EventTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

@SuppressWarnings("deprecation")
public class SilenceChatEvent extends EventTemplate implements Listener {


	public SilenceChatEvent(xEssentials pl) {
		super(pl, "Sillence");
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onChat(PlayerChatEvent e) {
		if(pl.getConfiguration().isChatSillenced()) {
			sendMessage(e.getPlayer(), ChatColor.GREEN + "all chat activity has been halted!, please wait a few minuts.");
			e.setCancelled(true);
		} else {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isSilenced()) {
				sendMessage(e.getPlayer(), ChatColor.GREEN + "you have silenced your chat, so you cannot chat unless you unmute your self with /silence");
				e.setCancelled(true);
				return;
			}

			//credits to feildmaster his silence source

			Iterator<Player> it = e.getRecipients().iterator();

			for(Player p = (it.hasNext() ? it.next() : null); it.hasNext(); p=it.next()) {
				XPlayer xp2 = pl.getManagers().getPlayerManager().getPlayer(p.getName());
				if(xp2.isSilenced()) {
					it.remove();
				}
			}
		}
	}

}
