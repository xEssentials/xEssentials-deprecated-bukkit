package tv.mineinthebox.essentials.events.chat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import tv.mineinthebox.essentials.events.customevents.PlayerChatSmilleyEvent;
import tv.mineinthebox.essentials.helpers.EffectType;

public class ChatSmilleyParticleEvent implements Listener {
	
	@EventHandler
	public void onChatParticle(PlayerChatSmilleyEvent e) {
		if(e.getMessage().contains(":@")) {
			EffectType.playEffect(e.getPlayer().getWorld(), EffectType.VILLAGER_ANGRY, e.getPlayer().getLocation().add(0, 3, 0), 2, 0, 2, 1, 3);
		} else if(e.getMessage().contains("<3")) {
			EffectType.playEffect(e.getPlayer().getWorld(), EffectType.HEART, e.getPlayer().getLocation().add(0, 3, 0), 2, 0, 2, 1, 3);
		}
	}

}
