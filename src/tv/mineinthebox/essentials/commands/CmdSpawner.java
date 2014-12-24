package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.enums.PermissionKey;

public class CmdSpawner {
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("spawner")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_SPAWNER.getPermission())) {
					EntityType[] entity = EntityType.values();
					if(args.length == 0) {
						StringBuilder build = new StringBuilder();
						for(int i = 0; i < entity.length; i++) {
							if(entity[i].isAlive()) {
								if(entity[i] == EntityType.WITHER) {
									
								} else if(entity[i] == EntityType.PLAYER) {
									
								} else {
									build.append(entity[i].name() + ", ").toString();
								}
							}
						}
						sender.sendMessage(ChatColor.GOLD + ".oO___[Spawnmob]___Oo.");
						sender.sendMessage(ChatColor.GRAY + build.toString().toLowerCase());
					} else if(args.length == 1) {
						Player p = (Player) sender;
						if(args[0].equalsIgnoreCase("give")) {
							sender.sendMessage(ChatColor.GREEN + "added spawner in your inventory");
							p.getInventory().addItem(new ItemStack(Material.MOB_SPAWNER));
						} else {
							try {
								EntityType type = EntityType.valueOf(args[0].toUpperCase());
								if(type.isAlive()) {
									if(type == EntityType.WITHER) {
										sender.sendMessage(ChatColor.RED + "you are not allowed to make wither spawners");
									} else if(type == EntityType.PLAYER) {
										sender.sendMessage(ChatColor.RED + "player entities cannot used through Bukkit");
									} else {
										@SuppressWarnings("deprecation")
										Block block = p.getTargetBlock(null, 50);
										if(block.getType() == Material.MOB_SPAWNER) {
											CreatureSpawner spawner = (CreatureSpawner) block.getState();
											String old = spawner.getSpawnedType().name();
											sender.sendMessage(ChatColor.GREEN + "successfully changed spawner from " + old.toLowerCase() + " to " + type.name().toLowerCase());
											spawner.setSpawnedType(type);
										}
									}
								} else {
									sender.sendMessage(ChatColor.RED + "this is not a peacefull mob or a hostile mob");
								}
							} catch(IllegalArgumentException e) {
								sender.sendMessage(ChatColor.RED + "this mob does not exist!");
							}
						}
					}
				} else {
					Warnings.getWarnings(sender).noPermission();
				}
			} else { 
				Warnings.getWarnings(sender).consoleMessage();
			}
		}
		return false;
	}

}
