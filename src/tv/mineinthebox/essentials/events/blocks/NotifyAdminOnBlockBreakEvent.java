package tv.mineinthebox.essentials.events.blocks;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.EventTemplate;

public class NotifyAdminOnBlockBreakEvent extends EventTemplate implements Listener {
	
	public NotifyAdminOnBlockBreakEvent(xEssentials pl) {
		super(pl, "Block Notification");
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBreak(BlockBreakEvent e) {
		List<String> blocks = pl.getConfiguration().getBlockConfig().getBlocksFromNotify();
		for(String s : blocks) {
			String[] mats = s.split(":");
			Byte subData = Byte.parseByte(mats[1]);
			Material mat = Material.getMaterial(mats[0]);
			if(e.getBlock().getType() == mat) {
				if(e.getBlock().getState().getData().getData() == subData) {
					for(Player p : pl.getOnlinePlayers()) {
						if(p.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							sendMessage(p, pl.getConfiguration().getBlockConfig().getNotifyOnBreakMessage().replace("%PLAYER%", e.getPlayer().getName()).replace("%BLOCK%", e.getBlock().getType().name()+":"+subData).replace("%LOCATION%", "x: " + e.getBlock().getX() + " y: " + e.getBlock().getY() + " z: " + e.getBlock().getZ() + " world: " + e.getBlock().getWorld().getName()));
						}
					}
					break;
				}
			}
		}
	}

}
