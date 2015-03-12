package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.helpers.EnumHelper;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;

public class CmdSpawnmob extends CommandTemplate {

	public CmdSpawnmob(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("spawnmob")) {
			if(sender.hasPermission(PermissionKey.CMD_SPAWN_MOB.getPermission())) {
				if(args.length == 0) {
					showHelp();
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						showHelp();
					} else {
						if(sender instanceof Player) {
							Player p = (Player) sender;
							if(EnumHelper.isDefined(EntityType.class, args[0].toUpperCase())) {
								EntityType type = EnumHelper.getEnum(EntityType.class, args[0].toUpperCase());
								p.getWorld().spawnEntity(p.getLocation(), type);
								sendMessage("an "+ type.name().toLowerCase() +" has been spawned!");
							} else {
								sendMessage(ChatColor.RED + "invalid entity given in!");
							}
						} else {
							getWarning(WarningType.PLAYER_ONLY);
						}
					}
				}
			} else {
				getWarning(WarningType.NO_PERMISSION);
			}
		}
		return false;
	}

	@Override
	public void showHelp() {
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
