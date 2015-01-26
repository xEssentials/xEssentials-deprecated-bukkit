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

public class CmdDrunk {
	
	private final xEssentials pl;
	
	public CmdDrunk(xEssentials pl) {
		this.pl = pl;
	}
	
	@SuppressWarnings("deprecation")
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("drunk")) {
			if(sender.hasPermission(PermissionKey.CMD_DRUNK.getPermission())) {
				if(args.length == 0) {
					if(sender instanceof Player) {
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
						if(xp.isDrunk()) {
							xp.setDrunk(false);
							sender.sendMessage(ChatColor.GREEN + "you have successfully disabled drunk chat");
						} else {
							xp.setDrunk(true);
							sender.sendMessage(ChatColor.GREEN + "you have successfully enabled drunk chat");
						}
					} else {
						Warnings.getWarnings(sender).consoleMessage();
					}
				} else if(args.length == 1) {
					Player p = Bukkit.getPlayer(args[0]);
					if(p instanceof Player) {
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(p.getName());
						if(xp.isDrunk()) {
							xp.setDrunk(false);
							p.sendMessage(ChatColor.GREEN + sender.getName() + " has successfully disabled drunk chat on you");
							sender.sendMessage(ChatColor.GREEN + "you have disabled drunk mode for " + p.getName());
						} else {
							xp.setDrunk(true);
							p.sendMessage(ChatColor.GREEN + sender.getName() + " has successfully enabled drunk chat on you");
							sender.sendMessage(ChatColor.GREEN + "you have enabled drunk mode for " + p.getName());
						}
					} else {
						if(pl.getManagers().getPlayerManager().isEssentialsPlayer(args[0])) {
							sender.sendMessage(ChatColor.RED + "player is offline!");
						} else {
							Warnings.getWarnings(sender).playerHasNeverPlayedBefore();
						}
					}
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
