package tv.mineinthebox.essentials.instances;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.xEssentials;

public class Gate {

	private final File f;
	private final FileConfiguration con;

	private Block sign;

	public Gate(File f, FileConfiguration con) {
		this.f = f;
		this.con = con;
	}

	public Block getSignBlock() {
		if(!(sign instanceof Block)) {
			String s = con.getString("signBlock");
			String[] split = s.split(":");

			World w = Bukkit.getWorld(split[0]);
			int x = Integer.parseInt(split[1]);
			int y = Integer.parseInt(split[2]);
			int z = Integer.parseInt(split[3]);

			if(w instanceof World) {
				this.sign = new Location(w, x, y, z).getBlock();
			}
		}
		return sign;
	}

	public World getWorld() {
		String s = con.getString("signBlock");
		String[] split = s.split(":");
		World w = Bukkit.getWorld(split[0]);
		return w;
	}

	public String getId() {
		return f.getName().replace(".yml", "");
	}

	public Block[] getInnerBlocks() {
		LinkedList<Block> innerBlocks = new LinkedList<Block>();
		for(String s : con.getStringList("blocks")) {
			String[] split = s.split(":");
			World w = Bukkit.getWorld(split[0]);
			int x = Integer.parseInt(split[1]);
			int y = Integer.parseInt(split[2]);
			int z = Integer.parseInt(split[3]);
			if(w instanceof World) {
				Block block = new Location(w, x, y, z).getBlock();
				if(block.getType() == Material.AIR || block.getType() == Material.FENCE) {
					innerBlocks.add(block);
				}
			}
		}
		return innerBlocks.toArray(new Block[innerBlocks.size()]);
	}

	public Block[] getFrameBlocks() {
		LinkedList<Block> frame = new LinkedList<Block>();
		for(String s : con.getStringList("blocks")) {
			String[] split = s.split(":");
			World w = Bukkit.getWorld(split[0]);
			int x = Integer.parseInt(split[1]);
			int y = Integer.parseInt(split[2]);
			int z = Integer.parseInt(split[3]);
			if(w instanceof World) {
				Block block = new Location(w, x, y, z).getBlock();
				if(block.getType() != Material.AIR || block.getType() != Material.FENCE) {
					frame.add(block);
				}
			}
		}
		return frame.toArray(new Block[frame.size()]);
	}

	public boolean isToggled() {
		update();
		if(getInnerBlocks().length > 0) {
			return false;
		}
		Block block = getInnerBlocks()[0];
		if(block.getType() == Material.AIR) {
			return false;
		} else {
			return true;
		}
	}

	public void toggleGate() {
		for(Block block : getInnerBlocks()) {
			if(block.getType() == Material.AIR) {
				block.setType(Material.FENCE);
			} else if(block.getType() == Material.FENCE) {
				block.setType(Material.AIR);
			}
		}
	}

	public void remove() {
		if(isToggled()) {
			f.delete();
		} else {
			toggleGate();
			f.delete();
		}
		if(xEssentials.getManagers().getGateManager().contains(this)) {
			xEssentials.getManagers().getGateManager().removeGate(this);
		}
	}

	public void update() {
		try {
			con.load(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
