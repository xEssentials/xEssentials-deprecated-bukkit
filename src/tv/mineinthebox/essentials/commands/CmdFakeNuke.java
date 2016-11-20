package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdFakeNuke extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdFakeNuke(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("fakenuke")) {
			if(sender.hasPermission(PermissionKey.CMD_FAKE_NUKE.getPermission())) {
				if(args.length == 0) {
					if(sender instanceof Player) {
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
						for(Entity entity : xp.getBukkitPlayer().getNearbyEntities(40, 40, 40)) {
							if(entity instanceof Player) {
								Player p = (Player) entity;
								p.sendMessage(ChatColor.GRAY + "May death rain upon them");
							}
						}
						sender.sendMessage(ChatColor.GRAY + "May death rain upon them");
						xp.getBukkitPlayer().getWorld().playSound(xp.getBukkitPlayer().getLocation(), Sound.ENTITY_TNT_PRIMED, 0.98F, 0.98F);
						xp.fakenuke();
					} else {
						getWarning(WarningType.PLAYER_ONLY);
					}
				} else if(args.length == 1) {
					Player p = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]).getBukkitPlayer();
					if(p instanceof Player) {
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(p.getName());
						for(Entity entity : xp.getBukkitPlayer().getNearbyEntities(40, 40, 40)) {
							if(entity instanceof Player) {
								Player r = (Player) entity;
								r.sendMessage(ChatColor.GRAY + "May death rain upon them");
							}
						}
						sender.sendMessage(ChatColor.GRAY + "May death rain upon them");
						xp.getBukkitPlayer().getWorld().playSound(xp.getBukkitPlayer().getLocation(), Sound.ENTITY_TNT_PRIMED, 0.98F, 0.98F);
						xp.fakenuke();
					} else {
						sendMessage("this player is not online!");
					}
				}
			} else {
				getWarning(WarningType.NO_PERMISSION);
			}
		}
		return false;
	}

}
