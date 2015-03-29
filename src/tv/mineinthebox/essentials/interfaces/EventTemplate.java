package tv.mineinthebox.essentials.interfaces;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.xEssentials;

public class EventTemplate extends Template {

	private final String prefix;
	
	public EventTemplate(xEssentials pl, String prefix) {
		super(pl);
		this.prefix = ChatColor.stripColor(prefix).toLowerCase();
	}

	/**
	 * @author xize
	 * @param p - the sender
	 * @param message - the message which gets formatted
	 */
	public void sendMessage(CommandSender p, String message) {
		String smsg = ChatColor.stripColor(message);
		String[] split = smsg.split(" ");
		for(String s : split) {
			if(pl.getManagers().getPlayerManager().isEssentialsPlayer(s)) {
				XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(s);
				if(off.isOnline()) {
					smsg = smsg.replace(s, pl.getConfiguration().getCommandConfig().getPlayerHighLight() + off.getName() + getSuffix());
				} else {
					smsg = smsg.replace(s, ChatColor.GRAY + "[Offline]" + pl.getConfiguration().getCommandConfig().getPlayerHighLight() + off.getName() + getSuffix());
				}
			}
		}
		p.sendMessage(String.format(getPrefix(), prefix)+getSuffix()+smsg);
	}
	
	/**
	 * sents a message to a other player rather than the player inside the event
	 * 
	 * @author xize
	 */
	@Override
	public void sendMessageTo(CommandSender p, String message) {
		String smsg = ChatColor.stripColor(message);
		String[] split = smsg.split(" ");
		for(String s : split) {
			if(pl.getManagers().getPlayerManager().isEssentialsPlayer(s)) {
				XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(s);
				if(off.isOnline()) {
					smsg = smsg.replace(s, pl.getConfiguration().getCommandConfig().getPlayerHighLight() + off.getName() + getSuffix());
				} else {
					smsg = smsg.replace(s, ChatColor.GRAY + "[Offline]" + pl.getConfiguration().getCommandConfig().getPlayerHighLight() + off.getName() + getSuffix());
				}
			}
		}
		p.sendMessage(String.format(getPrefix(), prefix)+getSuffix()+smsg);
	}
	
	/**
	 * broadcasts the message to every player by this event
	 * 
	 * @author xize
	 */
	@Override
	public void broadcast(String message) {
		String smsg = ChatColor.stripColor(message);
		String[] split = smsg.split(" ");
		for(String s : split) {
			if(pl.getManagers().getPlayerManager().isEssentialsPlayer(s)) {
				XOfflinePlayer off = pl.getManagers().getPlayerManager().getOfflinePlayer(s);
				if(off.isOnline()) {
					smsg = smsg.replace(s, pl.getConfiguration().getCommandConfig().getPlayerHighLight() + off.getName() + getSuffix());
				} else {
					smsg = smsg.replace(s, ChatColor.GRAY + "[Offline]" + pl.getConfiguration().getCommandConfig().getPlayerHighLight() + off.getName() + getSuffix());
				}
			}
		}
		Bukkit.broadcastMessage(String.format(getPrefix(), prefix)+getSuffix()+smsg);
	}

	/**
	 * @author xize
	 * @deprecated because for events we require a player object from a method rather than a super constructor
	 */
	@Override
	public void sendMessage(String message) {}

}
