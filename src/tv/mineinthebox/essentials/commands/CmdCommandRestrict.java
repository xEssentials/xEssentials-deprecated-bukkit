package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.RestrictedCommand;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;

public class CmdCommandRestrict extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdCommandRestrict(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("CommandRestrict")) {
			if(sender.hasPermission(PermissionKey.CMD_COMMAND_RESTRICT.getPermission())) {
				if(args.length == 0) {
					showHelp();
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						showHelp();
					} else {
						createRestriction(args, sender);
					}
				} else if(args.length == 2) {
					if(args[0].equalsIgnoreCase("list")) {
						XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[1]);
						if(off.hasCommandRestrictions()) {
							sender.sendMessage(ChatColor.GOLD + ".oO___[Restricted command list for player " + off.getName() + "]___Oo.");
							for(int i = 0; i < off.getCommandRestrictions().size(); i++) {
								sender.sendMessage(ChatColor.GREEN +""+ i + ChatColor.GRAY + ": " + off.getCommandRestrictions().get(i).getCommand());
							}
						} else {
							sendMessage(off.getName() + " doesn't have any command restrictions!");
						}
					} else {
						createRestriction(args, sender);
					}
				} else if(args.length == 3) {
					if(args[0].equalsIgnoreCase("remove")) {
						if(pl.getManagers().getPlayerManager().isEssentialsPlayer(args[1])) {
							if(isNumeric(args[2])) {
								try {
									Integer i = Integer.parseInt(args[2]);
									XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[1]);
									RestrictedCommand restriction = off.getCommandRestrictions().get(i);
									off.removeCommandRestriction(restriction);
									sendMessage("you have successfully removed the command restriction of player " + off.getName());
								} catch(IndexOutOfBoundsException e) {
									sendMessage("this id does not exist!");
								}
							} else {
								sendMessage("the third argument is not a number!");
							}
						} else {
							getWarning(WarningType.NEVER_PLAYED_BEFORE);
						}
					} else {
						createRestriction(args, sender);
					}
				} else {
					createRestriction(args, sender);
				}
			} else {
				getWarning(WarningType.NO_PERMISSION);
			}
		}
		return false;
	}

	private void createRestriction(String[] args, CommandSender sender) {
		String arg = toString(args);
		if(arg.contains(":")) {
			String[] split = arg.split(":");
			if(split.length == 3) {
				String playername = split[0];
				String restrictedCommand = split[1];
				String message = split[2];
				if(pl.getManagers().getPlayerManager().isEssentialsPlayer(playername)) {
					XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(playername);
					if(!off.hasContainedRestriction(restrictedCommand)) {
						off.setCommandRestriction(restrictedCommand, message, null);
						sendMessage("you have successfully set the restricted command for player " + off.getName());
					} else {
						sendMessage("this player has already this command restricted!");
					}
				} else {
					sendMessage("this player has never played before!");
				}
			} else if(split.length == 4) {
				String playername = split[0];
				String restrictedCommand = split[1];
				String message = split[2];
				String taskcommand = split[3];
				if(pl.getManagers().getPlayerManager().isEssentialsPlayer(playername)) {
					XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(playername);
					if(!off.hasContainedRestriction(restrictedCommand)) {
						off.setCommandRestriction(restrictedCommand, message, taskcommand);
						sendMessage("you have successfully set the restricted command for player " + off.getName());
					} else {
						sendMessage("this player has already this command restricted!");
					}
				} else {
					sendMessage("this player has never played before!");
				}
			} else {
				sendMessage("invalid arguments!");
			}
		} else {
			sendMessage("we don't know much about these arguments!");
		}
	}

	private boolean isNumeric(String s) {
		try {
			Integer i = Integer.parseInt(s);
			if(i != null) {
				return true;
			}
		} catch(NumberFormatException e) {
			return false;
		}
		return false;
	}
	
	private String toString(String[] args) {
		StringBuilder build = new StringBuilder();
		for(int i = 0; i<args.length;i++) {
			if(i == args.length) {
				build.append(args[i].replace(",", ""));
			} else {
				build.append(args[i].replace(",", "") + " ");
			}
		}
		return build.toString();
	}

	@Override
	public void showHelp() {
		sender.sendMessage(ChatColor.GOLD + ".oO___[CommandRestriction help]___Oo.");
		sender.sendMessage(ChatColor.RED + "warning, use this inside one line!, do not create this with spaces!");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/commandrestrict <player:command>:<message>" + ChatColor.WHITE + ": restrict a command specific for this player, with a custom message");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/commandrestrict <player:command>:<message>:<taskcommand> " + ChatColor.WHITE + ": restrict a command specific for this player, with a custom task");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/commandrestrict list <player> " + ChatColor.WHITE + ": shows a list with id's of current restricted commands of the player");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/commandrestrict remove <player> <id> " + ChatColor.WHITE + ": remove a id from the list of this player");
	}
}
