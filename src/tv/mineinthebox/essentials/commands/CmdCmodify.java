package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.ProtectionType;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XOfflinePlayer;

public class CmdCmodify extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdCmodify(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("cmodify")) {
			if(args.length == 0) {
				showHelp();
			} else if(args.length == 1) {
				if(pl.getManagers().getPlayerManager().isEssentialsPlayer(args[0])) {
					XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
					pl.getManagers().getProtectionDBManager().addSession(sender.getName(), off, ProtectionType.MODIFY);
					sendMessage("right click on a block to change permissions");
				} else {
					getWarning(WarningType.NEVER_PLAYED_BEFORE);
				}
			}
		}
		return false;
	}
	
	@Override
	public void showHelp() {
		sender.sendMessage(ChatColor.RED + "/cmodify user : adds the user");
	}

}
