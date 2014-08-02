package tv.mineinthebox.essentials.events.spleef;



import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import tv.mineinthebox.essentials.xEssentials;

public class CreateSpleefArenaEvent implements Listener {

	public static HashMap<String, String> hash = new HashMap<String, String>();

	private static HashMap<String, Location> firstPos = new HashMap<String, Location>();
	private static HashMap<String, Location> secondPos = new HashMap<String, Location>();
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.isCancelled()) {
			return;
		}
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(hash.containsKey(e.getPlayer().getName())) {
				if(xEssentials.getManagers().getMinigameManager().isArena(hash.get(e.getPlayer().getName()))) {
					e.getPlayer().sendMessage(ChatColor.RED + "arena does already exist!");
					return;
				}
				if(firstPos.containsKey(e.getPlayer().getName())) {
					if(!secondPos.containsKey(e.getPlayer().getName())) {
						secondPos.put(e.getPlayer().getName(), e.getClickedBlock().getLocation());
						
						Block pos1 = firstPos.get(e.getPlayer().getName()).getBlock();
						Block pos2 = secondPos.get(e.getPlayer().getName()).getBlock();
						
						List<Block> frame = new ArrayList<Block>(Arrays.asList(getBlocks(pos1, pos2)));
						List<Block> innerBlocks = new ArrayList<Block>(Arrays.asList(getInnerBlocks(frame.toArray(new Block[frame.size()]))));
						
						List<String> blocks = new ArrayList<String>();
						
						for(Block block : innerBlocks) {
							blocks.add(block.getWorld().getName()+":"+block.getX()+":"+block.getY()+":"+block.getZ());
						}
						
						try {
							File f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "minigames" + File.separator + "spleef" + File.separator + hash.get(e.getPlayer().getName()) + ".yml");
							FileConfiguration con = YamlConfiguration.loadConfiguration(f);
							con.set("arena.name", hash.get(e.getPlayer().getName()));
							con.set("arena.isrunning", false);
							con.set("blocks", blocks.toArray());
							con.set("type", "SPLEEF");
						} catch(Exception r) {
							r.printStackTrace();
						}
						
					}
				} else {
					firstPos.put(e.getPlayer().getName(), e.getClickedBlock().getLocation());
					e.getPlayer().sendMessage(ChatColor.GREEN + "you selected " + ChatColor.GRAY + "pos1" + ChatColor.GREEN + " now right click again");
				}
				e.setCancelled(true);
			}
		}
	}
	
	/**
	 * @author xize
	 * @param pos1 - position block 1
	 * @param pos2 - position block 2
	 * @return Block[]
	 */
	private Block[] getBlocks(Block pos1, Block pos2) {
		List<Block> blocks = new ArrayList<Block>();
		World w = pos1.getWorld();
		int xmin = Math.min(pos1.getX(), pos2.getX());
		int xmax = Math.max(pos1.getX(), pos2.getX());
		int zmin = Math.min(pos1.getZ(), pos2.getZ());
		int zmax = Math.max(pos1.getZ(), pos2.getZ());
		for(int x = xmin; x < xmax; x++) {
			for(int z = zmin; z < zmax; z++) {
				Block block = new Location(w, x, pos1.getY(), z).getBlock();
				blocks.add(block);
			}
		}
		return blocks.toArray(new Block[blocks.size()]);
	}
	
	/**
	 * @author xize
	 * @param args
	 * @return Block[]
	 */
	private Block[] getInnerBlocks(Block[] args) {
		List<Block> blocks = new ArrayList<Block>();
		for(Block block : args) {
			if(block.getType() == Material.AIR) {
				blocks.add(block);
			}
		}
		return blocks.toArray(new Block[blocks.size()]);
	}

}
