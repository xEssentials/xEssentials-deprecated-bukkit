package tv.mineinthebox.essentials.instances;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tv.mineinthebox.essentials.Configuration;
import tv.mineinthebox.essentials.xEssentials;

public class Portal implements Comparable<String> {

	private final FileConfiguration con;
	private final File f;

	public Portal(FileConfiguration con, File f) {
		this.con = con;
		this.f = f;
	}

	/**
	 * @author xize
	 * @param returns the portal name
	 * @return String
	 */
	public String getPortalName() {
		update();
		return con.getString("name");
	}

	/**
	 * @author xize
	 * @param returns all the blocks inside the portal
	 * @return Block
	 */
	public Block[] getInnerBlocks() {
		update();
		List<Block> blocks = new ArrayList<Block>();
		for(String s : con.getStringList("InnerBlocks")) {
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
	 * @param returns all the blocks around the inner blocks
	 * @return Block[]
	 */
	public Block[] getBlocks() {
		update();
		List<Block> blocks = new ArrayList<Block>();
		for(String s : con.getStringList("blocks")) {
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
	 * @param returns true if the portal is linked, else false.
	 * @return Boolean
	 */
	public boolean isLinked() {
		update();
		if(con.contains("linked")) {
			File f1 = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "portals" + File.separator + con.getString("linked") + ".yml");
			return f1.exists();
		}
		return false;
	}

	/**
	 * @author xize
	 * @param returns the linked portal
	 * @return Portal
	 */
	public Portal getLinkedPortal() {
		update();
		File f1 = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "portals" + File.separator + con.getString("linked") + ".yml");
		if(f1.exists()) {
			FileConfiguration con1 = YamlConfiguration.loadConfiguration(f1);
			return new Portal(con1, f1);	
		}
		return null;
	}

	/**
	 * @author xize
	 * @param unlink the portal or both, depending on the boolean given in.
	 * @param both - when true remove both, when false only remove this portal.
	 */
	public void unlinkPortal(Boolean both) {
		update();
		if(both) {
			Portal portal = getLinkedPortal();
			portal.unlinkPortal(false);	
		}
		con.set("linked", null);
		try {
			con.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @author xize
	 * @param name - the portal name where this portal needs to be linked to
	 * @param both - when true both portals will be changed, when false only this portal changes
	 * @throws NullPointerException - when the name is invalid.
	 */
	public void linkPortal(String name, Boolean both) throws Exception {
		update();
		File f1 = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "portals" + File.separator + name + ".yml");
		if(f1.exists()) {
			if(both) {
				con.set("linked", name);
				con.save(f);
				update();
				Portal portal = Configuration.getPortalConfig().getPortal(name);
				portal.linkPortal(getPortalName(), false);
			} else {
				con.set("linked", name);
				con.save(f);
				update();
			}
		} else {
			throw new NullPointerException("portal name does not exist!");
		}
	}

	/**
	 * @author xize
	 * @param returns true whenever the portal is closed, else false
	 * @return Boolean
	 */
	public boolean isClosed() {
		return (getInnerBlocks()[0].getType() == Material.IRON_FENCE || getInnerBlocks()[0].getType() == Material.AIR ? true : false);
	}

	/**
	 * @author xize
	 * @param close or open the portal
	 * @param bol - when true the portal will be closed, when false the portal will be opened
	 */
	public void setClosed(Boolean bol) {
		if(bol) {
			if(!isClosed()) {
				for(Block block : getInnerBlocks()) {
					block.setType(Material.IRON_FENCE);
				}	
			}
		} else {
			if(isClosed()) {
				for(Block block : getInnerBlocks()) {
					block.setType(Material.PORTAL);
				}	
			}
		}
	}

	/**
	 * @author xize
	 * @param removes the portal
	 */
	public void remove() {
		f.delete();
	}

	@Override
	public int compareTo(String s) {
		return s.compareTo(getPortalName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((con == null) ? 0 : con.hashCode());
		result = prime * result + ((f == null) ? 0 : f.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Portal other = (Portal) obj;
		if (con == null) {
			if (other.con != null)
				return false;
		} else if (!con.equals(other.con))
			return false;
		if (f == null) {
			if (other.f != null)
				return false;
		} else if (!f.equals(other.f))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Portal [con=" + con + ", f=" + f + ", getPortalName()="
				+ getPortalName() + ", getBlocks()="
				+ Arrays.toString(getBlocks()) + ", isLinked()=" + isLinked()
				+ ", getLinkedPortal()=" + getLinkedPortal() + ", hashCode()="
				+ hashCode() + ", getClass()=" + getClass() + ", toString()="
				+ super.toString() + "]";
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
