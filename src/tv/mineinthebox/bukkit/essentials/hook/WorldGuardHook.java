package tv.mineinthebox.bukkit.essentials.hook;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.interfaces.XPlayer;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class WorldGuardHook {

	/**
	 * @author xize
	 * @param gets the region location by using the region name
	 * @param gets the location by using the world
	 * @return Location
	 */
	public static Location getRegionLocation(String region, World w) {
		WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
		ProtectedRegion reg = wg.getRegionManager(w).getRegion(region);
		Location loc = new Location(w, reg.getMaximumPoint().getX(), 70, reg.getMinimumPoint().getZ(), 0,0);
		return loc;
	}

	/**
	 * @author xize
	 * @param region name
	 * @param World
	 * @return boolean
	 */
	public static boolean isValidRegion(String region, World w) {
		WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
		if(wg.getRegionManager(w).hasRegion(region)) {
			return true;
		}
		return false;
	}

	/**
	 * @author xize
	 * @param sends the quit message for fake quiting
	 * @return void
	 */
	public static void sendVanishQuitMessage(Player p) {
		XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(p.getName());
		if(!xp.isVanished()) {
			if(Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
				WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
				for(ProtectedRegion region : wg.getRegionManager(p.getWorld()).getApplicableRegions(p.getLocation())) {
					if(region.getFlag(DefaultFlag.MOB_SPAWNING) == State.DENY) {
						Bukkit.broadcastMessage(ChatColor.RED + "Whoosh!" + ChatColor.GRAY + " staff member " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has left the game safely!");
						xp.vanish();
						return;
					}
				}
				Bukkit.broadcastMessage(ChatColor.RED + "Whoosh!" + ChatColor.GRAY + " staff member " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has left the game in wild!");
				xp.vanish();
			}
		} else {
			p.sendMessage(ChatColor.RED + "you are allready vanished so you can't fake quit, use /vanish fakejoin instead or /vanish");
		}
	}

	/**
	 * @author xize
	 * @param sends a fake join message
	 * @return void
	 */
	public static void sendVanishJoinMessage(Player p) {
		XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(p.getName());
		if(xp.isVanished()) {
			if(Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
				WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
				for(ProtectedRegion region : wg.getRegionManager(p.getWorld()).getApplicableRegions(p.getLocation())) {
					if(region.getFlag(DefaultFlag.MOB_SPAWNING) == State.DENY) {
						Bukkit.broadcastMessage(ChatColor.GRAY + "a safe staff member " + p.getDisplayName() + ChatColor.GRAY + " has been appeared!");
						xp.vanish();
						return;
					}
				}
				Bukkit.broadcastMessage(ChatColor.GRAY + "a wild staff member " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has been appeared!");
				xp.unvanish();
			}
		} else {
			p.sendMessage(ChatColor.RED + "you are allready are unvanished so you can't fake join, use /vanish fakequit instead or /vanish");
		}
	}

	/**
	 * @author xize
	 * @param sends normal join message and if they are joined in the wild or in the spawn
	 * @return String
	 */
	public static String sendJoinMessage(Player p ) {
		WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
		if(p.getPlayer().hasPermission("xEssentials.isStaff")) {
			for(ProtectedRegion region : wg.getRegionManager(p.getPlayer().getWorld()).getApplicableRegions(p.getPlayer().getLocation())) {
				if(region.getFlag(DefaultFlag.MOB_SPAWNING) == State.DENY) {
					String message;
					if(p.getName().equalsIgnoreCase("Xeph0re")) {
						message = ChatColor.GRAY + "a safe Developer of xEssentials " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has been appeared!";	
					} else {
						message = ChatColor.GRAY + "a safe staff member " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has been appeared!";	
					}
					return message;
				}
			}
			String message;
			if(p.getName().equalsIgnoreCase("Xeph0re")) {
				message = ChatColor.GRAY + "a wild Developer of xEssentials " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has been appeared!";	
			} else {
				message = ChatColor.GRAY + "a wild staff member " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has been appeared!";	
			}
			return message;
		} else {
			for(ProtectedRegion region : wg.getRegionManager(p.getPlayer().getWorld()).getApplicableRegions(p.getPlayer().getLocation())) {
				if(region.getFlag(DefaultFlag.MOB_SPAWNING) == State.DENY) {
					String message = ChatColor.GRAY + "a safe " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has been appeared!";
					return message;
				}
			}
			String message = ChatColor.GRAY + "a wild " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has been appeared!";
			return message;
		}
	}

	/**
	 * @author xize
	 * @param sends normal quit message and if they are joined in the wild or in the spawn
	 * @return String
	 */
	public static String sendQuitMessage(Player p) {
		WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
		if(p.getPlayer().hasPermission("xEssentials.isStaff")) {
			for(ProtectedRegion region : wg.getRegionManager(p.getPlayer().getWorld()).getApplicableRegions(p.getPlayer().getLocation())) {
				if(region.getFlag(DefaultFlag.MOB_SPAWNING) == State.DENY) {
					String message = ChatColor.RED + "Whoosh!" + ChatColor.GRAY + " staff member " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has left the game safely!";
					return message;
				}
			}
			String message = ChatColor.RED + "Whoosh!" + ChatColor.GRAY + " staff member " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has left the game in wild!";
			return message;
		} else {
			for(ProtectedRegion region : wg.getRegionManager(p.getPlayer().getWorld()).getApplicableRegions(p.getPlayer().getLocation())) {
				if(region.getFlag(DefaultFlag.MOB_SPAWNING) == State.DENY) {
					String message = ChatColor.RED + "Whoosh! " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has left the game safely!";
					return message;
				}
			}
			String message = ChatColor.RED + "Whoosh! " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has left the game in wild!";
			return message;
		}
	}

	/**
	 * @author xize
	 * @param checks whenever location is inside a region
	 * @return boolean
	 */
	public static boolean isInRegion(Location loc) {
		WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
		for(ProtectedRegion region : wg.getRegionManager(loc.getWorld()).getApplicableRegions(loc)) {
			if(region.getFlag(DefaultFlag.MOB_SPAWNING) == State.DENY || region.getFlag(DefaultFlag.PVP) == State.DENY) {
				//player has entered
				return true;
			}
		}
		return false;
	}


	/**
	 * @author xize
	 * @param p
	 * @param from
	 * @param to
	 * return void
	 */
	public static void sendRegionMessage(Player p, Chunk from, Chunk to) {
		if(from.getX() != to.getX() || from.getZ() != to.getZ()) {
			if(!isInRegion(from.getBlock(1, 1, 1).getLocation()) && isInRegion(to.getBlock(1, 1, 1).getLocation())) {
				p.getPlayer().sendMessage(ChatColor.GOLD + ".oO___[Entering safe zone]___Oo.");
			} else if(isInRegion(from.getBlock(1, 1, 1).getLocation()) && !isInRegion(to.getBlock(1, 1, 1).getLocation())) {
				p.getPlayer().sendMessage(ChatColor.GOLD + ".oO___[Leaving safe zone]___Oo.");
			}
		}
	}

}
