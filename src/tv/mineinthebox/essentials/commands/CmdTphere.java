package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdTphere extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdTphere(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("tphere")) {
			if(sender.hasPermission(PermissionKey.CMD_TP_HERE.getPermission())) {
				if(args.length == 0) {
					showHelp();
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						showHelp();
					} else {
						if(sender instanceof Player) {
							XPlayer p = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
							XOfflinePlayer victem = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
							if(victem.getPlayer() instanceof Player) {
								if(p.isVanished()) {
									if(victem.getPlayer().hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
										victem.getEssentialsPlayer().vanish();
										sendMessageTo(victem.getPlayer(), ChatColor.GREEN + "the player you teleported to has been vanished, now you are vanished to!");	
									}
								}
								sendMessage(ChatColor.GREEN + "teleported " + victem.getPlayer().getName() + " to you!");
								sendMessageTo(victem.getPlayer(), ChatColor.GREEN + sender.getName() + " has teleported you to his location!");
								victem.getPlayer().teleport(p.getPlayer(), TeleportCause.COMMAND);
							} else {
								sendMessage(ChatColor.RED + "could not find a online player with that name!");
							}
						} else {
							getWarning(WarningType.PLAYER_ONLY);
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
		sender.sendMessage(ChatColor.GOLD + ".oO___[tphere help]___Oo.");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tphere help " + ChatColor.WHITE + ": shows help");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/tphere <player> " + ChatColor.WHITE + ": teleports a player to you!");
	}

}
