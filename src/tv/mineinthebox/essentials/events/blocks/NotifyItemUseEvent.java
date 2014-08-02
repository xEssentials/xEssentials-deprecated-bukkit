package tv.mineinthebox.essentials.events.blocks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.PermissionKey;

public class NotifyItemUseEvent implements Listener {

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onItemUse(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			for(String s : Configuration.getBlockConfig().getConsumedItemsFromNotify()) {
				String[] data = s.split(":");
				Material mat = Material.getMaterial(data[0]);
				Short durabillity = Short.parseShort(data[1]);
				if(e.getItem() != null) {
					if(e.getItem().getType() == mat) {
						if(e.getItem().getDurability() == durabillity) {
							for(Player p : Bukkit.getOnlinePlayers()) {
								if(p.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
									p.sendMessage(Configuration.getBlockConfig().getNotifyOnConsumeMessage().replace("%PLAYER%", e.getPlayer().getName()).replace("%ITEM%", mat.name()).replace("%LOCATION%", "x: " + e.getClickedBlock().getX() + " y: " + e.getClickedBlock().getY() + " z: " + e.getClickedBlock().getZ() + " world: " + e.getClickedBlock().getWorld().getName()));
								}
							}
							break;
						}
					}
				}
			}
		}
	}

}
