package tv.mineinthebox.essentials.events.backpack;

import static tv.mineinthebox.essentials.xEssentials.getManagers;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.Backpack;

public class OpenBackPackEvent implements Listener {
	
	@EventHandler
	public void interact(PlayerInteractEvent e) {
		if(e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			if(e.getItem() != null) {
				if(getManagers().getBackPackManager().isBackpack(e.getItem())) {
					Backpack pack = getManagers().getBackPackManager().getBackpackByItem(e.getItem());
					e.getPlayer().sendMessage(ChatColor.GREEN + "opening backpack");
					e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.CHEST_OPEN, 1F, 1F);
					e.getPlayer().openInventory(pack.getInventory());
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onMove(InventoryClickEvent e) {
		if(e.getCurrentItem() != null) {
			if(xEssentials.getManagers().getBackPackManager().getBackpackById(e.getInventory().getTitle()) != null) {
				if(xEssentials.getManagers().getBackPackManager().isBackpack(e.getCurrentItem())) {
					((Player)e.getWhoClicked()).sendMessage(ChatColor.RED + "you cannot move backpacks");
					e.setCancelled(true);	
				}
			}
		}
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Backpack pack = getManagers().getBackPackManager().getBackpackById(e.getInventory().getTitle());
		if(pack instanceof Backpack) {
			pack.setContents(e.getInventory().getContents());
			e.getPlayer().setItemInHand(pack.getBackPackItem());
			((Player)e.getPlayer()).playSound(e.getPlayer().getLocation(), Sound.CHEST_CLOSE, 1F, 1F);
			((Player)e.getPlayer()).sendMessage(ChatColor.GREEN + "closing backpack.");
		}
	}

}
