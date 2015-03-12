package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdSethome extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdSethome(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("sethome")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_SET_HOME.getPermission())) {
					if(args.length == 0) {
						if(pl.getManagers().getPlayerManager().isOnline(sender.getName())) {
							XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
							xp.setHome("default");
							sendMessage(ChatColor.GREEN + "successfully set default home ;-)");
						} else {
							sendMessage(ChatColor.RED + "something went wrong, please relog");
						}
					} else if(args.length == 1) {
						if(args[0].equalsIgnoreCase("help")) {
							showHelp();
						} else if(args[0].equalsIgnoreCase("list")) {
							sendMessage(ChatColor.RED + "you cannot use this name!");
						} else if(args[0].equalsIgnoreCase("delete")) {
							sendMessage(ChatColor.RED + "you cannot use this name!");
						} else if(args[0].equalsIgnoreCase("remove")) {
							sendMessage(ChatColor.RED + "you cannot use this name!");
						} else if(args[0].equalsIgnoreCase("default")) {
							sendMessage(ChatColor.RED + "you cannot use this name!, use /sethome instead");
						} else {
							if(sender.hasPermission(PermissionKey.MULTIPLE_HOMES.getPermission()) || pl.getConfiguration().getPlayerConfig().canUseMoreHomes()) {
								if(!pl.getManagers().getPlayerManager().isEssentialsPlayer(args[0])) {
									XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
									if(xp.isStaff()) {
										xp.setHome(args[0]);
										sendMessage(ChatColor.GREEN + "you successfully set your new custom home!");
									} else if(!(xp.getAmountOfHomes() > (pl.getConfiguration().getPlayerConfig().getMaxHomesAllowed())-1)) {
										xp.setHome(args[0]);
										sendMessage(ChatColor.GREEN + "you successfully set your new custom home!");
									} else {
										sendMessage(ChatColor.RED + "you reached above the limit to set homes!");
									}
								} else {
									sendMessage(ChatColor.RED + "you cannot set a home on a players name!");
								}
							} else {
								getWarning(WarningType.NO_PERMISSION);
							}
						}
					}
				} else {
					getWarning(WarningType.NO_PERMISSION);
				}
			} else {
				getWarning(WarningType.NO_PERMISSION);
			}
		}
		return false;
	}
	
	@Override
	public void showHelp() {
		sender.sendMessage(ChatColor.GOLD + ".oO___[sethome help]___Oo.");
		sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/sethome " + ChatColor.WHITE + ": allows you to set your home");
		sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/sethome <home name>" + ChatColor.WHITE + ": allows you to set your extra custom home");
	}

}
