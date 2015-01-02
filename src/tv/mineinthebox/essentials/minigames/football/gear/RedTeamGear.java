package tv.mineinthebox.essentials.minigames.football.gear;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import tv.mineinthebox.essentials.minigames.football.TeamType;

public class RedTeamGear implements Gear {

	private final ItemStack red_team_helmet = new ItemStack(Material.LEATHER_HELMET) {{
		ItemMeta meta = getItemMeta();
		meta.setDisplayName(ChatColor.RED + "RED TEAM");
		meta.setLore(Arrays.asList(new String[] {
				ChatColor.LIGHT_PURPLE + "RED TEAM's football helmet!",
				"",
				ChatColor.GRAY + "make sure you score to the correct team!"
		}));
		LeatherArmorMeta leather = (LeatherArmorMeta) meta;
		leather.setColor(DyeColor.RED.getColor());
		setItemMeta(meta);
	}};
	
	private final ItemStack red_team_chestplate = new ItemStack(Material.LEATHER_CHESTPLATE) {{
		ItemMeta meta = getItemMeta();
		meta.setDisplayName(ChatColor.RED + "RED TEAM");
		meta.setLore(Arrays.asList(new String[] {
				ChatColor.LIGHT_PURPLE + "RED TEAM's football chestplate!",
				"",
				ChatColor.GRAY + "make sure you score to the correct team!"
		}));
		LeatherArmorMeta leather = (LeatherArmorMeta) meta;
		leather.setColor(DyeColor.RED.getColor());
		setItemMeta(meta);
	}};
	
	private final ItemStack red_team_leggings = new ItemStack(Material.LEATHER_LEGGINGS) {{
		ItemMeta meta = getItemMeta();
		meta.setDisplayName(ChatColor.RED + "RED TEAM");
		meta.setLore(Arrays.asList(new String[] {
				ChatColor.LIGHT_PURPLE + "RED TEAM's football leggings!",
				"",
				ChatColor.GRAY + "make sure you score to the correct team!"
		}));
		LeatherArmorMeta leather = (LeatherArmorMeta) meta;
		leather.setColor(DyeColor.RED.getColor());
		setItemMeta(meta);
	}};
	
	private final ItemStack red_team_boots = new ItemStack(Material.LEATHER_LEGGINGS) {{
		ItemMeta meta = getItemMeta();
		meta.setDisplayName(ChatColor.RED + "RED TEAM");
		meta.setLore(Arrays.asList(new String[] {
				ChatColor.LIGHT_PURPLE + "RED TEAM's football boots!",
				"",
				ChatColor.GRAY + "make sure you score to the correct team!"
		}));
		LeatherArmorMeta leather = (LeatherArmorMeta) meta;
		leather.setColor(DyeColor.RED.getColor());
		setItemMeta(meta);
	}};
	
	@Override
	public ItemStack getHelmet() {
		return red_team_helmet;
	}

	@Override
	public ItemStack getChestPlate() {
		return red_team_chestplate;
	}

	@Override
	public ItemStack getLeggings() {
		return red_team_leggings;
	}

	@Override
	public ItemStack getBoots() {
		return red_team_boots;
	}

	@Override
	public TeamType getType() {
		return TeamType.RED_TEAM;
	}

}
