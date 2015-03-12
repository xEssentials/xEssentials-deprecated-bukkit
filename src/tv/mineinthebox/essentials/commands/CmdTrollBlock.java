package tv.mineinthebox.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.instances.TrollBlock;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;

public class CmdTrollBlock extends CommandTemplate {
	
	private final xEssentials pl;
	
	public CmdTrollBlock(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}
	
	@SuppressWarnings("deprecation")
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("trollblock")) {
			if(sender.hasPermission(PermissionKey.CMD_TROLLBLOCK.getPermission())) {
				if(sender instanceof Player) {
					Player p = (Player) sender;
					ItemStack item = new ItemStack(Material.GOLDEN_APPLE, 1);
					TrollBlock block = new TrollBlock(p.getTargetBlock(null, 100), item, p, pl);
					block.startTroll();
					sendMessage(ChatColor.GREEN + block.getType().name().toLowerCase().replace("_", "") + " is now a troll block!");
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
