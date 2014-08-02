package tv.mineinthebox.essentials.events.players;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.material.MaterialData;

import tv.mineinthebox.essentials.xEssentials;

public class RealisticGlass implements Listener {

	private static HashMap<Location, MaterialData> blocks = new HashMap<Location, MaterialData>();
	private HashMap<Location, Block> glasses = new HashMap<Location, Block>();

	@EventHandler
	public void projectiles(ProjectileHitEvent e) {
		if(e.getEntity() instanceof Arrow) {
			Arrow arrow = (Arrow) e.getEntity();
			if(arrow.getShooter() instanceof Player) {
				if(hasAttachedGlass(arrow.getLocation().getBlock())) {
					storeGlasses(arrow.getLocation().getBlock());
					arrow.remove();
					destroyRandomGlasses();
				}
			}
		}
	}

	private boolean hasAttachedGlass(Block block) {
		for(BlockFace face : BlockFace.values()) {
			if(face != BlockFace.SELF) {
				if(block.getRelative(face).getType() == Material.GLASS || block.getRelative(face).getType() == Material.THIN_GLASS || block.getRelative(face).getType() == Material.STAINED_GLASS || block.getRelative(face).getType() == Material.STAINED_GLASS_PANE) {
					return true;
				}
			}
		}
		return false;
	}

	private void storeGlasses(Block block) {
		for(int X = 0; X < 12;X++) {
			for(int Y = 0; Y < 12; Y++) {
				for(int Z  = 0; Z < 12; Z++) {
					Block allplus = block.getWorld().getBlockAt((block.getX()+X), (block.getY()+Y),(block.getZ()+Z));
					if(allplus.getType() == Material.GLASS || allplus.getType() == Material.THIN_GLASS || allplus.getType() == Material.STAINED_GLASS || allplus.getType() == Material.STAINED_GLASS_PANE) {
						glasses.put(allplus.getLocation(), allplus);
					}
					Block allmin = block.getWorld().getBlockAt((block.getX()-X), (block.getY()+Y),(block.getZ()-Z));
					if(allmin.getType() == Material.GLASS || allmin.getType() == Material.THIN_GLASS || allmin.getType() == Material.STAINED_GLASS || allmin.getType() == Material.STAINED_GLASS_PANE) {
						glasses.put(allmin.getLocation(), allmin);
					}
					Block xmin = block.getWorld().getBlockAt((block.getX()-X), (block.getY()+Y), (block.getZ()+Z));
					if(xmin.getType() == Material.GLASS || xmin.getType() == Material.THIN_GLASS || xmin.getType() == Material.STAINED_GLASS || xmin.getType() == Material.STAINED_GLASS_PANE) {
						glasses.put(xmin.getLocation(), xmin);
					}
					Block zmin = block.getWorld().getBlockAt((block.getX()+X), (block.getY()+Y), (block.getZ()-Z));
					if(zmin.getType() == Material.GLASS || zmin.getType() == Material.THIN_GLASS || zmin.getType() == Material.STAINED_GLASS || zmin.getType() == Material.STAINED_GLASS_PANE) {
						glasses.put(zmin.getLocation(), zmin);
					}
				}
			}
		}
	}

	private void destroyRandomGlasses() {
		for(int i = 0; i < glasses.size();i++) {
			Location[] locs = glasses.keySet().toArray(new Location[glasses.size()]);
			Location loc = locs[i];
			Block block = glasses.get(loc);
			blocks.put(loc, block.getState().getData());
			block.getWorld().playEffect(loc, Effect.STEP_SOUND, block.getType());
			glasses.remove(loc);
			block.breakNaturally();
		}
		glasses.clear();
	}

	@EventHandler
	public void entityFall(EntityDamageEvent e) {
		if(e.getCause() == DamageCause.FALL) {
			if(e.getEntity().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.GLASS || e.getEntity().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.STAINED_GLASS) {
				storeGlasses(e.getEntity().getLocation().getBlock().getRelative(BlockFace.DOWN));
				destroyRandomGlasses();
			}
		}
	}

	public void startRegen() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(xEssentials.getPlugin(), new Runnable() {

			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				if(!blocks.isEmpty() || blocks.size() != 0) {
					Iterator<Entry<Location, MaterialData>> it = blocks.entrySet().iterator();
					it.hasNext();
					Map.Entry<Location, MaterialData> its = (Map.Entry<Location, MaterialData>) it.next();
					Location loca = (Location) its.getKey();
					MaterialData blockType = (MaterialData) its.getValue();
					loca.getBlock().setTypeId(blockType.getItemTypeId());
					loca.getBlock().setData(blockType.getData());
					it.remove();
					blocks.remove(loca);
				}
			}

		}, 10, 100);
	}
	
	private String serializeLocation(Location loc) {
		return loc.getWorld().getName() + ","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ();
	}
	
	@SuppressWarnings("deprecation")
	private String serializeMaterialData(MaterialData data) {
		return data.getItemType().getId()+":"+data.getData();
	}
	
	private Location deSerializeLocation(String loc) {
		String[] args = loc.split(",");
		String worldName = args[0];
		int x = Integer.parseInt(args[1]);
		int y = Integer.parseInt(args[2]);
		int z = Integer.parseInt(args[3]);
		return new Location(Bukkit.getWorld(worldName), x, y , z);
	}
	
	@SuppressWarnings("deprecation")
	private MaterialData deSerializeMaterialData(String data) {
		String[] values = data.split(":");
		int id = Integer.parseInt(values[0]);
		byte sub = Byte.parseByte(values[0]);
		return new MaterialData(id, sub);
	}

	public void saveGlassBlocks() {
		try {
			File f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "RealisticGlass.db");
			FileConfiguration con = YamlConfiguration.loadConfiguration(f);
			Iterator<Entry<Location, MaterialData>> it = blocks.entrySet().iterator();
			List<String> locs = new ArrayList<String>();
			List<String> blocks = new ArrayList<String>();
			while(it.hasNext()) {
				Map.Entry<Location, MaterialData> map = it.next();
				locs.add(serializeLocation(map.getKey()));
				blocks.add(serializeMaterialData(map.getValue()));
				it.remove();
			}
			con.set("locations", locs);
			con.set("blocks", blocks);
			con.save(f);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	public void loadGlassBlocks() {
		try {
			File f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "RealisticGlass.db");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				for(int i = 0; i < con.getStringList("locations").size();i++) {
					Location loc = deSerializeLocation(con.getStringList("locations").get(i));
					MaterialData data = deSerializeMaterialData(con.getStringList("blocks").get(i));
					blocks.put(loc, data);
				}
				f.delete();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
