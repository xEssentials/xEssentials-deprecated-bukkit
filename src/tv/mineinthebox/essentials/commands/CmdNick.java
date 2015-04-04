package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdNick extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdNick(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("nick")) {
			if(sender.hasPermission(PermissionKey.CMD_NICK.getPermission())) {
				if(args.length == 0) {
					if(sender instanceof Player) {
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
						xp.setCustomName(xp.getUser());
						sendMessage("successfully setted your name back to default.");
						for(Player p : pl.getOnlinePlayers()) {
							if(p.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
								sendMessageTo(p, xp.getCustomName() + " has changed his name back to default.");
							}
						}
					} else {
						getWarning(WarningType.PLAYER_ONLY);
					}
				} else if(args.length == 1) {
					XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
					xp.setCustomName(ChatColor.translateAlternateColorCodes('&', args[0]));
					sendMessage("successfully setted your name back to " + xp.getCustomName());
					for(Player p : pl.getOnlinePlayers()) {
						if(p.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							sendMessageTo(p, xp.getUser() + " has changed his name to " + xp.getCustomName());
						}
					}
				} else if(args.length == 2) {
					if(pl.getManagers().getPlayerManager().isOnline(args[0])) {
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[0]);
						xp.setCustomName(ChatColor.translateAlternateColorCodes('&', args[1]));
						sendMessage("successfully setted "+xp.getUser()+" his name back to " + xp.getCustomName());
						for(Player p : pl.getOnlinePlayers()) {
							if(p.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
								sendMessageTo(p, xp.getUser() + " has changed his name to " + xp.getCustomName());
							}
						}	
					} else {
						sendMessage("player is not online.");
					}
				}
			} else {
				getWarning(WarningType.NO_PERMISSION);
			}
		}
		return false;
	}

}
