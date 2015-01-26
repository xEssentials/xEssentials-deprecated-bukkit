package tv.mineinthebox.essentials.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;

public class CmdRules {
	
	private final xEssentials pl;
	
	public CmdRules(xEssentials pl) {
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
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
