package tv.mineinthebox.essentials.commands;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;

public class CmdWild extends CommandTemplate {
	
	private int xRadius = 10000;
	private int zRadius = 10000;
	
	private final xEssentials pl;
	
	public CmdWild(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("wild")) {
			if(sender.hasPermission(PermissionKey.CMD_WILD.getPermission())) {
				if(args.length == 0) {
					if(sender instanceof Player) {
						Player p = (Player) sender;
						Random randx = new Random();
						Random randz = new Random();
						int x = randx.nextInt(xRadius)+p.getLocation().getBlockX();
						int z = randz.nextInt(zRadius)+p.getLocation().getBlockZ();
						Location loc = new Location(p.getWorld(), x, p.getWorld().getHighestBlockYAt(x, z), z);
						p.teleport(loc);
						sendMessage(ChatColor.GREEN + "you successfully has teleported to the wild!");
					} else {
						getWarning(WarningType.PLAYER_ONLY);
					}
				} else if(args.length == 1) {
					Player p = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]).getBukkitPlayer();
					if(p instanceof Player) {
						Random randx = new Random(xRadius);
						Random randz = new Random(zRadius);
						int x = randx.nextInt()+p.getLocation().getBlockX();
						int z = randz.nextInt()+p.getLocation().getBlockX();
						Location loc = new Location(p.getWorld(), x, p.getWorld().getHighestBlockYAt(x, z), z);
						p.teleport(loc);
						sendMessageTo(p, ChatColor.GREEN + "you successfully has teleported to the wild!");	
					} else {
						sender.sendMessage(ChatColor.RED + "this player is not online!");
					}
				}
			} else {
				getWarning(WarningType.NO_PERMISSION);
			}
		}
		return false;
	}

}
