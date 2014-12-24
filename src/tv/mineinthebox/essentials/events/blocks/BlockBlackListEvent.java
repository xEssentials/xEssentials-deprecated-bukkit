package tv.mineinthebox.essentials.events.blocks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import tv.mineinthebox.essentials.Configuration;

@SuppressWarnings("deprecation")
public class BlockBlackListEvent implements Listener {

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBreak(BlockBreakEvent e) {
		for(String s : Configuration.getBlockConfig().getBlockBlackList()) {
			String[] mats = s.split(":");
			Byte subData = Byte.parseByte(mats[1]);
			Material mat = Material.getMaterial(mats[0]);
			if(e.getBlock().getType() == mat) {
				if(e.getBlock().getData() == subData) {
					e.getPlayer().sendMessage(ChatColor.RED + "you are not allowed to break this block because this block is blacklisted!");
					e.setCancelled(true);
					break;
				}
			}
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		for(String s : Configuration.getBlockConfig().getBlockBlackList()) {
			String[] mats = s.split(":");
			Byte subData = Byte.parseByte(mats[1]);
			Material mat = Material.getMaterial(mats[0]);
			if(e.getBlock().getType() == mat) {
				if(e.getBlock().getData() == subData) {
					e.getPlayer().sendMessage(ChatColor.RED + "you are not allowed to place this block because this block is blacklisted!");
					e.setCancelled(true);
					break;
				}
			}
		}
	}
	
}
