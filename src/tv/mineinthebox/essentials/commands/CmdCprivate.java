package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.enums.ProtectionType;

public class CmdCprivate {
	
	private final xEssentials pl;
	
	public CmdCprivate(xEssentials pl) {
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("cprivate")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_CPRIVATE.getPermission())) {
					pl.getManagers().getProtectionDBManager().addSession(sender.getName(), ProtectionType.CREATE);
					sender.sendMessage(ChatColor.GREEN + "right click the block you want to register!");
				} else {
					Warnings.getWarnings(sender).noPermission();
				}
			} else {
				Warnings.getWarnings(sender).consoleMessage();
			}
		}
		return false;
	}

}
