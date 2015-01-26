package tv.mineinthebox.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.Home;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdHomeInvite {

	private final xEssentials pl;

	public CmdHomeInvite(xEssentials pl) {
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("homeinvite")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_HOME_INVITE.getPermission())) {
					if(args.length == 0) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[Home invite help]___Oo.");
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/homeinvite help " + ChatColor.WHITE + ": shows help");
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/homeinvite accept " + ChatColor.WHITE + ": accept a home invite of a player");
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/homeinvite deny " + ChatColor.WHITE + ": deny a home invite of a player");	
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/homeinvite <player> " + ChatColor.WHITE + ": invites a player to your default home");
						sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/homeinvite <player> <home>" + ChatColor.WHITE + ": invites a player to your custom home");	
					} else if(args.length == 1) {
						if(args[0].equalsIgnoreCase("help")) {
							sender.sendMessage(ChatColor.GOLD + ".oO___[Home invite help]___Oo.");
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/homeinvite help " + ChatColor.WHITE + ": shows help");
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/homeinvite accept " + ChatColor.WHITE + ": accept a home invite of a player");
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/homeinvite deny " + ChatColor.WHITE + ": deny a home invite of a player");	
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/homeinvite <player> " + ChatColor.WHITE + ": invites a player to your default home");
							sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/homeinvite <player> <home>" + ChatColor.WHITE + ": invites a player to your custom home");
						} else if(args[0].equalsIgnoreCase("accept")) {
							if(pl.getManagers().getHomeInviteManager().containsKey(sender.getName())) {
								Player p = (Player) sender;
								Home home = pl.getManagers().getHomeInviteManager().getRequestedHome(sender.getName());
								sender.sendMessage(ChatColor.GREEN + "you have accepted " + ChatColor.GRAY + pl.getManagers().getHomeInviteManager().get(sender.getName()).getUser() + ChatColor.GREEN + " his home request!");
								p.teleport(home.getLocation(), TeleportCause.COMMAND);
								XOfflinePlayer off = pl.getManagers().getHomeInviteManager().get(sender.getName());
								if(off.getPlayer() instanceof Player) {
									off.getPlayer().sendMessage(ChatColor.GRAY + sender.getName() + ChatColor.GREEN + " has accepted your home request");
								}
								pl.getManagers().getHomeInviteManager().remove(sender.getName());
								pl.getManagers().getHomeInviteManager().removeRequestedHome(sender.getName());
							} else {
								sender.sendMessage(ChatColor.RED + "you don't have home requests open!");
							}
						} else if(args[0].equalsIgnoreCase("deny")) {
							if(pl.getManagers().getHomeInviteManager().containsKey(sender.getName())) {
								XOfflinePlayer off = pl.getManagers().getHomeInviteManager().get(sender.getName());
								if(off.getPlayer() instanceof Player) {
									off.getPlayer().sendMessage(ChatColor.RED + "your invite has been canceled by " + ChatColor.GRAY + sender.getName());
								}
								sender.sendMessage(ChatColor.GREEN + "successfully canceled home invite for player " + ChatColor.GRAY + off.getUser());
								pl.getManagers().getHomeInviteManager().remove(sender.getName());
								pl.getManagers().getHomeInviteManager().removeRequestedHome(sender.getName());
							} else {
								sender.sendMessage(ChatColor.RED + "you don't have home requests open!");
							}
						} else {
							if(!pl.getManagers().getHomeInviteManager().containsKey(args[0])) {
								XOfflinePlayer xp = pl.getManagers().getPlayerManager().getOfflinePlayer(sender.getName());
								if(xp.hasHome()) {
									Player victem = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]).getPlayer();
									if(victem instanceof Player) {
										XPlayer xpp = pl.getManagers().getPlayerManager().getPlayer(victem.getName());
										if(xpp.isVanished()) {
											sender.sendMessage(ChatColor.RED + "player is not online!");
											return false;
										}
										pl.getManagers().getHomeInviteManager().put(victem.getName(), xp);
										if(pl.getManagers().getHomeInviteManager().setRequestedHome(victem.getName(), "default")) {
											delayed(victem.getName(), xp.getUser());
											sender.sendMessage(ChatColor.GREEN + "you successfully sended a home request to " + ChatColor.GRAY + victem.getName());
											victem.sendMessage(ChatColor.GRAY + sender.getName() + ChatColor.GREEN + " has invited you to his default home type:");
											victem.sendMessage(ChatColor.GREEN + "/hi accept - you get teleported to his home");
											victem.sendMessage(ChatColor.GREEN + "/hi deny - you don't get teleported to his home");
										} else {
											sender.sendMessage(ChatColor.RED + "you don't have set any home!");
										}
									} else {
										sender.sendMessage(ChatColor.RED + "player is not online!");
									}
								} else {
									sender.sendMessage(ChatColor.RED + "you don't have any homes set!");
								}
							} else {
								sender.sendMessage(ChatColor.RED + "you cannot invite this player when he already is invited by you!");
							}
						}
					} else if(args.length == 2) {
						if(!pl.getManagers().getHomeInviteManager().containsKey(args[0])) {
							XOfflinePlayer xp = pl.getManagers().getPlayerManager().getOfflinePlayer(sender.getName());
							if(xp.hasHome()) {
								Player victem = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]).getPlayer();
								if(victem instanceof Player) {
									XPlayer xpp = pl.getManagers().getPlayerManager().getPlayer(victem.getName());
									if(xpp.isVanished()) {
										sender.sendMessage(ChatColor.RED + "player is not online!");
										return false;
									}
									pl.getManagers().getHomeInviteManager().put(victem.getName(), xp);
									if(pl.getManagers().getHomeInviteManager().setRequestedHome(victem.getName(), args[1])) {
										delayed(victem.getName(), xp.getUser());
										sender.sendMessage(ChatColor.GREEN + "you successfully sended a home request to " + ChatColor.GRAY + victem.getName());
										victem.sendMessage(ChatColor.GRAY + sender.getName() + ChatColor.GREEN + " has invited you to his default home type:");
										victem.sendMessage(ChatColor.GREEN + "/hi accept - you get teleported to his home");
										victem.sendMessage(ChatColor.GREEN + "/hi deny - you don't get teleported to his home");
									} else {
										sender.sendMessage(ChatColor.RED + "you don't have set any home!");
									}
								} else {
									sender.sendMessage(ChatColor.RED + "player is not online!");
								}
							} else {
								sender.sendMessage(ChatColor.RED + "you don't have any homes set!");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "you cannot invite this player when he already is invited by you!");
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

	private void delayed(final String requester, final String victem) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(xEssentials.getPlugin(), new Runnable() {

			@Override
			public void run() {
				if(pl.getManagers().getHomeInviteManager().containsKey(requester)) {
					Player rp = pl.getManagers().getPlayerManager().getOfflinePlayer(victem).getPlayer();
					Player vp = pl.getManagers().getPlayerManager().getOfflinePlayer(requester).getPlayer();
					if(rp instanceof Player) {
						rp.sendMessage(ChatColor.GRAY + requester + ChatColor.RED + " has not accepted your home request, over time.");
					}
					if(vp instanceof Player) {
						vp.sendMessage(ChatColor.GRAY + victem + ChatColor.GREEN + " his home invite has been over time and canceled out.");
					}
					pl.getManagers().getHomeInviteManager().remove(requester);
					pl.getManagers().getHomeInviteManager().removeRequestedHome(requester);
				}
			}

		}, 1000);
	}

}
