package tv.mineinthebox.essentials.events.customEvents;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.BookMeta;

public class CallPlayerOpenBookEvent implements Listener {
	
	@EventHandler
	public void onCallPlayerOpenBookEvent(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getItem() != null) {
				if(e.getItem().getType() == Material.WRITTEN_BOOK) {
					BookMeta meta = (BookMeta)e.getItem().getItemMeta();
					Bukkit.getPluginManager().callEvent(new PlayerOpenBookEvent(e.getPlayer(), e.getItem(), meta));
				}
			}
		}
	}

}
