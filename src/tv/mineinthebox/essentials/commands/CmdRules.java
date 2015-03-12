package tv.mineinthebox.essentials.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;

public class CmdRules extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdRules(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("rules")) {
			if(sender.hasPermission(PermissionKey.CMD_RULES.getPermission())) {
				String prefix = pl.getConfiguration().getRulesConfig().getPrefix();
				String suffix = pl.getConfiguration().getRulesConfig().getSuffix();
				List<String> rules = pl.getConfiguration().getRulesConfig().getRules();
				sender.sendMessage(ChatColor.GOLD + ".oO___[Rules]___Oo.");
				for(String rule : rules) {
					sender.sendMessage(prefix+suffix+rule);
				}
			} else {
				getWarning(WarningType.NO_PERMISSION);
			}
		}
		return false;
	}

}
