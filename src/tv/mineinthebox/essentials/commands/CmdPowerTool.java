package tv.mineinthebox.essentials.commands;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdPowerTool extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdPowerTool(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("powertool")) {
			if(sender.hasPermission(PermissionKey.CMD_POWERTOOL.getPermission())) {
				if(sender instanceof Player) {
					XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
					if(xp.getPlayer().getItemInHand() != null) {
						if(args.length > 0) {
							xp.setPowerTool(xp.getPlayer().getItemInHand(), Arrays.toString(args).replace("[", "").replace(",", "").replace("]", ""));
							sendMessage(ChatColor.GREEN + "you have successfully binded the command /" + Arrays.toString(args).replace("[", "").replace(",", "").replace("]", "") + " on the " + xp.getPlayer().getItemInHand().getType().name() + " item!");
						} else {
							if(xp.hasPowerTool()) {
								xp.removePowerTool();
								sendMessage(ChatColor.GREEN + "powertool successfully unbinded from the " + xp.getPlayer().getItemInHand().getType().name() + " item!");
							} else {
								sendMessage(ChatColor.RED + "you don't seem to have a binded powertool!");
							}
						}
					} else {
						sendMessage(ChatColor.RED + "you need a item in your hand in order to bind a command!");
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
