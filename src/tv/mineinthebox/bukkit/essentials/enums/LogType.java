package tv.mineinthebox.bukkit.essentials.enums;

import org.bukkit.ChatColor;

public enum LogType {
	INFO(ChatColor.GOLD + "["+ChatColor.GRAY + "xEssentials"+ChatColor.GOLD+"]: " + ChatColor.WHITE),
	SEVERE(ChatColor.RED + "[ERROR]" + ChatColor.GOLD + "["+ChatColor.GRAY + "xEssentials"+ChatColor.GOLD+"]: " + ChatColor.WHITE),
	DEBUG(ChatColor.BLUE + "[DEBUG]" + ChatColor.GOLD + "["+ChatColor.GRAY + "xEssentials"+ChatColor.GOLD+"]: " + ChatColor.WHITE);
	
	private final String prefix;
	
	private LogType(String prefix) {
		this.prefix = prefix;
	}
	
	public String getPrefix() {
		return this.prefix;
	}
}
