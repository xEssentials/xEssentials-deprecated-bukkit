package tv.mineinthebox.essentials.events.chat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

@SuppressWarnings("deprecation")
public class DrunkChatEvent implements Listener {
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onDrunk(PlayerChatEvent e) {
		if(e.isCancelled()) {
			return;
		}
		
		xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
		if(xp.isDrunk()) {
			String derp = xp.getDrunkMessageFrom(e.getMessage(), false);
			e.setMessage(derp);
		}
	}

}
