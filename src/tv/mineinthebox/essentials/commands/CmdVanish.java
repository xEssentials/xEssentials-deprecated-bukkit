package tv.mineinthebox.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.events.customevents.VanishChangeEvent;
import tv.mineinthebox.essentials.hook.Hooks;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdVanish extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdVanish(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("vanish")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_VANISH.getPermission())) {
					Player p = (Player) sender;
					XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(p.getName());
					if(args.length == 0) {
						if(xp.isVanished()) {
							xp.unvanish();
							xp.setNoPickUp(false);
							sendMessage("you are successfully unvanished ;-)");
							Bukkit.getPluginManager().callEvent(new VanishChangeEvent(p, xp));
						} else {
							xp.vanish();
							xp.setNoPickUp(true);
							sendMessage("you are successfully vanished ;-)");
							Bukkit.getPluginManager().callEvent(new VanishChangeEvent(p, xp));
						}
					} else if(args.length == 1) {
						if(args[0].equalsIgnoreCase("help")) {
							showHelp();
						} else if(args[0].equalsIgnoreCase("nopickup")) {
							if(xp.isVanished()) {
								if(xp.isNoPickUpEnabled()) {
									xp.setNoPickUp(false);
									sendMessage("successfully disabled nopickup, you can now pickup items and interact with chests");
								} else {
									xp.setNoPickUp(true);
									sendMessage("successfully enabled nopickup, you can now not pickup items and interact with chests");
								}
							} else {
								sendMessage("you can only use this option while being vanished!");
							}
						} else if(args[0].equalsIgnoreCase("effect")) {
							if(xp.hasVanishEffects()) {
								xp.setVanishEffects(false);
								sendMessage("successfully disabled vanish effects for yourself");
							} else {
								xp.setVanishEffects(true);
								sendMessage("successfully enabled vanish effects for yourself");
							}
						} else if(args[0].equalsIgnoreCase("fj") || args[0].equalsIgnoreCase("fakejoin")) {
							if(xp.isVanished()) {
								xp.unvanish(true);
								if(Hooks.isWorldGuardEnabled()) {
									Bukkit.broadcastMessage(pl.getManagers().getWorldGuardManager().sendJoinMessage(xp.getBukkitPlayer()));
								} else {
									if(xp.isStaff()) {
										if(xp.getUniqueId().toString().equalsIgnoreCase("1f23499f-a759-4dfb-b3e1-d3eceb591cac0")) {
											Bukkit.broadcastMessage(ChatColor.GRAY + "Developer of xEssentials " + ChatColor.GREEN + xp.getBukkitPlayer().getName() + ChatColor.GRAY + " has joined the game!");
										} else {
											Bukkit.broadcastMessage(ChatColor.GRAY + "Staff member " + ChatColor.GREEN + xp.getBukkitPlayer().getName() + ChatColor.GRAY + " has joined the game!");	
										}
									} else {
										Bukkit.broadcastMessage(ChatColor.GREEN + "player " + ChatColor.GRAY + xp.getBukkitPlayer().getName() + ChatColor.GREEN + " has joined the game!");
									}
								}
							} else {
								sendMessage("you can't fake join a server when you are already unvanished!");
							}
						} else if(args[0].equalsIgnoreCase("fq") || args[0].equalsIgnoreCase("fakequit")) {
							xp.vanish();
							if(Hooks.isWorldGuardEnabled()) {
								Bukkit.broadcastMessage(pl.getManagers().getWorldGuardManager().sendQuitMessage(xp.getBukkitPlayer()));
							} else {
								if(xp.isStaff()) {
									if(xp.getUniqueId().toString().equalsIgnoreCase("1f23499f-a759-4dfb-b3e1-d3eceb591cac0")) {
										Bukkit.broadcastMessage(ChatColor.GRAY + "Developer of xEssentials " + ChatColor.GREEN + xp.getBukkitPlayer().getName() + ChatColor.GRAY + " has left the game!");
									} else {
										Bukkit.broadcastMessage(ChatColor.GRAY + "Staff member " + ChatColor.GREEN + xp.getBukkitPlayer().getName() + ChatColor.GRAY + " has left the game!");	
									}
								} else {
									Bukkit.broadcastMessage(ChatColor.GREEN + "player " + ChatColor.GRAY + xp.getBukkitPlayer().getName() + ChatColor.GREEN + " has left the game!");
								}
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
		sender.sendMessage(ChatColor.GOLD + ".oO___[vanish help]___Oo.");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/vanish " + ChatColor.WHITE + ": vanish yourself for other players");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/vanish nopickup " + ChatColor.WHITE + ": toggles nopickup or de-toggles nopickup");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/vanish effect " + ChatColor.WHITE + ": toggles the effects of vanish");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/vanish fq " + ChatColor.WHITE + ": fake quit from the server while being vanish");
		sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/vanish fj " + ChatColor.WHITE + ": fake join from the server while being unvanished");
	}

}
