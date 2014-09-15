package tv.mineinthebox.bukkit.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CmdTest {

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("test")) {
			
		}
		return false;
	}
}
