package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.Modreq;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdClaim extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdClaim(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("claim")) {
			if(sender.hasPermission(PermissionKey.CMD_CLAIM.getPermission())) {
				if(args.length == 0) {
					showHelp();
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						showHelp();
					} else {
						sendMessage("you forgot to use the tickets ID as second argument");
					}
				} else {
					if(pl.getManagers().getPlayerManager().isOnline(args[0])) {
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[0]);
						if(xp.hasModreqsOpen()) {
							try {
								int id = Integer.parseInt(args[1]);
								if(xp.isValidModreqId(id)) {
									Modreq mod = xp.getModreq(id);
									Player p = xp.getBukkitPlayer();
									sendMessageTo(p, sender.getName() + " has claimed your modreq with id: "+ mod.getId());
									sendMessage("successfully claimed modreq of player " + p.getName() + " with id: " + mod.getId());
								} else {
									sendMessage("this is a invalid modreq id");
								}
							} catch(NumberFormatException e) {
								sendMessage("second argument need to be a number only!");
							}
						} else {
							sendMessage("could not claim any modreq ticket");
						}
					} else {
						sendMessage("could not claim a ticket when a player is offline!");
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
		sender.sendMessage(ChatColor.GOLD + ".oO___[claim modreq help]___Oo.");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/claim help " + ChatColor.WHITE + ": shows help");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/claim <player> <id> " + ChatColor.WHITE + ": claim a modreq of a player!");
	}

}
