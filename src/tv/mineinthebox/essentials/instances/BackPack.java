package tv.mineinthebox.essentials.instances;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import tv.mineinthebox.essentials.xEssentials;

public class BackPack extends ItemStack {

	private final File f;
	private final FileConfiguration con;
	
	private int size = 0;
	
	private static Inventory inv;

	/**
	 * @author xize
	 * @param this constructor allows you to create your own BackPack instance like a setter.
	 * @param mat - Material
	 * @param dura - subvalue id, can be 0 to.
	 * @param amount - recommend is 1 if you want that backpacks don't share the same id, else it shares the same id.
	 */
	public BackPack(Material mat, Short dura, int amount) {
		super(new ItemStack(mat, amount, dura));
		UUID uuid = UUID.nameUUIDFromBytes((System.currentTimeMillis()+":"+super.toString()).getBytes());
		ItemMeta meta = super.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "Backpack");
		meta.setLore(Arrays.asList(new String[] {ChatColor.GREEN + "this is your own backpack!", ChatColor.GREEN + "enjoy, it has atleast 36 slots", "", ChatColor.GREEN + "note if you die you can loose your backpack!", "", convertToInvisibleString(uuid.toString()), "amount: 0"}));
		super.setItemMeta(meta);
		this.f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "backpacks" + File.separator + uuid.toString() + ".yml");
		this.con = YamlConfiguration.loadConfiguration(f);
		con.set("backpack.id", uuid.toString());
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		inv = Bukkit.createInventory(null, 36, ChatColor.DARK_GRAY + "Backpack:");
		update();
	}

	/**
	 * @author xize
	 * @param this constructor allows you to get a backpack.
	 * @param item - the ItemStack
	 * @throws IllegalArgumentException, IndexOutOfBoundsException - when the ItemStack is a invalid backpack
	 */
	public BackPack(ItemStack item, Player p) throws Exception {
		super(item);
		if(item.hasItemMeta()) {
			if(item.getItemMeta().hasLore()) {
				String uid = item.getItemMeta().getLore().get(5).replaceAll(""+ChatColor.COLOR_CHAR, "");
				String uuid = (uid.substring(0, 8) + "-" + uid.substring(8, 12) + "-" + uid.substring(12, 16) + "-" + uid.substring(16, 20) + "-" +uid.substring(20, 32));
				this.f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "backpacks" + File.separator + uuid + ".yml");
				this.con = YamlConfiguration.loadConfiguration(f);
				int stacksize = getContentSize();
				ItemMeta meta = super.getItemMeta();
				List<String> lores = new ArrayList<String>(meta.getLore());
				lores.remove(6);
				lores.add("amount: " + stacksize);
				meta.setLore(lores);
				super.setItemMeta(meta);
				
				if(!isSynced(inv)) {
					inv = Bukkit.createInventory(p, 36, ChatColor.DARK_GRAY + "Backpack:");
				}
				return;
			}
		}
		throw new IllegalArgumentException("invalid backpack");
	}
	
	/**
	 * @author xize
	 * @param this constructor allows you to get a backpack.
	 * @param item - the ItemStack
	 * @throws IllegalArgumentException, IndexOutOfBoundsException - when the ItemStack is a invalid backpack
	 */
	@Deprecated
	public BackPack(ItemStack item) throws Exception {
		super(item);
		if(item.hasItemMeta()) {
			if(item.getItemMeta().hasLore()) {
				String uid = item.getItemMeta().getLore().get(5).replaceAll(""+ChatColor.COLOR_CHAR, "");
				String uuid = (uid.substring(0, 8) + "-" + uid.substring(8, 12) + "-" + uid.substring(12, 16) + "-" + uid.substring(16, 20) + "-" +uid.substring(20, 32));
				this.f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "backpacks" + File.separator + uuid + ".yml");
				this.con = YamlConfiguration.loadConfiguration(f);
				int stacksize = getContentSize();
				ItemMeta meta = super.getItemMeta();
				List<String> lores = new ArrayList<String>(meta.getLore());
				lores.remove(6);
				lores.add("amount: " + stacksize);
				meta.setLore(lores);
				super.setItemMeta(meta);
				return;
			}
		}
		throw new IllegalArgumentException("invalid backpack");
	}

	/**
	 * @author xize
	 * @param returns the backpack inventory!
	 * @return Inventory
	 */
	@SuppressWarnings("unchecked")
	public Inventory getBackPack() {
		
		if(inv instanceof Inventory) {
			return inv;
		} else {
			if(con.contains("backpack.items")) {
				ItemStack[] items = ((List<ItemStack>)con.get("backpack.items")).toArray(new ItemStack[0]);
				inv.setContents(items);	
			}
		}

		return inv;
	}

	/**
	 * @author xize
	 * @param inv - the Inventory
	 * @param saves the backpack
	 */
	public void saveBackPack(Inventory inv) {
		
		this.size = getFixedSize(inv);
		
		con.set("backpack.items", null);
		con.set("backpack.items", inv.getContents());
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	/**
	 * @author xize
	 * @param returns the backpack id
	 * @return UUID
	 */
	public UUID getBackpackID() {
		return UUID.fromString(con.getString("backpack.id"));
	}
	
	/**
	 * @author xize
	 * @param removes the inventory file
	 */
	public void remove() {
		f.delete();
	}
	
	public int getContentSize() {
		return (size == 0 ? 0 : size);
	}
	
	private int getFixedSize(Inventory inv) {
		int i = 0;
		for(ItemStack a : inv.getContents()) {
			if(a != null && a.getType() != Material.AIR) {
				i++;
			}
		}
		return i;
	}
	
	private String convertToInvisibleString(String s) {
		String stripped = s.replaceAll("-", "");
		String invis = "";
		for(char c : stripped.toCharArray()) {
			invis += ChatColor.COLOR_CHAR + "" + c;
		}
		return invis;
	}
	
	private boolean isSynced(Inventory inv) {
		try {
			if(inv.getHolder() != null) {
				return true;
			}
		} catch(Exception e) {
			return false;
		}
		return false;
	}


	/**
	 * @author xize
	 * @param updates the configuration from flat to memory.
	 */
	private void update() {
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BackPack other = (BackPack) obj;
		if (con == null) {
			if (other.con != null)
				return false;
		} else if (!con.equals(other.con))
			return false;
		if (f == null) {
			if (other.f != null)
				return false;
		} else if (!f.equals(other.f))
			return false;
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
		return "BackPack [f=" + f + ", con=" + con + ", getBackPack()="
				+ getBackPack() + ", getBackpackID()=" + getBackpackID()
				+ ", getType()=" + getType() + ", getTypeId()=" + getTypeId()
				+ ", getAmount()=" + getAmount() + ", getData()=" + getData()
				+ ", getDurability()=" + getDurability()
				+ ", getMaxStackSize()=" + getMaxStackSize() + ", toString()="
				+ super.toString() + ", hashCode()=" + hashCode()
				+ ", getEnchantments()=" + getEnchantments() + ", serialize()="
				+ serialize() + ", getItemMeta()=" + getItemMeta()
				+ ", hasItemMeta()=" + hasItemMeta() + ", getClass()="
				+ getClass() + "]";
	}


}

