package tv.mineinthebox.bukkit.essentials.events.gates;

import java.io.File;
import java.util.LinkedList;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.instances.Gate;

public class GateCreateEvent implements Listener {

	@EventHandler
	public void onCreate(SignChangeEvent e) {
		if(e.getBlock().getType() == Material.WALL_SIGN) {
			if(e.getLine(0).equalsIgnoreCase("[gate]")) {
				
				org.bukkit.material.Sign sign = (org.bukkit.material.Sign) e.getBlock().getState().getData();
				
				LinkedList<Block> pilar1 = new LinkedList<Block>();
				
				Block firstBlock = e.getBlock().getRelative(sign.getAttachedFace()).getRelative(BlockFace.DOWN);
				
				pilar1.add(firstBlock);
				
				for(int y = 0; y < Configuration.getMiscConfig().getMaxGateSize(); y++) {
					Block block = firstBlock.getLocation().add(0, y, 0).getBlock();
					if(block.getType() != Material.AIR) {
						pilar1.add(block);
					}
				}
				
				Block lastPilar = pilar1.get(pilar1.size()-1);
				
				pilar1.remove(lastPilar);
				
				BlockFace face = getAttachedFace(pilar1.get(pilar1.size()-1).getRelative(BlockFace.UP));
				
				if(!(face instanceof BlockFace)) {
					e.setCancelled(true);
					e.getPlayer().sendMessage(ChatColor.RED + "invalid gate shape!");
					return;
				}
				
				LinkedList<Block> horizontalPilar = new LinkedList<Block>();
				
				Block horizontalBlock = pilar1.get(pilar1.size()-1).getRelative(BlockFace.UP);
				horizontalPilar.add(horizontalBlock);
				
				for(int i = 0; i < Configuration.getMiscConfig().getMaxGateSize(); i++) {
					Block block = horizontalPilar.get(horizontalPilar.size()-1).getRelative(face);
					if(block.getType() != Material.AIR) {
						horizontalPilar.add(block);
					} else {
						break;
					}
				}
				
				LinkedList<Block> pilar2 = new LinkedList<Block>();
				
				Block pilar2Block = horizontalPilar.get(horizontalPilar.size()-1);
				pilar2.add(pilar2Block);
				
				for(int i = 0; i < (pilar1.size()-1); i++) {
					Block block = pilar2.get(pilar2.size()-1).getRelative(BlockFace.DOWN);
					if(block.getType() != Material.AIR) {
						pilar2.add(block);
					} else {
						e.setCancelled(true);
						e.getPlayer().sendMessage(ChatColor.RED + "invalid gate shape!");
						return;
					}
				}
				
				LinkedList<String> allBlocks = new LinkedList<String>();
				
				Block block1 = pilar1.get(0);
				Block block2 = horizontalPilar.get(horizontalPilar.size()-1);
				
				int xmin = Math.min(block1.getX(), block2.getX());
				int xmax = Math.max(block1.getX(), block2.getX());
				
				int ymin = Math.min(block1.getY(), block2.getY());
				int ymax = Math.max(block1.getY(), block2.getY());
				
				int zmin = Math.min(block1.getZ(), block2.getZ());
				int zmax = Math.max(block1.getZ(), block2.getZ());
				
				if(xmin == xmax) {
					//loop over the z index.
					for(int z = zmin; z <= zmax; z++) {
						for(int y = ymin; y <= ymax; y++) {
							Location loc = new Location(block1.getWorld(), block1.getX(), y, z);
							String serialize = loc.getWorld().getName()+":"+loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ();
							allBlocks.add(serialize);
						}
					}
				} else if(zmin == zmax) {
					//loop over the x index.
					for(int x = xmin; x <= xmax; x++) {
						for(int y = ymin; y <= ymax; y++) {
							Location loc = new Location(block1.getWorld(), x, y, block1.getZ());
							String serialize = loc.getWorld().getName()+":"+loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ();
							allBlocks.add(serialize);
						}
					}
				}
				
				UUID id = UUID.nameUUIDFromBytes((e.getBlock().getWorld().getName()+":"+e.getBlock().getX()+":"+e.getBlock().getY()+":"+e.getBlock().getZ()).getBytes());
				
				try {
					File f = new File(xEssentials.getPlugin().getDataFolder() + File.separator + "misc" + File.separator + "gates" + File.separator + id.toString()+".yml");
					FileConfiguration con = YamlConfiguration.loadConfiguration(f);
					con.set("signBlock", e.getBlock().getWorld().getName()+":"+e.getBlock().getX()+":"+e.getBlock().getY()+":"+e.getBlock().getZ());
					con.set("blocks", allBlocks.toArray());
					con.save(f); 
					e.setLine(0, ChatColor.DARK_PURPLE + "[Gate]");
					Gate gate = new Gate(f, con);
					xEssentials.getManagers().getGateManager().addGate(gate);
					e.getPlayer().sendMessage(ChatColor.GREEN + "you have successfully created a gate!");
				} catch(Exception r) {
					r.printStackTrace();
				}
			}
		}
	}
	
	public BlockFace getAttachedFace(Block block) {
		BlockFace[] faces = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
		for(BlockFace face : faces) {
			if(block.getRelative(face).getType() != Material.AIR) {
				return face;
			}
		}
		return null;
	}
}
