package tv.mineinthebox.bukkit.essentials.hook;

import java.lang.reflect.Method;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import tv.mineinthebox.bukkit.essentials.xEssentials;
import tv.mineinthebox.bukkit.essentials.interfaces.XPlayer;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;


public class WorldGuardHook_reflection {

	/**
	 * @author xize
	 * @param gets the region location by using the region name
	 * @param gets the location by using the world
	 * @return Location
	 */
	public static Location getRegionLocation(String region, World w) {
		try {
			Object wg = Class.forName("com.sk89q.worldguard.bukkit.WorldGuardPlugin").cast(Bukkit.getPluginManager().getPlugin("WorldGuard"));
			Method m1 = wg.getClass().getMethod("getRegionManager", World.class);
			Object regionmanager = m1.invoke(wg, w);
			Method m2 = regionmanager.getClass().getMethod("getRegion", String.class);
			Object reg = m2.invoke(regionmanager, region);
			Object max = reg.getClass().getMethod("getMaximumPoint").invoke(reg);
			Object min = reg.getClass().getMethod("getMinimumPoint").invoke(reg);
			double x = (Double) ((Object)max.getClass().getMethod("getX").invoke(max));
			double z = (Double) ((Object)min.getClass().getMethod("getZ").invoke(min));
			return new Location(w, x, 70, z);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		/*
		WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
		ProtectedRegion reg = wg.getRegionManager(w).getRegion(region);
		Location loc = new Location(w, reg.getMaximumPoint().getX(), 70, reg.getMinimumPoint().getZ(), 0,0);
		return loc;
		*/
	}

	/**
	 * @author xize
	 * @param region name
	 * @param World
	 * @return boolean
	 */
	public static boolean isValidRegion(String region, World w) {
		try {
			Object wg = Class.forName("com.sk89q.worldguard.bukkit.WorldGuardPlugin").cast(Bukkit.getPluginManager().getPlugin("WorldGuard"));
			Method m1 = wg.getClass().getMethod("getRegionManager", World.class);
			Object regionmanager = m1.invoke(wg, w);
			Method m2 = regionmanager.getClass().getMethod("hasRegion", String.class);
			return (Boolean) m2.invoke(regionmanager, region);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @author xize
	 * @param checks whenever location is inside a region
	 * @return boolean
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isInRegion(Location loc) {
		try {
			Object wg = Class.forName("com.sk89q.worldguard.bukkit.WorldGuardPlugin").cast(Bukkit.getPluginManager().getPlugin("WorldGuard"));
			Method m1 = wg.getClass().getMethod("getRegionManager", World.class);
			Object regionmanager = m1.invoke(wg, loc.getWorld());
			Method m2 = regionmanager.getClass().getMethod("getApplicableRegions", Location.class);
			Object regionset = m2.invoke(regionmanager, loc);
			Set regions = (Set) regionset.getClass().getMethod("getRegions").invoke(regionset);
			for(Object obj : regions) {
				Method flag = obj.getClass().getSuperclass().getDeclaredMethod("getFlag", obj.getClass().getSuperclass()); //is getting stuck here, cant get the generic for this.
				Object state_mobspawning = flag.invoke(obj.getClass().getSuperclass(), DefaultFlag.MOB_SPAWNING);
				Object state_pvp = flag.invoke(obj.getClass().getSuperclass(), DefaultFlag.PVP);
				if(state_mobspawning == State.DENY || state_pvp == State.DENY) {
					return true;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
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

	/**
	 * @author xize
	 * @param sends the quit message for fake quiting
	 * @return void
	 */
	public static void sendVanishQuitMessage(Player p) {
		XPlayer xp = xEssentials.getManagers().getPlayerManager().getPlayer(p.getName());
		if(!xp.isVanished()) {
			if(Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
				if(isInRegion(p.getLocation())) {
					Bukkit.broadcastMessage(ChatColor.RED + "Whoosh!" + ChatColor.GRAY + " staff member " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has left the game safely!");
					xp.vanish();
					return;
				}
			}
			Bukkit.broadcastMessage(ChatColor.RED + "Whoosh!" + ChatColor.GRAY + " staff member " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has left the game in wild!");
			xp.vanish();
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
				if(isInRegion(p.getLocation())) {
					Bukkit.broadcastMessage(ChatColor.GRAY + "a safe staff member " + p.getDisplayName() + ChatColor.GRAY + " has been appeared!");
					xp.vanish();
					return;
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
		if(p.getPlayer().hasPermission("xEssentials.isStaff")) {
			if(isInRegion(p.getLocation())) {
				String message;
				if(p.getName().equalsIgnoreCase("Xeph0re")) {
					message = ChatColor.GRAY + "a safe Developer of xEssentials " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has been appeared!";	
				} else {
					message = ChatColor.GRAY + "a safe staff member " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has been appeared!";	
				}
				return message;
			}
			String message;
			if(p.getName().equalsIgnoreCase("Xeph0re")) {
				message = ChatColor.GRAY + "a wild Developer of xEssentials " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has been appeared!";	
			} else {
				message = ChatColor.GRAY + "a wild staff member " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has been appeared!";	
			}
			return message;
		} else {
			if(isInRegion(p.getLocation())) {
				String message = ChatColor.GRAY + "a safe " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has been appeared!";
				return message;
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
		if(p.getPlayer().hasPermission("xEssentials.isStaff")) {
			if(isInRegion(p.getLocation())) {
				String message = ChatColor.RED + "Whoosh!" + ChatColor.GRAY + " staff member " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has left the game safely!";
				return message;
			}
			String message = ChatColor.RED + "Whoosh!" + ChatColor.GRAY + " staff member " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has left the game in wild!";
			return message;
		} else {
			if(isInRegion(p.getLocation())) {
				String message = ChatColor.RED + "Whoosh! " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has left the game safely!";
				return message;
			}
			String message = ChatColor.RED + "Whoosh! " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has left the game in wild!";
			return message;
		}
	}

}
