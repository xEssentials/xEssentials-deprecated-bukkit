package tv.mineinthebox.essentials.commands;

import java.util.Arrays;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.ProtectedBlock;
import tv.mineinthebox.essentials.interfaces.CommandTemplate;

public class CmdTest extends CommandTemplate {

	private final xEssentials pl;

	public CmdTest(xEssentials pl, Command cmd, CommandSender sender) {
		super(pl, cmd, sender);
		this.pl = pl;
	}

	@SuppressWarnings("deprecation")
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("test")) {
			if(args.length == 0) {
				if(sender instanceof Player) {
					Player p = (Player) sender;
					Block block = p.getTargetBlock(null, 30);
					ProtectedBlock pblock = new ProtectedBlock(pl, block);
					if(pblock.isProtected()) {
						if(pblock.isMember(p.getUniqueId())) {
							sendMessage("you are an owner of this block");
							sendMessage("all owners: " + Arrays.toString(pblock.getMembers().toArray()));
						} else {
							sendMessage("adding you to the owners!");
							pblock.addProtection(p.getUniqueId());
						}
					} else {
						pblock.addProtection(p.getUniqueId());
						sendMessage("block is now protected!");
					}
				}
			} else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("clear")) {
					Player p = (Player) sender;
					Block block = p.getTargetBlock(null, 30);
					ProtectedBlock pblock = new ProtectedBlock(pl, block);
					if(pblock.isProtected()) {
						pblock.removeAll();
						sendMessage("removed all data of this block!");
					}
				}
			}
		}
		return false;
	}
}
