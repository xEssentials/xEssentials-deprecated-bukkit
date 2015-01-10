package tv.mineinthebox.essentials.events.players;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import tv.mineinthebox.essentials.helpers.Anvil;
import tv.mineinthebox.essentials.helpers.Anvil.AnvilDamageType;

public class AnvilResetEvent implements Listener {
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onOpenAnvilInventory(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.ANVIL) {
				
				Anvil anvil = new Anvil(e.getClickedBlock());
				anvil.setAnvilDamageType(AnvilDamageType.NON_DAMAGED);
				
				e.getPlayer().sendMessage(ChatColor.GOLD + "anvil refreshed!");
			}
		}
	}
	
}
