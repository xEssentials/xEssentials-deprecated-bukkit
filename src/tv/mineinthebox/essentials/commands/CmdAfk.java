package tv.mineinthebox.essentials.commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.events.customevents.PlayerAfkEvent;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdAfk {
	
	private final xEssentials pl;
	
	public CmdAfk(xEssentials pl) {
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("afk")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_AFK.getPermission())) {
					if(args.length == 0) {
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
						xp.setAfk("no reason given in");
						Bukkit.broadcastMessage(ChatColor.GREEN + sender.getName() + " has been afk");
						Bukkit.getPluginManager().callEvent(new PlayerAfkEvent(xp.getPlayer(), true, false, pl));
					} else  {
						String message = Arrays.toString(args).replace("[", "").replace(",", "").replace("]", "");
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
						xp.setAfk(message);
						Bukkit.broadcastMessage(ChatColor.GREEN + sender.getName() + " has been afk [ "+xp.getAfkReason()+" ]");
						Bukkit.getPluginManager().callEvent(new PlayerAfkEvent(xp.getPlayer(), true, false, pl));
					}
				} else {
					Warnings.getWarnings(sender).noPermission();
				}
			} else {
				Warnings.getWarnings(sender).consoleMessage();
			}
		}
		return false;
	}

}
