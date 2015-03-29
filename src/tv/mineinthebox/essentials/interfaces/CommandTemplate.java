package tv.mineinthebox.essentials.interfaces;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

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
		super(pl);
		this.cmd = cmd;
		this.sender = sender;
	}
	

	@Override
	public void sendMessage(String message) {
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
		sender.sendMessage(String.format(getPrefix(), cmd.getName())+getSuffix()+smsg);
	}
	
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
		p.sendMessage(String.format(getPrefix(), cmd.getName())+getSuffix()+smsg);
	}
	
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
