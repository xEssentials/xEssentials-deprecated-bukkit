package tv.mineinthebox.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;

public class CmdKill {

	private final xEssentials pl;
	
	public CmdKill(xEssentials pl) {
		this.pl = pl;
	}
	
	@SuppressWarnings("deprecation")
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("kill")) {
			if(sender.hasPermission(PermissionKey.CMD_KILL.getPermission())) {
				if(args.length == 1) {
					if(pl.getManagers().getPlayerManager().isOnline(args[0])) {
						Player p = (Player) Bukkit.getPlayer(args[0]);
						p.damage(p.getMaxHealth());
						p.sendMessage(ChatColor.GRAY + "you are killed by " + sender.getName());
					} else {
						sender.sendMessage(ChatColor.RED + "player is not online!");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "wrong syntax use \"/kill <player>\"");
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
