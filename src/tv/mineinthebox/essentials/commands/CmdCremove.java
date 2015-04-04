package tv.mineinthebox.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.enums.ProtectionType;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;


public class CmdCremove extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdCremove(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("cremove")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_CREMOVE.getPermission())) {
					pl.getManagers().getProtectionDBManager().addSession(sender.getName(), ProtectionType.REMOVE);
					sendMessage("right click the block you want to unregister!");
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
