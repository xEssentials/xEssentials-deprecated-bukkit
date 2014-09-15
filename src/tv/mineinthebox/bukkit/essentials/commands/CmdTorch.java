package tv.mineinthebox.bukkit.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.bukkit.essentials.Warnings;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsPlayer;

public class CmdTorch {
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("torch")) {
			if(sender.hasPermission(PermissionKey.CMD_TORCH.getPermission())) {
				if(sender instanceof Player) {
					if(xEssentials.getManagers().getPlayerManager().isOnline(sender.getName())) {
						Player p = (Player) sender;
						xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(p.getName());
						if(xp.isTorch()) {
							xp.setTorch(false);
							p.sendMessage(ChatColor.GREEN + "successfully disabled torch");
						} else {
							xp.setTorch(true);
							p.sendMessage(ChatColor.GREEN + "successfully enabled torch");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "you where not found in our global HashMap, please reload xEssentials in order to fix this problem");
					}
				} else {
					Warnings.getWarnings(sender).consoleMessage();
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
