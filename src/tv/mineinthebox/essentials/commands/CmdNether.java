package tv.mineinthebox.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;

public class CmdNether extends CommandTemplate {
	
	public CmdNether(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("nether")) {
			if(sender.hasPermission(PermissionKey.CMD_NETHER.getPermission())) {
				if(sender instanceof Player) {
					if(args.length == 0) {
						Player p = (Player)sender;
						Location loc = p.getLocation();
						loc.setX((loc.getX()/8));
						loc.setZ((loc.getZ()/8));
						
						World w = Bukkit.getWorld(loc.getWorld().getName()+"_nether");
						if(w instanceof World) {
							loc.setWorld(Bukkit.getWorld(loc.getWorld().getName()+"_nether"));	
						} else {
							sendMessage("it doesn't looks that this world has a nether maybe you are in the END?");
							return false;
						}
						sendMessage("teleporting to the exact nether location!");
						p.teleport(loc, TeleportCause.COMMAND);	
					} else if(args.length == 1) {
						if(args[0].equalsIgnoreCase("help")) {
							sendMessage(ChatColor.GOLD + ".oO___[nether]___Oo.");
							sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/nether " + ChatColor.WHITE + ": attemps to teleport to the nether on exact /8 coords where you stay");
						} else {
							sendMessage("unknown argument!");
						}
					}
				} else {
					getWarning(WarningType.PLAYER_ONLY);
				}
			} else {
				getWarning(WarningType.NO_PERMISSION);
			}
		}
		return false;
	}

}
