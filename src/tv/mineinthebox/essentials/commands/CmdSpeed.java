package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdSpeed extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdSpeed(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("speed")) {
			if(sender.hasPermission(PermissionKey.CMD_SPEED.getPermission())) {
				if(sender instanceof Player) {
					XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
					if(xp.isSpeedEnabled()) {
						xp.removeSpeed();
						sendMessage(ChatColor.GREEN + "speed disabled!");
					} else {
						xp.setSpeed(1);
						sendMessage(ChatColor.GREEN + "speed enabled!");
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
