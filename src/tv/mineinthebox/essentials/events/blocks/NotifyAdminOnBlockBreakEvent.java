package tv.mineinthebox.essentials.events.blocks;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;

public class NotifyAdminOnBlockBreakEvent implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBreak(BlockBreakEvent e) {
		List<String> blocks = Configuration.getBlockConfig().getBlocksFromNotify();
		for(String s : blocks) {
			String[] mats = s.split(":");
			Byte subData = Byte.parseByte(mats[1]);
			Material mat = Material.getMaterial(mats[0]);
			if(e.getBlock().getType() == mat) {
				if(e.getBlock().getState().getData().getData() == subData) {
					for(Player p : xEssentials.getOnlinePlayers()) {
						if(p.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							p.sendMessage(Configuration.getBlockConfig().getNotifyOnBreakMessage().replace("%PLAYER%", e.getPlayer().getName()).replace("%BLOCK%", e.getBlock().getType().name()+":"+subData).replace("%LOCATION%", "x: " + e.getBlock().getX() + " y: " + e.getBlock().getY() + " z: " + e.getBlock().getZ() + " world: " + e.getBlock().getWorld().getName()));
						}
					}
					break;
				}
			}
		}
	}

}
