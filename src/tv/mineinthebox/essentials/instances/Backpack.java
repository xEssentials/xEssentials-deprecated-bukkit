package tv.mineinthebox.essentials.instances;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static tv.mineinthebox.essentials.xEssentials.getManagers;

public class Backpack {
	
	private final File f;
	private final FileConfiguration con;
	
	public Backpack(File f, FileConfiguration con) {
		this.f = f;
		this.con = con;
	}
	
	/**
	 * @author xize
	 * @param returns the unique id where this backpack for is created.
	 * @return String
	 */
	public String getUniqueId() {
		update();
		return con.getString("id");
	}
	
	/**
	 * @author xize
	 * @param returns the size of the backpack
	 * @return int
	 */
	public int getAmountContents() {
		update();
		return getContents().length;
	}
	
	/**
	 * @author xize
	 * @param returns the holder of this backpack.
	 * @return ItemStack
	 */
	public ItemStack getBackPackItem() {
		update();
		ItemStack item = (ItemStack)con.get("backpack-item");
		ItemMeta meta = item.getItemMeta();
		List<String> lores = meta.getLore();
		lores.set(6, "amount: "+ getContents().length);
		meta.setLore(lores);
		item.setItemMeta(meta);
		return item;
	}
	
	/**
	 * @author xize
	 * @param returns the inventory.
	 * @return Inventory
	 */
	public Inventory getInventory() {
		Inventory inv = Bukkit.createInventory(null, 36, getUniqueId());
		inv.setContents(getContents());
		return inv;
	}
	
	/**
	 * @author xize
	 * @param returns safely the contents of the inventory.
	 * @return ItemStack[]
	 */
	@SuppressWarnings("unchecked")
	public ItemStack[] getContents() {
		update();
		ItemStack[] stack = new ItemStack[((List<ItemStack>)con.get("contents")).size()];
		int i = 0;
		for(ItemStack item : ((List<ItemStack>)con.get("contents"))) {
			stack[i] = item;
		}
		return stack;
	}
	
	/**
	 * @author xize
	 * @param stacks - the item stacks
	 * @param sets the new items in the inventory.
	 */
	public void setContents(ItemStack[] stacks) {
		con.set("contents", stacks);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}
	
	public void remove() {
		if(getManagers().getBackPackManager().isBackpack(this)) { 
			getManagers().getBackPackManager().removeBackpack(this);
		}
		f.delete();
	}
	
	public void update() {
		try {
			con.load(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
