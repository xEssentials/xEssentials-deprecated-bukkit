package tv.mineinthebox.bukkit.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.instances.xEssentialsPlayer;

public class CmdEditSign {
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("editsign")) {
			xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(sender.getName());
			if(xp.isEditSignEnabled()) {
				xp.setEditSign(false);
				sender.sendMessage(ChatColor.GREEN + "you have disabled editsign modus");
			} else {
				xp.setEditSign(true);
				sender.sendMessage(ChatColor.GREEN + "you have enabled editsign modus");
			}
		}
		return false;
	}

}
