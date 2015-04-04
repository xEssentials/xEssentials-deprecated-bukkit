package tv.mineinthebox.essentials.commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.Modreq;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdModreq extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdModreq(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("modreq")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_MODREQ.getPermission())) {
					if(args.length == 0) {
						showHelp();
					} else if(args.length == 1) {
						if(args[0].equalsIgnoreCase("help")) {
							showHelp();
						} else {
							if(pl.getManagers().getPlayerManager().isOnline(sender.getName())) {
								XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
								xp.createModreq(args[0]);
								int lastID = xp.getModreqs().length;
								Modreq mod = xp.getModreq((lastID-1));
								for(Player p : Bukkit.getOnlinePlayers()) {
									if(p.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
										sendMessageTo(p, "[Modreq]" + sender.getName() + " has created a new modreq!");
										sendMessageTo(p, "[Modreq]" + "id: " + mod.getId() +" message: " + mod.getMessage() + " date: " + mod.getDate());
									}
								}
								sendMessage("[Modreq]" + "successfully created a modreq!");
							} else {
								sendMessage("something went wrong please reload xEssentials!");
							}
						}
					} else {
						String message = Arrays.toString(args).replace("[", "").replace("]", "").replace(",", "");
						if(pl.getManagers().getPlayerManager().isOnline(sender.getName())) {
							XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
							xp.createModreq(message);
							int lastID = xp.getModreqs().length;
							Modreq mod = xp.getModreq((lastID-1));
							for(Player p : Bukkit.getOnlinePlayers()) {
								if(p.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
									sendMessageTo(p, "[Modreq]" + sender.getName() +" has created a new modreq!");
									sendMessageTo(p,"[Modreq]" + "id: " + mod.getId() + " message: " + mod.getMessage() + " date: " + mod.getDate());
								}
							}
							sendMessage("[Modreq]" + "successfully created a modreq!");
						} else {
							sendMessage("something went wrong please reload xEssentials!");
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

	@Override
	public void showHelp() {
		sender.sendMessage(ChatColor.GOLD + ".oO___[modreq help]___Oo.");
		sender.sendMessage(ChatColor.GRAY + "Default: " + ChatColor.GRAY + "/modreq " + ChatColor.WHITE + ": shows help");
		sender.sendMessage(ChatColor.GRAY + "Default: " + ChatColor.GRAY + "/modreq help " + ChatColor.WHITE + ": shows help");
		sender.sendMessage(ChatColor.GRAY + "Default: " + ChatColor.GRAY + "/modreq <message> " + ChatColor.WHITE + ": creates a modreq");
	}

}
