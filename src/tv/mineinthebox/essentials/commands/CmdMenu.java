package tv.mineinthebox.essentials.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.xEssentialsOfflinePlayer;
import tv.mineinthebox.essentials.instances.xEssentialsPlayer;

public class CmdMenu {

	private List<String> getPlayerByName(String p) {
		List<String> s = new ArrayList<String>();
		for(xEssentialsOfflinePlayer name : xEssentials.getManagers().getPlayerManager().getOfflinePlayers()) {
			if(name.getUser().toUpperCase().startsWith(p.toUpperCase())) {
				s.add(name.getUser());
			}
		}
		return s;
	}

	public List<String> onTabComplete(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("menu")) {
			if(args.length == 1) {
				if(sender.hasPermission(PermissionKey.CMD_MENU.getPermission())) {
					return getPlayerByName(args[0]);
				}
			}
		}
		return null;
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("menu")) {
			if(sender.hasPermission(PermissionKey.CMD_MENU.getPermission())) {
				if(sender instanceof Player) {
					Player p = (Player) sender;
					if(args.length == 0) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[quick menu help]___Oo.");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/menu <player> " + ChatColor.WHITE + ": opens a quick menu of this player!");
					} else if(args.length == 1) {
						if(xEssentials.getManagers().getPlayerManager().isEssentialsPlayer(args[0])) {
							if(xEssentials.getManagers().getPlayerManager().isOnline(args[0])) {
								xEssentialsPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(args[0]);
								Inventory inv = Bukkit.createInventory(null, 36, ChatColor.DARK_PURPLE + "Quick menu: " + xp.getUser());

								String[] tntLore = {ChatColor.DARK_PURPLE + "when activated this player gets launched high in the air!", ChatColor.DARK_PURPLE + "then as followed the player falls safely on the ground again:)"};
								ItemStack tnt = makeButton(Material.TNT, ChatColor.GREEN + "activate boom for this player!", tntLore, false);
								inv.setItem(0, tnt);

								String[] potatolore = {ChatColor.DARK_PURPLE + "when summoned on a player the player changes into a potato", ChatColor.DARK_PURPLE + "he also can't build or interact this curse duration is about a few seconds."};
								ItemStack potato = makeButton(Material.POTATO_ITEM, ChatColor.GREEN + "activate the magic potato curse!", potatolore, false);
								inv.setItem(1, potato);

								String[] kicklore = {ChatColor.DARK_PURPLE + "this will kick the player, its easy to have a button like this:)"};
								ItemStack kick = makeButton(Material.LEATHER_BOOTS, ChatColor.GREEN + "kick the player", kicklore, false);
								inv.setItem(2, kick);

								String[] tntRainLore = {ChatColor.DARK_PURPLE + "once when its activated it will rain ignited tnt uopen the player!"};
								ItemStack tntRain = makeButton(Material.TNT, ChatColor.GREEN + "TNT rain, this will rain tnt uopen them!", tntRainLore, false);
								inv.setItem(3, tntRain);

								String[] tntFakeRainLore = {ChatColor.DARK_PURPLE + "once when its activated it will rain tnt uopen the player!", ChatColor.DARK_PURPLE + "however this TNT is not dangerous nor can players get damaged of those"};
								ItemStack tntFakeRain = makeButton(Material.TNT, ChatColor.GREEN + "fake TNT rain, this will rain tnt uopen them!", tntFakeRainLore, false);
								inv.setItem(4, tntFakeRain);

								String[] banlore = {ChatColor.DARK_PURPLE + "this will stop the player for playing on this server for ever!"};
								ItemStack ban = makeButton(Material.FIREBALL, ChatColor.GREEN + "ban the player for playing on this server!", banlore, false);
								inv.setItem(5, ban);

								p.sendMessage(ChatColor.GREEN + "opening menu for player " + ChatColor.GRAY + xp.getUser());

								p.openInventory(inv);
								p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1, 0);
							} else {
								xEssentialsOfflinePlayer off = xEssentials.getManagers().getPlayerManager().getOfflinePlayer(args[0]);
								Inventory inv = Bukkit.createInventory(p, 36, ChatColor.DARK_PURPLE + "Quick menu: " + off.getUser());

								String[] banlore = {ChatColor.DARK_PURPLE + "this will stop the player for playing on this server for ever!"};
								ItemStack ban = makeButton(Material.FIREBALL, ChatColor.GREEN + "ban the player for playing on this server!", banlore, false);
								inv.setItem(0, ban);

								p.sendMessage(ChatColor.GREEN + "opening menu for player " + ChatColor.GRAY + off.getUser());

								p.openInventory(inv);
								p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1, 0);
							}
						} else {
							sender.sendMessage(ChatColor.RED + "this player has never played before!");
						}
					}
				} else {
					Warnings.getWarnings(sender).consoleMessage();
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

	private ItemStack makeButton(Material mat, String title, String[] lores, Boolean MakeEnchanted) {
		ItemStack stack = new ItemStack(mat, 1);
		if(MakeEnchanted) {
			stack.addUnsafeEnchantment(Enchantment.DURABILITY, 100);
		}
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(title);
		meta.setLore(Arrays.asList(lores));
		stack.setItemMeta(meta);
		return stack;
	}

}
