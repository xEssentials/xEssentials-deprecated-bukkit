package tv.mineinthebox.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdSpectate {
	
	private final xEssentials pl;
	
	public CmdSpectate(xEssentials pl) {
		this.pl = pl;
	}

	@SuppressWarnings("deprecation")
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("spectate")) {
			if(sender.hasPermission(PermissionKey.CMD_SPECTATE.getPermission())) {
				if(sender instanceof Player) {
					if(args.length == 0) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[spectate]___Oo.");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/spectate " + ChatColor.WHITE + ": shows help");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/spectate <player> " + ChatColor.WHITE + ": spectates someone");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/spectate stop " + ChatColor.WHITE + ": stops the current spectate session");
					} else if(args.length == 1) {
						if(args[0].equalsIgnoreCase("stop")) {
							XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
							if(xp.isSpectate()) {
								xp.stopSpectate();
								sender.sendMessage(ChatColor.GREEN + "successfully stopped spectate.");
							} else {
								sender.sendMessage(ChatColor.RED + "you never spectated someone!");
							}
						} else {
							Player target = Bukkit.getPlayer(args[0]);
							if(target instanceof Player) {
								if(sender.getName().equalsIgnoreCase(target.getName())) {
									sender.sendMessage(ChatColor.RED + "you cannot spectate yourself!");
									return false;
								}
								XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
								xp.spectate(target);
								sender.sendMessage(ChatColor.GREEN + "successfully spectating player " + target.getName());
							}
						}
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
