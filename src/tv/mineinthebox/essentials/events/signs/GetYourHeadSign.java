package tv.mineinthebox.essentials.events.signs;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import tv.mineinthebox.essentials.enums.PermissionKey;

public class GetYourHeadSign implements Listener {
	
	@EventHandler
	public void head(SignChangeEvent s) {
		if(s.getLine(0).equalsIgnoreCase("[GetYourHead]") && s.getPlayer().hasPermission(PermissionKey.SIGN_GETYOURHEAD.getPermission())) {
			s.setLine(0, ChatColor.GREEN + "[GetYourHead]");
			s.getBlock().getState().update(true);
			s.getPlayer().sendMessage(ChatColor.GOLD + "[GetYourHead] " + ChatColor.GREEN + "You successfully placed a GetYourHead sign!");
		} else {
			if(s.getLine(0).equalsIgnoreCase("[GetYourHead]") && !s.getPlayer().hasPermission(PermissionKey.SIGN_GETYOURHEAD.getPermission())) {
				s.getBlock().breakNaturally();
				s.getPlayer().sendMessage("You are not allowed to place such signs!");
				s.setCancelled(true);		
			}
		}
	}
	
	@EventHandler
	public void signRightClickhead(PlayerInteractEvent s) {
		if(s.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(s.getClickedBlock().getState().getType() == Material.SIGN || s.getClickedBlock().getState().getType() == Material.SIGN_POST || s.getClickedBlock().getState().getType() == Material.WALL_SIGN) {
				//log.info("This is a sign...");
				Sign sign = (Sign) s.getClickedBlock().getState();
				if(sign.getLine(0).contains("[GetYourHead]")) {
					//log.info("Launching dispatch command....");
					ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
					SkullMeta skullmeta = (SkullMeta)skull.getItemMeta();
					skullmeta.setOwner(s.getPlayer().getName());
					skull.setItemMeta(skullmeta);
					skull.setAmount(1);
					if(s.getPlayer().getItemInHand().getType() == Material.AIR) {
						s.getPlayer().getInventory().setItemInHand(skull);
						s.getPlayer().sendMessage(ChatColor.GOLD + "[GetYourHead] " + ChatColor.GREEN + "your head has been added in your hand!");	
					} else {
						s.getPlayer().sendMessage(ChatColor.GOLD + "[GetYourHead] " + ChatColor.RED + "your hands need to be empty!");
						s.setCancelled(true);
					}
				}
			}
		}
	}

}
