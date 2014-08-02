package tv.mineinthebox.essentials.events.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;

public class ChunkProtection implements Listener {
	
	@EventHandler
	public void onChunkProtect(ChunkLoadEvent e) {
		for(Entity entity : e.getChunk().getEntities()) {
			if(entity instanceof WitherSkull) {
				WitherSkull wither = (WitherSkull) entity;
				xEssentials.getPlugin().log("removed wither skull to prevent lag, or to protect the server for a possible grief attempt", LogType.INFO);
				wither.remove();
			} else if(entity instanceof Fireball) {
				Fireball fb = (Fireball) entity;
				xEssentials.getPlugin().log("removed fireball to prevent lag, or to protect the server for a possible grief attempt", LogType.INFO);
				fb.remove();
			}
		}
	}
	
	@EventHandler
	public void onChunkProtect(ChunkUnloadEvent e) {
		for(Entity entity : e.getChunk().getEntities()) {
			if(entity instanceof WitherSkull) {
				WitherSkull wither = (WitherSkull) entity;
				xEssentials.getPlugin().log("removed wither skull to prevent lag, or to protect the server for a possible grief attempt", LogType.INFO);
				wither.remove();
			} else if(entity instanceof Fireball) {
				Fireball fb = (Fireball) entity;
				xEssentials.getPlugin().log("removed fireball to prevent lag, or to protect the server for a possible grief attempt", LogType.INFO);
				fb.remove();
			}
		}
	}

}
