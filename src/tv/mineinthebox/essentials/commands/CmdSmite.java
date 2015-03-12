package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;

public class CmdSmite extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdSmite(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("smite")) {
			if(sender.hasPermission(PermissionKey.CMD_SMITE.getPermission())) {
				if(args.length == 0) {
					if(sender instanceof Player) {
						Player p = (Player) sender;
						p.getWorld().strikeLightning(p.getLocation());
						sendMessage(ChatColor.GRAY + "you have smited yourself!");
					} else {
						getWarning(WarningType.PLAYER_ONLY);
					}
				} else if(args.length == 1) {
					Player p = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]).getPlayer();
					if(p instanceof Player) {
						p.getWorld().strikeLightning(p.getLocation());
						sendMessage(ChatColor.GRAY + "you got smited!");
					} else {
						sendMessage(ChatColor.RED + "the player is not online");
					}
				}
			}
		}
		return false;
	}

}
