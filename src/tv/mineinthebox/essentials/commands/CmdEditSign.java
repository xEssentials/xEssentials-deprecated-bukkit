package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdEditSign {
	
	private final xEssentials pl;
	
	public CmdEditSign(xEssentials pl) {
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("editsign")) {
			XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(sender.getName());
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
