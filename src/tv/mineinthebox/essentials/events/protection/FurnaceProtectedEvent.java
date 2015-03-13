package tv.mineinthebox.essentials.events.protection;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.ProtectedBlock;
import tv.mineinthebox.essentials.interfaces.EventTemplate;

public class FurnaceProtectedEvent extends EventTemplate implements Listener {
	
	public FurnaceProtectedEvent(xEssentials pl) {
		super(pl, "Protection");
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlace(BlockPlaceEvent e) {
		if(e.getBlock().getType() == Material.FURNACE) {
			ProtectedBlock pblock = new ProtectedBlock(pl, e.getBlock());
			if(pblock.addProtection(e.getPlayer().getUniqueId())) {
				sendMessage(e.getPlayer(), ChatColor.GREEN + "registered private furnace");
			} else {
				sendMessage(e.getPlayer(), pl.getConfiguration().getProtectionConfig().getDisallowMessage().replace("%BLOCK%", e.getBlock().getType().name()));
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onBreak(BlockBreakEvent e) {
		if(e.isCancelled()) {
			return;
		}
		if(e.getBlock().getType() == Material.FURNACE) {
			ProtectedBlock pblock = new ProtectedBlock(pl, e.getBlock());
			if(pblock.isProtected()) {
				if(pblock.isMember(e.getPlayer().getUniqueId()) || e.getPlayer().hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
					pblock.removeAll();
					sendMessage(e.getPlayer(), ChatColor.GREEN + "unregistered that furnace.");
				} else {
					sendMessage(e.getPlayer(), pl.getConfiguration().getProtectionConfig().getDisallowMessage().replace("%BLOCK%", e.getBlock().getType().name()));
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.FURNACE) {
				ProtectedBlock pblock = new ProtectedBlock(pl, e.getClickedBlock());
				if(pblock.isProtected()) {
					if(pblock.isMember(e.getPlayer().getUniqueId()) || e.getPlayer().hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
						sendMessage(e.getPlayer(), ChatColor.GREEN + "opening privated furnace of " + (pblock.isMember(e.getPlayer().getUniqueId()) ? "you" : Arrays.asList(pblock.getMembers().toArray())));
					} else {
						sendMessage(e.getPlayer(), pl.getConfiguration().getProtectionConfig().getDisallowMessage().replace("%BLOCK%", e.getClickedBlock().getType().name()));
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	public void onPiston(BlockPistonExtendEvent e) {
		for(Block block : e.getBlocks()) {
			if(block.getType() == Material.FURNACE) {
				ProtectedBlock pblock = new ProtectedBlock(pl, block);
				if(pblock.isProtected()) {
					e.setCancelled(true);
					break;
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
	public void onExplosion(EntityExplodeEvent e) {
		for(Block block : e.blockList()) {
			if(block.getType() == Material.FURNACE) {
				ProtectedBlock pblock = new ProtectedBlock(pl, block);
				if(pblock.isProtected()) {
					e.setCancelled(true);
				}
			}
		}
	}

}
