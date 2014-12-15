package tv.mineinthebox.bukkit.essentials.commands;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.bukkit.essentials.Warnings;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;
import tv.mineinthebox.bukkit.essentials.interfaces.XPlayer;

public class CmdPowerTool {
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("powertool")) {
			if(sender.hasPermission(PermissionKey.CMD_POWERTOOL.getPermission())) {
				if(sender instanceof Player) {
					XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
					if(xp.getPlayer().getItemInHand() != null) {
						if(args.length > 0) {
							xp.setPowerTool(xp.getPlayer().getItemInHand(), Arrays.toString(args).replace("[", "").replace(",", "").replace("]", ""));
							sender.sendMessage(ChatColor.GREEN + "you have successfully binded the command /" + Arrays.toString(args).replace("[", "").replace(",", "").replace("]", "") + " on the " + xp.getPlayer().getItemInHand().getType().name() + " item!");
						} else {
							if(xp.hasPowerTool()) {
								xp.removePowerTool();
								sender.sendMessage(ChatColor.GREEN + "powertool successfully unbinded from the " + xp.getPlayer().getItemInHand().getType().name() + " item!");
							} else {
								sender.sendMessage(ChatColor.RED + "you don't seem to have a binded powertool!");
							}
						}
					} else {
						sender.sendMessage(ChatColor.RED + "you need a item in your hand in order to bind a command!");
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
