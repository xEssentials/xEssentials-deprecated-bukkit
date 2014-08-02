package tv.mineinthebox.essentials.hook;

import org.bukkit.Bukkit;

public class Hooks {
	
	public static boolean isWorldGuardEnabled() {
		if(Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
			return true;
		}
		return false;
	}
	
	public static boolean isWorldeditEnabled() {
		if(Bukkit.getPluginManager().isPluginEnabled("WorldEdit")) {
			return true;
		}
		return false;
	}
	
	public static boolean isVaultEnabled() {
		if(Bukkit.getPluginManager().isPluginEnabled("Vault")) {
			return true;
		}
		return false;
	}
	
	public static boolean isManCoEnabled() {
		if(Bukkit.getPluginManager().isPluginEnabled("ManCo")) {
			return true;
		}
		return false;
	}
	
	public static boolean isProtocolLibEnabled() {
		if(Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) {
			return true;
		}
		return false;
	}

}
