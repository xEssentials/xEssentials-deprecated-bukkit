package tv.mineinthebox.essentials.managers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import tv.mineinthebox.essentials.xEssentials;
import tv.mineinthebox.essentials.enums.LogType;
import tv.mineinthebox.essentials.enums.PermissionKey;
import tv.mineinthebox.essentials.interfaces.XPlayer;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class WorldGuardManager {

	private final StateFlag MONSTER_SPAWN = new MonsterFlag();

	/**
	 * @author xize
	 * @param gets the region location by using the region name
	 * @param gets the location by using the world
	 * @return Location
	 */
	public Location getRegionLocation(String region, World w) {
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
	public boolean isValidRegion(String region, World w) {
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
	public void sendVanishQuitMessage(Player p) {
		XPlayer xp = xEssentials.getPlugin().getManagers().getPlayerManager().getPlayer(p.getName());
		if(!xp.isVanished()) {
			sendQuitMessage(p);
		} else {
			p.sendMessage(ChatColor.RED + "you are allready vanished so you can't fake quit, use /vanish fakejoin instead or /vanish");
		}
	}

	/**
	 * @author xize
	 * @param sends a fake join message
	 * @return void
	 */
	public void sendVanishJoinMessage(Player p) {
		XPlayer xp = xEssentials.getPlugin().getManagers().getPlayerManager().getPlayer(p.getName());
		if(xp.isVanished()) {
			sendJoinMessage(p);
		} else {
			p.sendMessage(ChatColor.RED + "you are allready are unvanished so you can't fake join, use /vanish fakequit instead or /vanish");
		}
	}

	/**
	 * @author xize
	 * @param sends normal join message and if they are joined in the wild or in the spawn
	 * @return String
	 */
	public String sendJoinMessage(Player p ) {
		if(p.getPlayer().hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
			if(isSafeZone(p.getLocation())) {
				String message;
				if(p.getName().equalsIgnoreCase("Xeph0re")) {
					message = ChatColor.GRAY + "a safe Developer of xEssentials " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has been appeared!";	
				} else {
					message = ChatColor.GRAY + "a safe staff member " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has been appeared!";	
				}
				return message;
			} else {
				String message;
				if(p.getName().equalsIgnoreCase("Xeph0re")) {
					message = ChatColor.GRAY + "a wild Developer of xEssentials " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has been appeared!";	
				} else {
					message = ChatColor.GRAY + "a wild staff member " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has been appeared!";	
				}
				return message;
			}
		} else {
			if(isSafeZone(p.getLocation())) {
				String message = ChatColor.GRAY + "a safe " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has been appeared!";
				return message;
			} else {
				String message = ChatColor.GRAY + "a wild " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has been appeared!";
				return message;
			}
		}
	}

	/**
	 * @author xize
	 * @param sends normal quit message and if they are joined in the wild or in the spawn
	 * @return String
	 */
	public String sendQuitMessage(Player p) {
		if(p.getPlayer().hasPermission(PermissionKey.IS_ADMIN.getPermission())) {
			if(isSafeZone(p.getLocation())) {
				String message = ChatColor.RED + "Whoosh!" + ChatColor.GRAY + " staff member " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has left the game safely!";
				return message;
			} else {
				String message = ChatColor.RED + "Whoosh!" + ChatColor.GRAY + " staff member " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has left the game in wild!";
				return message;
			}
		} else {
			if(isSafeZone(p.getLocation())) {
				String message = ChatColor.RED + "Whoosh! " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has left the game safely!";
				return message;
			} else {
				String message = ChatColor.RED + "Whoosh! " + ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + " has left the game in wild!";
				return message;	
			}
		}
	}

	/**
	 * @author xize
	 * @param checks whenever location is inside a region
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public boolean isInRegion(Location loc) {
		WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
		try {
			Iterable<ProtectedRegion> regionset = (Iterable<ProtectedRegion>)wg.getRegionManager(loc.getWorld()).getClass().getMethod("getApplicableRegions", Location.class).invoke(wg.getRegionManager(loc.getWorld()), loc);
			return regionset.iterator().hasNext();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @author xize
	 * @param loc - gets a regionset by a location
	 * @return Iterable<protectionRegion>
	 */
	@SuppressWarnings("unchecked")
	public Iterable<ProtectedRegion> getRegion(Location loc) {
		WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
		try {
			Iterable<ProtectedRegion> regionset = (Iterable<ProtectedRegion>)wg.getRegionManager(loc.getWorld()).getClass().getMethod("getApplicableRegions", Location.class).invoke(wg.getRegionManager(loc.getWorld()), loc);
			return regionset;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean isFlagAllowed(StateFlag flag, Location loc) {
		WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
		try {
			Object obj = wg.getRegionManager(loc.getWorld()).getClass().getMethod("getApplicableRegions", Location.class).invoke(wg.getRegionManager(loc.getWorld()), loc);
			Method m1 = obj.getClass().getMethod("allows", StateFlag.class);
			boolean bol = (Boolean)m1.invoke(obj, flag);
			return bol;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isSafeZone(Location loc) {
		if(isInRegion(loc)) {
			if(!isFlagAllowed(DefaultFlag.MOB_SPAWNING, loc) || !isFlagAllowed(MONSTER_SPAWN, loc) && !isFlagAllowed(DefaultFlag.PVP, loc)) {
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
	public void sendRegionMessage(Player p, Chunk from, Chunk to) {
		if(from.getX() != to.getX() || from.getZ() != to.getZ()) {
			if(!isSafeZone(from.getBlock(1, 1, 1).getLocation()) && isSafeZone(to.getBlock(1, 1, 1).getLocation())) {
				p.getPlayer().sendMessage(ChatColor.GOLD + ".oO___[Entering safe zone]___Oo.");
			} else if(isSafeZone(from.getBlock(1, 1, 1).getLocation()) && !isSafeZone(to.getBlock(1, 1, 1).getLocation())) {
				p.getPlayer().sendMessage(ChatColor.GOLD + ".oO___[Leaving safe zone]___Oo.");
			}
		}
	}

	private void registerFlag(Flag<?> flag) throws Exception {
		Field f1 = DefaultFlag.class.getDeclaredField("flagsList");
		Field f2 = Field.class.getDeclaredField("modifiers");
		f2.setAccessible(true);
		f2.setInt(f1, f1.getModifiers() &~Modifier.FINAL);
		f1.setAccessible(true);

		Flag<?>[] flags = DefaultFlag.getFlags();

		List<Flag<?>> flaglist = Arrays.asList(flags);
		if(!flaglist.contains(flag)) {
			Flag<?>[] newflags = Arrays.copyOf(flags, flags.length+1);
			newflags[newflags.length-1] = flag;
			f1.set(null, newflags);	
		}

		f1.setAccessible(false);
		f2.setAccessible(false);
	}

	private void unregisterFlag(Flag<?> flag) throws Exception {
		Field f1 = DefaultFlag.class.getDeclaredField("flagsList");
		Field f2 = Field.class.getDeclaredField("modifiers");
		f2.setAccessible(true);
		f2.setInt(f1, f1.getModifiers() &~Modifier.FINAL);
		f1.setAccessible(true);

		Flag<?>[] flags = DefaultFlag.getFlags();

		List<Flag<?>> flaglist = Arrays.asList(flags);
		if(flaglist.contains(flag)) {
			flaglist.remove(flag);
			Flag<?>[] newflags = flaglist.toArray(new Flag<?>[flaglist.size()]);
			f1.set(null, newflags);	
		}

		f1.setAccessible(false);
		f2.setAccessible(false);
	}

	public void registerMonsterFlag() {
		try {
			registerFlag(MONSTER_SPAWN);
			xEssentials.log("added new worldguard flag: monster-spawn", LogType.INFO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void unregisterMonsterFlag() {
		try {
			unregisterFlag(MONSTER_SPAWN);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public StateFlag getMonsterFlag() {
		return MONSTER_SPAWN;
	}

	public void reloadWG() {
		WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
		wg.getRegionContainer().reload();
	}

	private class MonsterFlag extends StateFlag {

		public MonsterFlag() {
			super("monster-spawn", true);
		}

	}

}
