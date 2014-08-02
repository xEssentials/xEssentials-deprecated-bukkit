package tv.mineinthebox.essentials.commands;

import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.enums.PermissionKey;

public class CmdClear {

	@SuppressWarnings("deprecation")
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("clear")) {
			if(sender.hasPermission(PermissionKey.CMD_CLEAR.getPermission())) {
				if(args.length == 0){
					if(sender instanceof Player) {
						Player p = (Player) sender;
						int i = 0;
						for(ItemStack stack : p.getInventory().getContents()) {
							if(stack != null) {
								if(isBackPack(stack)) {
									try {
										//BackPack pack = new BackPack(stack);
										//pack.remove();
									} catch (Exception e) {
										sender.sendMessage(ChatColor.RED + "there is a backpack conflict with a other plugin, if this persist please disable the /clear command in commands.yml.");
									}
								}
								i += stack.getAmount();	
							}
						}
						for(ItemStack stack : p.getInventory().getArmorContents()) {
							if(stack != null) {
								if(isBackPack(stack)) {
									try {
										//BackPack pack = new BackPack(stack);
									//	pack.remove();
									} catch (Exception e) {
										sender.sendMessage(ChatColor.RED + "there is a backpack conflict with a other plugin, if this persist please disable the /clear command in commands.yml.");
									}
								}
								i += stack.getAmount();	
							}
						}
						p.getInventory().clear();
						p.getInventory().setHelmet(null);
						p.getInventory().setChestplate(null);
						p.getInventory().setLeggings(null);
						p.getInventory().setBoots(null);
						sender.sendMessage(ChatColor.GREEN + "you have successfully removed " + i + " items");
					} else {
						Warnings.getWarnings(sender).consoleMessage();
					}
				} else if(args.length == 1) {
					Player p = Bukkit.getPlayer(args[0]);
					int i = 0;
					for(ItemStack stack : p.getInventory().getContents()) {
						if(stack != null) {
							if(isBackPack(stack)) {
								try {
									//BackPack pack = new BackPack(stack);
									//pack.remove();
								} catch (Exception e) {
									sender.sendMessage(ChatColor.RED + "there is a backpack conflict with a other plugin, if this persist please disable the /clear command in commands.yml.");
								}
							}
							i += stack.getAmount();	
						}
					}
					for(ItemStack stack : p.getInventory().getArmorContents()) {
						if(stack != null) {
							if(isBackPack(stack)) {
								try {
									//BackPack pack = new BackPack(stack);
									//pack.remove();
								} catch (Exception e) {
									sender.sendMessage(ChatColor.RED + "there is a backpack conflict with a other plugin, if this persist please disable the /clear command in commands.yml.");
								}
							}
							i += stack.getAmount();	
						}
					}
					p.getInventory().clear();
					p.getInventory().setHelmet(null);
					p.getInventory().setChestplate(null);
					p.getInventory().setLeggings(null);
					p.getInventory().setBoots(null);
					sender.sendMessage(ChatColor.GREEN + "you have successfully removed " + i + " items from " + p.getName() + " his inventory");
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

	private boolean doesMatch(String s) {
		String regex = ChatColor.GOLD + "Backpack";
		return s.equalsIgnoreCase(regex);
	}

	private boolean isBackPack(ItemStack stack) {
		if(stack.hasItemMeta()) {
			if(stack.getItemMeta().hasDisplayName()) {
				if(stack.getItemMeta().hasLore()) {
					if(stack.getItemMeta().getLore().size() >= 5) {
						Pattern pat = Pattern.compile("^[a-f-0-9"+ChatColor.COLOR_CHAR+"]*$", Pattern.CASE_INSENSITIVE);
						if(stack.getItemMeta().getLore().get(5).matches(pat.pattern())) {
							return doesMatch(stack.getItemMeta().getDisplayName());	
						}
					}
				}
			}
		}
		return false;
	}

}
