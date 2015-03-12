package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdKnock extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdKnock(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("knock")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_KNOCK.getPermission())) {
					XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
					if(xp.isKnock()) {
						sendMessage(ChatColor.GREEN + "you disabled antiknockback!");
						xp.setKnock(false);
					} else {
						sendMessage(ChatColor.GREEN + "you enabled antiknockback!");
						xp.setKnock(true);
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
