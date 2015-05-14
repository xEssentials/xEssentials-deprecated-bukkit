package tv.mineinthebox.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.helpers.NameTag;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;

public class CmdTest extends CommandTemplate {
	
	public CmdTest(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("test")) {
			//NameTag.sentPacket((Player)sender, "BLAH");
		}
		return false;
	}
}
