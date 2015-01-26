package tv.mineinthebox.essentials.managers;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.Backpack;

public class BackPackManager {

	private final Set<Backpack> backpacks = new HashSet<Backpack>();
	
	private final xEssentials pl;

	public BackPackManager(xEssentials pl) {
		this.pl = pl;
	}
	
	public boolean isBackpack(Backpack pack) {
		return backpacks.contains(pack);
	}

	public boolean isBackpack(ItemStack stack) {
		if(stack.hasItemMeta()) {
			if(stack.getItemMeta().hasLore()) {
				if(stack.getItemMeta().getLore().size() >= 5) {
					if(getBackpackById(stack.getItemMeta().getLore().get(5).replaceAll(ChatColor.COLOR_CHAR+"", "")) != null) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void addBackpack(Backpack pack) {
		backpacks.add(pack);
	}

	public void removeBackpack(Backpack pack) {
		backpacks.remove(pack);
	}

	public Backpack getBackpackById(String id) {
		for(Backpack pack : backpacks) {
			if(id.equals(pack.getUniqueId())) {
				return pack;
			}
		}
		return null;
	}

	public Backpack getBackpackByItem(ItemStack item) {
		if(item.hasItemMeta()) {
			if(item.getItemMeta().getLore().size() >= 5) {
				if(getBackpackById(item.getItemMeta().getLore().get(5).replaceAll(ChatColor.COLOR_CHAR+"", "")) != null) {
					return getBackpackById(item.getItemMeta().getLore().get(5).replaceAll(ChatColor.COLOR_CHAR+"", ""));
				}
			}
		}
		return null;
	}

	public Backpack[] getBackpacks() {
		Backpack[] packs = new Backpack[backpacks.size()];
		int i = 0;
		for(Backpack pack : backpacks) {
			packs[i] = pack;
			i++;
		}
		return packs;
	}

	public void loadBackpacks() {
		try {
			File dir = new File(pl.getDataFolder() + File.separator + "backpacks");
			if(dir.isDirectory()) {
				File[] files = dir.listFiles();
				for(File f : files) {
					FileConfiguration con = YamlConfiguration.loadConfiguration(f);
					Backpack pack = new Backpack(f, con, pl);
					backpacks.add(pack);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Backpack createBackpack(Material mat, short sub) {
		ItemStack stack = new ItemStack(mat, 1, sub);
		ItemMeta meta = stack.getItemMeta();
		UUID id = UUID.randomUUID();
		meta.setDisplayName(ChatColor.GOLD + "Backpack");
		meta.setLore(Arrays.asList(new String[] {
				ChatColor.GREEN + "this is your own backpack!",
				ChatColor.GREEN + "enjoy, it has atleast 36 slots",
				"",
				ChatColor.GREEN + "note if you die you can loose your backpack!",
				"",
				convertToInvisibleString(id.toString()),
		"amount: 0"}));
		stack.setItemMeta(meta);
		File f = new File(pl.getDataFolder() + File.separator + "backpacks" + File.separator + id.toString().replaceAll("-", "")+".yml");
		FileConfiguration con = YamlConfiguration.loadConfiguration(f);
		con.set("id", id.toString().replaceAll("-", ""));
		con.set("backpack-item", stack);
		con.set("contents", new ItemStack[0]);
		try {
			con.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Backpack pack = new Backpack(f, con, pl);
		addBackpack(pack);
		return pack;
	}

	private String convertToInvisibleString(String s) {
		String stripped = s.replaceAll("-", "");
		String invis = "";
		for(char c : stripped.toCharArray()) {
			invis += ChatColor.COLOR_CHAR +""+ c;
		}
		return invis;
	}

}
