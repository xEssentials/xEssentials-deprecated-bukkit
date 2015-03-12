package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;

public class CmdWarps extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdWarps(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("warps")) {
			if(sender.hasPermission(PermissionKey.CMD_WARPS.getPermission())) {
				if(pl.getManagers().getWarpManager().getWarps().length != 0) {
					StringBuilder build = new StringBuilder();
					for(int i = 0; i < pl.getManagers().getWarpManager().getWarps().length; i++) {
						if(i == pl.getManagers().getWarpManager().getWarps().length) {
							build.append(pl.getManagers().getWarpManager().getWarps()[i].getWarpName());
						} else {
							build.append(pl.getManagers().getWarpManager().getWarps()[i].getWarpName() + ", ");
						}
					}
					sender.sendMessage(ChatColor.GOLD + ".oO___[Warp list]___Oo.");
					sender.sendMessage(ChatColor.GRAY + build.toString());
				} else {
					sender.sendMessage(ChatColor.GOLD + ".oO___[Warp list]___Oo.");
					sender.sendMessage(ChatColor.RED + "no warps could be found!");
				}
			} else {
				getWarning(WarningType.NO_PERMISSION);
			}
		}
		return false;
	}

}
