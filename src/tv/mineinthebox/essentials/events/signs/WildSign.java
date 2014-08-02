package tv.mineinthebox.essentials.events.signs;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import tv.mineinthebox.essentials.enums.PermissionKey;

public class WildSign implements Listener {
	
	@EventHandler
	public void onCreate(SignChangeEvent e) {
		if(e.getLine(0).equalsIgnoreCase("[wild]")) {
			if(e.getPlayer().hasPermission(PermissionKey.SIGN_WILD_SIGN.getPermission())) {
				e.setLine(0, ChatColor.DARK_GRAY + "[wild]");
			} else {
				e.getBlock().breakNaturally();
				e.getPlayer().sendMessage(ChatColor.RED + "you are not allowed to make these kind of signs!");
			}
		}
	}
	
	private int xRadius = 10000;
	private int zRadius = 10000;
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getState() instanceof Sign) {
				Sign sign = (Sign) e.getClickedBlock().getState();
				if(sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_GRAY + "[wild]")) {
					if(e.getPlayer().hasPermission(PermissionKey.SIGN_WILD_SIGN_USE.getPermission())) {
						Random randx = new Random();
						Random randz = new Random();
						int x = randx.nextInt(xRadius)+e.getPlayer().getLocation().getBlockX();
						int z = randz.nextInt(zRadius)+e.getPlayer().getLocation().getBlockZ();
						Location loc = new Location(e.getPlayer().getWorld(), x, e.getPlayer().getWorld().getHighestBlockYAt(x, z), z);
						loc.getWorld().refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
						e.getPlayer().teleport(loc);
						e.getPlayer().sendMessage(ChatColor.GREEN + "you successfully has teleported to the wild!");	
					} else {
						e.getPlayer().sendMessage(ChatColor.RED + "you cannot use this sign!");
					}
				}
			}
		}
	}

}
