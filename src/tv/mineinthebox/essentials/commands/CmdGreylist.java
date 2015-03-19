package tv.mineinthebox.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.GreyListCause;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.events.customevents.OfflinePlayerGreyListedEvent;
import tv.mineinthebox.essentials.events.customevents.PlayerGreyListedEvent;
import tv.mineinthebox.essentials.hook.Hooks;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;
public class CmdGreylist extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdGreylist(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("greylist")) {
			if(sender.hasPermission(PermissionKey.CMD_GREYLIST.getPermission())) {
				if(args.length == 0) {
					showHelp();
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						showHelp();
					}
				} else if(args.length == 2) {
					if(args[0].equalsIgnoreCase("add")) {
						if(pl.getManagers().getPlayerManager().isEssentialsPlayer(args[1])) {
							if(pl.getManagers().getPlayerManager().isOnline(args[1])) {
								XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[1]);
								xp.setGreyListed(true);
								if(Hooks.isVaultPermissionsEnabled()) {
									String oldGroup = pl.getManagers().getVaultManager().getGroup(xp.getPlayer());
									String newGroup = pl.getConfiguration().getGreyListConfig().getGroup();
									pl.getManagers().getVaultManager().setGroup(Bukkit.getWorlds().get(0), xp.getName(), newGroup);
									Bukkit.getPluginManager().callEvent(new PlayerGreyListedEvent(xp.getPlayer(), newGroup, oldGroup, GreyListCause.COMMAND, pl));
								} else {
									sendMessage(ChatColor.RED + "no vault installed!");
									return false;
								}
								sendMessage(ChatColor.GREEN + "you successfully greylisted " + xp.getName());
							} else {
								XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[1]);
								off.setGreyListed(true);
								if(Hooks.isVaultPermissionsEnabled()) {
									String oldGroup = pl.getManagers().getVaultManager().getGroup(Bukkit.getWorlds().get(0), off.getName());
									String newGroup = pl.getConfiguration().getGreyListConfig().getGroup();
									pl.getManagers().getVaultManager().setGroup(Bukkit.getWorlds().get(0), off.getName(), newGroup);
									Bukkit.getPluginManager().callEvent(new OfflinePlayerGreyListedEvent(off.getName(), newGroup, oldGroup, GreyListCause.COMMAND, pl));
								} else {
									sendMessage(ChatColor.RED + "no vault installed!");
									return false;
								}
								sendMessage(ChatColor.GREEN + "you successfully greylisted offline player " + off.getName());
							}
						} else {
							sendMessage(ChatColor.RED + "this player has never played before!");
						}
					} else if(args[0].equalsIgnoreCase("remove")) {
						if(pl.getManagers().getPlayerManager().isEssentialsPlayer(args[1])) {
							if(pl.getManagers().getPlayerManager().isOnline(args[1])) {
								XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(args[1]);
								xp.setGreyListed(false);
								if(Hooks.isVaultPermissionsEnabled()) {
									String DefaultGroup = pl.getManagers().getVaultManager().getDefaultGroup();
									pl.getManagers().getVaultManager().setGroup(Bukkit.getWorlds().get(0), xp.getName(), DefaultGroup);
								} else {
									sendMessage(ChatColor.RED + "no vault intalled!");
									return false;
								}
								sendMessage(ChatColor.GREEN + "you have successfully removed " + xp.getName() + " from the greylist!");
							} else {
								XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[1]);
								off.setGreyListed(false);
								if(Hooks.isVaultPermissionsEnabled()) {
									String DefaultGroup = pl.getManagers().getVaultManager().getDefaultGroup();
									pl.getManagers().getVaultManager().setGroup(Bukkit.getWorlds().get(0), off.getName(), DefaultGroup);
								} else {
									sendMessage(ChatColor.RED + "no vault intalled!");
									return false;
								}
								sendMessage(ChatColor.GREEN + "you have successfully removed " + off.getName() + " from the greylist!");
							}
						} else {
							sendMessage(ChatColor.RED + "this player has never played before!");
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
		sender.sendMessage(ChatColor.GOLD + ".oO___[greylist help]___Oo.");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/greylist help " + ChatColor.WHITE + ": shows help");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/greylist add <player> " + ChatColor.WHITE + ": excempt the player to the greylist");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/greylist remove <player> " + ChatColor.WHITE + ": remove the player from the greylist");
	}

}
