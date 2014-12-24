package tv.mineinthebox.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdTpa {

	public boolean execute(final CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("tpa")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_TPA.getPermission())) {
					if(args.length == 0) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[tpa help]___Oo.");
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/tpa help " + ChatColor.WHITE + ": shows help");
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/tpa <player> " + ChatColor.WHITE + ": sent a tpa request to a player");
					} else if(args.length == 1) {
						if(args[0].equalsIgnoreCase("help")) {
							sender.sendMessage(ChatColor.GOLD + ".oO___[tpa help]___Oo.");
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/tpa help " + ChatColor.WHITE + ": shows help");
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/tpa <player> " + ChatColor.WHITE + ": sent a tpa request to a player");
						} else {
							final Player victem = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[0]).getPlayer();
							final String victemName = victem.getName();
							final String senderName = sender.getName();
							if(victem instanceof Player) {
								XPlayer xpp = xEssentials.getManagers().getPlayerManager().getPlayer(victem.getName());
								if(xpp.isVanished()) {
									sender.sendMessage(ChatColor.RED + "this player is offline!");
									return false;
								}
								if(!(xEssentials.getManagers().getTpaManager().containsKey(victem.getName()) || xEssentials.getManagers().getTpaManager().containsValue(victem.getName()))) {
									xEssentials.getManagers().getTpaManager().put(victem.getName(), sender.getName());
									Bukkit.getScheduler().scheduleSyncDelayedTask(xEssentials.getPlugin(), new Runnable() {

										@Override
										public void run() {
											if(xEssentials.getManagers().getTpaManager().containsKey(victemName)) {
												xEssentials.getManagers().getTpaManager().remove(victemName);
												if(sender instanceof Player) {
													sender.sendMessage(ChatColor.RED + "the tpa request to player " + victemName + " has been over time!");
												}
												if(victem instanceof Player) {
													victem.sendMessage(ChatColor.RED + "the tpa request from " + senderName + " been canceled out");
												}
											}
										}

									}, 5000);
									sender.sendMessage(ChatColor.GREEN + "tpa request sent to player " + victem.getName());
									victem.sendMessage(ChatColor.GREEN + sender.getName() + " wants to teleport to you");
									victem.sendMessage(ChatColor.GREEN + "type: " + ChatColor.GRAY + "/tpaccept - so the player can teleport to you");
									victem.sendMessage(ChatColor.GREEN + "type: " + ChatColor.GRAY + "/tpdeny - so the player cannot teleport to you");
								} else {
									sender.sendMessage(ChatColor.RED + "this player has to many tpa requests open!");
								}
							} else {
								sender.sendMessage(ChatColor.RED + "this player is offline!");
							}
						}
					}
				} else {
					Warnings.getWarnings(sender).noPermission();
				}
			} else {
				Warnings.getWarnings(sender).consoleMessage();
			}
		}
		return false;
	}

}
