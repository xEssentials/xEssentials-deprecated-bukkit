package tv.mineinthebox.essentials.events.players;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class InventoryMenuEvent implements Listener {
	
	private final xEssentials pl;
	
	public InventoryMenuEvent(xEssentials pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(e.getInventory().getTitle().contains(ChatColor.DARK_PURPLE + "Quick menu: ")) {
			if(pl.getManagers().getPlayerManager().isOnline(extractPlayerNameFromTitle(e.getInventory().getTitle()))) {
				XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(extractPlayerNameFromTitle(e.getInventory().getTitle()));
				Player p = (Player) e.getWhoClicked();
				e.setCancelled(true);
				if(isButton(e.getCurrentItem())) {
					if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "activate boom for this player!")) {
						e.getWhoClicked().closeInventory();
						p.performCommand("boom " + xp.getName());
					} else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "activate the magic potato curse!")) {
						e.getWhoClicked().closeInventory();
						p.performCommand("potato " + xp.getName());
					} else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "kick the player")) {
						e.getWhoClicked().closeInventory();
						p.performCommand("kick " + xp.getName());
					} else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "TNT rain, this will rain tnt uopen them!")) {
						e.getWhoClicked().closeInventory();
						p.performCommand("nuke " + xp.getName());
					} else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "fake TNT rain, this will rain tnt uopen them!")) {
						e.getWhoClicked().closeInventory();
						p.performCommand("fakenuke " + xp.getName());
					} else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "ban the player for playing on this server!")) {
						e.getWhoClicked().closeInventory();
						p.performCommand("ban " + xp.getName());
					} else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "OpKit!")) {
						e.getWhoClicked().closeInventory();
						p.performCommand("opkit");
					}
				}
			} else {
				XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(extractPlayerNameFromTitle(e.getInventory().getTitle()));
				Player p = (Player) e.getWhoClicked();
				if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "ban the player for playing on this server!")) {
					e.getWhoClicked().closeInventory();
					p.performCommand("ban " + off.getName());
				}
			}
		}
	}

	private String extractPlayerNameFromTitle(String title) {
		String[] data = title.split(":");
		return data[1].substring(1, data[1].length());
	}

	private boolean isButton(ItemStack item) {
		try {
			if(item.getType() != Material.AIR) {
				return true;
			}
		} catch(NullPointerException e) {
			return false;
		}
		return false;
	}

}
