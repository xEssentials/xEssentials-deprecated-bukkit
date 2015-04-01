package tv.mineinthebox.essentials.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdDoubleJump extends CommandTemplate {

	private final xEssentials pl;

	public CmdDoubleJump(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;	
	}

	private List<String> getPlayerByName(String p) {
		List<String> s = new ArrayList<String>();
		for(XOfflinePlayer name : pl.getManagers().getPlayerManager().getOfflinePlayers()) {
			if(name.getName().toUpperCase().startsWith(p.toUpperCase())) {
				s.add(name.getName());
			}
		}
		return s;
	}

	public List<String> onTabComplete(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("doublejump")) {
			if(args.length == 1) {
				if(sender.hasPermission(PermissionKey.CMD_DOUBLEJUMP.getPermission())) {
					List<String> list = getPlayerByName(args[0]);
					return list;
				}
			}
		}
		return null;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("doublejump")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_DOUBLEJUMP.getPermission())) {
					if(args.length == 0) {
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
						if(xp.hasDoubleJump()) {
							xp.setDoubleJump(false);
							sendMessage(ChatColor.GREEN + "disabled double jump");
						} else {
							xp.setDoubleJump(true);
							sendMessage(ChatColor.GREEN + "enabled double jump");
						}
					} else if(args.length == 1) {
						if(pl.getManagers().getPlayerManager().isEssentialsPlayer(args[0])) {
							if(pl.getManagers().getPlayerManager().isOnline(args[0])) {
								XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[0]);
								if(xp.hasDoubleJump()) {
									xp.setDoubleJump(false);
									sendMessageTo(xp.getBukkitPlayer(), ChatColor.GREEN + sender.getName() + " has turned off your double jump");
									sendMessage(ChatColor.GREEN + "disabled double jump on online player " + xp.getName());
								} else {
									xp.setDoubleJump(false);
									sendMessageTo(xp.getBukkitPlayer(), ChatColor.GREEN + sender.getName() + " has turned on your double jump");
									sendMessage(ChatColor.GREEN + "enabled double jump on online player " + xp.getName());
								}
							} else {
								sendMessage(ChatColor.RED + "player is offline!");
							}
						} else {
							getWarning(WarningType.NEVER_PLAYED_BEFORE);
						}
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
