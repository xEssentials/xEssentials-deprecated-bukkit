package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdUnmute extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdUnmute(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("unmute")) {
			if(sender.hasPermission(PermissionKey.CMD_UNMUTE.getPermission())) {
				if(args.length == 0) {
					showHelp();
				} else if(args.length == 1) {
					if(pl.getManagers().getPlayerManager().isOnline(args[0])) {
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[0]);
						if(xp.isMuted()) {
							xp.unmute();
							sendMessage(ChatColor.GREEN + "you successfully unmuted the player " + xp.getName());
							sendMessageTo(xp.getBukkitPlayer(), ChatColor.GREEN + sender.getName() + " has unmuted your chat!");
						} else {
							sendMessage(ChatColor.RED + "this player is already unmuted!");
						}
					} else {
						try {
							XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
							if(off.isMuted()) {
								off.unmute();
								sendMessage(ChatColor.GREEN + "you successfully unmuted the offline player " + off.getName() + "!");
							} else {
								sendMessage(ChatColor.RED + "this offline player is already unmuted!");
							}
						} catch(NullPointerException e) {
							getWarning(WarningType.NEVER_PLAYED_BEFORE);
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
		sender.sendMessage(ChatColor.GOLD + ".oO___[unmute help]___Oo.");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/unmute help " + ChatColor.WHITE + ": shows help");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/unmute <player> " + ChatColor.WHITE + "unmute a player!");
	}

}
