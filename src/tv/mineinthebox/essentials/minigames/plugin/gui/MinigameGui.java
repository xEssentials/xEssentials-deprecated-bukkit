package tv.mineinthebox.essentials.minigames.plugin.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import tv.mineinthebox.essentials.minigames.plugin.MinigamePlugin;
import tv.mineinthebox.essentials.minigames.plugin.arena.MinigameArena;
import tv.mineinthebox.essentials.minigames.plugin.arena.MinigamePlayer;

public class MinigameGui implements Runnable {
	
	private final ItemStack button;
	private final MinigamePlugin pl;
	private final Inventory inv;
	
	public MinigameGui(MinigamePlugin pl, String title, String[] description, Material mat) {
		this.pl = pl;
		this.button = new ItemStack(mat);
		ItemMeta meta = this.button.getItemMeta();
		meta.setDisplayName(ChatColor.RED +""+ ChatColor.BOLD + ChatColor.stripColor(title));
		List<String> lores = new ArrayList<String>();
		lores.add("");
		lores.add(ChatColor.GOLD + "Arena: " + ChatColor.GRAY + "%arena%");
		lores.add(ChatColor.GOLD + "Type: " + ChatColor.GRAY + "%type%");
		lores.add(ChatColor.GOLD + "Online: " + ChatColor.GRAY + "%onlinecount%");
		lores.add(ChatColor.GOLD + "Status: " + ChatColor.GRAY + "%status%");
		lores.add("");
		for(String desc : description) {
			lores.add(desc);
		}
		meta.setLore(lores);
		this.button.setItemMeta(meta);
		this.inv = Bukkit.createInventory(null, 9, ChatColor.DARK_PURPLE + "game selector");
		Bukkit.getScheduler().runTaskTimer(pl.getEssentialsPlugin(), this, 3L, 8L);
	}
	
	public MinigameGui(MinigamePlugin pl, String title, String[] description, Material mat, short subdata) {
		this.pl = pl;
		this.button = new ItemStack(mat, subdata);
		ItemMeta meta = this.button.getItemMeta();
		meta.setDisplayName(title);
		List<String> lores = new ArrayList<String>();
		lores.add(ChatColor.GOLD + "Arena: %arena%");
		lores.add(ChatColor.GOLD + "Type:  %type%");
		lores.add(ChatColor.GOLD + "Online: %onlinecount%");
		lores.add(ChatColor.GOLD + "Status: %status%");
		for(String desc : description) {
			lores.add(desc);
		}
		meta.setLore(lores);
		this.button.setItemMeta(meta);
		this.inv = Bukkit.createInventory(null, 9, ChatColor.DARK_PURPLE + "game selector");
		Bukkit.getScheduler().runTaskTimer(pl.getEssentialsPlugin(), this, 3L, 8L);
	}
	
	public ItemStack getButton() {
		return button;
	}
	
	public ItemStack getButtonByArena(MinigameArena arena) {
		String onlinecount = arena.getPlayers().size() + "/" + arena.getMaxPlayers();
		String status = arena.getArenaStatus();
		
		ItemStack clone = button.clone();
		clone.setAmount(arena.getPlayers().size());
		ItemMeta meta = clone.getItemMeta();
		
		List<String> lore = new ArrayList<String>();
		
		for(String a : meta.getLore()) {
			String s = a.replace("%arena%", arena.getName()).replace("%type%", arena.getType().getName()).replace("%onlinecount%", onlinecount).replace("%status%", status);
			lore.add(s);
		}
		meta.setLore(lore);
		clone.setItemMeta(meta);
		return clone;
	}
	
	/**
	 * opens the automaticly self updating server gui
	 * 
	 * @author xize
	 * @param xp - the player
	 */
	public void openGui(MinigamePlayer mp) {
		mp.getBukkitPlayer().openInventory(inv);
	}
	
	private void updateGUI() {
		inv.clear();
		MinigameArena[] arenas = pl.getArenas();
		
		for(int i = 0; i < 8; i++) {
			if(i < arenas.length) {
				MinigameArena arena = arenas[i];
				
				inv.addItem(getButtonByArena(arena));
			} else {
				break;
			}
		}
	}

	@Override
	public void run() {
		updateGUI();
	}
}