package tv.mineinthebox.essentials.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.Kit;

public class CmdKits {
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("kits")) {
			if(sender.hasPermission(PermissionKey.CMD_KITS.getPermission())) {
				List<Kit> kits = new ArrayList<Kit>(Configuration.getKitConfig().getConfigKits().values());
				sender.sendMessage(ChatColor.GOLD + ".oO___[kits]___Oo.");
				StringBuilder build = new StringBuilder();
				for(int i = 0; i < kits.size(); i++) {
					if(i == kits.size()) {
						build.append(kits.get(i).getKitName());
					} else {
						build.append(kits.get(i).getKitName() + ", ");
					}
				}
				sender.sendMessage(ChatColor.GRAY + build.toString());
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
