package tv.mineinthebox.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;

public class CmdFire extends CommandTemplate {
	
	public CmdFire(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
	}

	@SuppressWarnings("deprecation")
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("fire")) {
			if(sender.hasPermission(PermissionKey.CMD_FIRE.getPermission())) {
				if(args.length == 0) {
					if(sender instanceof Player) {
						Player p = (Player) sender;
						p.setFireTicks(100);
						sendMessage(ChatColor.GREEN + "you have ignited yourself.");
					} else {
						getWarning(WarningType.PLAYER_ONLY);
					}
				} else if(args.length == 1) {
					Player p = Bukkit.getPlayer(args[0]);
					if(p instanceof Player) {
						p.setFireTicks(100);
						sendMessage(ChatColor.GREEN + "you have ignited " + p.getName());
					} else {
						sender.sendMessage(ChatColor.RED + "player is not online");
					}
				}
			} else {
				getWarning(WarningType.NO_PERMISSION);
			}
		}
		return false;
	}

}
