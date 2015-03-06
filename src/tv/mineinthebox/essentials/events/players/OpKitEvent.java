package tv.mineinthebox.essentials.events.players;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.OpKit;

public class OpKitEvent implements Listener {
	
	private final xEssentials pl;
	
	public OpKitEvent(xEssentials pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		
		if(e.getCurrentItem() != null) {
			if(e.getInventory().getTitle().equalsIgnoreCase(ChatColor.GOLD + "op kit")) {
				Player p = (Player) e.getWhoClicked();
				
				String enjoymsg = String.format(pl.getConfiguration().getCommandConfig().getPrefix(), "Op kit") + pl.getConfiguration().getCommandConfig().getSuffix() +"enjoy your kit!";
				
				if(e.getCurrentItem().getType() == OpKit.DIAMOND_KIT.getButton().getType()) {
					e.getWhoClicked().getInventory().setHelmet(OpKit.DIAMOND_KIT.getHelmet());
					e.getWhoClicked().getInventory().setChestplate(OpKit.DIAMOND_KIT.getChestPlate());
					e.getWhoClicked().getInventory().setLeggings(OpKit.DIAMOND_KIT.getLeggings());
					e.getWhoClicked().getInventory().setBoots(OpKit.DIAMOND_KIT.getBoots());
					
					e.getWhoClicked().getInventory().addItem(OpKit.DIAMOND_KIT.getSword());
					e.getWhoClicked().getInventory().addItem(OpKit.DIAMOND_KIT.getAxe());
					e.getWhoClicked().getInventory().addItem(OpKit.DIAMOND_KIT.getPickAxe());
					e.getWhoClicked().closeInventory();
					p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 200));
					p.playSound(p.getLocation(), Sound.PORTAL_TRAVEL, 1F, 1F);
					p.sendMessage(enjoymsg);
					e.setCancelled(true);
				} else if(e.getCurrentItem().getType() == OpKit.IRON_KIT.getButton().getType()) {
					e.getWhoClicked().getInventory().setHelmet(OpKit.IRON_KIT.getHelmet());
					e.getWhoClicked().getInventory().setChestplate(OpKit.IRON_KIT.getChestPlate());
					e.getWhoClicked().getInventory().setLeggings(OpKit.IRON_KIT.getLeggings());
					e.getWhoClicked().getInventory().setBoots(OpKit.IRON_KIT.getBoots());
					
					e.getWhoClicked().getInventory().addItem(OpKit.IRON_KIT.getSword());
					e.getWhoClicked().getInventory().addItem(OpKit.IRON_KIT.getAxe());
					e.getWhoClicked().getInventory().addItem(OpKit.IRON_KIT.getPickAxe());
					e.getWhoClicked().closeInventory();
					p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 200));
					p.playSound(p.getLocation(), Sound.PORTAL_TRAVEL, 1F, 1F);
					p.sendMessage(enjoymsg);
					e.setCancelled(true);
				} else if(e.getCurrentItem().getType() == OpKit.STONE_KIT.getButton().getType()) {
					e.getWhoClicked().getInventory().setHelmet(OpKit.STONE_KIT.getHelmet());
					e.getWhoClicked().getInventory().setChestplate(OpKit.STONE_KIT.getChestPlate());
					e.getWhoClicked().getInventory().setLeggings(OpKit.STONE_KIT.getLeggings());
					e.getWhoClicked().getInventory().setBoots(OpKit.STONE_KIT.getBoots());
					
					e.getWhoClicked().getInventory().addItem(OpKit.STONE_KIT.getSword());
					e.getWhoClicked().getInventory().addItem(OpKit.STONE_KIT.getAxe());
					e.getWhoClicked().getInventory().addItem(OpKit.STONE_KIT.getPickAxe());
					e.getWhoClicked().closeInventory();
					p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 200));
					p.playSound(p.getLocation(), Sound.PORTAL_TRAVEL, 1F, 1F);
					p.sendMessage(enjoymsg);
					e.setCancelled(true);
				} else {
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if(e.getInventory().getTitle().equalsIgnoreCase(ChatColor.GOLD + "op kit")) {
			((Player)e.getPlayer()).playSound(e.getPlayer().getLocation(), Sound.CHEST_CLOSE, 1F, 1F);
		}
	}

}
