package tv.mineinthebox.bukkit.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import tv.mineinthebox.bukkit.essentials.Warnings;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;
import tv.mineinthebox.bukkit.essentials.helpers.EnumHelper;

public class CmdSpawnmob {

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("spawnmob")) {
			if(sender.hasPermission(PermissionKey.CMD_SPAWN_MOB.getPermission())) {
				if(args.length == 0) {
					sendHelp(sender);
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						sendHelp(sender);
					} else {
						if(sender instanceof Player) {
							Player p = (Player) sender;
							if(EnumHelper.isDefined(EntityType.class, args[0].toUpperCase())) {
								EntityType type = EnumHelper.getEnum(EntityType.class, args[0].toUpperCase());
								p.getWorld().spawnEntity(p.getLocation(), type);
							} else {
								sender.sendMessage(ChatColor.RED + "invalid entity given in!");
							}
						} else {
							Warnings.getWarnings(sender).consoleMessage();
						}
					}
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

	private void sendHelp(CommandSender sender) {
		StringBuilder build = new StringBuilder();
		for(EntityType entity : EntityType.values()) {
			if(entity.isAlive()) {
				if(entity != EntityType.PLAYER) {
					build.append(entity.name() + ", ").toString();
				}
			}
		}
		sender.sendMessage(ChatColor.GOLD + ".oO___[spawnmob]___Oo.");
		sender.sendMessage(ChatColor.GRAY + build.toString().toLowerCase());
		sender.sendMessage(ChatColor.GOLD + ".oO___[spawnmob types/colors]___Oo.");
		sender.sendMessage(ChatColor.GRAY + "/spawnmob <mobname> - spawns a mob");
	}

}
