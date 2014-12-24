package tv.mineinthebox.essentials.events.players;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Sign;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.hook.Hooks;
import tv.mineinthebox.essentials.hook.LWCHook;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class SignEditEvent implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEditSign(SignChangeEvent e) {
		Sign sign = (Sign)e.getBlock().getState().getData();
		Block other = e.getBlock().getRelative(sign.getFacing().getOppositeFace());
		if(other.getType() == Material.WALL_SIGN || other.getType() == Material.SIGN_POST) {
			org.bukkit.block.Sign signn = (org.bukkit.block.Sign)other.getState();
			XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
			if(xp.isEditSignEnabled()) {
				if(Configuration.getProtectionConfig().isProtectionEnabled()) {
					if(xEssentials.getManagers().getProtectionDBManager().isOwner(e.getPlayer().getName(), other)) {
						signn.setLine(0, e.getLine(0));
						signn.setLine(1, e.getLine(1));
						signn.setLine(2, e.getLine(2));
						signn.setLine(3, e.getLine(3));
						signn.update(true);
						e.getPlayer().sendMessage(ChatColor.GREEN + "successfully edited sign.");
						xEssentials.getManagers().getProtectionDBManager().unregister(e.getPlayer().getName(), e.getBlock());
						e.getBlock().setType(Material.AIR);
						Bukkit.getPluginManager().callEvent(new BlockPlaceEvent(signn.getBlock(), signn, signn.getBlock(), new ItemStack(Material.SIGN, 1), e.getPlayer(), true));
					} else {
						e.getBlock().setType(Material.AIR);
						e.setCancelled(true);
						e.getPlayer().sendMessage(ChatColor.RED + "cannot modify sign, this sign does not belongs to you");
					}
				} else if(Hooks.isLWCEnabled()) {
					if(LWCHook.isOwner(e.getPlayer(), signn.getBlock())) {
						signn.setLine(0, e.getLine(0));
						signn.setLine(1, e.getLine(1));
						signn.setLine(2, e.getLine(2));
						signn.setLine(3, e.getLine(3));
						signn.update(true);
						e.getPlayer().sendMessage(ChatColor.GREEN + "successfully edited sign.");
						LWCHook.removeProtection(e.getBlock());
						e.getBlock().setType(Material.AIR);
						Bukkit.getPluginManager().callEvent(new BlockPlaceEvent(signn.getBlock(), signn, signn.getBlock(), new ItemStack(Material.SIGN, 1), e.getPlayer(), true));
					} else {
						e.getBlock().setType(Material.AIR);
						e.setCancelled(true);
						e.getPlayer().sendMessage(ChatColor.RED + "cannot modify sign, this sign does not belongs to you");
					}
				}
			}
		}

	}
}
