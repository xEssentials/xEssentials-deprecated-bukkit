package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;

public class CmdExplode extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdExplode(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("explode")) {
			if(sender.hasPermission(PermissionKey.CMD_EXPLODE.getPermission())) {
				if(args.length == 0) {
					if(sender instanceof Player) {
						Player p = (Player) sender;
						p.getWorld().createExplosion(p.getLocation(), 6f);
						sendMessage(ChatColor.GRAY + "you have explode yourself!");
					} else {
						getWarning(WarningType.PLAYER_ONLY);
					}
				} else if(args.length == 1) {
					if(pl.getManagers().getPlayerManager().isEssentialsPlayer(args[0])) {
						Player p = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]).getBukkitPlayer();
						if(p instanceof Player) {
							p.getWorld().createExplosion(p.getLocation(), 6f);
							sendMessageTo(p, ChatColor.GRAY + "you got exploded!");	
						} else {
							sendMessage(ChatColor.RED + "the player is not online");
						}
					} else {
						sendMessage(ChatColor.RED + "the player is not online");
					}
				}
			}
		}
		return false;
	}
	
}
