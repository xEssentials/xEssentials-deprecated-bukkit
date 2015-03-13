package tv.mineinthebox.essentials.events.players;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.helpers.Anvil;
import tv.mineinthebox.essentials.helpers.Anvil.AnvilDamageType;
import tv.mineinthebox.essentials.interfaces.EventTemplate;

public class AnvilResetEvent extends EventTemplate implements Listener {
	
	public AnvilResetEvent(xEssentials pl) {
		super(pl, "Anvil");
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onOpenAnvilInventory(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.ANVIL) {
				
				Anvil anvil = new Anvil(e.getClickedBlock());
				anvil.setAnvilDamageType(AnvilDamageType.NON_DAMAGED);
				
				sendMessage(e.getPlayer(), ChatColor.GOLD + "anvil refreshed!");
			}
		}
	}
	
}
