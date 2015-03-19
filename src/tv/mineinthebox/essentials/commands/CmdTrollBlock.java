package tv.mineinthebox.essentials.commands;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
	
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("trollblock")) {
			if(sender.hasPermission(PermissionKey.CMD_TROLLBLOCK.getPermission())) {
				if(sender instanceof Player) {
					Player p = (Player) sender;
					ItemStack item = new ItemStack(Material.GOLDEN_APPLE, 1);
					Block block = p.getTargetBlock((Set<Material>)null, 15);
					TrollBlock tblock = new TrollBlock(p.getTargetBlock((Set<Material>)null, 15), item, p, pl);
					tblock.startTroll();
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
