package tv.mineinthebox.essentials.minigames.football.gear;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import tv.mineinthebox.essentials.minigames.football.TeamType;

public class BlueTeamGear implements Gear {

	private final ItemStack blue_team_helmet = new ItemStack(Material.LEATHER_HELMET) {{
		ItemMeta meta = getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "BLUE TEAM");
		meta.setLore(Arrays.asList(new String[] {
				ChatColor.LIGHT_PURPLE + "BLUE TEAM's football helmet!",
				"",
				ChatColor.GRAY + "make sure you score to the correct team!"
		}));
		LeatherArmorMeta leather = (LeatherArmorMeta) meta;
		leather.setColor(DyeColor.BLUE.getColor());
		setItemMeta(meta);
	}};
	
	private final ItemStack blue_team_chestplate = new ItemStack(Material.LEATHER_CHESTPLATE) {{
		ItemMeta meta = getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "BLUE TEAM");
		meta.setLore(Arrays.asList(new String[] {
				ChatColor.LIGHT_PURPLE + "BLUE TEAM's football chestplate!",
				"",
				ChatColor.GRAY + "make sure you score to the correct team!"
		}));
		LeatherArmorMeta leather = (LeatherArmorMeta) meta;
		leather.setColor(DyeColor.BLUE.getColor());
		setItemMeta(meta);
	}};
	
	private final ItemStack blue_team_leggings = new ItemStack(Material.LEATHER_LEGGINGS) {{
		ItemMeta meta = getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "BLUE TEAM");
		meta.setLore(Arrays.asList(new String[] {
				ChatColor.LIGHT_PURPLE + "BLUE TEAM's football leggings!",
				"",
				ChatColor.GRAY + "make sure you score to the correct team!"
		}));
		LeatherArmorMeta leather = (LeatherArmorMeta) meta;
		leather.setColor(DyeColor.BLUE.getColor());
		setItemMeta(meta);
	}};
	
	private final ItemStack blue_team_boots = new ItemStack(Material.LEATHER_LEGGINGS) {{
		ItemMeta meta = getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "BLUE TEAM");
		meta.setLore(Arrays.asList(new String[] {
				ChatColor.LIGHT_PURPLE + "BLUE TEAM's football boots!",
				"",
				ChatColor.GRAY + "make sure you score to the correct team!"
		}));
		LeatherArmorMeta leather = (LeatherArmorMeta) meta;
		leather.setColor(DyeColor.BLUE.getColor());
		setItemMeta(meta);
	}};
	
	@Override
	public ItemStack getHelmet() {
		return blue_team_helmet;
	}

	@Override
	public ItemStack getChestPlate() {
		return blue_team_chestplate;
	}

	@Override
	public ItemStack getLeggings() {
		return blue_team_leggings;
	}

	@Override
	public ItemStack getBoots() {
		return blue_team_boots;
	}

	@Override
	public TeamType getType() {
		return TeamType.BLUE_TEAM;
	}

}
