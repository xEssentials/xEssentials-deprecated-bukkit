package tv.mineinthebox.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;

public class CmdTpAccept extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdTpAccept(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("tpaccept")) {
			if(sender.hasPermission(PermissionKey.CMD_TP_ACCEPT.getPermission())) {
				if(pl.getManagers().getTpaManager().containsKey(sender.getName())) {
					Player p = (Player) sender;
					Player victem = pl.getManagers().getPlayerManager().getOfflinePlayer(pl.getManagers().getTpaManager().get(sender.getName())).getBukkitPlayer();
					if(victem instanceof Player) {
						victem.teleport(p, TeleportCause.COMMAND);
						sendMessageTo(victem, sender.getName() + " has successfully accepted your tpa request!");
						sendMessage("you have successfully accepted " + victem.getName() + " his tpa request!");
						pl.getManagers().getTpaManager().remove(sender.getName());
					} else {
						sendMessage("the player went offline!");
						pl.getManagers().getTpaManager().remove(sender.getName());
					}
				} else {
					sendMessage("you don't have tpa requests open!");
				}
			} else {
				getWarning(WarningType.NO_PERMISSION);
			}
		}
		return false;
	}

}
