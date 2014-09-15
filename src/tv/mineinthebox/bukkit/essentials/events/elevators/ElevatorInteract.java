package tv.mineinthebox.bukkit.essentials.events.elevators;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import tv.mineinthebox.bukkit.essentials.Configuration;
import tv.mineinthebox.bukkit.essentials.enums.PermissionKey;

public class ElevatorInteract implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			//up
			if(e.getClickedBlock().getType() == Material.SIGN_POST || e.getClickedBlock().getType() == Material.WALL_SIGN) {
				Sign sign = (Sign) e.getClickedBlock().getState();
				if(sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_PURPLE + "[Elevator]")) {
					for(int y = sign.getY()+1; y < sign.getWorld().getMaxHeight(); y++) {
						Location loc = sign.getLocation().clone();
						loc.setY(y);
						Block block = loc.getBlock();
						if(block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN) {
							Sign sign1 = (Sign) block.getState();
							if(sign1.getLine(0).equalsIgnoreCase(ChatColor.DARK_PURPLE + "[Elevator]")) {
								if(!isObstructed(sign1)) {
									e.getPlayer().sendMessage(ChatColor.GREEN + "teleporting you up!");
									if(!sign1.getLine(3).isEmpty()) {
										e.getPlayer().sendMessage(ChatColor.GREEN + "elevator level: " + ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', sign1.getLine(3)));
									}
									Location pdest = sign1.getLocation().clone();
									pdest.setYaw(e.getPlayer().getLocation().getYaw());
									pdest.setPitch(e.getPlayer().getLocation().getPitch());
									e.getPlayer().teleport(pdest);
									sign.getWorld().playEffect(sign.getLocation(), Effect.SMOKE, 10);
									sign.getWorld().playSound(sign.getLocation(), Sound.FIREWORK_LAUNCH, 1F, 1F);
								} else {
									e.getPlayer().sendMessage(ChatColor.RED + "the elevator is obstructed!");
								}
								e.setCancelled(true);
								return;
							}
						}
					}
					e.getPlayer().sendMessage(ChatColor.RED + "could not find sign to above.");
					e.setCancelled(true);
				}
			}
		} else if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
			//down
			if(e.getClickedBlock().getType() == Material.SIGN_POST || e.getClickedBlock().getType() == Material.WALL_SIGN) {
				Sign sign = (Sign) e.getClickedBlock().getState();
				if(sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_PURPLE + "[Elevator]")) {
					if(e.getPlayer().isSneaking() && e.getPlayer().hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
						return;
					}
					for(int y = sign.getY()-1; y > 0; y--) {
						Location loc = sign.getLocation().clone();
						loc.setY(y);
						Block block = loc.getBlock();
						if(block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN) {
							Sign sign1 = (Sign) block.getState();
							if(sign1.getLine(0).equalsIgnoreCase(ChatColor.DARK_PURPLE + "[Elevator]")) {
								if(!isObstructed(sign1)) {
									e.getPlayer().sendMessage(ChatColor.GREEN + "teleporting you down!");
									if(!sign1.getLine(3).isEmpty()) {
										e.getPlayer().sendMessage(ChatColor.GREEN + "elevator level: " + ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', sign1.getLine(3)));
									}
									Location pdest = sign1.getLocation().clone();
									pdest.setYaw(e.getPlayer().getLocation().getYaw());
									pdest.setPitch(e.getPlayer().getLocation().getPitch());
									e.getPlayer().teleport(pdest);
									sign.getWorld().playEffect(sign.getLocation(), Effect.SMOKE, 10);
									sign.getWorld().playSound(sign.getLocation(), Sound.FIREWORK_LAUNCH, 1F, 1F);
								} else {
									e.getPlayer().sendMessage(ChatColor.RED + "the elevator is obstructed!");
								}
								e.setCancelled(true);
								return;
							}
						}
					}
					e.getPlayer().sendMessage(ChatColor.RED + "could not find sign to beneath.");
					e.setCancelled(true);
				}
			}
		}
	}

	private boolean isObstructed(Sign sign) {
		int blockx = sign.getX() - (Configuration.getMiscConfig().getMaxElevatorObstructionSize()/2);
		int blockz = sign.getZ() - (Configuration.getMiscConfig().getMaxElevatorObstructionSize()/2);

		for(int x = 0; x < Configuration.getMiscConfig().getMaxElevatorObstructionSize(); x++) {
			for(int y = 0; y < Configuration.getMiscConfig().getMaxElevatorObstructionSize(); y++) {
				for(int z = 0; z < Configuration.getMiscConfig().getMaxElevatorObstructionSize(); z++) {
					Block block = new Location(sign.getWorld(), blockx+x, sign.getY()+y, blockz+z).getBlock();
					if(block.getType() != Material.AIR && block.getType() != Material.SIGN_POST && block.getType() != Material.WALL_SIGN) {
						block.setType(Material.GLOWSTONE);
						return true;
					}
				}
			}
		}

		return false;
	}

}
