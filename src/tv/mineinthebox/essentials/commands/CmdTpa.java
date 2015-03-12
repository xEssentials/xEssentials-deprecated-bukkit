package tv.mineinthebox.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdTpa extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdTpa(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	public boolean execute(final CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("tpa")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_TPA.getPermission())) {
					if(args.length == 0) {
						showHelp();
					} else if(args.length == 1) {
						if(args[0].equalsIgnoreCase("help")) {
							showHelp();
						} else {
							final Player victem = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]).getPlayer();
							final String victemName = victem.getName();
							final String senderName = sender.getName();
							if(victem instanceof Player) {
								XPlayer xpp = pl.getManagers().getPlayerManager().getPlayer(victem.getName());
								if(xpp.isVanished()) {
									sendMessage(ChatColor.RED + "this player is offline!");
									return false;
								}
								if(!(pl.getManagers().getTpaManager().containsKey(victem.getName()) || pl.getManagers().getTpaManager().containsValue(victem.getName()))) {
									pl.getManagers().getTpaManager().put(victem.getName(), sender.getName());
									Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {

										@Override
										public void run() {
											if(pl.getManagers().getTpaManager().containsKey(victemName)) {
												pl.getManagers().getTpaManager().remove(victemName);
												if(sender instanceof Player) {
													sendMessage(ChatColor.RED + "the tpa request to player " + victemName + " has been over time!");
												}
												if(victem instanceof Player) {
													sendMessageTo(victem, ChatColor.RED + "the tpa request from " + senderName + " been canceled out");
												}
											}
										}

									}, 5000);
									sendMessage(ChatColor.GREEN + "tpa request sent to player " + victem.getName());
									sendMessageTo(victem, ChatColor.GREEN + sender.getName() + " wants to teleport to you");
									sendMessageTo(victem, ChatColor.GREEN + "type: " + ChatColor.GRAY + "/tpaccept - so the player can teleport to you");
									sendMessageTo(victem, ChatColor.GREEN + "type: " + ChatColor.GRAY + "/tpdeny - so the player cannot teleport to you");
								} else {
									sendMessage(ChatColor.RED + "this player has to many tpa requests open!");
								}
							} else {
								sendMessage(ChatColor.RED + "this player is offline!");
							}
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
		sender.sendMessage(ChatColor.GOLD + ".oO___[tpa help]___Oo.");
		sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/tpa help " + ChatColor.WHITE + ": shows help");
		sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/tpa <player> " + ChatColor.WHITE + ": sent a tpa request to a player");
	}

}
