package tv.mineinthebox.essentials.instances;

import org.bukkit.inventory.ItemStack;

public class Kit {
	
	private String kitname;
	private ItemStack[] items;
	
	public Kit(String kitname, ItemStack[] items) {
		this.kitname = kitname;
		this.items = items;
	}
	
	/**
	 * @author xize
	 * @param returns the kit name
	 * @return String
	 */
	public String getKitName() {
		return kitname;
	}
	
	/**
	 * @author xize
	 * @param returns the kit items!
	 * @return ItemStack[]
	 */
	public ItemStack[] getKitItems() {
		return items;
	}

}
