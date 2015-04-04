package tv.mineinthebox.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdFirefly extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdFirefly(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("firefly")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_FIREFLY.getPermission())) {
					if(pl.getManagers().getPlayerManager().isOnline(sender.getName())) {
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
						if(xp.isFirefly()) {
							xp.setFirefly(false);
							sendMessage("you successfully disabled firefly!");
						} else {
							xp.setFirefly(true);
							sendMessage("you successfully enabled firefly!");
						}
					} else {
						sendMessage("you don't seems to be listed at our global list, please reload pl");
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

}
