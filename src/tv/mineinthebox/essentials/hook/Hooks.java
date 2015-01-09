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

	public static boolean isVaultEcoEnabled() {
		try {
			Class<?> vault = Class.forName("net.milkbowl.vault.economy.Economy");
			if(Bukkit.getServicesManager().getRegistration(vault) != null) {
				return true;
			} else {
				return false;
			}
		} catch(Exception e) {
			return false;
		}
	}
	
	public static boolean isVaultChatEnabled() {
		try {
			Class<?> vault = Class.forName("net.milkbowl.vault.chat.Chat");
			if(Bukkit.getServicesManager().getRegistration(vault) != null) {
				return true;
			} else {
				return false;
			}
		} catch(Exception e) {
			return false;
		}
	}
	
	public static boolean isVaultPermissionsEnabled() {
		try {
			Class<?> vault = Class.forName("net.milkbowl.vault.permission.Permission");
			if(Bukkit.getServicesManager().getRegistration(vault) != null) {
				return true;
			} else {
				return false;
			}
		} catch(Exception e) {
			return false;
		}
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

	public static boolean isLWCEnabled() {
		if(Bukkit.getPluginManager().isPluginEnabled("LWC")) {
			return true;
		}
		return false;
	}

	public static boolean isVotifierEnabled() {
		if(Bukkit.getPluginManager().isPluginEnabled("Votifier")) {
			return true;
		}
		return false;
	}

}
