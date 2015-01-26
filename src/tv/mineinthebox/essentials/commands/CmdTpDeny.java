package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;

public class CmdTpDeny {
	
	private final xEssentials pl;
	
	public CmdTpDeny(xEssentials pl) {
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("tpdeny")) {
			if(sender.hasPermission(PermissionKey.CMD_TP_DENY.getPermission())) {
				if(pl.getManagers().getTpaManager().containsKey(sender.getName())) {
					Player requester = pl.getManagers().getPlayerManager().getOfflinePlayer(pl.getManagers().getTpaManager().get(sender.getName())).getPlayer();
					if(requester instanceof Player) {
						requester.sendMessage(ChatColor.RED + sender.getName() + " has denied your tpa request!");
					}
					sender.sendMessage(ChatColor.GREEN + "successfully denied " + pl.getManagers().getTpaManager().get(sender.getName()) + " his request!");
					pl.getManagers().getTpaManager().remove(sender.getName());
				} else {
					sender.sendMessage(ChatColor.RED + "you don't have any tpa requests open!");
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
