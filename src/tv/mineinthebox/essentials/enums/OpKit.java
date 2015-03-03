package tv.mineinthebox.essentials.enums;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum OpKit {

		STONE_KIT(new ItemStack[] {
				new ItemStack(Material.LEATHER_HELMET) {{
					ItemMeta meta = getItemMeta();
					meta.setDisplayName(ChatColor.GOLD + "stone op kit!");
					meta.setLore(Arrays.asList(new String[] {
							ChatColor.GRAY + "get a special over powered kit!"
					}));
					setItemMeta(meta);
				}},
				getOpGear(Material.LEATHER_HELMET, "Xeph0re"),
				getOpGear(Material.LEATHER_CHESTPLATE, "Xeph0re"),
				getOpGear(Material.LEATHER_LEGGINGS, "Xeph0re"),
				getOpGear(Material.LEATHER_BOOTS, "Xeph0re"),
				getOpGear(Material.STONE_SWORD, "Xeph0re"),
				getOpGear(Material.STONE_AXE, "Xeph0re"),
				getOpGear(Material.STONE_PICKAXE, "Xeph0re")
			
		}),
		IRON_KIT(new ItemStack[] {
				new ItemStack(Material.IRON_HELMET) {{
					ItemMeta meta = getItemMeta();
					meta.setDisplayName(ChatColor.GOLD + "iron op kit!");
					meta.setLore(Arrays.asList(new String[] {
							ChatColor.GRAY + "get a special over powered kit!"
					}));
					setItemMeta(meta);
				}},
				getOpGear(Material.IRON_HELMET, "Xeph0re"),
				getOpGear(Material.IRON_CHESTPLATE, "Xeph0re"),
				getOpGear(Material.IRON_LEGGINGS, "Xeph0re"),
				getOpGear(Material.IRON_BOOTS, "Xeph0re"),
				getOpGear(Material.IRON_SWORD, "Xeph0re"),
				getOpGear(Material.IRON_AXE, "Xeph0re"),
				getOpGear(Material.IRON_PICKAXE, "Xeph0re")
		}),
		DIAMOND_KIT(new ItemStack[] {
				new ItemStack(Material.DIAMOND_HELMET) {{
					ItemMeta meta = getItemMeta();
					meta.setDisplayName(ChatColor.GOLD + "diamond op kit!");
					meta.setLore(Arrays.asList(new String[] {
							ChatColor.GRAY + "get a special over powered kit!"
					}));
					setItemMeta(meta);
				}},
				getOpGear(Material.DIAMOND_HELMET, "Xeph0re"),
				getOpGear(Material.DIAMOND_CHESTPLATE, "Xeph0re"),
				getOpGear(Material.DIAMOND_LEGGINGS, "Xeph0re"),
				getOpGear(Material.DIAMOND_BOOTS, "Xeph0re"),
				getOpGear(Material.DIAMOND_SWORD, "Xeph0re"),
				getOpGear(Material.DIAMOND_AXE, "Xeph0re"),
				getOpGear(Material.DIAMOND_PICKAXE, "Xeph0re")
		});
		
		private final ItemStack[] items;
		
		private OpKit(ItemStack[] items) {
			this.items = items;
		}
		
		/**
		 * returns the button of the menu
		 * 
		 * @author xize
		 * @return ItemStack
		 */
		public ItemStack getButton() {
			return items[0];
		}
		
		/**
		 * returns the helmet
		 * 
		 * @author xize
		 * @return ItemStack
		 */
		public ItemStack getHelmet() {
			return items[1];
		}
		
		/**
		 * returns the chestplate
		 * 
		 * @author xize
		 * @return ItemStack
		 */
		public ItemStack getChestPlate() {
			return items[2];
		}
		
		/**
		 * returns the leggings
		 * 
		 * @author xize
		 * @return ItemStack
		 */
		public ItemStack getLeggings() {
			return items[3];
		}
		
		/**
		 * returns the boots
		 * 
		 * @author xize
		 * @return ItemStack
		 */
		public ItemStack getBoots() {
			return items[4];
		}
		
		/**
		 * returns the sword
		 * 
		 * @author xize
		 * @return ItemStack
		 */
		public ItemStack getSword() {
			return items[5];
		}
		
		/**
		 * returns the axe
		 * 
		 * @author xize
		 * @return ItemStack
		 */
		public ItemStack getAxe() {
			return items[6];
		}
		
		/**
		 * returns the pickaxe
		 * 
		 * @author xize
		 * @return ItemStack
		 */
		public ItemStack getPickAxe() {
			return items[7];
		}
		
		private static ItemStack getOpGear(Material mat, final String signedby) {
			return new ItemStack(mat) {{
				ItemMeta meta = getItemMeta();
				meta.setDisplayName(ChatColor.GOLD + "[OP] " + getType().name().toLowerCase().replace("_", " "));
				meta.setLore(Arrays.asList(new String[] {
					"",
					ChatColor.GRAY + "slay them all jimmy!",
					"",
					ChatColor.DARK_PURPLE + "- " + signedby
				}));
				setItemMeta(meta);
				for(Enchantment ench : Enchantment.values()) {
					if(!ench.equals(Enchantment.LOOT_BONUS_BLOCKS) && !ench.equals(Enchantment.LOOT_BONUS_MOBS)) {
						addUnsafeEnchantment(ench, 1609);	
					}
				}
			}};
		}
}
