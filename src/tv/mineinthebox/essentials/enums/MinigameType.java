package tv.mineinthebox.essentials.enums;

import org.bukkit.ChatColor;

public enum MinigameType {
	
	SPLEEF(ChatColor.BLUE + "[Spleef]"+ChatColor.GRAY),
	HUNGERGAMES(ChatColor.GREEN + "[HG]"+ChatColor.GRAY);
	
	private final String prefix;
	
	private MinigameType(String prefix) {
		this.prefix = prefix;
	}
	
	public String getPrefix() {
		return prefix;
	}

}
