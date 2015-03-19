package tv.mineinthebox.essentials.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.Warp;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;

public class CmdWarp extends CommandTemplate {

	private final xEssentials pl;

	public CmdWarp(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	private List<String> getwarps(String p) {
		List<Warp> s = new ArrayList<Warp>(Arrays.asList(pl.getManagers().getWarpManager().getWarps()));
		List<String> tabs = new ArrayList<String>();
		for(Warp warp : s) {
			if(warp.getWarpName().toLowerCase().startsWith(p.toLowerCase())) {
				tabs.add(warp.getWarpName());
			}
		}
		return tabs;
	}

	public List<String> onTabComplete(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("warp")) {
			if(args.length == 1) {
				if(sender.hasPermission(PermissionKey.CMD_WARP.getPermission())) {
					List<String> list = getwarps(args[0]);
					return list;
				}
			}
		}
		return null;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("warp")) {
			if(sender.hasPermission(PermissionKey.CMD_WARP.getPermission())) {
				if(args.length == 0) {
					showHelp();
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						showHelp();
					} else {
						if(sender instanceof Player) {
							Player p = (Player) sender;
							if(pl.getManagers().getWarpManager().isWarp(args[0])) {
								Warp warp = pl.getManagers().getWarpManager().getWarp(args[0], (Player)sender);
								p.teleport(warp.getWarpLocation(), TeleportCause.COMMAND);
								sendMessage(ChatColor.GREEN + "teleporting to warp " + warp.getWarpName());
							} else {
								sendMessage(ChatColor.RED + "warp doesn't exist!");
							}
						} else {
							getWarning(WarningType.PLAYER_ONLY);
						}
					}
				} else if(args.length == 2) {
					Player p = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]).getPlayer();
					if(p instanceof Player) {
						if(pl.getManagers().getWarpManager().isWarp(args[1])) {
							Warp warp = pl.getManagers().getWarpManager().getWarp(args[1], (Player)sender);
							p.teleport(warp.getWarpLocation(), TeleportCause.COMMAND);
							sendMessageTo(p, ChatColor.GREEN + "teleporting to warp " + warp.getWarpName());
						} else {
							sendMessage(ChatColor.RED + "warp doesn't exist!");
						}
					} else {
						getWarning(WarningType.NEVER_PLAYED_BEFORE);
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
		sender.sendMessage(ChatColor.GOLD + ".oO___[warp help]___Oo.");
		sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/warp help " + ChatColor.WHITE + ": shows help");
		sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/warp <warp> " + ChatColor.WHITE + ": allows you to teleport to the warp");
		if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
			sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/warp <player> <warp> " + ChatColor.WHITE + ": teleports a player to a warp");
		}
	}

}
