package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.SpawnerBlock;

public class CmdSpawnerBlock {
	
	private final xEssentials pl;
	
	public CmdSpawnerBlock(xEssentials pl) {
		this.pl = pl;
	}
	
	@SuppressWarnings("deprecation")
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("spawnerblock")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_SPAWNER_BLOCK.getPermission())) {
					if(args.length == 0) {
						Player p = (Player) sender;
						Block block = p.getTargetBlock(null, 30);
						SpawnerBlock spawner = new SpawnerBlock(block, EntityType.ZOMBIE, pl);
						spawner.start();
						sender.sendMessage(ChatColor.GREEN + block.getType().name().toLowerCase() + " is now a tempory zombie spawner.");
					} else if(args.length == 1) {
						if(args[0].equalsIgnoreCase("help")) {
							sender.sendMessage(ChatColor.GOLD + ".oO___[spawner block]___Oo.");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/spawnerblock " + ChatColor.WHITE + ": makes a block a tempory zombie spawner");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/spawnerblock <entity> " + ChatColor.WHITE + ": makes a tempory spawner block on a entity");
							sender.sendMessage(ChatColor.GRAY + "--entities--");
							String s = ChatColor.GRAY + "";
							for(int size = 0; size < EntityType.values().length; size++) {
								if(size == EntityType.values().length) {
									s += EntityType.values()[size].name().toLowerCase();
								} else {
									s += EntityType.values()[size].name().toLowerCase() + ", ";
								}
							}
							sender.sendMessage(s);
						} else {
							for(EntityType type : EntityType.values()) {
								if(type.name().equalsIgnoreCase(args[0])) {
									Player p = (Player) sender;
									Block block = p.getTargetBlock(null, 30);
									SpawnerBlock spawner = new SpawnerBlock(block, type, pl);
									spawner.start();
									sender.sendMessage(ChatColor.GREEN + block.getType().name().toLowerCase() + " is now a tempory " + type.name().toLowerCase() + " spawner.");
									return true;
								}
							}
							sender.sendMessage(ChatColor.RED + "could not find any entity named as " + args[0]);
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
