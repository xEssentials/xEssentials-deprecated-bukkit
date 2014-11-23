package tv.mineinthebox.bukkit.essentials.events.entity;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import tv.mineinthebox.bukkit.essentials.xEssentials;

public class EntityBleedEvent implements Listener {

	private final Random rand = new Random();
	private final int range = 4;

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled=true)
	public void onBleed(EntityDamageEvent e) {
		if(e.isCancelled()) {
			return;
		}
		LinkedList<Location> list = new LinkedList<Location>();
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
					if(newloc2.getBlock().getType() != Material.AIR && !newloc2.getBlock().getType().isTransparent() && !newloc2.getBlock().isLiquid()) {
						Player p = (Player) entity;
						p.getWorld().playEffect(newloc2.getBlock().getRelative(BlockFace.UP).getLocation(), Effect.STEP_SOUND, Material.REDSTONE_WIRE);
						p.sendBlockChange(newloc2, Material.STAINED_GLASS, DyeColor.RED.getWoolData());
						//Item item = newloc.getWorld().dropItemNaturally(newloc, new ItemStack(Material.STAINED_GLASS, 64, DyeColor.RED.getWoolData()));
						//item.setMetadata("bleed", new FixedMetadataValue(xEssentials.getPlugin(), e.getEntity().getUniqueId()));
						list.add(newloc2);
					}
				}
			}
		}
		BleedRegen regen = new BleedRegen(list);
		regen.startRegen();
	}

}

class BleedRegen {

	private final LinkedList<Location> list;

	public BleedRegen(LinkedList<Location> list) {
		this.list = list;
	}

	public void startRegen() {
		new BukkitRunnable() {

			private Iterator<Location> it; 

			public Iterator<Location> getIterator() {
				if(!(it instanceof Iterator)) {
					this.it = list.iterator();
				}
				return it;
			}

			@Override
			public void run() {
				if(getIterator().hasNext()) {
					Location loc = getIterator().next();
					loc.getBlock().getState().update(true);
					getIterator().remove();
				} else {
					list.clear();
					cancel();
				}
			}

		}.runTaskTimer(xEssentials.getPlugin(), 0L, 80L);
	}



}
