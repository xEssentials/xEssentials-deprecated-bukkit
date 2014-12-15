package tv.mineinthebox.bukkit.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.bukkit.essentials.Warnings;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;
import tv.mineinthebox.bukkit.essentials.events.customevents.VanishChangeEvent;
import tv.mineinthebox.bukkit.essentials.hook.Hooks;
import tv.mineinthebox.bukkit.essentials.hook.WorldGuardHook;
import tv.mineinthebox.bukkit.essentials.interfaces.XPlayer;

public class CmdVanish {

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("vanish")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_VANISH.getPermission())) {
					Player p = (Player) sender;
					XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(p.getName());
					if(args.length == 0) {
						if(xp.isVanished()) {
							xp.unvanish();
							xp.setNoPickUp(false);
							sender.sendMessage(ChatColor.GREEN + "you are successfully unvanished ;-)");
							Bukkit.getPluginManager().callEvent(new VanishChangeEvent(p, xp));
						} else {
							xp.vanish();
							xp.setNoPickUp(true);
							sender.sendMessage(ChatColor.GREEN + "you are successfully vanished ;-)");
							Bukkit.getPluginManager().callEvent(new VanishChangeEvent(p, xp));
						}
					} else if(args.length == 1) {
						if(args[0].equalsIgnoreCase("help")) {
							sender.sendMessage(ChatColor.GOLD + ".oO___[vanish help]___Oo.");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/vanish " + ChatColor.WHITE + ": vanish yourself for other players");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/vanish nopickup " + ChatColor.WHITE + ": toggles nopickup or de-toggles nopickup");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/vanish effect " + ChatColor.WHITE + ": toggles the effects of vanish");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/vanish fq " + ChatColor.WHITE + ": fake quit from the server while being vanish");
							sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/vanish fj " + ChatColor.WHITE + ": fake join from the server while being unvanished");
						} else if(args[0].equalsIgnoreCase("nopickup")) {
							if(xp.isVanished()) {
								if(xp.isNoPickUpEnabled()) {
									xp.setNoPickUp(false);
									sender.sendMessage(ChatColor.GREEN + "successfully disabled nopickup, you can now pickup items and interact with chests");
								} else {
									xp.setNoPickUp(true);
									sender.sendMessage(ChatColor.GREEN + "successfully enabled nopickup, you can now not pickup items and interact with chests");
								}
							} else {
								sender.sendMessage(ChatColor.RED + "you can only use this option while being vanished!");
							}
						} else if(args[0].equalsIgnoreCase("effect")) {
							if(xp.hasVanishEffects()) {
								xp.setVanishEffects(false);
								sender.sendMessage(ChatColor.GREEN + "successfully disabled vanish effects for yourself");
							} else {
								xp.setVanishEffects(true);
								sender.sendMessage(ChatColor.GREEN + "successfully enabled vanish effects for yourself");
							}
						} else if(args[0].equalsIgnoreCase("fj") || args[0].equalsIgnoreCase("fakejoin")) {
							if(xp.isVanished()) {
								xp.unvanish(true);
								if(Hooks.isWorldGuardEnabled()) {
									Bukkit.broadcastMessage(WorldGuardHook.sendJoinMessage(xp.getPlayer()));
								} else {
									if(xp.isStaff()) {
										if(xp.getPlayer().getName().equalsIgnoreCase("Xeph0re")) {
											Bukkit.broadcastMessage(ChatColor.GRAY + "Developer of xEssentials " + ChatColor.GREEN + xp.getPlayer().getName() + ChatColor.GRAY + " has joined the game!");
										} else {
											Bukkit.broadcastMessage(ChatColor.GRAY + "Staff member " + ChatColor.GREEN + xp.getPlayer().getName() + ChatColor.GRAY + " has joined the game!");	
										}
									} else {
										Bukkit.broadcastMessage(ChatColor.GREEN + "player " + ChatColor.GRAY + xp.getPlayer().getName() + ChatColor.GREEN + " has joined the game!");
									}
								}
							} else {
								sender.sendMessage(ChatColor.RED + "you can't fake join a server when you are already unvanished!");
							}
						} else if(args[0].equalsIgnoreCase("fq") || args[0].equalsIgnoreCase("fakequit")) {
							xp.vanish();
							if(Hooks.isWorldGuardEnabled()) {
								Bukkit.broadcastMessage(WorldGuardHook.sendQuitMessage(xp.getPlayer()));
							} else {
								if(xp.isStaff()) {
									if(xp.getPlayer().getName().equalsIgnoreCase("Xeph0re")) {
										Bukkit.broadcastMessage(ChatColor.GRAY + "Developer of xEssentials " + ChatColor.GREEN + xp.getPlayer().getName() + ChatColor.GRAY + " has left the game!");
									} else {
										Bukkit.broadcastMessage(ChatColor.GRAY + "Staff member " + ChatColor.GREEN + xp.getPlayer().getName() + ChatColor.GRAY + " has left the game!");	
									}
								} else {
									Bukkit.broadcastMessage(ChatColor.GREEN + "player " + ChatColor.GRAY + xp.getPlayer().getName() + ChatColor.GREEN + " has left the game!");
								}
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
