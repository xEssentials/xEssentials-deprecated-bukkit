package tv.mineinthebox.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdDrunk extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdDrunk(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}
	
	@SuppressWarnings("deprecation")
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("drunk")) {
			if(sender.hasPermission(PermissionKey.CMD_DRUNK.getPermission())) {
				if(args.length == 0) {
					if(sender instanceof Player) {
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
						if(xp.isDrunk()) {
							xp.setDrunk(false);
							sendMessage("you have successfully disabled drunk chat");
						} else {
							xp.setDrunk(true);
							sendMessage("you have successfully enabled drunk chat");
						}
					} else {
						getWarning(WarningType.PLAYER_ONLY);
					}
				} else if(args.length == 1) {
					Player p = Bukkit.getPlayer(args[0]);
					if(p instanceof Player) {
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(p.getName());
						if(xp.isDrunk()) {
							xp.setDrunk(false);
							sendMessageTo(p, sender.getName() + " has successfully disabled drunk chat on you");
							sendMessage("you have disabled drunk mode for " + p.getName());
						} else {
							xp.setDrunk(true);
							sendMessageTo(p, sender.getName() + " has successfully enabled drunk chat on you");
							sendMessage("you have enabled drunk mode for " + p.getName());
						}
					} else {
						if(pl.getManagers().getPlayerManager().isEssentialsPlayer(args[0])) {
							sendMessage("player is offline!");
						} else {
							getWarning(WarningType.NEVER_PLAYED_BEFORE);
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
