package tv.mineinthebox.essentials.interfaces;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;

/**
 * a skeleton holding the api for commands template design, so we do not have inconstancy between command templates and rather have one global.
 * this will avoid the problem when working with out dated commands and new commands where styles may differ.
 * 
 * @author xize
 *
 */
public abstract class CommandTemplate extends Template {
	
	private final Command cmd;
	protected final CommandSender sender;
	
	public CommandTemplate(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, sender);
		this.cmd = cmd;
		this.sender = sender;
	}
	

	@Override
	public void sendMessage(String message) {
		String smsg = ChatColor.stripColor(message);
		sender.sendMessage(String.format(getPrefix(), cmd.getName())+getSuffix()+smsg);
	}
	
	@Override
	public void sendMessageTo(Player p, String message) {
		String smsg = ChatColor.stripColor(message);
		p.sendMessage(String.format(getPrefix(), cmd.getName())+getSuffix()+smsg);
	}
	
	@Override
	public void broadcast(String message) {
		String smsg = ChatColor.stripColor(message);
		Bukkit.broadcastMessage(String.format(getPrefix(), cmd.getName())+getSuffix()+smsg);
	}
	
	/**
	 * a skeleton method container where we should define sended text which is not themed like the command
	 * 
	 * @author xize
	 * @param sender - the sender
	 */
	public void showHelp() {}

}
