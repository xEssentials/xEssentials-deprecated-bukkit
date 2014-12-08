package tv.mineinthebox.bukkit.essentials.events.signs;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Sign;

import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;

public class DispenseSignEvent implements Listener {
	
	@EventHandler
	public void onCreate(SignChangeEvent e) {
		if(e.getLine(0).equalsIgnoreCase("[dispense]") || e.getLine(0).equalsIgnoreCase("[dispenser]")) {
			if(e.getPlayer().hasPermission(PermissionKey.SIGN_DISPENSER_CREATE.getPermission())) {
				
				Sign sign = (Sign) e.getBlock().getState().getData();
				
				if(e.getBlock().getRelative(sign.getAttachedFace()).getType() == Material.DISPENSER) {
					e.setLine(0, ChatColor.BLUE + "[Dispense]");
					e.getPlayer().sendMessage(ChatColor.GREEN + "you successfully placed a dispense sign!");	
				} else {
					e.getPlayer().sendMessage(ChatColor.RED + "your sign has to be attached to a dispenser!");
					e.setCancelled(true);
				}
			} else {
				e.getPlayer().sendMessage(ChatColor.RED + "you are not allowed to make dispense signs!");
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPower(BlockDispenseEvent e) {
			if(e.getBlock().getType() == Material.DISPENSER) {
				if(hasNearbySign(e.getBlock())) {
					org.bukkit.block.Sign sign = getSign(e.getBlock());
					if(sign.getLine(0).equalsIgnoreCase(ChatColor.BLUE + "[Dispense]")) {
						Dispenser disp = (Dispenser) e.getBlock().getState();
						for(ItemStack stack : disp.getInventory().getContents()) {
							if(stack != null) {
								ItemStack clone = stack.clone();
								clone.setAmount(64);
								disp.getInventory().addItem(clone);
							}
						}
					}
				}
			}
	}
	
	private org.bukkit.block.Sign getSign(Block block) {
		BlockFace[] faces = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
		for(BlockFace face : faces) {
			if(block.getRelative(face).getType() == Material.WALL_SIGN) {
				return (org.bukkit.block.Sign)block.getRelative(face).getState();
			}
		}
		return null;
	}
	
	private boolean hasNearbySign(Block block) {
		BlockFace[] faces = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
		for(BlockFace face : faces) {
			if(block.getRelative(face).getType() == Material.WALL_SIGN) {
				return true;
			}
		}
		return false;
	}

}
