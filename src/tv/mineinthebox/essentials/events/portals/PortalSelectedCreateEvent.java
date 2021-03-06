package tv.mineinthebox.essentials.events.portals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.events.customevents.EssentialsPortalCreateEvent;
import tv.mineinthebox.essentials.instances.Portal;
import tv.mineinthebox.essentials.interfaces.EventTemplate;

public class PortalSelectedCreateEvent extends EventTemplate implements Listener {

	private final HashMap<String, Block[]> locations = new HashMap<String, Block[]>();
	
	public PortalSelectedCreateEvent(xEssentials pl) {
		super(pl, "Portal");
	}

	@EventHandler
	public void onCreate(PlayerInteractEvent e) {
		if(e.isCancelled()) {
			return;
		}
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getPlayer().hasMetadata("portal")) {
				if(locations.containsKey(e.getPlayer().getName())) {
					Block[] locs = locations.get(e.getPlayer().getName());
					locs[1] = e.getClickedBlock();
					locations.put(e.getPlayer().getName(), locs);
					e.getPlayer().sendMessage(ChatColor.GRAY + "pos2 set!");

					Location pos1 = locations.get(e.getPlayer().getName())[0].getLocation();
					Location pos2 = locations.get(e.getPlayer().getName())[1].getLocation();

					if(pos1.distanceSquared(pos2) > 2) {
						Block[] portalblocks = getPortalBlocks(pos1.getBlock(), pos2.getBlock());
						Block[] frameblocks = getFrameBlocks(pos1.getBlock(), pos2.getBlock());
						File f = new File(pl.getDataFolder() + File.separator + "portals" + File.separator + e.getPlayer().getMetadata("portal").get(0).asString() + ".yml");
						FileConfiguration con = save(f, frameblocks, portalblocks);
						Portal portal = new Portal(con, f, pl);
						portal.setClosed(false);
						sendMessage(e.getPlayer(), ChatColor.GRAY + "portal created with name " + portal.getPortalName() + "!");
						Bukkit.getPluginManager().callEvent(new EssentialsPortalCreateEvent(e.getPlayer(), portal, pl));
						e.getPlayer().removeMetadata("portal", pl);
						locations.remove(e.getPlayer().getName());
					} else {
						sendMessage(e.getPlayer(), ChatColor.RED + "invalid portal, please select more blocks!");
					}
				} else {
					Block[] locs = new Block[2];
					locs[0] = e.getClickedBlock();
					locations.put(e.getPlayer().getName(), locs);
					sendMessage(e.getPlayer(), ChatColor.GREEN + "now right click a block to set your second pos!");
				}
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPsyics(BlockPhysicsEvent e) {
		if(e.isCancelled()) {
			return;
		}
		
		if(e.getBlock().getType() == Material.PORTAL) {
			for(Portal portal : pl.getConfiguration().getPortalConfig().getPortals().values()) {
				if(portal.getInnerBlocks().contains(e.getBlock())) {
					e.setCancelled(true);
					break;
				}
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(e.getPlayer().hasMetadata("portal")) {
			e.getPlayer().removeMetadata("portal", pl);
		}
		if(locations.containsKey(e.getPlayer().getName())) {
			locations.remove(e.getPlayer().getName());
		}
	}
	
	@EventHandler
	public void onQuit(PlayerKickEvent e) {
		if(e.getPlayer().hasMetadata("portal")) {
			e.getPlayer().removeMetadata("portal", pl);
		}
		if(locations.containsKey(e.getPlayer().getName())) {
			locations.remove(e.getPlayer().getName());
		}
	}

	private Block[] getFrameBlocks(Block pos1, Block pos2) {
		List<Block> blocks = new ArrayList<Block>();
		Block[] allblocks = getBlocks(pos1, pos2);
		for(Block block : allblocks) {
			if(block.getType() != Material.AIR && block.getType() != Material.PORTAL) {
				blocks.add(block);
			}
		}
		return blocks.toArray(new Block[blocks.size()]);
	}

	private Block[] getPortalBlocks(Block pos1, Block pos2) {
		List<Block> blocks = new ArrayList<Block>();
		Block[] allblocks = getBlocks(pos1, pos2);
		for(Block block : allblocks) {
			if(block.getType() == Material.AIR) {
				block.setType(Material.PORTAL);
				blocks.add(block);
			}
		}
		return blocks.toArray(new Block[blocks.size()]);
	}

	private Block[] getBlocks(Block pos1, Block pos2) {
		List<Block> blocks = new ArrayList<Block>();

		int xmax = Math.max(pos1.getX(), pos2.getX());
		int xmin = Math.min(pos1.getX(), pos2.getX());

		int ymax = Math.max(pos1.getY(), pos2.getY());
		int ymin = Math.min(pos1.getY(), pos2.getY());

		int zmax = Math.max(pos1.getZ(), pos2.getZ());
		int zmin = Math.min(pos1.getZ(), pos2.getZ());

		if(xmax == xmin) {
			//z-index
			for(int z = zmin; z < (zmax+1); z++) {
				for(int y = ymin; y < (ymax+1); y++) {
					Block block = new Location(pos1.getWorld(), pos1.getX(), y, z).getBlock();
					blocks.add(block);
				}
			}
		} else if(zmax == zmin) {
			//x-index
			for(int x = xmin; x < (xmax+1); x++) {
				for(int y = ymin; y < (ymax+1); y++) {
					Block block = new Location(pos1.getWorld(), x, y, pos1.getZ()).getBlock();
					blocks.add(block);
				}
			}
		}
		return blocks.toArray(new Block[blocks.size()]);
	}

	private FileConfiguration save(File f, Block[] frame, Block[] inner) {
		String name = f.getName().replace(".yml", "");
		FileConfiguration con = YamlConfiguration.loadConfiguration(f);
		try {
			con.set("name", name);
			List<String> innerblocks = new ArrayList<String>();
			for(Block blocky : inner) {
				blocky.setType(Material.PORTAL);
				String world = blocky.getWorld().getName();
				int x = blocky.getX();
				int y = blocky.getY();
				int z = blocky.getZ();
				String serialize = world+":"+x+":"+y+":"+z;
				innerblocks.add(serialize);
			}
			con.set("InnerBlocks", innerblocks.toArray());
			List<String> blockss = new ArrayList<String>();
			for(Block blocky : frame) {
				String world = blocky.getWorld().getName();
				int x = blocky.getX();
				int y = blocky.getY();
				int z = blocky.getZ();
				String serialize = world+":"+x+":"+y+":"+z;
				blockss.add(serialize);
			}
			con.set("blocks", blockss.toArray());	
			con.save(f);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}
}
