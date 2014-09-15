package tv.mineinthebox.bukkit.essentials.events.shops;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;
import tv.mineinthebox.bukkit.essentials.instances.ShopSign;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsPlayer;

public class SignNormalShopCreateEvent implements Listener {

	private final ShopSign shop = new ShopSign();
	
	@EventHandler
	public void onSignCreate(SignChangeEvent e) {
		if(e.getLine(0).equalsIgnoreCase("[shop]")) {
			if(e.getPlayer().hasPermission(PermissionKey.SIGN_SHOP.getPermission())) {
				int amount = shop.getNumberFromString(e.getLine(1));
				if(!(amount > 64 || amount == 0)) {
					if(shop.validateBuyAndSell(e.getLine(2))) {
						if(shop.isValidMaterial(e.getLine(3))) {
							if(shop.isAttachedOnChest(e.getBlock())) {
								e.setLine(0, e.getPlayer().getName());
								e.setLine(3, shop.getItemFromSign(e.getLine(3).toUpperCase()));
								e.getPlayer().sendMessage(ChatColor.GREEN + "you successfully created a shop sign!");
								xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
								xp.addShopSign(e.getBlock().getLocation());
							} else {
								e.getBlock().breakNaturally();
								e.getPlayer().sendMessage(ChatColor.RED + "there whas no chest found at the location of the sign!");
							}
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
