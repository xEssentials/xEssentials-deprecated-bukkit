package tv.mineinthebox.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdTorch extends CommandTemplate {

	private final xEssentials pl;
	
	public CmdTorch(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("torch")) {
			if(sender.hasPermission(PermissionKey.CMD_TORCH.getPermission())) {
				if(sender instanceof Player) {
					if(pl.getManagers().getPlayerManager().isOnline(sender.getName())) {
						Player p = (Player) sender;
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(p.getName());
						if(xp.isTorch()) {
							xp.setTorch(false);
							sendMessageTo(p, "successfully disabled torch");
						} else {
							xp.setTorch(true);
							sendMessageTo(p, "successfully enabled torch");
						}
					} else {
						sendMessage("you where not found in our global HashMap, please reload xEssentials in order to fix this problem");
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
