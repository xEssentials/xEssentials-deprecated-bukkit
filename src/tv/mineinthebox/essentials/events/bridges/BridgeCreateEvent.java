package tv.mineinthebox.essentials.events.bridges;

import java.io.File;
import java.util.LinkedList;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.instances.Bridge;

public class BridgeCreateEvent implements Listener {
	
	private final xEssentials pl;
	
	public BridgeCreateEvent(xEssentials pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onCreate(SignChangeEvent e) {
		if(e.getLine(0).equalsIgnoreCase("[bridge]")) {
			if(hasSignOnOtherSide(e.getBlock())) {
				Block sign1 = e.getBlock();
				Block sign2 = getOtherSignBlock(sign1);
				
				LinkedList<String> locs = new LinkedList<String>();
				
				int xmin = Math.min(sign1.getX(), sign2.getX());
				int xmax = Math.max(sign1.getX(), sign2.getX());
				int zmin = Math.min(sign1.getZ(), sign2.getZ());
				int zmax = Math.max(sign1.getZ(), sign2.getZ());
				
				if(xmin == xmax) {
					//z-index
					for(int z = zmin; z < zmax; z++) {
						Location loc = new Location(sign1.getWorld(),sign1.getX(), (sign1.getY()-1), z);
						Location loc2 = new Location(sign1.getWorld(),(sign1.getX()+1), (sign1.getY()-1), z);
						Location loc3 = new Location(sign1.getWorld(),(sign1.getX()-1), (sign1.getY()-1), z);
						if(loc.getBlock().getType() == Material.AIR) {
							String serialized = loc.getWorld().getName()+":"+loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ();
							locs.add(serialized);	
						}
						if(loc2.getBlock().getType() == Material.AIR) {
							String serialized = loc2.getWorld().getName()+":"+loc2.getBlockX()+":"+loc2.getBlockY()+":"+loc2.getBlockZ();
							locs.add(serialized);
						}
						if(loc3.getBlock().getType() == Material.AIR) {
							String serialized = loc3.getWorld().getName()+":"+loc3.getBlockX()+":"+loc3.getBlockY()+":"+loc3.getBlockZ();
							locs.add(serialized);
						}
					}
				} else if(zmin == zmax) {
					//x-index
					for(int x = xmin; x < xmax; x++) {
						Location loc = new Location(sign1.getWorld(),x, (sign1.getY()-1), sign1.getZ());
						Location loc2 = new Location(sign1.getWorld(),x, (sign1.getY()-1), (sign1.getZ()+1));
						Location loc3 = new Location(sign1.getWorld(),x, (sign1.getY()-1), (sign1.getZ()-1));
						if(loc.getBlock().getType() == Material.AIR) {
							String serialized = loc.getWorld().getName()+":"+loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ();
							locs.add(serialized);	
						}
						if(loc2.getBlock().getType() == Material.AIR) {
							String serialized = loc2.getWorld().getName()+":"+loc2.getBlockX()+":"+loc2.getBlockY()+":"+loc2.getBlockZ();
							locs.add(serialized);
						}
						if(loc3.getBlock().getType() == Material.AIR) {
							String serialized = loc3.getWorld().getName()+":"+loc3.getBlockX()+":"+loc3.getBlockY()+":"+loc3.getBlockZ();
							locs.add(serialized);
						}
					}
				}
				
				try {
					File f = new File(pl.getDataFolder() + File.separator + "misc" + File.separator + "bridges" + File.separator + UUID.randomUUID().toString() + ".yml");
					FileConfiguration con = YamlConfiguration.loadConfiguration(f);
					con.set("signBlock1", sign1.getWorld().getName()+":"+sign1.getX()+":"+sign1.getY()+":"+sign1.getZ());
					con.set("signBlock2", sign2.getWorld().getName()+":"+sign2.getX()+":"+sign2.getY()+":"+sign2.getZ());
					con.set("blocks", locs);
					con.save(f);
					
					Bridge bridge = new Bridge(f, con, pl);
					pl.getManagers().getBridgeManager().addBridge(bridge);
					
					e.setLine(0, ChatColor.DARK_PURPLE + "[Bridge]");
					e.getPlayer().sendMessage(ChatColor.GREEN + "you have successfully created a bridge!, and connected them.");
				} catch(Exception r) {
					r.printStackTrace();
				}
				
			} else {
				e.getPlayer().sendMessage(ChatColor.GREEN + "you have successfully placed a bridge sign, but the sign is not connected yet.");
				e.setLine(0, ChatColor.DARK_PURPLE + "[Bridge]");
			}
		}
	}
	
	private boolean hasSignOnOtherSide(Block block) {
		
		for(int x = block.getX(); x < (block.getX()+pl.getConfiguration().getMiscConfig().getMaxBridgeSize()); x++) {
			Block blocksign = block.getWorld().getBlockAt(x, block.getY(), block.getZ());
			if(blocksign.getType() == Material.SIGN_POST) {
				Sign sign = (Sign) blocksign.getState();
				if(sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_PURPLE + "[Bridge]")) {
					return true;
				}
			}
		}
		
		for(int x = block.getX(); x > (block.getX() - pl.getConfiguration().getMiscConfig().getMaxBridgeSize()); x--) {
			Block blocksign = block.getWorld().getBlockAt(x, block.getY(), block.getZ());
			if(blocksign.getType() == Material.SIGN_POST) {
				Sign sign = (Sign) blocksign.getState();
				if(sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_PURPLE + "[Bridge]")) {
		 			return true;
				}
			}
		}
		
