package tv.mineinthebox.essentials.events.blocks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.interfaces.EventTemplate;

public class BedrockPlaceEvent extends EventTemplate implements Listener {
	
	public BedrockPlaceEvent(xEssentials pl) {
		super(pl, "AntiBedrock");
	}
	
	@EventHandler
	public void onBedrockPlace(BlockPlaceEvent e) {
		if(e.getBlock().getType() == Material.BEDROCK) {
			sendMessage(e.getPlayer(), ChatColor.RED + "you are not allowed to place bedrock!");
			e.setCancelled(true);
		}
	}

}
