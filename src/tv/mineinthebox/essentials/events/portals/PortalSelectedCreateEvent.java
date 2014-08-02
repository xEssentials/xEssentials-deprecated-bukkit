package tv.mineinthebox.essentials.events.portals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import tv.mineinthebox.essentials.events.customEvents.EssentialsPortalCreateEvent;
import tv.mineinthebox.essentials.instances.Portal;

public class PortalSelectedCreateEvent implements Listener {

	public static HashMap<String, String> hash = new HashMap<String, String>();
	
	private static HashMap<String, Block> firstPos = new HashMap<String, Block>();
	private static HashMap<String, Block> secondPos = new HashMap<String, Block>();
	
	
	@EventHandler
	public void onCreate(PlayerInteractEvent e) {
		if(e.isCancelled()) {
			return;
		}
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(hash.containsKey(e.getPlayer().getName())) {
				if(firstPos.containsKey(e.getPlayer().getName())) {
					if(!secondPos.containsKey(e.getPlayer().getName())) {
						secondPos.put(e.getPlayer().getName(), e.getClickedBlock());
						e.getPlayer().sendMessage(ChatColor.GRAY + "you selected the second block(lowest)");
						e.getPlayer().sendMessage(ChatColor.GREEN + "creating portal now...");
						Block[] blocks = getPortalBlocks(firstPos.get(e.getPlayer().getName()), secondPos.get(e.getPlayer().getName()));
						if(blocks.length < 4) {
							e.getPlayer().sendMessage(ChatColor.RED + "invalid portal, please select more blocks!");
							firstPos.remove(e.getPlayer().getName());
							secondPos.remove(e.getPlayer().getName());
							hash.remove(e.getPlayer().getName());
							return;
						}
						Block[] innerBlocks = getInnerPortalBlocks(blocks);
						Block[] portalFrameBlocks = getPortalFrameBlocks(blocks);
						String portal = hash.get(e.getPlayer().getName());
						try {
							File f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "portals" + File.separator + portal + ".yml");
							FileConfiguration con = YamlConfiguration.loadConfiguration(f);
							con.set("name", portal);
							List<String> innerblocks = new ArrayList<String>();
							for(Block blocky : innerBlocks) {
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
							for(Block blocky : portalFrameBlocks) {
								String world = blocky.getWorld().getName();
								int x = blocky.getX();
								int y = blocky.getY();
								int z = blocky.getZ();
								String serialize = world+":"+x+":"+y+":"+z;
								blockss.add(serialize);
							}
							con.set("blocks", blockss.toArray());	
							con.save(f);
							firstPos.remove(e.getPlayer().getName());
							secondPos.remove(e.getPlayer().getName());
							hash.remove(e.getPlayer().getName());
							e.getPlayer().sendMessage(ChatColor.GREEN + "portal " + portal + " successfully created ;-)");
							Bukkit.getPluginManager().callEvent(new EssentialsPortalCreateEvent(e.getPlayer(), new Portal(con, f)));
						} catch(Exception r) {
							r.printStackTrace();
						}
					}
					e.setCancelled(true);
				} else {
					firstPos.put(e.getPlayer().getName(), e.getClickedBlock());
					e.getPlayer().sendMessage(ChatColor.GRAY + "you selected the first block(highest)");
				}
			}
		}
	}
	
	@EventHandler
	public void psycic(BlockPhysicsEvent e) {
		if(!hash.isEmpty()) {
			if(e.getBlock().getType() == Material.PORTAL) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(hash.containsKey(e.getPlayer().getName())) {
			if(firstPos.containsKey(e.getPlayer().getName())) {
				firstPos.remove(e.getPlayer().getName());
			}
			if(secondPos.containsKey(e.getPlayer().getName())) {
				secondPos.remove(e.getPlayer().getName());
			}
			hash.remove(e.getPlayer().getName());
		}
	}
	
	@EventHandler
	public void onQuit(PlayerKickEvent e) {
		if(hash.containsKey(e.getPlayer().getName())) {
			if(firstPos.containsKey(e.getPlayer().getName())) {
				firstPos.remove(e.getPlayer().getName());
			}
			if(secondPos.containsKey(e.getPlayer().getName())) {
				secondPos.remove(e.getPlayer().getName());
			}
			hash.remove(e.getPlayer().getName());
		}
	}
	
	/**
	 * @author xize
	 * @param returns a specific list of all blocks calculated inside this portal.
	 * @param block1 - the highest block
	 * @param block2 - the lowest block
	 * @return Block[]
	 */
	public Block[] getPortalBlocks(Block block1, Block block2) {
		List<Block> blocks = new ArrayList<Block>();
		
		//calculates which location is its lowest or highest compared through 2 locations.
		//so we can loop from lowest to highest without problems, neither the problem with negative integers.
		int minx = Math.min(block1.getX(), block2.getX());
		int maxx = Math.max(block1.getX(), block2.getX());
		
		int miny = Math.min(block1.getY(), block2.getY());
		int maxy = Math.max(block1.getY(), block2.getY());
		
		int minz = Math.min(block1.getZ(), block2.getZ());
		int maxz = Math.max(block1.getZ(), block2.getZ());
		
		//checking the x axis, if this matches we need to work on the z axis.
		if(minx == maxx) {
			for(int z = minz; z < maxz; z++) {
				for(int y = miny; y < maxy; y++) {
					Block block = block1.getWorld().getBlockAt(block1.getX(), y, z);
					blocks.add(block);
				}
			}
		//checking the z axis, if this matches we need to work on the x axis.
		} else if(minz == maxz) {
			for(int x = minx; x < maxx; x++) {
				for(int y = miny; y < maxy; y++) {
					Block block = block1.getWorld().getBlockAt(x, y, block1.getZ());
					blocks.add(block);
				}
			}
		}
		return blocks.toArray(new Block[blocks.size()]);
	}
	
	/**
	 * @author xize
	 * @param returns all the blocks inside the portal frame, rather than the full frame ;-)
	 * @param portal - the block array which is extracted from pos1 and pos2
	 * @return Block[]
	 */
	public Block[] getInnerPortalBlocks(Block[] portal) {
		List<Block> blocks = new ArrayList<Block>();
		for(Block block : portal) {
			if(block.getType() == Material.AIR) {
				blocks.add(block); 
			}
		}
		return blocks.toArray(new Block[blocks.size()]);
	}
	
	/**
	 * @author xize 
	 * @param returns all the portal frame blocks
	 * @param portal - the block array which is extracted from pos1 and pos2
	 * @return Block[]
	 */
	public Block[] getPortalFrameBlocks(Block[] portal) {
		List<Block> blocks = new ArrayList<Block>();
		for(Block block : portal) {
			if(block.getType() != Material.AIR || block.getType() != Material.PORTAL) {
				blocks.add(block);
			}
		}
		return blocks.toArray(new Block[blocks.size()]);
	}

}
