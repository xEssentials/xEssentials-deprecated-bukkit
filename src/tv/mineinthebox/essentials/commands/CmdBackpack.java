package tv.mineinthebox.essentials.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.Backpack;

public class CmdBackpack {

	private List<String> getContainedMaterials(String material) {
		List<String> list = new ArrayList<String>();
		for(String mat : Configuration.getMaterials()) {
			if(mat.startsWith(material.toUpperCase())) {
				list.add(mat);
			}
		}
		return list;
	}

	public List<String> onTabComplete(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("backpack")) {
			if(sender.hasPermission(PermissionKey.CMD_BACKPACK.getPermission())) {
				if(args.length == 2) {
					List<String> list = getContainedMaterials(args[1]);
					return list;
				} else if(args.length == 3) {
					List<String> list = getContainedMaterials(args[1]);
					return list;
				}	
			}
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("backpack")) {
			if(sender.hasPermission(PermissionKey.CMD_BACKPACK.getPermission())) {
				if(args.length == 0) {
					sender.sendMessage(ChatColor.GOLD + ".oO___[backpack help]___Oo.");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/backpack " + ChatColor.WHITE + ": shows help");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/backpack help " + ChatColor.WHITE + ": shows help");
					sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/backpack <player> <item:subdata> " + ChatColor.WHITE + ": adds a backpack on a item.");
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[backpack help]___Oo.");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/backpack " + ChatColor.WHITE + ": shows help");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/backpack help " + ChatColor.WHITE + ": shows help");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/backpack <player> <item:subdata> " + ChatColor.WHITE + ": adds a backpack on a item.");
					} else {
						sender.sendMessage(ChatColor.RED + "we don't know much about this argument!");
					}
				} else if(args.length == 2) {
					try {
						Player p = Bukkit.getPlayer(args[0]);
						if(p instanceof Player) {
							if(args[1].contains(":")) {
								String[] data = args[1].split(":");
								if(isNumberic(data[0])) {
									if(!isNumberic(data[1])) {
										sender.sendMessage(ChatColor.RED + "the sub data value needs to be a number!");
										return false;
									}
									Material mat = Material.getMaterial(Integer.parseInt(data[0]));
									Short dura = Short.parseShort(data[1]);
									ItemStack item = new ItemStack(mat, 1);
									item.setDurability(dura);
									sender.sendMessage(ChatColor.GRAY + "giving " + item.getType().name() + ":"+dura + " amount: 1 to player " + p.getName());
									try {
										Backpack backpack = xEssentials.getManagers().getBackPackManager().createBackpack(item.getType(), item.getDurability());
										p.getInventory().addItem(backpack.getBackPackItem());
										p.sendMessage(ChatColor.GREEN + "you retrieved items from " + sender.getName());
									} catch(IllegalArgumentException e) {
										sender.sendMessage(ChatColor.RED + p.getName() + " is to full");
									}
								} else {
									Material mat = Material.getMaterial(data[0].toUpperCase());
									if(!isNumberic(data[1])) {
										sender.sendMessage(ChatColor.RED + "the sub data value needs to be a number!");
										return false;
									}
									Short dura = Short.parseShort(data[1]);
									ItemStack item = new ItemStack(mat, 1);
									item.setDurability(dura);
									Backpack backpack = xEssentials.getManagers().getBackPackManager().createBackpack(item.getType(), item.getDurability());
									try {
										p.getInventory().addItem(backpack.getBackPackItem());
										p.sendMessage(ChatColor.GREEN + "you retrieved items from " + sender.getName());
									} catch(IllegalArgumentException e) {
										sender.sendMessage(ChatColor.RED + p.getName() + " is to full");
									}
								}						
							} else {
								if(isNumberic(args[1])) {
									Material mat = Material.getMaterial(Integer.parseInt(args[1]));
									ItemStack item = new ItemStack(mat, 1);
									Backpack backpack = xEssentials.getManagers().getBackPackManager().createBackpack(item.getType(), item.getDurability());
									try {
										p.getInventory().addItem(backpack.getBackPackItem());
										p.sendMessage(ChatColor.GREEN + "you retrieved items from " + sender.getName());
									} catch(IllegalArgumentException e) {
										sender.sendMessage(ChatColor.RED + p.getName() + " is to full");
									}
								} else {
									Material mat = Material.getMaterial(args[1].toUpperCase());
									ItemStack item = new ItemStack(mat, 1);
									Backpack backpack = xEssentials.getManagers().getBackPackManager().createBackpack(item.getType(), item.getDurability());
									try {
									p.getInventory().addItem(backpack.getBackPackItem());
										p.sendMessage(ChatColor.GREEN + "you retrieved items from " + sender.getName());
									} catch(IllegalArgumentException e) {
										sender.sendMessage(ChatColor.RED + p.getName() + " is to full");
									}
								}
							}
						} else {
							sender.sendMessage(ChatColor.RED + "this player is not online!");
						}
					} catch(NullPointerException e) {
						sender.sendMessage(ChatColor.RED + "invalid item!");
					}
				}
			} else {
				Warnings.getWarnings(sender);
			}
		}
		return false;
	}

	private Boolean isNumberic(String s) {
		try {
			Integer i = Integer.parseInt(s);
			if(i != null) {
				return true;
			}
		} catch(NumberFormatException e) {
			return false;
		}
		return false;
	}
}
