package tv.mineinthebox.essentials.events.shops;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.ShopSign;

public class SignAdminShopCreateEvent implements Listener {
	
	private final ShopSign shop = new ShopSign();
	
	@EventHandler
	public void onSignCreate(SignChangeEvent e) {
		if(e.getLine(0).equalsIgnoreCase(Configuration.getShopConfig().getAdminPrefix()) || e.getLine(0).equalsIgnoreCase("[backpack]")) {
			if(e.getPlayer().hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
				
				if(e.getLine(0).equalsIgnoreCase("[backpack]")) {
					e.setLine(0, ChatColor.GOLD + "[backpack]");
				}
				
				int amount = shop.getNumberFromString(e.getLine(1));
				if(!(amount > 64 || amount < 0 || amount == 0)) {
					
					if(ChatColor.stripColor(e.getLine(0)).equalsIgnoreCase("[backpack]")) {
						if(amount != 1) {
							e.getBlock().breakNaturally();
							e.getPlayer().sendMessage(ChatColor.RED + "you can only set 1 as amount for backpacks only, this is against serious problems.");
							return;
						}
					}
					
					if(shop.validateBuyAndSell(e.getLine(2))) {
						if(shop.isValidMaterial(e.getLine(3))) {
							e.setLine(3, shop.getItemFromSign(e.getLine(3).toUpperCase()));
							e.getPlayer().sendMessage(ChatColor.GREEN + "you have successfully created a Admin shop!");
						} else {
							e.getBlock().breakNaturally();
							e.getPlayer().sendMessage(ChatColor.RED + "the material you put on the shop sign is invalid!");
						}
					} else {
						e.getBlock().breakNaturally();
						e.getPlayer().sendMessage(ChatColor.RED + "buy and sell is invalid!");
					}
				} else {
					e.getBlock().breakNaturally();
					e.getPlayer().sendMessage(ChatColor.RED + "you have inserted a invalid amount of items on the sign!");
				}
			} else {
				e.getBlock().breakNaturally();
				e.getPlayer().sendMessage(ChatColor.RED + "you are not allowed to make this kind of signs!");
			}
		}
	}
}
