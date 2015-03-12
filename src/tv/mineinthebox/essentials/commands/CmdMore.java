package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;

public class CmdMore extends CommandTemplate {
	
	public CmdMore(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
	}

	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("more")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(PermissionKey.CMD_MORE.getPermission())) {
					Player p = (Player) sender;
					if(p.getItemInHand().getType() != Material.AIR) {
						ItemStack item = p.getItemInHand().clone();
						item.setAmount(64);
						if(p.getInventory().firstEmpty() > -1) {
							p.getInventory().addItem(item);
							sendMessage(ChatColor.GREEN + "you have successfully cloned the item in your hand!");
						} else {
							sendMessage(ChatColor.RED + "you inventory is to full to clone your item in hand!");
						}
					} else {
						sendMessage(ChatColor.RED + "you cannot copy your stack as air!");
					}
				} else {
					getWarning(WarningType.NO_PERMISSION);
				}
			} else {
				getWarning(WarningType.PLAYER_ONLY);
			}
		}
		return false;
	}

}
