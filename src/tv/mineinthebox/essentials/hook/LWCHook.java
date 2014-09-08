package tv.mineinthebox.essentials.hook;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.griefcraft.lwc.LWC;
import com.griefcraft.model.Protection;

public class LWCHook {
	
	public static boolean isOwner(Player p, Block block) {
		LWC api = ((LWCPlugn)Bukkit.getPluginManager().getPlugin("LWC")).;
		return api.canAccessProtection(p, block);
	}
	
	public static void removeProtection(Block block) {
		LWC api = (LWC) Bukkit.getPluginManager().getPlugin("LWC");
		Protection protect = api.findProtection(block);
		protect.remove();
	}
}
