package tv.mineinthebox.essentials.events.blocks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import tv.mineinthebox.essentials.Configuration;

@SuppressWarnings("deprecation")
public class ItemBlackListEvent implements Listener {

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onUse(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			for(String s : Configuration.getBlockConfig().getItemBlackList()) {
				String[] mats = s.split(":");
				Byte subData = Byte.parseByte(mats[1]);
				Material mat = Material.getMaterial(mats[0]);
				if(e.getItem().getType() == mat) {
					if(e.getClickedBlock().getData() == subData) {
						e.getPlayer().sendMessage(ChatColor.RED + "you are not allowed to break this block because this block is blacklisted!");
						e.setCancelled(true);
						break;
					}
				}
			}
		}
	}

}
