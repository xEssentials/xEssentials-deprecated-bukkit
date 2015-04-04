package tv.mineinthebox.essentials.commands;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdBoom extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdBoom(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("boom")) {
			if(sender.hasPermission(PermissionKey.CMD_BOOM.getPermission())) {
				if(args.length == 1) {
					Player boom = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]).getBukkitPlayer();
					if(boom instanceof Player) {
						if(boom.getGameMode() == GameMode.CREATIVE) {
							boom.setGameMode(GameMode.SURVIVAL);
						} else if(boom.isFlying()) {
							boom.setFlying(false);
						}
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(boom.getName());
						xp.setBoom();
						sendMessage("You are boomed!");
						broadcast("The player " + args[0] + " has been boomed by " + sender.getName());
						Location loc = boom.getPlayer().getLocation();
						loc.setY(loc.getY() + 100);
						int speed = 10;
						Vector vector = loc.toVector().subtract(boom.getLocation().toVector()).normalize();
						boom.setVelocity(vector.multiply(speed));
					} else {
						sendMessage("This player is not online");
					}
				} else {
					sendMessage("Syntax: /boom <player> - explodes a player high in the sky.");
				}
			}
		} else {
			getWarning(WarningType.NO_PERMISSION);
		}
			return false;
	}

}
