package tv.mineinthebox.essentials.events.elevators;

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

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.EventTemplate;

public class ElevatorInteractEvent extends EventTemplate implements Listener {
	
	public ElevatorInteractEvent(xEssentials pl) {
		super(pl, "Elevator");
	}

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
									sendMessage(e.getPlayer(), ChatColor.GREEN + "teleporting you up!");
									if(!sign1.getLine(3).isEmpty()) {
										sendMessage(e.getPlayer(), ChatColor.GREEN + "elevator level: " + ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', sign1.getLine(3)));
									}
									Location pdest = sign1.getLocation().clone();
									pdest.setYaw(e.getPlayer().getLocation().getYaw());
									pdest.setPitch(e.getPlayer().getLocation().getPitch());
									e.getPlayer().teleport(pdest);
									sign.getWorld().playEffect(sign.getLocation(), Effect.SMOKE, 10);
									sign.getWorld().playSound(sign.getLocation(), Sound.FIREWORK_LAUNCH, 1F, 1F);
								} else {
									sendMessage(e.getPlayer(), ChatColor.RED + "the elevator is obstructed!");
								}
								e.setCancelled(true);
								return;
							}
						}
					}
					sendMessage(e.getPlayer(), ChatColor.RED + "could not find sign to above.");
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
									sendMessage(e.getPlayer(), ChatColor.GREEN + "teleporting you down!");
									if(!sign1.getLine(3).isEmpty()) {
										sendMessage(e.getPlayer(), ChatColor.GREEN + "elevator level: " + ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', sign1.getLine(3)));
									}
									Location pdest = sign1.getLocation().clone();
									pdest.setYaw(e.getPlayer().getLocation().getYaw());
									pdest.setPitch(e.getPlayer().getLocation().getPitch());
									e.getPlayer().teleport(pdest);
									sign.getWorld().playEffect(sign.getLocation(), Effect.SMOKE, 10);
									sign.getWorld().playSound(sign.getLocation(), Sound.FIREWORK_LAUNCH, 1F, 1F);
								} else {
									sendMessage(e.getPlayer(), ChatColor.RED + "the elevator is obstructed!");
								}
								e.setCancelled(true);
								return;
							}
						}
					}
					sendMessage(e.getPlayer(), ChatColor.RED + "could not find sign to beneath.");
					e.setCancelled(true);
				}
			}
		}
	}

	private boolean isObstructed(Sign sign) {
		int blockx = sign.getX() - (pl.getConfiguration().getMiscConfig().getMaxElevatorObstructionSize()/2);
		int blockz = sign.getZ() - (pl.getConfiguration().getMiscConfig().getMaxElevatorObstructionSize()/2);

		for(int x = 0; x < pl.getConfiguration().getMiscConfig().getMaxElevatorObstructionSize(); x++) {
			for(int y = 0; y < pl.getConfiguration().getMiscConfig().getMaxElevatorObstructionSize(); y++) {
				for(int z = 0; z < pl.getConfiguration().getMiscConfig().getMaxElevatorObstructionSize(); z++) {
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
