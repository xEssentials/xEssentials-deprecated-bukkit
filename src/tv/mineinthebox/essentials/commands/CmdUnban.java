package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdUnban extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdUnban(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("unban")) {
			if(sender.hasPermission(PermissionKey.CMD_UNBAN.getPermission())) {
				if(args.length == 0) {
					showHelp();
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						showHelp();
					} else {
						if(pl.getManagers().getPlayerManager().isOnline(args[0])) {
							XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[0]);
							if(xp.isPermBanned() || xp.isTempBanned()) {
								xp.unban();
								sendMessage(xp.getName() + " has been unbanned!");
							} else {
								sendMessage("this player is not banned!");
							}
						} else {
							try {
								XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
								if(off.isPermBanned() || off.isTempBanned()) {
									off.unban();
									sendMessage(off.getName() + " has been unbanned!");
								} else {
									sendMessage("this player is not banned!");
								}
							} catch(NullPointerException e) {
								getWarning(WarningType.NEVER_PLAYED_BEFORE);
							}
						}
					}
				}
			} else {
				getWarning(WarningType.NO_PERMISSION);
			}
		}
		return false;
	}
	
	@Override
	public void showHelp() {
		sender.sendMessage(ChatColor.GOLD + ".oO___[unban help]___Oo.");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/unban <player> " + ChatColor.WHITE + ": unbans a player with a default message");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/unban help " + ChatColor.WHITE + ": shows help");
	}

}
