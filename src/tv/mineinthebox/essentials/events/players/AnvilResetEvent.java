package tv.mineinthebox.essentials.events.players;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;

public class AnvilResetEvent implements Listener {
	
	public HashMap<String, BlockState> hash = new HashMap<String, BlockState>();
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onOpenAnvilInventory(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.ANVIL) {
				hash.put(e.getPlayer().getName(), e.getClickedBlock().getState());
			}
		}
	}
	
	@EventHandler
	public void closeAnvil(InventoryCloseEvent e) {
		if(e.getInventory().getType() == InventoryType.ANVIL) {
			if(hash.containsKey(e.getPlayer().getName())) {
				Block block = hash.get(e.getPlayer().getName()).getBlock();
				//block.setData(block.getData());
				block.getState().update();
				hash.remove(e.getPlayer().getName());
			}
		}
	}
}
