package tv.mineinthebox.essentials.events.signs;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import tv.mineinthebox.essentials.enums.PermissionKey;

public class FreeSign implements Listener {
	
	@EventHandler
	public void signCreate(SignChangeEvent e) {
		if(e.getLine(0).contains("[free]")) {
			if(e.getPlayer().hasPermission(PermissionKey.SIGN_FREE.getPermission())) {
				if(e.getLine(1).isEmpty()) {
					e.getPlayer().sendMessage(ChatColor.GREEN + "you have successfully placed a free sign, now right click the sign with your selected block");
					e.setLine(0, ChatColor.DARK_BLUE + e.getLine(0));
					return;
				} else {
					e.getPlayer().sendMessage(ChatColor.RED + "you have to leave the second line empty, and put the block with right click in the sign");
					e.setCancelled(true);
				}
			} else {
				e.getPlayer().sendMessage(ChatColor.RED + "you don't have permission to place these type signs");
				e.setCancelled(true);
			}
		} else {
			return;
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void interact(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.SIGN || e.getClickedBlock().getType() == Material.SIGN_POST || e.getClickedBlock().getType() == Material.WALL_SIGN) {
				Sign sign = (Sign) e.getClickedBlock().getState();
				if(sign.getLine(0).contains("[free]")) {
					if(!sign.getLine(1).isEmpty()) {
						try {
							Material mat = Material.valueOf(sign.getLine(1));
							ItemStack item = new ItemStack(mat);
							item.setTypeId(mat.getId());
							int bytevalue = Integer.parseInt(sign.getLine(2));
							item.setDurability((byte) bytevalue);
							item.setAmount(1);
							Location loc = e.getPlayer().getLocation();
							loc.setX(loc.getX() + 2);
							loc.setZ(loc.getZ() + 1);
							loc.setY(loc.getY() + 3);
							e.getPlayer().getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 100);
							e.getPlayer().getWorld().dropItemNaturally(loc, item);
						} catch(Exception r) {
							e.getPlayer().sendMessage(ChatColor.RED + "Error this item is not a vanilla item!, please show us your error in the console");
							r.printStackTrace();
						}
						
					} else {
						if(e.getClickedBlock().getType() == Material.SIGN || e.getClickedBlock().getType() == Material.SIGN_POST || e.getClickedBlock().getType() == Material.WALL_SIGN) {
							if(e.getPlayer().hasPermission(PermissionKey.SIGN_FREE.getPermission())) {
								//Sign sign = (Sign) e.getClickedBlock().getState();
								if(sign.getLine(0).contains("[free]")) {
									if(sign.getLine(1).isEmpty()) {
										ItemStack itemInHand = new ItemStack(e.getPlayer().getInventory().getItemInHand());
										sign.setLine(1, ""+itemInHand.getType());
										sign.setLine(2, ""+itemInHand.getData().getData());
										sign.update();
									}
								}
							}
						}
					}
				}
			}
		}
	}

}
