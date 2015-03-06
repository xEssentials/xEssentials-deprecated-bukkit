package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdSilence extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdSilence(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("silence")) {
			if(sender.hasPermission(PermissionKey.CMD_SILENCE.getPermission())) {
				if(args.length == 0) {
					XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
					if(xp.isSilenced()) {
						xp.setSilenced(false);
						sendMessage(ChatColor.GREEN + "you no longer have chat silenced!");
					} else {
						xp.setSilenced(true);
						sendMessage(ChatColor.GREEN + "you successfully silenced chat!");
					}
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("all")) {
						if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
							if(pl.getConfiguration().isChatSillenced()) {
								pl.getConfiguration().toggleSillenceChat();
								broadcast(ChatColor.GREEN + "All server chat is now unhalted!");
							} else {
								pl.getConfiguration().toggleSillenceChat();
								broadcast(ChatColor.GREEN + "All server chat is now halted!");
							}
						} else {
							getWarning(WarningType.NO_PERMISSION);
						}
					}
				}
			} else {
				getWarning(WarningType.NO_PERMISSION);
			}
		}
		return false;
	}

}
