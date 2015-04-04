package tv.mineinthebox.essentials.interfaces;

import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.xEssentials;

public abstract class Template {
	
	protected xEssentials pl;
	protected String prefix;
	protected String suffix;
	
	public Template(xEssentials pl) {
		this.pl = pl;
		this.prefix = pl.getConfiguration().getCommandConfig().getPrefix();
		this.suffix = pl.getConfiguration().getCommandConfig().getSuffix();
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
	public void sendMessage(String message) {}
	
	/**
	 * sents out a formatted message to the player in the same style as the command
	 * 
	 * @author xize
	 * @param p - the player
	 * @param message - the message
	 */
	public void sendMessageTo(CommandSender p, String message) {}
	
	/**
	 * broadcast the message in the same style as the command it self
	 * 
	 * @author xize
	 * @param message - the message
	 */
	public void broadcast(String message) {}
	
	/**
	 * sents a warning to the sender
	 * 
	 * @author xize
	 * @param warning - the warning type
	 */
	public void getWarning(WarningType warning) {
		sendMessage(warning.getErrorMessage());
	}
	
	public enum WarningType {
		
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
