package tv.mineinthebox.essentials.instances;

import org.bukkit.ChatColor;

public class RestrictedCommand {
	
	private String command;
	private String reason;
	private boolean hasTask = false;
	private String taskcmd;
	private String serializedKey;
	
	public RestrictedCommand(String cmd) {
		this.serializedKey = cmd;
		String[] args = cmd.split(",");
		if(args.length == 2) {
			this.command = args[0];
			this.reason = ChatColor.translateAlternateColorCodes('&', args[1]);	
		} else if(args.length == 3) {
			this.command = args[0];
			this.reason = ChatColor.translateAlternateColorCodes('&', args[1]);	
			this.hasTask = true;
			this.taskcmd = args[2];
		}
	}
	
	/**
	 * @author xize
	 * @param gets the command returned by this object
	 * @return String
	 */
	public String getCommand() {
		return command;
	}
	
	/**
	 * @author xize
	 * @param get the reason why this command has been blocked
	 * @return String
	 */
	public String getReason() {
		return reason;
	}
	
	/**
	 * @author xize
	 * @param returns true if the player has a task
	 * @return Boolean
	 */
	public boolean hasTask() {
		return hasTask;
	}
	
	/**
	 * @author xize
	 * @param returns the task command
	 * @return String
	 */
	public String getTaskCommand() {
		return taskcmd;
	}
	
	/**
	 * @author xize
	 * @param returns the serialized key from the config
	 * @return String
	 */
	public String getSerializedKey() {
		return serializedKey;
	}

}
