package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;

public class CmdTpAccept {
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("tpaccept")) {
			if(sender.hasPermission(PermissionKey.CMD_TP_ACCEPT.getPermission())) {
				if(xEssentials.getManagers().getTpaManager().containsKey(sender.getName())) {
					Player p = (Player) sender;
					Player victem = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(xEssentials.getManagers().getTpaManager().get(sender.getName())).getPlayer();
					if(victem instanceof Player) {
						victem.teleport(p);
						victem.sendMessage(ChatColor.GREEN + sender.getName() + " has successfully accepted your tpa request!");
						sender.sendMessage(ChatColor.GREEN + "you have successfully accepted " + victem.getName() + " his tpa request!");
						xEssentials.getManagers().getTpaManager().remove(sender.getName());
					} else {
						sender.sendMessage(ChatColor.RED + "the player went offline!");
						xEssentials.getManagers().getTpaManager().remove(sender.getName());
					}
				} else {
					sender.sendMessage(ChatColor.RED + "you don't have tpa requests open!");
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
