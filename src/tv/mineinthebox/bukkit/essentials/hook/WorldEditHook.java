package tv.mineinthebox.bukkit.essentials.hook;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class WorldEditHook {
	
	/**
	 * @author xize
	 * @param disables the worldedit //wand
	 * @return void
	 */
	public static void turnOffWand(Player player) {
		if(Bukkit.getPluginManager().isPluginEnabled("WorldEdit")) {

			WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");

			if(player.hasPermission("worldedit.wand")) {	
				if(player.getGameMode() == GameMode.SURVIVAL) {
					if(we.getSession(player).isToolControlEnabled()) {
						we.getSession(player).setToolControl(false);
						player.sendMessage(ChatColor.GOLD + ".oO___[Gamemode alert]___Oo.");
						player.sendMessage(ChatColor.GRAY + "your worldedit wand has been " + ChatColor.GREEN + "disabled!");
						player.sendMessage(ChatColor.GRAY + "if you want to renable it switch to creative or use /toggleeditwand");
					}
				} else if(player.getGameMode() == GameMode.CREATIVE) {
					if(!we.getSession(player).isToolControlEnabled()) {
						we.getSession(player).setToolControl(true);
						player.sendMessage(ChatColor.GOLD + ".oO___[Gamemode alert]___Oo.");
						player.sendMessage(ChatColor.GRAY + "your worldedit wand has been " + ChatColor.GREEN + "Enabled!");
						player.sendMessage(ChatColor.GRAY + "if you want to redisable it switch to survival or use /toggleeditwand");
					}
				}
			}
		}
	}

}
