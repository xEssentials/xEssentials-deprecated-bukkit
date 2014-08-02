package tv.mineinthebox.essentials;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Warnings {
	
	private CommandSender sender;
	
	public Warnings(CommandSender sender) {
		this.sender = sender;
	}
	
	public void noPermission() {
		sender.sendMessage(ChatColor.RED + "you don't have permission to do that!");
	}
	
	public void consoleMessage() {
		sender.sendMessage(ChatColor.RED + "you need to be a player to perform this command!");
	}
	
	public void playerHasNeverPlayedBefore() {
		sender.sendMessage(ChatColor.RED + "this player has never played before!");
	}
	
	public static Warnings getWarnings(CommandSender sender) {
		Warnings warn = new Warnings(sender);
		return warn;
	}

}
