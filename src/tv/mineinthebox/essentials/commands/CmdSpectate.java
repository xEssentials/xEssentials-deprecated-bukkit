package tv.mineinthebox.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdSpectate extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdSpectate(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	@SuppressWarnings("deprecation")
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("spectate")) {
			if(sender.hasPermission(PermissionKey.CMD_SPECTATE.getPermission())) {
				if(sender instanceof Player) {
					if(args.length == 0) {
						showHelp();
					} else if(args.length == 1) {
						if(args[0].equalsIgnoreCase("stop")) {
							XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
							if(xp.isSpectate()) {
								xp.stopSpectate();
								sendMessage("successfully stopped spectate.");
							} else {
								sendMessage("you never spectated someone!");
							}
						} else {
							Player target = Bukkit.getPlayer(args[0]);
							if(target instanceof Player) {
								if(sender.getName().equalsIgnoreCase(target.getName())) {
									sendMessage("you cannot spectate yourself!");
									return false;
								}
								XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
								xp.spectate(target);
								sendMessage("successfully spectating player " + target.getName());
							}
						}
					}
				} else {
					getWarning(WarningType.PLAYER_ONLY);
				}
			} else {
				getWarning(WarningType.NO_PERMISSION);
			}
		}
		return false;
	}
	
	@Override
	public void showHelp() {
		sender.sendMessage(ChatColor.GOLD + ".oO___[spectate]___Oo.");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/spectate " + ChatColor.WHITE + ": shows help");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/spectate <player> " + ChatColor.WHITE + ": spectates someone");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/spectate stop " + ChatColor.WHITE + ": stops the current spectate session");
	}

}
