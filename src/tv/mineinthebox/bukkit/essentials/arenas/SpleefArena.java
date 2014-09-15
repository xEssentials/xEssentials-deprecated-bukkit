package tv.mineinthebox.bukkit.essentials.arenas;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;

public class SpleefArena extends Minigame {
	
	public SpleefArena(File f, FileConfiguration con) {
		super(f, con);
	}
	
	/**
	 * @author xize
	 * @param returns all the blocks in the spleef arena.
	 * @return Block[]
	 */
	public Block[] getSpleefBlocks() {
		Block[] blocks = new Block[getValue().getStringList("blocks").size()];
		int i = 0;
		for(String s : getValue().getStringList("blocks")) {
			String[] split = s.split(":");
			World w = Bukkit.getWorld(split[0]);
			int x = Integer.parseInt(split[1]);
			int y = Integer.parseInt(split[2]);
			int z = Integer.parseInt(split[3]);
			blocks[i] = new Location(w, x, y, z).getBlock();
			i++;
		}
		return blocks;
	}
	
	/**
	 * @author xize
	 * @param returns true when there are AIR and SNOW blocks found, returns false if only air is found.
	 * @return Boolean
	 */
	public boolean isToggled() {
		ArrayList<Block> airs = new ArrayList<Block>();
		ArrayList<Block> snows = new ArrayList<Block>();
		for(Block block : getSpleefBlocks()) {
			if(block.getType() == Material.AIR) {
				airs.add(block);
			} else if(block.getType() == Material.SNOW_BLOCK) {
				snows.add(block);
			}
		}
		if(airs.isEmpty() && !snows.isEmpty()) {
			//its toggled because there are no air blocks found, but snow is.
			return true;
		} else if(snows.isEmpty() && !airs.isEmpty()) {
			//its not toggled because there are air blocks found and snow is not found
			return false;
		} else {
			//mixed, both air blocks and snowblocks found return true.
			return true;
		}
	}
	
	/**
	 * @author xize
	 * @param toggles the snow overlay based on the isToggled() flag.
	 */
	public void toggleSnow() {
		if(isToggled()) {
			for(Block block : getSpleefBlocks()) {
				block.setType(Material.AIR);
			}
		} else {
			for(Block block : getSpleefBlocks()) {
				block.setType(Material.SNOW_BLOCK);
			}
		}
	}
}
