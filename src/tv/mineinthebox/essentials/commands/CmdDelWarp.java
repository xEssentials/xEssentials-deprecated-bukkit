package tv.mineinthebox.essentials.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.Warp;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdDelWarp extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdDelWarp(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}
	
	private List<String> getwarps(CommandSender sender, String p, Boolean playerOnly) {
		List<String> tabs = new ArrayList<String>();
		if(!playerOnly) {
			List<Warp> s = new ArrayList<Warp>(Arrays.asList(pl.getManagers().getWarpManager().getWarps()));
			for(Warp warp : s) {
				if(warp.getWarpName().toLowerCase().startsWith(p.toLowerCase())) {
					tabs.add(warp.getWarpName());
				}
			}	
		} else {
			if(pl.getManagers().getPlayerManager().isEssentialsPlayer(sender.getName())) {
				XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
				for(Warp warp : pl.getManagers().getWarpManager().getWarps(xp.getPlayer())) {
					if(warp.getWarpName().toLowerCase().startsWith(p.toLowerCase())) {
						tabs.add(warp.getWarpName());
					}
				}
			}
		}
		return tabs;
	}
	
	public List<String> onTabComplete(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("delwarp")) {
			if(args.length == 1) {
				if(sender.hasPermission(PermissionKey.CMD_DELWARP.getPermission())) {
					if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
						List<String> list = getwarps(sender, args[0], false);
						return list;	
					} else {
						List<String> list = getwarps(sender, args[0], true);
						return list;	
					}
				}
			}
		}
		return null;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("delwarp")) {
			if(sender.hasPermission(PermissionKey.CMD_DELWARP.getPermission())) {
				if(args.length == 0) {
					showHelp();
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						showHelp();
					} else if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
						if(pl.getManagers().getWarpManager().isWarp(args[0].toLowerCase())) {
							Warp warp = pl.getManagers().getWarpManager().getWarp(args[0].toLowerCase(), (Player)sender);
							if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
								warp.removeWarp();
								sendMessage(ChatColor.GREEN + "you successfully removed the warp ");
							} else {
								sendMessage(ChatColor.RED + "you dont have permission to remove the warp");
							}
						} else {
							sendMessage(ChatColor.RED + "warp does not exist!");
						}
					} else {
						if(pl.getManagers().getWarpManager().isWarp(args[0].toLowerCase())) {
							Warp warp = pl.getManagers().getWarpManager().getWarp(args[0].toLowerCase(), (Player)sender);
							if(warp.isOwner()) {
								sendMessage(ChatColor.GREEN + "you successfully removed the warp ");
							} else {
								sendMessage(ChatColor.RED + "you are not the owner of this warp!");
							}
						} else {
							sendMessage(ChatColor.RED + "warp does not exist!");
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
		sender.sendMessage(ChatColor.GOLD + ".oO___[delwarp help]___Oo.");
		sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/delwarp help " + ChatColor.WHITE + ": shows help");
		if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
			sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/delwarp <warp> " + ChatColor.WHITE + ": deletes the warp");
		} else {
			sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/delwarp <warp> " + ChatColor.WHITE + ": deletes the warp you own");
		}
	}

}
