package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;

public class CmdxEssentials extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdxEssentials(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("xEssentials")) {
			if(sender.hasPermission(PermissionKey.CMD_XESSENTIALS.getPermission())) {
				if(args.length == 0) {
					showHelp();
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						showHelp();
					} else if(args[0].equalsIgnoreCase("reload")) {
						sendMessage("reloading xEssentials version " + pl.getDescription().getVersion());
						pl.getConfiguration().reload();
						sendMessage("reload completed!");
					} else if(args[0].equalsIgnoreCase("tps")) {
						if(sender.hasPermission(PermissionKey.CMD_TPS.getPermission())) {
							ChatColor tpsColor = null;
							sender.sendMessage(ChatColor.GOLD + ".oO___[tps]___Oo.");
							sender.sendMessage(ChatColor.GRAY + "Gc max: " + pl.getManagers().getTPSManager().garbageCollectorMax() + "mb");
							sender.sendMessage(ChatColor.GRAY + "Free memory: " + pl.getManagers().getTPSManager().getFreeMemory() + "mb");
							sender.sendMessage(ChatColor.GRAY + "Max memory " + pl.getManagers().getTPSManager().getMemoryMax() + "mb");
							if(pl.getManagers().getTPSManager().getServerTicks() < 15) {
								tpsColor = ChatColor.GREEN;
							} else if(pl.getManagers().getTPSManager().getServerTicks() > 15 && pl.getManagers().getTPSManager().getServerTicks() < 25) {
								tpsColor = ChatColor.YELLOW;
							} else if(pl.getManagers().getTPSManager().getServerTicks() > 25) {
								tpsColor = ChatColor.RED;
							}
							sender.sendMessage(ChatColor.GRAY + "ticks: " + tpsColor + pl.getManagers().getTPSManager().getServerTicks());
						} else {
							getWarning(WarningType.NO_PERMISSION);
						}
					}
				}
			} else {
				getWarning(WarningType.NO_PERMISSION);
			}
		}
		return false;
	}
	
	public void showHelp() {
		sender.sendMessage(ChatColor.GOLD + ".oO___[xEssentials version " + pl.getDescription().getVersion() + "___Oo.");
		sender.sendMessage(ChatColor.GREEN + "this plugin is written by Xeph0re AKA xize ");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/xEssentials reload " + ChatColor.WHITE + ": reloads the plugin");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/xEssentials help " + ChatColor.WHITE + ": shows help");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/xEssentials tps " + ChatColor.WHITE + ": shows tps of the server");
	}
}
