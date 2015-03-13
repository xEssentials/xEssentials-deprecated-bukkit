package tv.mineinthebox.essentials.events.blocks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.EventTemplate;

public class BedrockBreakEvent extends EventTemplate implements Listener {
	
	public BedrockBreakEvent(xEssentials pl) {
		super(pl, "AntiBedrock");
	}
	
	@EventHandler
	public void OnbreakBedrock(BlockBreakEvent e) {
		if(e.getBlock().getType() == Material.BEDROCK) {
			sendMessage(e.getPlayer(), ChatColor.RED + "you are not allowed to break bedrock!");
			e.setCancelled(true);
		}
	}

}
