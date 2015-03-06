package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.enums.ProtectionType;

public class CmdCprivate extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdCprivate(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("cprivate")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_CPRIVATE.getPermission())) {
					pl.getManagers().getProtectionDBManager().addSession(sender.getName(), ProtectionType.CREATE);
					sendMessage(ChatColor.GREEN + "right click the block you want to register!");
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
