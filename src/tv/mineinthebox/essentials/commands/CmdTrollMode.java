package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

public class CmdTrollMode {
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("trollmode")) {
			if(sender.hasPermission(PermissionKey.CMD_TROLLMODE.getPermission())) {
				if(sender instanceof Player) {
					xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
					if(xp.isTrollMode()) {
						if(xp.getPlayer().getPassenger() instanceof Player) {
							Player p = (Player) xp.getPlayer().getPassenger();
							p.eject();
						}
						xp.setTrollMode(false);
						sender.sendMessage(ChatColor.GREEN + "you have disabled troll mode!");
					} else {
						xp.setTrollMode(true);
						sender.sendMessage(ChatColor.GREEN + "you have enabled troll mode!");
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
