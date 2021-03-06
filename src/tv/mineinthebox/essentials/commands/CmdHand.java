package tv.mineinthebox.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;

public class CmdHand extends CommandTemplate {
	
	public CmdHand(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
	}

	@SuppressWarnings("deprecation")
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("hand")) {
			if(sender.hasPermission(PermissionKey.CMD_HAND.getPermission())) {
				if(sender instanceof Player) {
					Player p = (Player) sender;
					sendMessage("the item in your hand has a data value of: " + p.getItemInHand().getType().getId() + "("+p.getItemInHand().getType().name()+") and has a sub data value of: " + p.getItemInHand().getDurability());
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
