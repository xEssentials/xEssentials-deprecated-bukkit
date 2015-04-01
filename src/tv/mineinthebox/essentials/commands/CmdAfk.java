package tv.mineinthebox.essentials.commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.events.customevents.PlayerAfkEvent;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdAfk extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdAfk(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("afk")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_AFK.getPermission())) {
					if(args.length == 0) {
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
						xp.setAfk("no reason given in");
						broadcast(ChatColor.GREEN + sender.getName() + " has been afk");
						Bukkit.getPluginManager().callEvent(new PlayerAfkEvent(xp.getBukkitPlayer(), true, false, pl));
					} else  {
						String message = Arrays.toString(args).replace("[", "").replace(",", "").replace("]", "");
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
						xp.setAfk(message);
						broadcast(ChatColor.GREEN + sender.getName() + " has been afk [ "+xp.getAfkReason()+" ]");
						Bukkit.getPluginManager().callEvent(new PlayerAfkEvent(xp.getBukkitPlayer(), true, false, pl));
					}
				} else {
					getWarning(WarningType.NO_PERMISSION);
				}
			} else {
				getWarning(WarningType.PLAYER_ONLY);
			}
		}
		return false;
	}

}
