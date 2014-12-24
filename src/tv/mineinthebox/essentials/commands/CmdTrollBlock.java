package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import tv.mineinthebox.essentials.Warnings;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.TrollBlock;

public class CmdTrollBlock {
	
	@SuppressWarnings("deprecation")
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("trollblock")) {
			if(sender.hasPermission(PermissionKey.CMD_TROLLBLOCK.getPermission())) {
				if(sender instanceof Player) {
					Player p = (Player) sender;
					ItemStack item = new ItemStack(Material.GOLDEN_APPLE, 1);
					TrollBlock block = new TrollBlock(p.getTargetBlock(null, 100), item, p);
					block.startTroll();
					sender.sendMessage(ChatColor.GREEN + block.getType().name().toLowerCase().replace("_", "") + " is now a troll block!");
				} else {
					Warnings.getWarnings(sender).consoleMessage();
				}
			} else {
				Warnings.getWarnings(sender).noPermission();
			}
		}
		return false;
	}

}
