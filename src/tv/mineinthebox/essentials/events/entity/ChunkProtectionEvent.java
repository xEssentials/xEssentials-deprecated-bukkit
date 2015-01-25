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

public class ChunkProtectionEvent implements Listener {
	
	private final xEssentials pl;
	
	public ChunkProtectionEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onChunkProtect(ChunkLoadEvent e) {
		for(Entity entity : e.getChunk().getEntities()) {
			if(entity instanceof WitherSkull) {
				WitherSkull wither = (WitherSkull) entity;
				if(pl.getConfiguration().getDebugConfig().isEnabled()) {
					xEssentials.log("removed wither skull at: {" + wither.getWorld().getName() + ", " + wither.getLocation().getBlockX() + ", " + wither.getLocation().getBlockY() + ", " + wither.getLocation().getBlockZ() + "} to prevent lag", LogType.INFO);	
				}
				wither.remove();
			} else if(entity instanceof Fireball) {
				Fireball fb = (Fireball) entity;
				if(pl.getConfiguration().getDebugConfig().isEnabled()) {
					xEssentials.log("removed fireball at: {" + fb.getWorld().getName() + ", " + fb.getLocation().getBlockX() + ", " + fb.getLocation().getBlockY() + ", " + fb.getLocation().getBlockZ() + "} to prevent lag", LogType.INFO);
				}
				fb.remove();
			}
		}
	}
	
	@EventHandler
	public void onChunkProtect(ChunkUnloadEvent e) {
		for(Entity entity : e.getChunk().getEntities()) {
			if(entity instanceof WitherSkull) {
				WitherSkull wither = (WitherSkull) entity;
				if(pl.getConfiguration().getDebugConfig().isEnabled()) {
					xEssentials.log("removed wither skull at: {" + wither.getWorld().getName() + ", " + wither.getLocation().getBlockX() + ", " + wither.getLocation().getBlockY() + ", " + wither.getLocation().getBlockZ() + "} to prevent lag", LogType.INFO);
				}
				wither.remove();
			} else if(entity instanceof Fireball) {
				Fireball fb = (Fireball) entity;
				if(pl.getConfiguration().getDebugConfig().isEnabled()) {
					xEssentials.log("removed fireball at: {" + fb.getWorld().getName() + ", " + fb.getLocation().getBlockX() + ", " + fb.getLocation().getBlockY() + ", " + fb.getLocation().getBlockZ() + "} to prevent lag", LogType.INFO);
				}
				fb.remove();
			}
		}
	}

}