		for(int z = block.getZ(); z < (block.getZ()+pl.getConfiguration().getMiscConfig().getMaxBridgeSize()); z++) {
			Block blocksign = block.getWorld().getBlockAt(block.getX(), block.getY(), z);
			if(blocksign.getType() == Material.SIGN_POST) {
				Sign sign = (Sign) blocksign.getState();
				if(sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_PURPLE + "[Bridge]")) {
					return true;
				}
			
			
			}
		}
		
		for(int z = block.getZ(); z > (block.getZ() - pl.getConfiguration().getMiscConfig().getMaxBridgeSize()); z--) {
			Block blocksign = block.getWorld().getBlockAt(block.getX(), block.getY(), z);
			if(blocksign.getType() == Material.SIGN_POST) {
				Sign sign = (Sign) blocksign.getState();
				if(sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_PURPLE + "[Bridge]")) {
					return true;
				}
			}
		}
		return false;
	}

	private Block getOtherSignBlock(Block block) {
		
		for(int x = block.getX(); x < (block.getX()+pl.getConfiguration().getMiscConfig().getMaxBridgeSize()); x++) {
			Block blocksign = block.getWorld().getBlockAt(x, block.getY(), block.getZ());
			if(blocksign.getType() == Material.SIGN_POST) {
				Sign sign = (Sign) blocksign.getState();
				if(sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_PURPLE + "[Bridge]")) {
					return sign.getBlock();
				}
			}
		}
		
		for(int x = block.getX(); x > (block.getX()-pl.getConfiguration().getMiscConfig().getMaxBridgeSize()); x--) {
			Block blocksign = block.getWorld().getBlockAt(x, block.getY(), block.getZ());
			if(blocksign.getType() == Material.SIGN_POST) {
				Sign sign = (Sign) blocksign.getState();
				if(sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_PURPLE + "[Bridge]")) {
					return sign.getBlock();
				}
			}
		}
		
		for(int z = block.getZ(); z < (block.getZ()+pl.getConfiguration().getMiscConfig().getMaxBridgeSize()); z++) {
			Block blocksign = block.getWorld().getBlockAt(block.getX(), block.getY(), z);
			if(blocksign.getType() == Material.SIGN_POST) {
				Sign sign = (Sign) blocksign.getState();
				if(sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_PURPLE + "[Bridge]")) {
					return sign.getBlock();
				}
			
			
			}
		}
		
		for(int z = block.getZ(); z > (block.getZ()-pl.getConfiguration().getMiscConfig().getMaxBridgeSize()); z--) {
			Block blocksign = block.getWorld().getBlockAt(block.getX(), block.getY(), z);
			if(blocksign.getType() == Material.SIGN_POST) {
				Sign sign = (Sign) blocksign.getState();
				if(sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_PURPLE + "[Bridge]")) {
					return sign.getBlock();
				}
			}
		}
		return null;
	}

}
