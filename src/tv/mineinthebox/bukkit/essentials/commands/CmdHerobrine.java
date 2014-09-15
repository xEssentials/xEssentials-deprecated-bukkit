package tv.mineinthebox.bukkit.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import tv.mineinthebox.bukkit.essentials.Warnings;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;

public class CmdHerobrine {
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("herobrine")) {
			if(sender.hasPermission(PermissionKey.CMD_HEROBRINE.getPermission())) {
				if(args.length == 0) {
					if(sender instanceof Player) {
						Player p = (Player) sender;
						Skeleton zombe = (Skeleton) p.getWorld().spawnEntity(p.getLocation().add(6, 0, 6), EntityType.SKELETON);
						ItemStack item = new ItemStack(Material.SKULL_ITEM);
						item.setDurability((short)3);
						item.setAmount(1);
						SkullMeta meta = (SkullMeta) item.getItemMeta();
						meta.setOwner("Herobrine");
						item.setItemMeta(meta);
						zombe.getEquipment().setHelmet(item);
						zombe.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
						zombe.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));
						zombe.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
						zombe.getEquipment().setItemInHand(new ItemStack(Material.DIAMOND_SWORD,1));
						zombe.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 10), false);
						zombe.setCustomName("Herobrine");
						sender.sendMessage(ChatColor.GREEN + "you have successfully summoned a Herobrine!");
					} else {
						Warnings.getWarnings(sender).consoleMessage();
					}
				} else if(args.length == 1) {
					Player p = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[0]).getPlayer();
					if(p instanceof Player) {
						Skeleton zombe = (Skeleton) p.getWorld().spawnEntity(p.getLocation().add(6, 0, 6), EntityType.SKELETON);
						ItemStack item = new ItemStack(Material.SKULL_ITEM);
						item.setDurability((short)3);
						item.setAmount(1);
						SkullMeta meta = (SkullMeta) item.getItemMeta();
						meta.setOwner("Herobrine");
						item.setItemMeta(meta);
						zombe.getEquipment().setHelmet(item);
						zombe.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
						zombe.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));
						zombe.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
						zombe.getEquipment().setItemInHand(new ItemStack(Material.DIAMOND_SWORD,1));
						zombe.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 10), false);
						zombe.setCustomName("Herobrine");
						sender.sendMessage(ChatColor.GREEN + "you have successfully summoned a Herobrine! near player " + p.getName());
					} else {
						sender.sendMessage(ChatColor.RED + "this player is not online!");
					}
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
