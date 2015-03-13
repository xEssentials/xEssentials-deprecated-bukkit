package tv.mineinthebox.essentials.events.backpack;

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
import tv.mineinthebox.essentials.interfaces.EventTemplate;

public class OpenBackPackEvent extends EventTemplate implements Listener {
	
	private final xEssentials pl;
	
	public OpenBackPackEvent(xEssentials pl) {
		super(pl, "Backpack");
		this.pl = pl;
	}
	
	@EventHandler
	public void interact(PlayerInteractEvent e) {
		if(e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			if(e.getItem() != null) {
				if(pl.getManagers().getBackPackManager().isBackpack(e.getItem())) {
					Backpack pack = pl.getManagers().getBackPackManager().getBackpackByItem(e.getItem());
					sendMessage(e.getPlayer(), ChatColor.GREEN + "opening backpack");
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
			if(pl.getManagers().getBackPackManager().getBackpackById(e.getInventory().getTitle()) != null) {
				if(pl.getManagers().getBackPackManager().isBackpack(e.getCurrentItem())) {
					sendMessage((Player)e.getWhoClicked(), ChatColor.RED + "you cannot move backpacks");
					e.setCancelled(true);	
				}
			}
		}
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Backpack pack = pl.getManagers().getBackPackManager().getBackpackById(e.getInventory().getTitle());
		if(pack instanceof Backpack) {
			pack.setContents(e.getInventory().getContents());
			e.getPlayer().setItemInHand(pack.getBackPackItem());
			((Player)e.getPlayer()).playSound(e.getPlayer().getLocation(), Sound.CHEST_CLOSE, 1F, 1F);
			sendMessage((Player)e.getPlayer(), ChatColor.GREEN + "closing backpack.");
		}
	}

}
