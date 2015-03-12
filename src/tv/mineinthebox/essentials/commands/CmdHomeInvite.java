package tv.mineinthebox.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.Home;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdHomeInvite extends CommandTemplate {

	private final xEssentials pl;

	public CmdHomeInvite(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("homeinvite")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_HOME_INVITE.getPermission())) {
					if(args.length == 0) {
						showHelp();
					} else if(args.length == 1) {
						if(args[0].equalsIgnoreCase("help")) {
							showHelp();
						} else if(args[0].equalsIgnoreCase("accept")) {
							if(pl.getManagers().getHomeInviteManager().containsKey(sender.getName())) {
								Player p = (Player) sender;
								Home home = pl.getManagers().getHomeInviteManager().getRequestedHome(sender.getName());
								sendMessage(ChatColor.GREEN + "you have accepted " + ChatColor.GRAY + pl.getManagers().getHomeInviteManager().get(sender.getName()).getUser() + ChatColor.GREEN + " his home request!");
								p.teleport(home.getLocation(), TeleportCause.COMMAND);
								XOfflinePlayer off = pl.getManagers().getHomeInviteManager().get(sender.getName());
								if(off.getPlayer() instanceof Player) {
									sendMessageTo(off.getPlayer(), ChatColor.GRAY + sender.getName() + ChatColor.GREEN + " has accepted your home request");
								}
								pl.getManagers().getHomeInviteManager().remove(sender.getName());
								pl.getManagers().getHomeInviteManager().removeRequestedHome(sender.getName());
							} else {
								sendMessage(ChatColor.RED + "you don't have home requests open!");
							}
						} else if(args[0].equalsIgnoreCase("deny")) {
							if(pl.getManagers().getHomeInviteManager().containsKey(sender.getName())) {
								XOfflinePlayer off = pl.getManagers().getHomeInviteManager().get(sender.getName());
								if(off.getPlayer() instanceof Player) {
									sendMessageTo(off.getPlayer(), ChatColor.RED + "your invite has been canceled by " + ChatColor.GRAY + sender.getName());
								}
								sendMessage(ChatColor.GREEN + "successfully canceled home invite for player " + ChatColor.GRAY + off.getUser());
								pl.getManagers().getHomeInviteManager().remove(sender.getName());
								pl.getManagers().getHomeInviteManager().removeRequestedHome(sender.getName());
							} else {
								sendMessage(ChatColor.RED + "you don't have home requests open!");
							}
						} else {
							if(!pl.getManagers().getHomeInviteManager().containsKey(args[0])) {
								XOfflinePlayer xp = pl.getManagers().getPlayerManager().getOfflinePlayer(sender.getName());
								if(xp.hasHome()) {
									Player victem = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]).getPlayer();
									if(victem instanceof Player) {
										XPlayer xpp = pl.getManagers().getPlayerManager().getPlayer(victem.getName());
										if(xpp.isVanished()) {
											sendMessage(ChatColor.RED + "player is not online!");
											return false;
										}
										pl.getManagers().getHomeInviteManager().put(victem.getName(), xp);
										if(pl.getManagers().getHomeInviteManager().setRequestedHome(victem.getName(), "default")) {
											delayed(victem.getName(), xp.getUser());
											sendMessage(ChatColor.GREEN + "you successfully sended a home request to " + ChatColor.GRAY + victem.getName());
											sendMessageTo(victem, ChatColor.GRAY + sender.getName() + ChatColor.GREEN + " has invited you to his default home type:");
											sendMessageTo(victem, ChatColor.GREEN + "/hi accept - you get teleported to his home");
											sendMessageTo(victem, ChatColor.GREEN + "/hi deny - you don't get teleported to his home");
										} else {
											sendMessage(ChatColor.RED + "you don't have set any home!");
										}
									} else {
										sendMessage(ChatColor.RED + "player is not online!");
									}
								} else {
									sendMessage(ChatColor.RED + "you don't have any homes set!");
								}
							} else {
								sendMessage(ChatColor.RED + "you cannot invite this player when he already is invited by you!");
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
										sendMessage(ChatColor.RED + "player is not online!");
										return false;
									}
									pl.getManagers().getHomeInviteManager().put(victem.getName(), xp);
									if(pl.getManagers().getHomeInviteManager().setRequestedHome(victem.getName(), args[1])) {
										delayed(victem.getName(), xp.getUser());
										sendMessage(ChatColor.GREEN + "you successfully sended a home request to " + ChatColor.GRAY + victem.getName());
										sendMessageTo(victem, ChatColor.GRAY + sender.getName() + ChatColor.GREEN + " has invited you to his default home type:");
										sendMessageTo(victem, ChatColor.GREEN + "/hi accept - you get teleported to his home");
										sendMessageTo(victem, ChatColor.GREEN + "/hi deny - you don't get teleported to his home");
									} else {
										sendMessage(ChatColor.RED + "you don't have set any home!");
									}
								} else {
									sendMessage(ChatColor.RED + "player is not online!");
								}
							} else {
								sendMessage(ChatColor.RED + "you don't have any homes set!");
							}
						} else {
							sendMessage(ChatColor.RED + "you cannot invite this player when he already is invited by you!");
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

	private void delayed(final String requester, final String victem) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {

			@Override
			public void run() {
				if(pl.getManagers().getHomeInviteManager().containsKey(requester)) {
					Player rp = pl.getManagers().getPlayerManager().getOfflinePlayer(victem).getPlayer();
					Player vp = pl.getManagers().getPlayerManager().getOfflinePlayer(requester).getPlayer();
					if(rp instanceof Player) {
						sendMessageTo(rp, ChatColor.GRAY + requester + ChatColor.RED + " has not accepted your home request, over time.");
					}
					if(vp instanceof Player) {
						sendMessageTo(vp, ChatColor.GRAY + victem + ChatColor.GREEN + " his home invite has been over time and canceled out.");
					}
					pl.getManagers().getHomeInviteManager().remove(requester);
					pl.getManagers().getHomeInviteManager().removeRequestedHome(requester);
				}
			}

		}, 1000);
	}

	@Override
	public void showHelp() {
		sender.sendMessage(ChatColor.GOLD + ".oO___[Home invite help]___Oo.");
		sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/homeinvite help " + ChatColor.WHITE + ": shows help");
		sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/homeinvite accept " + ChatColor.WHITE + ": accept a home invite of a player");
		sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/homeinvite deny " + ChatColor.WHITE + ": deny a home invite of a player");	
		sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/homeinvite <player> " + ChatColor.WHITE + ": invites a player to your default home");
		sender.sendMessage(ChatColor.DARK_GRAY + "Default: " + ChatColor.GRAY + "/homeinvite <player> <home>" + ChatColor.WHITE + ": invites a player to your custom home");	
	}

}
