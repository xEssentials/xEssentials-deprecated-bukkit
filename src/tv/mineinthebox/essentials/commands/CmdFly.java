package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdFly {

	private final xEssentials pl;

	public CmdFly(xEssentials pl) {
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("fly")) {
			if(sender.hasPermission(PermissionKey.CMD_FLY.getPermission())) {
				if(args.length == 0) {
					if(sender instanceof Player) {
						if(pl.getManagers().getPlayerManager().isOnline(sender.getName())) {
							XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
							if(xp.isFlying()) {
								sender.sendMessage(ChatColor.GRAY + "successfully disabled fly mode");
								xp.setFlying(false);
							} else {
								sender.sendMessage(ChatColor.GRAY + "successfully enabled fly mode");
								xp.setFlying(true);
							}
						} else {
							sender.sendMessage(ChatColor.RED + "something went wrong, please reload pl");
						}
					} else {
						Warnings.getWarnings(sender).consoleMessage();
					}
				} else if(args.length == 1 ) {
					if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
						if(pl.getManagers().getPlayerManager().isEssentialsPlayer(args[0])) {
							if(pl.getManagers().getPlayerManager().isOnline(args[0])) {
								XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[0]);
								if(xp.isFlying()) {
									sender.sendMessage(ChatColor.GRAY + "you successfully disabled fly for player " + xp.getUser());
									xp.getPlayer().sendMessage(ChatColor.GRAY + sender.getName() + " disabled your fly mode");
									xp.setFlying(false);
								} else {
									sender.sendMessage(ChatColor.GRAY + "you successfully enabled fly for player " + xp.getUser());
									xp.getPlayer().sendMessage(ChatColor.GRAY + sender.getName() + " enabled your fly mode");
									xp.setFlying(true);
								}
							} else {
								sender.sendMessage(ChatColor.RED + "player not online!");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "player does not exist!");
						}
					} else {
						Warnings.getWarnings(sender).noPermission();
					}
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
