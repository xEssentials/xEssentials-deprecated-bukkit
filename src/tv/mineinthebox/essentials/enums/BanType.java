package tv.mineinthebox.essentials.enums;

import org.bukkit.ChatColor;

public enum BanType {
	
	BANNED_BEFORE(ChatColor.GREEN +"(Banned Before)"),
	BANNED(ChatColor.RED +"(Banned)"),
	TEMPBANNED(ChatColor.RED +"(TempBanned)"),
	NEVER_BANNED(ChatColor.GRAY +"(Not Banned)");
	
	private final String bantype;
	
	private BanType(String bantype) {
		this.bantype = bantype;
	}
	
	/**
	 * @author xize
	 * @param returns the prefix of the desired enum
	 * @return String
	 */
	public String getPrefix() {
		return bantype;
	}

}
