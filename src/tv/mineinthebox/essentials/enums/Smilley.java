package tv.mineinthebox.essentials.enums;

import org.bukkit.ChatColor;

public enum Smilley {
	//usefull website for this ;-)
	//http://code.cside.com/3rdpage/us/javaUnicode/converter.html
	
	HAPPY(ChatColor.GREEN + "\u263B"),
	LOVE(ChatColor.RED + "\u2665"),
	ANGRY(ChatColor.RED + "(\u256F\u00B0\u25A1\u00B0\uFF09\u256F\uFE35\u253B\u2501\u253B"),
	DUEL_CARD(ChatColor.GREEN + "\u2663");
	
	private final String smilley;
	
	private Smilley(String smilley) {
		this.smilley = smilley;
	}

	public String getChar() {
		return smilley;
	}
}
