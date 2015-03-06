package tv.mineinthebox.essentials.commands;

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
public abstract class CommandTemplate {
	
	private final Command cmd;
	protected final CommandSender sender;
	
	private String prefix = ChatColor.GREEN + "[%s]:";
	private String suffix = ChatColor.GRAY + "";
	
	public CommandTemplate(xEssentials pl, Command cmd, CommandSender sender) {
		this.prefix = pl.getConfiguration().getCommandConfig().getPrefix();
		this.suffix = pl.getConfiguration().getCommandConfig().getSuffix();
		this.cmd = cmd;
		this.sender = sender;
	}
	
	/**
	 * returns the prefix which can be formatted by %s
	 * 
	 * @author xize
	 * @return String
	 */
	public String getPrefix() {
		return prefix;
	}
	
	/**
	 * sets the prefix
	 * 
	 * @author xize
	 * @param prefix - the new prefix
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	/**
	 * gets the suffix
	 * 
	 * @author xize
	 * @return String
	 */
	public String getSuffix() {
		return suffix;
	}
	
	/**
	 * sets the suffix of this command
	 * 
	 * @author xize
	 * @param suffix - the suffix
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	/**
	 * sents out the global formated version of prefix+suffix+message
	 * 
	 * @author xize
	 * @param message - the message
	 */
	public void sendMessage(String message) {
		String smsg = ChatColor.stripColor(message);
		sender.sendMessage(String.format(getPrefix(), cmd.getName())+getSuffix()+smsg);
	}
	
	/**
	 * sents out a formatted message to the player in the same style as the command
	 * 
	 * @author xize
	 * @param p - the player
	 * @param message - the message
	 */
	public void sendMessageTo(Player p, String message) {
		String smsg = ChatColor.stripColor(message);
		p.sendMessage(String.format(getPrefix(), cmd.getName())+getSuffix()+smsg);
	}
	
	/**
	 * broadcast the message in the same style as the command it self
	 * 
	 * @author xize
	 * @param message - the message
	 */
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
	
	/**
	 * sents a warning to the sender
	 * 
	 * @author xize
	 * @param warning - the warning type
	 */
	public void getWarning(WarningType warning) {
		sendMessage(warning.getErrorMessage());
	}
	
	enum WarningType {
		
		NO_PERMISSION("you dont have permission to do that!"),
		PLAYER_ONLY("you need to be a player to perform this command!"),
		NEVER_PLAYED_BEFORE("this player has never played before!");
		
		private final String error;
		
		private WarningType(String error) {
			this.error = error;	
		}
		
		public String getErrorMessage() {
			return error;
		}
	}

}
