package tv.mineinthebox.bukkit.essentials.events.shops;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;
import tv.mineinthebox.bukkit.essentials.instances.ShopSign;

public class SignShopBreakEvent implements Listener {
	
	private final ShopSign shop = new ShopSign();
	
	@EventHandler
	public void onbreak(BlockBreakEvent e) {
		if(e.getBlock().getState() instanceof Sign) {
			Sign sign = (Sign) e.getBlock().getState();
			if(shop.isStoredShopSign(e.getBlock().getLocation())) {
				if(!shop.getCompatUserName(e.getBlock().getLocation()).equalsIgnoreCase(e.getPlayer().getName()) || !e.getPlayer().hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
					e.setCancelled(true);
				}
			} else if(sign.getLine(0).equalsIgnoreCase(Configuration.getShopConfig().getAdminPrefix())) {
				if(!e.getPlayer().hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
					e.setCancelled(true);
				}
			}
		} else {
			if(shop.hasSignAttached(e.getBlock())) {
				Sign sign = shop.getSignFromAttachedBlock(e.getBlock());
				if(shop.isStoredShopSign(sign.getBlock().getLocation())) {
					if(!shop.getCompatUserName(sign.getLocation()).equalsIgnoreCase(e.getPlayer().getName()) || !e.getPlayer().hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
						e.setCancelled(true);
					}
				} else if(sign.getLine(0).equalsIgnoreCase(Configuration.getShopConfig().getAdminPrefix())) {
					if(!e.getPlayer().hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
						e.setCancelled(true);
					}
				}
			}
		}
	}

}
