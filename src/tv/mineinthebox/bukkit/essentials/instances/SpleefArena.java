package tv.mineinthebox.bukkit.essentials.instances;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.bukkit.essentials.arenas.Minigame;

public class SpleefArena extends Minigame {
	
	private FileConfiguration con;
	
	public SpleefArena(File f, FileConfiguration con) throws NullPointerException {
		super(f, con);
	}

	/**
	 * @author xize
	 * @param returns the snow layer
	 * @return Block[]
	 */
	public Block[] getSnowLayer() {
		List<Block> blocks = new ArrayList<Block>();
		for(String s : con.getStringList("arena.snowlayers")) {
			String[] args = s.split(":");
			World w = Bukkit.getWorld(args[0]);
			int x = Integer.parseInt(args[1]);
			int y = Integer.parseInt(args[2]);
			int z = Integer.parseInt(args[3]);
			Block block = new Location(w, x, y, z).getBlock();
			blocks.add(block);
		}
		return blocks.toArray(new Block[blocks.size()]);
	}
	
	/**
	 * @author xize
	 * @param toggle the snow when the game is finished or is started.
	 * @param bol - when true all the air blocks will be replaced to snow, else snow will be replaced to AIR
	 */
	@SuppressWarnings("deprecation")
	public void toggleSnow(boolean bol) {
		if(bol) {
			for(Block block : getSnowLayer()) {
				if(block.getType() == Material.AIR) {
					block.setType(Material.SNOW_BLOCK);
					block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, Material.SNOW_BLOCK.getId());
				}
			}
		} else {
			for(Block block : getSnowLayer()) {
				if(block.getType() != Material.AIR) {
					block.setType(Material.AIR);
				}
			}
		}
	}
}
