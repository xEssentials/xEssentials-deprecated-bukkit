package tv.mineinthebox.bukkit.essentials.hook;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.griefcraft.lwc.LWC;
import com.griefcraft.lwc.LWCPlugin;
import com.griefcraft.model.Protection;

public class LWCHook {
	
	public static boolean isOwner(Player p, Block block) {
		LWC api = ((LWCPlugin) Bukkit.getPluginManager().getPlugin("LWC")).getLWC();
		return api.canAccessProtection(p, block);
	}
	
	public static void removeProtection(Block block) {
		LWC api = ((LWCPlugin) Bukkit.getPluginManager().getPlugin("LWC")).getLWC();
		Protection protect = api.findProtection(block);
		protect.remove();
	}
}
