package tv.mineinthebox.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;

public class CmdTest extends CommandTemplate {
	public CmdTest(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("test")) {

		}
		return false;
	}
}
