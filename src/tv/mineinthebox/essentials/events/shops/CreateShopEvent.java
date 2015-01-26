package tv.mineinthebox.essentials.events.shops;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.XPlayer;

public class CreateShopEvent implements Listener {
	
	private final xEssentials pl;
	
	public CreateShopEvent(xEssentials pl) {
		this.pl = pl;
	}

	@EventHandler
	public void signChange(SignChangeEvent e) {
		if(e.isCancelled()) {return;}
		
		if(e.getLine(0).equalsIgnoreCase(ChatColor.stripColor(pl.getConfiguration().getShopConfig().getAdminShopPrefix()))) {
			if(e.getPlayer().hasPermission(PermissionKey.SIGN_SHOP.getPermission()) && e.getPlayer().hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
				if(isShopSign(e.getLines(), true)) {
					e.setLine(0, pl.getConfiguration().getShopConfig().getAdminShopPrefix());
					e.getPlayer().sendMessage(ChatColor.GREEN + "[Shop]" + ChatColor.GRAY + " you successfully made a admin shop!");
				} else {
					e.getPlayer().sendMessage(ChatColor.RED + "invalid sign!");
					e.setCancelled(true);
				}
			} else {
				e.getPlayer().sendMessage(ChatColor.RED + "you are not allowed to place admin shops!");
				e.setCancelled(true);
			}
		} else if(e.getLine(0).isEmpty()) {
			if(isShopSign(e.getLines(), false)) {
				if(e.getPlayer().hasPermission(PermissionKey.SIGN_SHOP.getPermission())) {
					if(isChestNearby(e.getBlock())) {
						e.setLine(0, e.getPlayer().getName());
						XPlayer xp = pl.getManagers().getPlayerManager().getPlayer(e.getPlayer().getName());
						xp.setShop(e.getBlock().getLocation(), getNearbyChest(e.getBlock()));
						e.getPlayer().sendMessage(ChatColor.GREEN + "[Shop] " + ChatColor.GRAY + "you successfully created your own shop!");
					} else {
						e.getPlayer().sendMessage(ChatColor.RED + "no chest nearby to connect to the shop!");
						e.setCancelled(true);
					}
				} else {
					e.getPlayer().sendMessage(ChatColor.RED + "you are not allowed to place sign shops!");
					e.setCancelled(true);
				}
			}
		}
	}

	private boolean isChestNearby(Block block) {
		BlockFace[] faces = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN};
		for(BlockFace face : faces) {
			if(block.getRelative(face).getType() == Material.CHEST) {
				Block chest = block.getRelative(face);
				for(BlockFace face2 : faces) {
					if(!chest.getRelative(face2).equals(block) && chest.getRelative(face2).getType() == Material.SIGN) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
	
	private Chest getNearbyChest(Block block) {
		BlockFace[] faces = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN};
		for(BlockFace face : faces) {
			if(block.getRelative(face).getType() == Material.CHEST) {
				Chest chest = (Chest) block.getRelative(face).getState();
				return chest;
			}
		}
		return null;
	}

	private boolean isShopSign(String[] lines, boolean isAdmin) {
		if(isAdmin) {
			if(lines[0].equalsIgnoreCase(ChatColor.stripColor(pl.getConfiguration().getShopConfig().getAdminShopPrefix()))) {
				if(lines[1].matches("[0-9]+")) {
					int amount = Integer.parseInt(lines[1]);
					if(amount <= 64) {
						System.out.print("the amount has a good limit of 64");
						if(lines[2].matches("(?i)^b \\d+ s \\d+$") || lines[2].matches("(?i)^b \\d+$") || lines[2].matches("(?i)^s \\d+$")) {
							if(lines[3].matches("([0-9]+):([0-9]+)")) {
								return true;
							} else {
								try {
									Material.valueOf(lines[3].toUpperCase().replaceAll(" ", "_"));
									return true;
								} catch(IllegalArgumentException e) {
									return false;
								}
							}
						}
					}
				}
			}
		} else {
			if(lines[1].matches("[0-9]+")) {
				int amount = Integer.parseInt(lines[1]);
				if(amount <= 64) {
					if(lines[2].matches("(?i)^b \\d+ s \\d+$") || lines[2].matches("(?i)^b \\d+$") || lines[2].matches("(?i)^s \\d+$")) {
						if(lines[3].matches("([0-9]+):([0-9]+)")) {
							return true;
						} else {
							try {
								Material.valueOf(lines[3].toUpperCase().replaceAll(" ", "_"));
								return true;
							} catch(IllegalArgumentException e) {
								return false;
							}
						}
					}
				}
			}
		}
		return false;
	}

}
