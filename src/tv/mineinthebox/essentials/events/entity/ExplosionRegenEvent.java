package tv.mineinthebox.essentials.events.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.RegenObject;

public class ExplosionRegenEvent implements Listener {
	
	private final HashSet<FallingBlock> fallingBlocks = new HashSet<FallingBlock>();
	private final xEssentials pl;
	
	public ExplosionRegenEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onExplosion(EntityExplodeEvent e) {
		
		if(e.isCancelled()) {
			return;
		}
		
		e.setCancelled(true);
		List<Block> blocks = new ArrayList<Block>();
		blocks.addAll(e.blockList());
		
		Collections.sort(blocks, new Comparator<Block>() {

			@Override
			public int compare(Block o1, Block o2) {
				return Double.compare(o1.getLocation().getY(), o2.getLocation().getY());
			}
			
		});
		
		LinkedHashMap<Location, MaterialData> data = new LinkedHashMap<Location, MaterialData>();
		
		Iterator<Block> it = blocks.iterator();
		while(it.hasNext()) {
			Block block = it.next();
			if(allowedMaterials().contains(block.getType())) {
				data.put(block.getLocation(), block.getState().getData());
				bounceBlock(block.getState());
				block.setType(Material.AIR);
			}
			it.remove();
			blocks.remove(block);
		}
		
		RegenObject regen = new RegenObject(data, pl);
		pl.getManagers().getExplosionRegenManager().getList.add(regen);
	}

	@SuppressWarnings("deprecation")
	public void bounceBlock(BlockState b) {
		if(b == null) return;

		if(fallingBlocks.size() > 1500) {
			return;
		}
		
		for(Material mat : allowedMaterials()) {
			if(b.getType() == mat) {
				FallingBlock fb = b.getWorld().spawnFallingBlock(b.getLocation(), b.getData().getItemType(), b.getData().getData());
				

				float x = (float) -1 + (float) (Math.random() * ((1 - -1) + 1));
				float y = 2;//(float) -5 + (float)(Math.random() * ((5 - -5) + 1));
				float z = (float) -0.3 + (float)(Math.random() * ((0.3 - -0.3) + 1));

				fb.setDropItem(false);
				fb.setVelocity(new Vector(x, y, z));
				fallingBlocks.add(fb);
				fb.setMetadata("xe:explosion", new FixedMetadataValue(xEssentials.getPlugin(), ""));
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public List<Material> allowedMaterials() {
			Material[] materials = {
			Material.WOOD,
			Material.LOG,
			Material.LOG_2,
			Material.ICE,
			Material.SOUL_SAND,
			Material.PACKED_ICE,
			Material.NETHERRACK,
			Material.LEAVES,
			Material.LEAVES_2,
			Material.WOOL,
			Material.GLASS,
			Material.THIN_GLASS,
			Material.BRICK,
			Material.COBBLESTONE,
			Material.COBBLESTONE_STAIRS,
			Material.NETHER_BRICK,
			Material.NETHERRACK,
			Material.DIRT,
			Material.STONE,
			Material.SAND,
			Material.WOOD_STAIRS,
			Material.WOOD_STEP,
			Material.DIRT,
			Material.GRASS,
			Material.BRICK,
			Material.MOSSY_COBBLESTONE,
			Material.getMaterial(98)};
			return Arrays.asList(materials);
	}
	
	@EventHandler
	public void removeBlock(EntityChangeBlockEvent e) {
		if(e.getEntity() instanceof FallingBlock) {
			FallingBlock fb = (FallingBlock)e.getEntity();
			if(fb.hasMetadata("xe:explosion")) {
				e.setCancelled(true);
				fallingBlocks.remove(fb);
			}
		}
	}

}
