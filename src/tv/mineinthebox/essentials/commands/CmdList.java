package tv.mineinthebox.essentials.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CmdList {
	
	private final xEssentials pl;
	
	public CmdList(xEssentials pl) {
		this.pl = pl;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("list")) {
			if(sender.hasPermission(PermissionKey.CMD_LIST.getPermission())) {
				if(sender.hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
					List<String> players = new ArrayList<String>();
					for(XPlayer xp : pl.getManagers().getPlayerManager().getPlayers()) {
						if(xp.isPotato()) {
							players.add(ChatColor.GREEN + "(Potato)"+ChatColor.GRAY + xp.getUser());
						} else if(xp.isVanished()) {
							players.add(ChatColor.GREEN + "(Vanished)"+ChatColor.GRAY + xp.getUser());
						} else if(xp.isAfk()) {
							players.add(ChatColor.DARK_GRAY + "(AFK)" + ChatColor.GRAY + xp.getUser());	
						} else {
							players.add(ChatColor.GRAY + xp.getUser());
						}
					}
					if(!players.isEmpty()) {
						String[] onlines = players.toArray(new String[players.size()]);
						sender.sendMessage(ChatColor.GOLD + ".oO___[player list]___Oo.");
						sender.sendMessage(ChatColor.GREEN + "online: " + ChatColor.GRAY + (players.size()) + "/"+Bukkit.getMaxPlayers());
						sender.sendMessage(ChatColor.GRAY + Arrays.toString(onlines).replace("[", "").replace("]", ""));
						players.clear();
					} else {
						sender.sendMessage(ChatColor.GOLD + ".oO___[player list]___Oo.");
						sender.sendMessage(ChatColor.RED + "currently there are no players online!");
					}
					return false;
				}
				List<String> players = new ArrayList<String>();
				for(XPlayer xp : pl.getManagers().getPlayerManager().getPlayers()) {
					if(xp.isPotato()) {
						players.add(ChatColor.GREEN + "(Potato)" + ChatColor.GRAY + xp.getUser());
					} else if(xp.isAfk()) {
						players.add(ChatColor.DARK_GRAY + "(AFK)" + ChatColor.GRAY + xp.getUser());
					} else if(!xp.isVanished()) {
						players.add(ChatColor.GRAY + xp.getUser());
					}
				}
				if(!players.isEmpty()) {
					String[] onlines = players.toArray(new String[players.size()]);
					sender.sendMessage(ChatColor.GOLD + ".oO___[player list]___Oo.");
					sender.sendMessage(ChatColor.GREEN + "online: " + ChatColor.GRAY + (players.size()) + "/"+Bukkit.getMaxPlayers());
					sender.sendMessage(ChatColor.GRAY + Arrays.toString(onlines).replace("[", "").replace("]", ""));
					players.clear();
				} else {
					sender.sendMessage(ChatColor.GOLD + ".oO___[player list]___Oo.");
					sender.sendMessage(ChatColor.RED + "currently there are no players online!");
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
