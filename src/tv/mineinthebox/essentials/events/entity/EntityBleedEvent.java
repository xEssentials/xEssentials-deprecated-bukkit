package tv.mineinthebox.essentials.events.entity;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import tv.mineinthebox.essentials.xEssentials;

public class EntityBleedEvent implements Listener {

	private final Random rand = new Random();
	private final int range = 4;

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBleed(EntityDamageEvent e) {
		LinkedHashMap<Location, Item> hash = new LinkedHashMap<Location, Item>();
		for(int i = 0; i < e.getDamage()*3; i++) {

			Location loc = e.getEntity().getLocation();
			loc.setX(loc.getX()-(range/2));
			loc.setY(loc.getY()-(range/2));
			loc.setZ(loc.getZ()-(range/2));

			int x = rand.nextInt(range);
			int z = rand.nextInt(range);

			Location newloc = loc.add(x, 5, z);


			Location newloc2 = newloc.clone();
			newloc2.setY(newloc.getY()-4);
			//Location loca = newloc.getWorld().getHighestBlockAt(newloc).getRelative(BlockFace.DOWN).getLocation();

			for(Entity entity : e.getEntity().getNearbyEntities(14, 120, 14)) {
				if(entity instanceof Player) {
					if(newloc2.getBlock().getType() != Material.AIR && !newloc2.getBlock().getType().isTransparent()) {
						Player p = (Player) entity;
						p.sendBlockChange(newloc2, Material.STAINED_GLASS, DyeColor.RED.getWoolData());
						Item item = newloc.getWorld().dropItemNaturally(newloc, new ItemStack(Material.STAINED_GLASS, 64, DyeColor.RED.getWoolData()));
						item.setMetadata("bleed", new FixedMetadataValue(xEssentials.getPlugin(), e.getEntity().getUniqueId()));
						hash.put(newloc2, item);
					}
				}
			}
		}
		BleedRegen regen = new BleedRegen(hash);
		regen.startRegen();
	}

	@EventHandler
	public void onPickUp(PlayerPickupItemEvent e) {
		if(e.getItem().hasMetadata("bleed")) {
			e.setCancelled(true);
		}
	}

}

class BleedRegen {

	private final LinkedHashMap<Location, Item> hash;

	public BleedRegen(LinkedHashMap<Location, Item> hash) {
		this.hash = hash;
	}

	public void startRegen() {
		new BukkitRunnable() {

			private Iterator<Map.Entry<Location, Item>> it; 
			private boolean isCleaned = false;

			public Iterator<Map.Entry<Location, Item>> getIterator() {
				if(!(it instanceof Iterator)) {
					this.it = hash.entrySet().iterator();
				}
				return it;
			}

			public void cleanup(World w) {
				if(!isCleaned) {
					for(Item item : w.getEntitiesByClass(Item.class)) {
						if(item.hasMetadata("bleed")) {
							if(!hash.containsValue(item)) {
								item.remove();
							}
						}
					}
				}
				isCleaned = true;
			}

			@Override
			public void run() {
				if(getIterator().hasNext()) {
					Entry<Location, Item> entry = getIterator().next();
					cleanup(entry.getKey().getWorld());
					entry.getKey().getBlock().getState().update(true);
					entry.getValue().remove();
					getIterator().remove();
					hash.remove(entry.getKey());
				} else {
					hash.clear();
					cancel();
				}
			}

		}.runTaskTimer(xEssentials.getPlugin(), 0L, 20L);
	}



}
