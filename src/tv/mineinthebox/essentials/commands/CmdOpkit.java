package tv.mineinthebox.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.OpKit;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;

@SuppressWarnings("deprecation")
public class CmdOpkit extends CommandTemplate {
	
	public CmdOpkit(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
	}
	
	private final Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "op kit"); {
		inv.setItem(0, OpKit.STONE_KIT.getButton());
		
		inv.setItem(1, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getData()));
		inv.setItem(2, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getData()));
		inv.setItem(3, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getData()));
		
		inv.setItem(4, OpKit.IRON_KIT.getButton());
		
		inv.setItem(5, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getData()));
		inv.setItem(6, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getData()));
		inv.setItem(7, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getData()));
		
		inv.setItem(8, OpKit.DIAMOND_KIT.getButton());
	}
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("opkit")) {
			if(sender.hasPermission(PermissionKey.CMD_OPKIT.getPermission())) {
				if(sender instanceof Player) {
					Player p = (Player) sender;
					sendMessage(ChatColor.GRAY + "opening opkit selector!");
					p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1F, 1F);
					p.openInventory(inv);
				} else {
					getWarning(WarningType.PLAYER_ONLY);
				}
			} else {
				getWarning(WarningType.NO_PERMISSION);
			}
		}
		return false;
	}

}
