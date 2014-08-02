package tv.mineinthebox.essentials.instances;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import tv.mineinthebox.essentials.xEssentials;

public class Bridge {

	private final File f;
	private final FileConfiguration con;

	public Bridge(File f, FileConfiguration con) {
		this.f = f;
		this.con = con;
	}
	
	public String getId() {
		return f.getName().replace(".yml", "");
	}

	public Block[] getSigns() {
		update();
		Block[] blocks = new Block[2];
		
		String block1 = con.getString("signBlock1");
		String[] args1 = block1.split(":");
		World w1 = Bukkit.getWorld(args1[0]);
		int x1 = Integer.parseInt(args1[1]);
		int y1 = Integer.parseInt(args1[2]);
		int z1 = Integer.parseInt(args1[3]);
		blocks[0] = new Location(w1, x1, y1, z1).getBlock();
		
		String block2 = con.getString("signBlock2");
		String[] args2 = block2.split(":");
		World w2 = Bukkit.getWorld(args2[0]);
		int x2 = Integer.parseInt(args2[1]);
		int y2 = Integer.parseInt(args2[2]);
		int z2 = Integer.parseInt(args2[3]);
		blocks[1] = new Location(w2, x2, y2, z2).getBlock();
		return blocks;
	}
	
	public World getWorld() {
		return getSigns()[0].getWorld();
	}
	
	public Block[] getBridgeBlocks() {
		Block[] blocks = new Block[con.getStringList("blocks").size()];
		int i = 0;
		for(String s : con.getStringList("blocks")) {
			String[] split = s.split(":");
			World w = Bukkit.getWorld(split[0]);
			int x = Integer.parseInt(split[1]);
			int y = Integer.parseInt(split[2]);
			int z = Integer.parseInt(split[3]);
			Block block = new Location(w, x, y, z).getBlock();
			blocks[i] = block;
			i++;
		}
		return blocks;
	}
	
	public boolean isToggled() {
		update();
		if(getBridgeBlocks()[0].getType() == Material.WOOD) {
			return true;
		} else if(getBridgeBlocks()[0].getType() == Material.AIR) {
			return false;	
		}
		return false;
	}
	
	public void toggleBridge() {
		update();
		for(Block block : getBridgeBlocks()) {
			if(block.getType() == Material.AIR) {
				block.setType(Material.WOOD);
			} else {
				block.setType(Material.AIR);
			}
		}
	}
	
	public void remove() {
		if(isToggled()) {
			toggleBridge();
			f.delete();
		} else {
			f.delete();
		}
		if(xEssentials.getManagers().getBridgeManager().contains(this)) {
			xEssentials.getManagers().getBridgeManager().removeBridge(this);
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
