package tv.mineinthebox.essentials.events.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Monster;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

public class CleanupUnloadedChunkEvent implements Listener {
	
	@EventHandler
	public void onCleanup(ChunkUnloadEvent e) {
		for(Entity entity : e.getChunk().getEntities()) {
			if(entity instanceof Item) {
				Item item = (Item) entity;
				item.remove();
			} else if(entity instanceof Monster) {
				Monster monster = (Monster) entity;
				monster.remove();
			} else if(entity instanceof ExperienceOrb) {
				ExperienceOrb orb = (ExperienceOrb) entity;
				orb.remove();
			}
		}
	}

}
