package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdTrollMode extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdTrollMode(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("trollmode")) {
			if(sender.hasPermission(PermissionKey.CMD_TROLLMODE.getPermission())) {
				if(sender instanceof Player) {
					XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
					if(xp.isTrollMode()) {
						if(xp.getPlayer().getPassenger() instanceof Player) {
							Player p = (Player) xp.getPlayer().getPassenger();
							p.eject();
						}
						xp.setTrollMode(false);
						sendMessage(ChatColor.GREEN + "you have disabled troll mode!");
					} else {
						xp.setTrollMode(true);
						sendMessage(ChatColor.GREEN + "you have enabled troll mode!");
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
