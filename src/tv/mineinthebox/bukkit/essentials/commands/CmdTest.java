package tv.mineinthebox.bukkit.essentials.commands;

import java.lang.reflect.Method;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tv.mineinthebox.bukkit.essentials.hook.Hooks;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class CmdTest {

	@SuppressWarnings("unused")
	public boolean execute(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("test")) {
			if(Hooks.isWorldGuardEnabled()) {
				Player p = (Player) sender;

				WorldGuardPlugin pl = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
				RegionManager manager = pl.getRegionManager(p.getWorld());
				ApplicableRegionSet regions = manager.getApplicableRegions(p.getLocation());
				Set<ProtectedRegion> reglist = regions.getRegions();

				try {
					Object wg = Class.forName("com.sk89q.worldguard.bukkit.WorldGuardPlugin").cast(Bukkit.getPluginManager().getPlugin("WorldGuard"));
					if(wg.equals(pl)) {
						sender.sendMessage(ChatColor.GREEN + "reflection to WorldGuardPlugin.java is succeeded both objects are identical!");
					} else {
						sender.sendMessage(ChatColor.RED + "WorldGuardPlugin is a other object than would be expected!");
					}
					
					Method method = wg.getClass().getMethod("getRegionManager", World.class);
					if(pl.getClass().getMethod("getRegionManager", World.class).getReturnType() == method.getReturnType()) {
						sender.sendMessage(ChatColor.GREEN + "reflection method matches with the return type of the worldguard call.");
					} else {
						sender.sendMessage(ChatColor.RED + "method did not matches");
					}
					Object manager2 = method.invoke(wg, p.getWorld());
					
					if(manager == manager2) {
						sender.sendMessage(ChatColor.GREEN + "region manager equals!");
					} else {
						sender.sendMessage(ChatColor.RED + "region manager does not equals!");
					}
					
					Method m1 = manager2.getClass().getMethod("getApplicableRegions", Location.class);
					Object regionset = m1.invoke(manager2, p.getLocation());
					
					if(regions == regionset) {
						sender.sendMessage(ChatColor.GREEN + "regionset matches!");
					} else {
						sender.sendMessage(ChatColor.RED + "regionset doesnt match!");
					}
					
					Object hash = regionset.getClass().getMethod("getRegions").invoke(regionset);
					sender.sendMessage(ChatColor.GREEN + "result is: " + hash.toString());
					
				} catch(Exception e) {
					e.printStackTrace();
				}
			} else {
				sender.sendMessage(ChatColor.RED + "cannot perform test.");
			}
		}
		return false;
	}
}
