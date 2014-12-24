package tv.mineinthebox.essentials.events.players;

import java.util.HashSet;

import org.bukkit.Sound;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class FakeNukeEvent implements Listener {
	
	public static HashSet<FallingBlock> blocks = new HashSet<FallingBlock>();
	
	@EventHandler
	public void onChangeBlock(EntityChangeBlockEvent e) {
		if(e.getEntity() instanceof FallingBlock) {
			FallingBlock entity = (FallingBlock) e.getEntity();
			if(blocks.contains(entity)) {
				e.setCancelled(true);
				entity.getWorld().playSound(entity.getLocation(), Sound.EXPLODE, 0.8F, 0.8F);
				blocks.remove(entity);
			}
		}
	}

}
