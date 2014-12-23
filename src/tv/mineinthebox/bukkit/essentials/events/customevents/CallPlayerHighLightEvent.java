package tv.mineinthebox.bukkit.essentials.events.customevents;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import tv.mineinthebox.bukkit.essentials.xEssentials;

@SuppressWarnings("deprecation")
public class CallPlayerHighLightEvent implements Listener {

	@EventHandler
	public void onChatHighLightEvent(PlayerChatEvent e) {
		List<String> playerList = new ArrayList<String>();
		String[] args = e.getMessage().split(" ");
		for(int i = 0; i < args.length; i++) {
			String name = args[i].replace("?", "").replace(",", "").replace(":", "").replace("=", "").replace("!", "").replace(".", "");
			if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(name)) {
				playerList.add(name);
			}
		}
		String[] PlayerNames = playerList.toArray(new String[playerList.size()]);
		if(PlayerNames.length > 0) {
			Bukkit.getPluginManager().callEvent(new PlayerChatHighLightEvent(e, PlayerNames));
		}
	}

}
