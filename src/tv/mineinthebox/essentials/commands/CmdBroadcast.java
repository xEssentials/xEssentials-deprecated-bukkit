package tv.mineinthebox.essentials.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;

public class CmdBroadcast {
	
	private final xEssentials pl;
	
	public CmdBroadcast(xEssentials pl) {
		this.pl = pl;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("broadcast")) {
			if(sender.hasPermission(PermissionKey.CMD_BROADCAST.getPermission())) {
				if(args.length == 0) {
					sender.sendMessage(ChatColor.GOLD + ".oO___[broadcast help]___Oo.");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/broadcast " + ChatColor.WHITE + ": shows help");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/broadcast help " + ChatColor.WHITE + ": shows help");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/broadcast stop " + ChatColor.WHITE + ": stops the broadcast");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/broadcast stop " + ChatColor.WHITE + ": starts the broadcast");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/broadcast list " + ChatColor.WHITE + ": shows the list of all broadcast messages with id's");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/broadcast remove <id> " + ChatColor.WHITE + ": removes the id of the broadcast");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/broadcast add message " + ChatColor.WHITE + ": add a new broadcast");
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[broadcast help]___Oo.");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/broadcast " + ChatColor.WHITE + ": shows help");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/broadcast help " + ChatColor.WHITE + ": shows help");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/broadcast stop " + ChatColor.WHITE + ": stops the broadcast");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/broadcast stop " + ChatColor.WHITE + ": starts the broadcast");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/broadcast list " + ChatColor.WHITE + ": shows the list of all broadcast messages with id's");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/broadcast remove <id> " + ChatColor.WHITE + ": removes the id of the broadcast");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/broadcast add message " + ChatColor.WHITE + ": add a new broadcast");
					} else if(args[0].equalsIgnoreCase("list")) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[broadcast messages]___Oo.");
						for(int i = 0; i < pl.getConfiguration().getBroadcastConfig().getMessages().size(); i++) {
							String broadcast = ChatColor.translateAlternateColorCodes('&', pl.getConfiguration().getBroadcastConfig().getMessages().get(i));
							sender.sendMessage(ChatColor.GREEN + "id: " + i + ChatColor.GRAY + " message: " + broadcast);
						}
					} else if(args[0].equalsIgnoreCase("stop")) {
						try {
							File f = new File(pl.getDataFolder() + File.separator + "broadcast.yml");
							FileConfiguration con = YamlConfiguration.loadConfiguration(f);
							con.set("broadcast.enable", false);
							con.save(f);
							pl.getConfiguration().reload();
							sender.sendMessage(ChatColor.GREEN + "successfully disabled broadcast!");
						} catch(Exception e) {
							e.printStackTrace();
						}
					} else if(args[0].equalsIgnoreCase("start")) {
						try {
							File f = new File(pl.getDataFolder() + File.separator + "broadcast.yml");
							FileConfiguration con = YamlConfiguration.loadConfiguration(f);
							con.set("broadcast.enable", true);
							con.save(f);
							pl.getConfiguration().reload();
							sender.sendMessage(ChatColor.GREEN + "successfully enabled broadcast!");
						} catch(Exception e) {
							e.printStackTrace();
						}
					}
				} else if(args.length == 2) {
					if(args[0].equalsIgnoreCase("remove")) {
						List<String> list = new ArrayList<String>();
						for(String s : pl.getConfiguration().getBroadcastConfig().getMessages()) {
							list.add(s);
						}
						try {
							Integer id = Integer.parseInt(args[1]);
							String name = list.get(id);
							list.remove(name);
							try {
								File f = new File(pl.getDataFolder() + File.separator + "broadcast.yml");
								FileConfiguration con = YamlConfiguration.loadConfiguration(f);
								con.set("broadcast.messages", list);
								con.save(f);
								pl.getConfiguration().reload();
								sender.sendMessage(ChatColor.GREEN + "you have successfully removed broadcast id: " + id);
							} catch(Exception e) {
								e.printStackTrace();
							}
						} catch(NumberFormatException e) {
							sender.sendMessage(ChatColor.RED + args[1] + " needs to be a number!");
						} catch(IndexOutOfBoundsException e) {
							sender.sendMessage(ChatColor.RED + "id does not exist!");
						}
					}
				} else if(args.length > 2) {
					if(args[0].equalsIgnoreCase("add")) {
						List<String> list = new ArrayList<String>();
						for(String s : pl.getConfiguration().getBroadcastConfig().getMessages()) {
							list.add(s);
						}
						String message = Arrays.toString(args).replace(args[0]+"," + " ", "").replace(",", "").replace("[", "").replace("]", "");
						list.add(message);
						try {
							File f = new File(pl.getDataFolder() + File.separator + "broadcast.yml");
							FileConfiguration con = YamlConfiguration.loadConfiguration(f);
							con.set("broadcast.messages", list);
							con.save(f);
							pl.getConfiguration().reload();
							sender.sendMessage(ChatColor.GREEN + "you have successfully added the new broadcast!");
						} catch(Exception e) {
							e.printStackTrace();
						}
						
					}
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
