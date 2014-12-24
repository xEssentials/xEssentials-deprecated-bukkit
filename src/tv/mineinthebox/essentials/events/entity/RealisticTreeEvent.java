package tv.mineinthebox.essentials.events.entity;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.metadata.FixedMetadataValue;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.SlowUpdatableBlock;

public class RealisticTreeEvent implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onLeaveDecay(LeavesDecayEvent e) {
		if(e.isCancelled()) {
			return;
		}
		byte sub = e.getBlock().getData();
		Material mat = e.getBlock().getType();
		FallingBlock fall = e.getBlock().getWorld().spawnFallingBlock(e.getBlock().getLocation(), mat.getId(), sub);
		fall.setMetadata("tree", new FixedMetadataValue(xEssentials.getPlugin(), "a tree"));
		e.getBlock().setType(Material.AIR);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onLand(EntityChangeBlockEvent e) {
		if(e.getEntity() instanceof FallingBlock) {
			FallingBlock fall = (FallingBlock) e.getEntity();
			if(fall.hasMetadata("tree")) {
				final Location loc = fall.getLocation().add(0,-1,0);
				for(Entity entity : fall.getNearbyEntities(10, 180, 10)) {
					if(entity instanceof Player) {
						Player p = (Player) entity;
						p.sendBlockChange(loc, fall.getBlockId(), fall.getBlockData());
						p.playEffect(loc.add(0, 1, 0), Effect.STEP_SOUND, Material.LEAVES.getId());
					}
				}
				SlowUpdatableBlock slow = new SlowUpdatableBlock(loc, 400L);
				slow.startUpdate();
				e.setCancelled(true);
			}
		}
	}

}
