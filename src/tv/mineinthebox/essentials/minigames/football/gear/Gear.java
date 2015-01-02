package tv.mineinthebox.essentials.minigames.football.gear;

import org.bukkit.inventory.ItemStack;

import tv.mineinthebox.essentials.minigames.football.TeamType;

public interface Gear {
	
	public ItemStack getHelmet();
	
	public ItemStack getChestPlate();
	
	public ItemStack getLeggings();
	
	public ItemStack getBoots();
	
	public TeamType getType();

}
